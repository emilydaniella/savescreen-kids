const API_URL = "http://localhost:8082/";

document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    const name = document.getElementById("nome");
    const telefone = document.getElementById("telefone");
    const email = document.getElementById("email");
    const password = document.getElementById("password");
    const message = document.getElementById("message");
    const togglePassword = document.getElementById("togglePassword");
    const submitButton = document.getElementById("botao-cadastro");

    form.addEventListener("submit", async function (event) {
        event.preventDefault();
        message.innerHTML = "";

        if (!validateForm()) {
            return;
        }

        submitButton.disabled = true;
        submitButton.innerHTML = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Cadastrando...';

        try {
            await registerUser(email.value, password.value, name.value, telefone.value);
            form.reset();
            setTimeout(() => {
                window.location.href = "/pages/usuario/editar-informacoes.html";
            }, 2000);
        } catch (error) {
            showMessage(error.message, "danger");
        } finally {
            submitButton.disabled = false;
            submitButton.innerHTML = 'Cadastrar';
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

    function validateForm() {
        let isValid = true;

        // Validação do nome
        if (name.value.trim().length < 3) {
            showMessage("O nome deve ter pelo menos 3 caracteres.", "danger");
            isValid = false;
        }

        // Validação do telefone
        /*   if (!/^\(\d{2}\) \d{5}-\d{4}$/.test(telefone.value.trim())) {
              showMessage("Digite um telefone válido no formato (99) 99999-9999.", "danger");
              isValid = false;
          } */

        // Validação do email
        if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.value.trim())) {
            showMessage("Digite um e-mail válido.", "danger");
            isValid = false;
        }

        // Validação da senha
        if (password.value.trim().length < 6) {
            showMessage("A senha deve ter pelo menos 6 caracteres.", "danger");
            isValid = false;
        }

        return isValid;
    }

    function showMessage(text, type) {
        message.innerHTML = `<div class="alert alert-${type} alert-dismissible fade show" role="alert">
            ${text}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>`;
    }

    async function registerUser(email, password, username, telefone) {
        try {
            const response = await fetch(`${API_URL}usuario`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    email,           // email no campo email
                    password,        // senha no campo password
                    username,
                    telefone
                })
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || "Erro ao cadastrar usuário.");
            }

            showMessage("Cadastro realizado com sucesso! Redirecionando para o login...", "success");
        } catch (error) {
            throw new Error(error.message || "Erro ao cadastrar usuário. Tente novamente mais tarde.");
        }
    }
});
