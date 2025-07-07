let userId = localStorage.getItem('userId');

if (!userId) {
    Swal.fire({
        title: 'Acesso restrito',
        text: 'Por favor, faça login para continuar.',
        icon: 'warning',
        confirmButtonColor: '#d6bdff',
    }).then(() => {
        window.location.replace('../usuario/login.html');
    });
}

let db = [];
let criancas = [];
let criancaSelecionada = null;

// Busca as crianças do usuário
function buscarCriancas(callback) {
    fetch(`http://localhost:8082/criancas/${userId}`)
        .then(response => {
            if (!response.ok) throw new Error("Erro ao buscar crianças");
            return response.json();
        })
        .then(data => callback(data))
        .catch(error => {
            console.error(error);
            Swal.fire("Erro", "Não foi possível carregar as crianças.", "error");
        });
}

// Preenche o select de crianças
function preencherSelectCriancas() {
    const select = document.getElementById('select-crianca');
    select.innerHTML = '<option value="">Selecione uma criança</option>';
    criancas.forEach((crianca) => {
        const option = document.createElement('option');
        option.value = crianca.id;
        option.textContent = crianca.nome;
        select.appendChild(option);
    });
    criancaSelecionada = criancas.length > 0 ? criancas[0].id : null;
}

// Atualiza a rotina ao trocar de criança
function onCriancaChange() {
    const select = document.getElementById('select-crianca');
    criancaSelecionada = select.value;
    listTarefas();
}

// Busca todas as tarefas do usuário
function findAllTasks(userId, callback) {
    // Primeiro tenta buscar do localStorage
    const tarefasLocal = JSON.parse(localStorage.getItem(`tarefas_${userId}`)) || [];
    if (tarefasLocal.length > 0) {
        callback(tarefasLocal);
    }

    // Tenta buscar da API
    fetch(`http://localhost:8082/atividades/${userId}`)
        .then(response => {
            if (!response.ok) throw new Error("Erro ao buscar tarefas");
            return response.json();
        })
        .then(data => {
            // Transforma os dados para o formato esperado pelo frontend
            const tarefasFormatadas = data.map(tarefa => ({
                id: tarefa.id,
                title: tarefa.nome,
                time: tarefa.horario,
                weekDay: getWeekDayFromHorario(tarefa.horario),
                userId: tarefa.idResponsavel,
                description: tarefa.descricao
            }));
            // Salva no localStorage para uso offline
            localStorage.setItem(`tarefas_${userId}`, JSON.stringify(tarefasFormatadas));
            callback(tarefasFormatadas);
        })
}

// Função auxiliar para extrair o dia da semana do horário
function getWeekDayFromHorario(horario) {
    // Aqui você pode implementar a lógica para extrair o dia da semana do horário
    // Por enquanto, vamos retornar um valor padrão
    return 0; // Domingo
}

// Lista as tarefas na tabela
function listTarefas() {
    let filtroTarefa = document.getElementById('filtro-tarefa')?.value.toLowerCase() || '';

    const tableTarefas = document.querySelector('#tableTarefas > tbody') || document.getElementById('tableTarefas');
    const tarefasFiltradas = db.filter((task) => {
        return (
            task.userId == userId &&
            (!criancaSelecionada || task.criancaId == criancaSelecionada) &&
            task.title.toLowerCase().includes(filtroTarefa)
        );
    });

    const grupoHorastarefas = ordenaArrayTarefas(tarefasFiltradas);

    // Limpa a tabela, mantendo o cabeçalho
    while (tableTarefas.rows.length > 1) {
        tableTarefas.deleteRow(1);
    }

    grupoHorastarefas.forEach((tarefas) => {
        const linhatarefas = tableTarefas.insertRow();
        const horaCell = linhatarefas.insertCell();

        horaCell.className = 'infoRotina';
        horaCell.innerHTML = tarefas[0].time;

        for (let index = 0; index < 7; index++) {
            const tarefa = tarefas.find((t) => t.weekDay === index);
            const adicionarCell = linhatarefas.insertCell(index + 1);

            adicionarCell.className = 'tarefas';

            if (!tarefa) {
                adicionarCell.innerHTML = `<p></p>`;
                continue;
            }

            adicionarCell.innerHTML = `
                <p>${tarefa.title}</p>
            `;
        }
    });
}

// Ordena as tarefas por horário e dia da semana
function ordenaArrayTarefas(db) {
    const diasSemana = [
        'Domingo', 'Segunda-feira', 'Terça-feira', 'Quarta-feira',
        'Quinta-feira', 'Sexta-feira', 'Sabado'
    ];

    const tarefasIndexDiaSemana = db.map((tarefa) => ({
        id: tarefa.id,
        title: tarefa.title,
        weekDay: diasSemana.indexOf(tarefa.weekDay),
        time: tarefa.time,
    }));

    const tarefasOrdenadas = tarefasIndexDiaSemana.sort((a, b) => {
        const horaA = new Date(`1970-01-01T${a.time}`);
        const horaB = new Date(`1970-01-01T${b.time}`);
        return horaA - horaB;
    });

    const gruposHoras = [];
    let grupoAtual = [];
    let horaAtual = null;

    for (const tarefa of tarefasOrdenadas) {
        if (horaAtual === null || tarefa.time === horaAtual) {
            grupoAtual.push(tarefa);
        } else {
            gruposHoras.push(grupoAtual);
            grupoAtual = [tarefa];
        }
        horaAtual = tarefa.time;
    }

    if (grupoAtual.length > 0) {
        gruposHoras.push(grupoAtual);
    }

    gruposHoras.forEach((grupo) => {
        grupo.sort((a, b) => a.weekDay - b.weekDay);
    });

    return gruposHoras;
}

// Inicializa a página
findAllTasks(userId, (data) => {
    db = data;
    buscarCriancas((lista) => {
        criancas = lista;
        preencherSelectCriancas();
        document.getElementById('select-crianca').addEventListener('change', onCriancaChange);
        listTarefas();
    });
});

// Adiciona event listener para o formulário de busca
document.getElementById('search-form')?.addEventListener('submit', function (event) {
    event.preventDefault();
    listTarefas();
}); 