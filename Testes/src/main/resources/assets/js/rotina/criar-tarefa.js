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

// Busca as crianças do usuário (adapte para seu backend se necessário)
function buscarCriancas(callback) {
    let criancasSalvas = JSON.parse(localStorage.getItem('criancas')) || [
        { id: '1', nome: 'Ana' },
        { id: '2', nome: 'Lucas' }
    ];
    callback(criancasSalvas);
}

// Preenche o select de crianças no formulário
function preencherSelectCriancas() {
    const select = document.getElementById('criancaTarefa');
    select.innerHTML = '';
    buscarCriancas((criancas) => {
        criancas.forEach((crianca) => {
            const option = document.createElement('option');
            option.value = crianca.id;
            option.textContent = crianca.nome;
            select.appendChild(option);
        });
    });
}

function init() {
    preencherSelectCriancas();

    let formularioTarefa = document.querySelector('form');

    formularioTarefa.addEventListener('submit', (e) => {
        e.preventDefault();

        let tituloTarefa = document.getElementById('tituloTarefa').value;
        let diaDaSemana = document.getElementById('diaDaSemana').value;
        let horaTarefa = document.getElementById('horaTarefa').value;
        let criancaId = document.getElementById('criancaTarefa').value;

        if (!formularioTarefa.checkValidity()) {
            displayMessage('Preencha o formulário corretamente.', 'warning');
            return;
        }

        if (tituloTarefa.trim().length < 5) {
            displayMessage(
                'O título da tarefa deve ter pelo menos 5 caracteres.',
                'warning'
            );
            return;
        }

        const tarefa = {
            title: tituloTarefa,
            weekDay: diaDaSemana,
            time: horaTarefa,
            userId: userId,
            criancaId: criancaId // Adiciona o id da criança
        };

        fetch(`http://localhost:8081/rotina/${userId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(tarefa),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Erro ao criar tarefa');
                }
                return response.json();
            })
            .then((data) => {
                displayMessage('Tarefa criada com sucesso!', 'success');
                formularioTarefa.reset();
            })
            .catch((error) => {
                console.error('Erro ao criar tarefa:', error);
                displayMessage('Erro ao criar tarefa.', 'error');
            });
    });
}

// Chame init() no onload do body ou ao carregar o JS