document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    const email = document.getElementById("email");
    const password = document.getElementById("password");
    const message = document.getElementById("message");
    const togglePassword = document.getElementById("togglePassword");

    form.addEventListener("submit", function (event) {
        event.preventDefault();
        message.innerHTML = "";

        if (!validateEmail() || !validatePassword()) {
            return;
        }

        loginUser(email.value, password.value);
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
            const data = await response.json();

            const user = [...data.Pessoas, ...data.Assinantes].find(u => u.Usuario === email && u.Senha === password);

            if (user) {
                showMessage("Login realizado com sucesso!", "success");
                setTimeout(() => {
                    window.location.href = "dashboard.html";
                }, 1000);
            } else {
                showMessage("E-mail ou senha incorretos.", "danger");
            }
        } catch (error) {
            showMessage("Erro ao conectar com o servidor.", "danger");
        }
    }

    async function registerUser(email, password, isSubscriber = false, plano = "") {
        try {
            const response = await fetch("jdbc:postgresql://localhost:5432/postgres");
            const data = await response.json();

            const newUser = isSubscriber
                ? { ID: Date.now(), Usuario: email, Plano: plano, Senha: password }
                : { ID: Date.now(), Usuario: email, Senha: password };

            const updatedData = isSubscriber
                ? { ...data, Assinantes: [...data.Assinantes, newUser] }
                : { ...data, Pessoas: [...data.Pessoas, newUser] };

            const saveResponse = await fetch("jdbc:postgresql://localhost:5432/postgres", {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(updatedData)
            });

            if (saveResponse.ok) {
                showMessage("Usuário cadastrado com sucesso!", "success");
            } else {
                showMessage("Erro ao cadastrar usuário.", "danger");
            }
        } catch (error) {
            showMessage("Erro ao conectar com o servidor.", "danger");
        }
    }
});
