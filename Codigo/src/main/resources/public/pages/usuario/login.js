const API_URL = "http://localhost:8082/";

document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    const email = document.getElementById("email");
    const password = document.getElementById("password");
    const message = document.getElementById("message");
    const togglePassword = document.getElementById("togglePassword");
    const submitButton = document.getElementById("botao-login");

    // Verifica se já existe um  válido
    /*  const user = localStorage.getItem('user');
     /* if (user) {
         window.location.href = "/pages/usuario/editar-informacoes.html";
     } */

    form.addEventListener("submit", async function (event) {
        event.preventDefault();
        message.innerHTML = "";

        if (!validateEmail() || !validatePassword()) {
            return;
        }

        // Desabilita o botão durante o envio
        submitButton.disabled = true;
        submitButton.innerHTML = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Entrando...';

        try {
            await loginUser(email.value, password.value);
        } catch (error) {
            showMessage(error.message, "danger");
        } finally {
            // Reabilita o botão
            submitButton.disabled = false;
            submitButton.innerHTML = 'Entrar';
        }
    });

    togglePassword.addEventListener("click", function () {
        if (password.type === "password") {
            password.type = "text";
            togglePassword.classList.add("fa-eye-slash");
        } else {
            password.type = "password";
            togglePassword.classList.remove("fa-eye-slash");
        }
    });

    function validateEmail() {
        if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.value.trim())) {
            showMessage("Digite um e-mail válido.", "danger");
            return false;
        }
        return true;
    }

    function validatePassword() {
        if (password.value.trim().length < 6) {
            showMessage("A senha deve ter pelo menos 6 caracteres.", "danger");
            return false;
        }
        return true;
    }

    function showMessage(text, type) {
        message.innerHTML = `<div class="alert alert-${type} alert-dismissible fade show" role="alert">
            ${text}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>`;
    }

    async function loginUser(email, password) {
        try {
            console.log('=== INÍCIO DO PROCESSO DE LOGIN ===');
            console.log('Dados de entrada:', {
                email: email,
                password: password,
                emailTrimmed: email.trim(),
                passwordTrimmed: password.trim()
            });

            // Primeiro, busca o usuário pelo email
            console.log('Fazendo requisição GET para:', `${API_URL}usuario`);
            const response = await fetch(`${API_URL}usuario`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json"
                }
            });

            if (!response.ok) {
                console.error('Erro na resposta da API:', response.status, response.statusText);
                throw new Error("Erro ao buscar usuários");
            }

            const users = await response.json();
            console.log('Resposta da API (usuários):', users);

            // Procura o usuário com email e senha correspondentes
            const user = users.find(u => {
                const emailMatch = u.password === email; // password na API é o email
                const senhaMatch = u.email === password; // email na API é a senha

                console.log('Comparando usuário:', {
                    userId: u.id,
                    userName: u.username,
                    userEmail: u.password, // password na API é o email
                    userSenha: u.email,    // email na API é a senha
                    inputEmail: email,
                    inputPassword: password,
                    emailMatch: emailMatch,
                    senhaMatch: senhaMatch
                });

                return emailMatch && senhaMatch;
            });

            if (user) {
                console.log('Usuário encontrado:', user);
                // Armazena os dados do usuário no localStorage
                localStorage.setItem('userId', user.id);
                localStorage.setItem('user', JSON.stringify(user));

                showMessage("Login realizado com sucesso! Redirecionando...", "success");

                setTimeout(() => {
                    window.location.href = "/pages/usuario/editar-informacoes.html";
                }, 1000);
            } else {
                console.log('Nenhum usuário encontrado com as credenciais fornecidas');
                throw new Error("E-mail ou senha incorretos.");
            }
        } catch (error) {
            console.error('Erro no login:', error);
            throw new Error(error.message || "Erro ao conectar com o servidor. Tente novamente mais tarde.");
        } finally {
            console.log('=== FIM DO PROCESSO DE LOGIN ===');
        }
    }
});
