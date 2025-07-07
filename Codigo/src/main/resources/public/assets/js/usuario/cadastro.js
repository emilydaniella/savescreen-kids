let userId = localStorage.getItem('userId');

if (userId) {
    window.location.replace('./editar-informacoes.html');
}

let db = [];

findAllUsers((data) => {
    db = data;
    init();
});

function init() {
    let formularioCadastro = document.querySelector('form');
    let campoNome = document.getElementById('nome');
    let campoTelefone = document.getElementById('telefone');
    let campoEmail = document.getElementById('email');
    let campoPassword = document.getElementById('password');
    let campoConfirmPassword = document.getElementById('confirm-password');
    let message = document.getElementById('message');

    // validado em tempo real
    campoNome.addEventListener('input', () => {
        if (campoNome.value.trim().length < 10) {
            displayMessage('O nome deve ter pelo menos 10 caracteres.', 'warning');
        } else {
            clearMessage();
        }
    });

    campoTelefone.addEventListener('input', () => {
        if (campoTelefone.value.length !== 15) {
            displayMessage('Telefone deve estar no formato: (XX) XXXXX-XXXX.', 'warning');
        } else {
            clearMessage();
        }
    });

    campoPassword.addEventListener('input', validarSenha);
    campoConfirmPassword.addEventListener('input', validarSenha);

    function validarSenha() {
        const senha = campoPassword.value.trim();
        const confirmar = campoConfirmPassword.value.trim();

        if (senha.length < 8) {
            displayMessage('A senha deve ter pelo menos 8 caracteres.', 'warning');
        } else if (!senha.match(/[0-9]/)) {
            displayMessage('A senha deve conter pelo menos um número.', 'warning');
        } else if (!senha.match(/[a-zA-Z]/)) {
            displayMessage('A senha deve conter pelo menos uma letra.', 'warning');
        } else if (!senha.match(/[!@#$%^&*(),.?":{}|<>]/)) {
            displayMessage('A senha deve conter pelo menos um caractere especial.', 'warning');
        } else if (confirmar && senha !== confirmar) {
            displayMessage('As senhas não coincidem.', 'warning');
        } else {
            clearMessage();
        }
    }

    formularioCadastro.addEventListener('submit', (e) => {
        e.preventDefault();

        const nome = campoNome.value.trim();
        const telefone = campoTelefone.value;
        const email = campoEmail.value;
        const senha = campoPassword.value.trim();
        const confirmarSenha = campoConfirmPassword.value;

        if (!formularioCadastro.checkValidity()) {
            displayMessage('Preencha o formulário corretamente.', 'warning');
            return;
        }

        if (nome.length < 10) {
            displayMessage('O nome do usuário deve ter pelo menos 10 caracteres.', 'danger');
            return;
        }

        if (telefone.length !== 15) {
            displayMessage('Insira um número de telefone válido com DDD (formato: (XX) XXXXX-XXXX).', 'danger');
            return;
        }

        if (senha.length < 8) {
            displayMessage('A senha deve conter pelo menos 8 caracteres.', 'warning');
            return;
        }

        if (!senha.match(/[0-9]/)) {
            displayMessage('A senha deve conter pelo menos um número.', 'warning');
            return;
        }

        if (!senha.match(/[a-zA-Z]/)) {
            displayMessage('A senha deve conter pelo menos uma letra.', 'warning');
            return;
        }

        if (!senha.match(/[!@#$%^&*(),.?":{}|<>]/)) {
            displayMessage('A senha deve conter pelo menos um caractere especial.', 'warning');
            return;
        }

        if (senha !== confirmarSenha) {
            displayMessage('As senhas não se coincidem.', 'warning');
            return;
        }

        const usuarioEncontrado = db.find((user) => user.email === email);

        if (usuarioEncontrado) {
            displayMessage('O e-mail informado já está cadastrado.', 'warning');
            return;
        }

        let encryptedPassword = CryptoJS.SHA256(senha).toString();

        let usuario = {
            name: nome,
            phone: telefone,
            email: email,
            password: encryptedPassword,
        };

        createUser(usuario);
        enviarEmailCriacaoConta(email, nome, telefone);
    });

    function displayMessage(msg, type) {
        message.innerHTML = `<p class="text-${type}">${msg}</p>`;
        message.classList.add('show');
    }

    function clearMessage() {
        message.innerHTML = '';
        message.classList.remove('show');
    }
}
