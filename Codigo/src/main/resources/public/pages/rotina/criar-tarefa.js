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
    // Primeiro tenta buscar do localStorage
    const criancasLocal = JSON.parse(localStorage.getItem('criancas')) || [];
    if (criancasLocal.length > 0) {
        callback(criancasLocal);
        return;
    }

    // Se não houver no localStorage, tenta buscar da API
    fetch(`http://localhost:8082/criancas`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao buscar crianças");
            }
            return response.json();
        })
        .then(data => {
            // Filtra apenas as crianças do usuário atual
            const criancasDoUsuario = data.filter(crianca => crianca.responsavelId === userId);
            // Salva no localStorage para uso offline
            localStorage.setItem('criancas', JSON.stringify(criancasDoUsuario));
            callback(criancasDoUsuario);
        })
        .catch(error => {
            console.error(error);
            // Se houver erro na API, usa os dados do localStorage
            const criancasLocal = JSON.parse(localStorage.getItem('criancas')) || [];
            callback(criancasLocal);
            Swal.fire({
                title: 'Atenção',
                text: 'Não foi possível conectar ao servidor. Usando dados salvos localmente.',
                icon: 'warning',
                confirmButtonColor: '#d6bdff'
            });
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
    // Busca as atividades da API
    fetch(`http://localhost:8082/atividades/${criancaId}`)
        .then(response => response.json())
        .then(data => {
            // Garante que estamos trabalhando com um array
            const atividades = Array.isArray(data) ? data : [];
            return atividades;
        })
        .catch(error => {
            console.error('Erro ao buscar atividades:', error);
            return [];
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
function atualizarTabelaAtividades(atividades) {
    const tbody = document.getElementById('atividadesBody');
    tbody.innerHTML = '';

    // Verifica se atividades é um array antes de usar forEach
    if (!Array.isArray(atividades)) {
        console.error('Atividades não é um array:', atividades);
        return;
    }

    atividades.forEach(atividade => {
        const row = tbody.insertRow();
        const horaCell = row.insertCell();
        horaCell.className = 'infoRotina';
        horaCell.textContent = atividade.hora;

        const cell = row.insertCell();
        cell.className = 'atividade';
        cell.innerHTML = `
            <p>${atividade.titulo}</p>
            <button onclick="excluirAtividade(${atividade.id})" class="btn-excluir">
                <i class="fas fa-trash"></i>
            </button>
        `;
    });

    // Adiciona linha de botões para adicionar tarefas
    const linhaButtons = tbody.insertRow();
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
            // Remove do localStorage primeiro
            const atividadesLocal = JSON.parse(localStorage.getItem(`atividades_${criancaSelecionada}`)) || [];
            const novasAtividades = atividadesLocal.filter(a => a.id !== atividadeId);
            localStorage.setItem(`atividades_${criancaSelecionada}`, JSON.stringify(novasAtividades));

            // Tenta remover da API
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
                    Swal.fire({
                        title: 'Atenção',
                        text: 'Não foi possível conectar ao servidor. A atividade foi removida apenas localmente.',
                        icon: 'warning',
                        confirmButtonColor: '#d6bdff'
                    });
                    buscarAtividades(criancaSelecionada);
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
        nome: nome,
        responsavelId: userId,
        id: Date.now().toString() // Gera um ID temporário
    };

    // Adiciona ao localStorage primeiro
    const criancasLocal = JSON.parse(localStorage.getItem('criancas')) || [];
    criancasLocal.push(novaCrianca);
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
            novaCrianca.id = data.id;
            const criancasLocal = JSON.parse(localStorage.getItem('criancas')) || [];
            const index = criancasLocal.findIndex(c => c.id === novaCrianca.id);
            if (index !== -1) {
                criancasLocal[index] = novaCrianca;
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