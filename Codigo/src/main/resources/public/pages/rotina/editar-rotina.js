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

let criancas = [];
let criancaSelecionada = null;
let atividades = [];

// Busca as crianças do usuário
function buscarCriancas(callback) {
    fetch(`http://localhost:8082/criancas`)
        .then(response => {
            if (!response.ok) throw new Error("Erro ao buscar crianças");
            return response.json();
        })
        .then(data => {
            // Garante que estamos trabalhando com um array
            const criancasArray = Array.isArray(data) ? data : [data];
            // Filtra apenas as crianças do usuário atual
            const criancasDoUsuario = criancasArray.filter(crianca => crianca.idResponsavel === parseInt(userId));
            callback(criancasDoUsuario);
        })
        .catch(error => {
            console.error(error);
            Swal.fire("Erro", "Não foi possível carregar as crianças.", "error");
        });
}

// Atualiza a lista de crianças cadastradas
function atualizarListaCriancas() {
    const lista = document.getElementById('listaCriancas');
    lista.innerHTML = '';
    criancas.forEach(crianca => {
        const li = document.createElement('li');
        li.textContent = crianca.nome;
        lista.appendChild(li);
    });
}

// Busca as atividades da criança selecionada
function buscarAtividades(criancaId) {
    fetch(`http://localhost:8082/atividades`)
        .then(response => {
            if (!response.ok) throw new Error("Erro ao buscar atividades");
            return response.json();
        })
        .then(data => {
            // Garante que estamos trabalhando com um array
            const atividadesArray = Array.isArray(data) ? data : [data];
            // Filtra apenas as atividades da criança selecionada
            atividades = atividadesArray.filter(atividade => atividade.criancaId === parseInt(criancaId));
            atualizarTabelaAtividades();
        })
        .catch(error => {
            console.error(error);
            Swal.fire("Erro", "Não foi possível carregar as atividades.", "error");
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

    // Se houver apenas uma criança, seleciona ela automaticamente
    if (criancas.length === 1) {
        select.value = criancas[0].id;
        criancaSelecionada = criancas[0].id;
        buscarAtividades(criancaSelecionada);
    }
}

// Atualiza a rotina ao trocar de criança
function onCriancaChange() {
    const select = document.getElementById('select-crianca');
    criancaSelecionada = select.value;
    if (criancaSelecionada) {
        document.getElementById('message').innerHTML = '';
        buscarAtividades(criancaSelecionada);
    } else {
        document.getElementById('message').innerHTML = '<div class="alert alert-warning">Selecione uma criança para editar sua rotina</div>';
    }
}

// Atualiza a tabela de atividades
function atualizarTabelaAtividades() {
    const table = document.getElementById('tableTarefas');
    // Limpa a tabela mantendo o cabeçalho
    while (table.rows.length > 1) {
        table.deleteRow(1);
    }

    // Agrupa atividades por horário
    const atividadesPorHorario = {};
    atividades.forEach(atividade => {
        if (!atividadesPorHorario[atividade.hora]) {
            atividadesPorHorario[atividade.hora] = [];
        }
        atividadesPorHorario[atividade.hora].push(atividade);
    });

    // Ordena os horários
    const horariosOrdenados = Object.keys(atividadesPorHorario).sort();

    // Preenche a tabela
    horariosOrdenados.forEach(horario => {
        const row = table.insertRow();
        const horaCell = row.insertCell();
        horaCell.className = 'infoRotina';
        horaCell.textContent = horario;

        // Cria células para cada dia da semana
        const diasSemana = ['Domingo', 'Segunda-feira', 'Terça-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sábado'];
        diasSemana.forEach(dia => {
            const cell = row.insertCell();
            const atividade = atividadesPorHorario[horario].find(a => a.diaSemana === dia);
            if (atividade) {
                cell.innerHTML = `
                    <div class="atividade">
                        <p>${atividade.titulo}</p>
                        <button onclick="excluirAtividade(${atividade.id})" class="btn-excluir">
                            <i class="fas fa-trash"></i>
                        </button>
                    </div>
                `;
            }
        });
    });

    // Adiciona linha de botões para adicionar tarefas
    const linhaButtons = table.insertRow();
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
}

// Exclui uma atividade
function excluirAtividade(atividadeId) {
    Swal.fire({
        title: 'Tem certeza?',
        text: "Esta ação não poderá ser revertida!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d6bdff',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sim, excluir!',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(`http://localhost:8082/atividades/${atividadeId}`, {
                method: 'DELETE'
            })
                .then(response => {
                    if (!response.ok) throw new Error("Erro ao excluir atividade");
                    Swal.fire('Excluído!', 'A atividade foi excluída com sucesso.', 'success');
                    buscarAtividades(criancaSelecionada);
                })
                .catch(error => {
                    console.error(error);
                    Swal.fire('Erro!', 'Não foi possível excluir a atividade.', 'error');
                });
        }
    });
}

// Inicializa a página
function init() {
    buscarCriancas((lista) => {
        criancas = lista;
        preencherSelectCriancas();
        atualizarListaCriancas();
        document.getElementById('select-crianca').addEventListener('change', onCriancaChange);

        // Se não houver crianças, mostra mensagem
        if (criancas.length === 0) {
            document.getElementById('message').innerHTML = '<div class="alert alert-warning">Você ainda não tem crianças cadastradas. Cadastre uma criança para criar sua rotina.</div>';
        }
    });
}

// Adiciona event listener para o formulário de cadastro de criança
document.getElementById('formCrianca').addEventListener('submit', function (e) {
    e.preventDefault();
    const nome = document.getElementById('nomeCrianca').value.trim();
    if (!nome) return;

    const novaCrianca = {
        username: nome,
        dataNascimento: new Date().toISOString().split('T')[0],
        idResponsavel: parseInt(userId)
    };

    // Adiciona ao localStorage primeiro
    const criancasLocal = JSON.parse(localStorage.getItem('criancas')) || [];
    const criancaLocal = {
        ...novaCrianca,
        id: Date.now().toString()
    };
    criancasLocal.push(criancaLocal);
    localStorage.setItem('criancas', JSON.stringify(criancasLocal));

    // Tenta salvar na API
    fetch('http://localhost:8082/criancas', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(novaCrianca)
    })
        .then(response => {
            if (!response.ok) throw new Error("Erro ao cadastrar criança");
            return response.json();
        })
        .then(data => {
            // Atualiza o ID com o retornado pela API
            const criancasLocal = JSON.parse(localStorage.getItem('criancas')) || [];
            const index = criancasLocal.findIndex(c => c.username === novaCrianca.username);
            if (index !== -1) {
                criancasLocal[index].id = data.id.toString();
                localStorage.setItem('criancas', JSON.stringify(criancasLocal));
            }

            Swal.fire({
                title: 'Sucesso!',
                text: 'Criança cadastrada com sucesso!',
                icon: 'success',
                confirmButtonColor: '#d6bdff'
            }).then(() => {
                document.getElementById('formCrianca').reset();
                buscarCriancas((lista) => {
                    criancas = lista;
                    preencherSelectCriancas();
                    atualizarListaCriancas();
                });
            });
        })
        .catch(error => {
            console.error(error);
            Swal.fire({
                title: 'Atenção',
                text: 'Não foi possível conectar ao servidor. A criança foi cadastrada apenas localmente.',
                icon: 'warning',
                confirmButtonColor: '#d6bdff'
            }).then(() => {
                document.getElementById('formCrianca').reset();
                buscarCriancas((lista) => {
                    criancas = lista;
                    preencherSelectCriancas();
                    atualizarListaCriancas();
                });
            });
        });
});
