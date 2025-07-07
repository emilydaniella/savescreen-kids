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

var db = [];
var criancas = [];
var criancaSelecionada = null;

// Busca as crianças do usuário (adapte para seu backend se necessário)
function buscarCriancas(callback) {
    let criancasSalvas = JSON.parse(localStorage.getItem('criancas')) || [
        { id: '1', nome: 'Ana' },
        { id: '2', nome: 'Lucas' }
    ];
    callback(criancasSalvas);
}

// Preenche o select de crianças
function preencherSelectCriancas() {
    const select = document.getElementById('select-crianca');
    select.innerHTML = '';
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

<<<<<<< HEAD
findAllTasks((data) => {
    db = data;
    buscarCriancas((lista) => {
        criancas = lista;
        preencherSelectCriancas();
        document.getElementById('select-crianca').addEventListener('change', onCriancaChange);
        listTarefas();
    });
});
=======
fetch(`http://localhost:5432/8081/${userId}`)
    .then((response) => {
        if (!response.ok) {
            throw new Error('Erro ao buscar tarefas');
        }
        return response.json();
    })
    .then((data) => {
        db = data;
        listTarefas();
    })
    .catch((error) => {
        console.error('Erro:', error);
        Swal.fire({
            title: 'Erro',
            text: 'Não foi possível carregar as tarefas.',
            icon: 'error',
            confirmButtonColor: '#d6bdff',
        });
    });
>>>>>>> 6f617af7306d26193276c24af70d235cbfc05104

function listTarefas() {
    let filtroTarefa = document
        .getElementById('filtro-tarefa')
        .value.toLowerCase();

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

    grupoHorastarefas.map((tarefas) => {
        const linhatarefas = tableTarefas.insertRow();
        const horaCell = linhatarefas.insertCell();

        horaCell.className = 'infoRotina';
        horaCell.innerHTML = tarefas[0].time;

        for (let index = 0; index < 7; index++) {
            const tarefa = tarefas.find((t) => t.weekDay === index);
            const adicionarCell = linhatarefas.insertCell(index + 1);

            adicionarCell.className = 'tarefas';

            if (!tarefa) {
                adicionarCell.innerHTML += ` <p></p>`;
                continue;
            }

            adicionarCell.innerHTML += `
                <p>${tarefa.title}</p>
            `;
        }
    });
}

function ordenaArrayTarefas(db) {
    const diasSemana = [
        'Domingo',
        'Segunda-feira',
        'Terça-feira',
        'Quarta-feira',
        'Quinta-feira',
        'Sexta-feira',
        'Sabado',
    ];

    const tarefasIndexDiaSemana = db.map((tarefa) => {
        return {
            _id: tarefa._id,
            title: tarefa.title,
            weekDay: diasSemana.indexOf(tarefa.weekDay),
            time: tarefa.time,
        };
    });

    const tarefasOrdenadas = tarefasIndexDiaSemana.sort(
        (anterior, posterior) => {
            const horanterior = new Date(`1970-01-01T${anterior.time}`);
            const horaposterior = new Date(`1970-01-01T${posterior.time}`);

            if (horanterior < horaposterior) {
                return -1;
            } else if (horanterior > horaposterior) {
                return 1;
            } else {
                return 0;
            }
        }
    );

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
        grupo.sort((tarefa1, tarefa2) => {
            const diaSemana1 = tarefa1.weekDay;
            const diaSemana2 = tarefa2.weekDay;

            if (diaSemana1 < diaSemana2) {
                return -1;
            } else if (diaSemana1 > diaSemana2) {
                return 1;
            } else {
                return 0;
            }
        });
    });

    return gruposHoras;
}

document
    .getElementById('search-form')
    .addEventListener('submit', function (event) {
        event.preventDefault();
        listTarefas();
    });