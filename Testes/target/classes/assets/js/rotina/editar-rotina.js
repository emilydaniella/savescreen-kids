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
var criancas = []; // Lista de crianças do usuário
var criancaSelecionada = null;

<<<<<<< HEAD
// Função para buscar as crianças do usuário (exemplo, adapte para seu backend)
function buscarCriancas(callback) {
    // Exemplo: substitua por chamada AJAX/Fetch real
    // Aqui, simula crianças cadastradas no localStorage
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
    criancas.forEach((crianca, idx) => {
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

// Busca tarefas e inicializa
findAllTasks((data) => {
=======
findAllTasks(userId, (data) => {
>>>>>>> 6f617af7306d26193276c24af70d235cbfc05104
    db = data;
    buscarCriancas((lista) => {
        criancas = lista;
        preencherSelectCriancas();
        document.getElementById('select-crianca').addEventListener('change', onCriancaChange);
        listTarefas();
    });
});

function findAllTasks(userId, callback) {
    fetch(`http://localhost:8081/rotina/${userId}`)
        .then(response => {
            if (!response.ok) throw new Error("Erro ao buscar tarefas");
            return response.json();
        })
        .then(data => callback(data))
        .catch(error => {
            console.error(error);
            Swal.fire("Erro", "Não foi possível carregar as tarefas.", "error");
        });
}

function deleteTask(taskId) {
    fetch(`http://localhost:5432/rotina/${taskId}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao excluir tarefa");
            }
        })
        .catch(error => {
            console.error(error);
            Swal.fire("Erro", "Não foi possível excluir a tarefa.", "error");
        });
}

function listTarefas() {
<<<<<<< HEAD
    let tableTarefas = document.getElementById('tableTarefas');
    // Limpa a tabela, mantendo o cabeçalho
    while (tableTarefas.rows.length > 1) {
        tableTarefas.deleteRow(1);
    }

    // Filtra tarefas pelo usuário e pela criança selecionada
    const tarefasPeloUserId = db.filter((task) => {
        return task.userId == userId && (!criancaSelecionada || task.criancaId == criancaSelecionada);
    });

    const grupoHorastarefas = ordenaArrayTarefas(tarefasPeloUserId);
=======
    const tarefasPeloUserId = db.filter((task) => task.userId == userId);

    const grupoHorastarefas = ordenaArrayTarefas(tarefasPeloUserId);
    let tableTarefas = document.getElementById('tableTarefas');
>>>>>>> 6f617af7306d26193276c24af70d235cbfc05104

    grupoHorastarefas.map((tarefas, index_array) => {
        const linhatarefas = tableTarefas.insertRow();
        const horaCell = linhatarefas.insertCell();

        horaCell.className = 'infoRotina';
        horaCell.innerHTML = tarefas[0].time;

        for (let index = 0; index < 7; index++) {
            const tarefa = tarefas.find((t) => t.weekDay === index);
            const adicionarCell = linhatarefas.insertCell(index + 1);

            adicionarCell.className = 'tarefas';

            if (!tarefa) {
                adicionarCell.innerHTML += `<p></p>`;
                continue;
            }

            adicionarCell.innerHTML += `
<<<<<<< HEAD
                <button class="excluir" id=${tarefa._id}>
=======
                <button class="excluir" id=${tarefa.id}>
>>>>>>> 6f617af7306d26193276c24af70d235cbfc05104
                    <i class="fa-solid fa-trash" style="color: #d80032;"></i>
                </button>
                <p>${tarefa.title}</p>
            `;
        }
    });

    const linhaButtons = tableTarefas.insertRow();
    const buttonCell = linhaButtons.insertCell(0);
    buttonCell.className = 'infoRotina';

    for (let i = 1; i <= 7; i++) {
        const adicionarCell = linhaButtons.insertCell(i);
        adicionarCell.className = 'adicionar';
        adicionarCell.innerHTML = `
            <button onclick="window.location.href = './criar-tarefa.html'">
                <img src="../../assets/images/rotina/botao.png" alt="botão para adicionar tarefa">
            </button>
        `;
    }

    init();
}

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

function init() {
    let btnDelete = document.getElementsByClassName('excluir');
    for (let i = 0; i < btnDelete.length; i++) {
        btnDelete[i].addEventListener('click', function () {
            let taskId = this.id;
            deleteTask(taskId);
<<<<<<< HEAD

            setTimeout(() => {
                location.reload();
            }, 500);
=======
            setTimeout(() => location.reload(), 500);
>>>>>>> 6f617af7306d26193276c24af70d235cbfc05104
        });
    }
}