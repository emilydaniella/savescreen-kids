document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    const email = document.getElementById("email");
    const password = document.getElementById("password");
    const message = document.getElementById("message");
    const togglePassword = document.getElementById("togglePassword");

    form.addEventListener("submit", async function (event) {
        event.preventDefault();
        message.innerHTML = "";

        if (!validateEmail() || !validatePassword()) {
            return;
        }

        await loginUser(email.value, password.value);
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
        message.innerHTML = `<p class='text-${type}'>${text}</p>`;
    }

    async function loginUser(email, password) {
        try {
            const response = await fetch("jdbc:postgresql://localhost:5432/postgres");
            if (!response.ok) {
                throw new Error("Erro ao buscar usuários");
            }

            const users = await response.json();
            const user = users.find(user => user.email === email && user.senha === password);

            if (user) {
                showMessage("Login realizado com sucesso!", "success");
                setTimeout(() => {
                    window.location.href = "dashboard.html"; // Redireciona para a dashboard
                }, 1000);
            } else {
                showMessage("E-mail ou senha incorretos.", "danger");
            }
        } catch (error) {
            showMessage("Erro ao conectar com o servidor.", "danger");
        }
    }
});
