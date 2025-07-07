document.addEventListener("DOMContentLoaded", function () {
    const userId = localStorage.getItem("userId");

    if (!userId) {
        Swal.fire({
            title: "Acesso restrito",
            text: "Por favor, faça login para continuar.",
            icon: "warning",
            confirmButtonColor: "#d6bdff",
        }).then(() => {
            window.location.replace("../usuario/login.html");
        });
        return;
    }

    const form = document.getElementById("formCriarTarefa");

    form.addEventListener("submit", function (event) {
        event.preventDefault();

        const titulo = document.getElementById("tituloTarefa").value.trim();
        const diaSemana = document.getElementById("diaDaSemana").value;
        const hora = document.getElementById("horaTarefa").value;

        // Validações
        if (!form.checkValidity()) {
            Swal.fire("Aviso", "Preencha o formulário corretamente.", "warning");
            return;
        }

        if (titulo.length < 5) {
            Swal.fire("Aviso", "O título deve ter pelo menos 5 caracteres.", "warning");
            return;
        }

        const tarefa = {
            titulo: titulo,
            diaDaSemana: diaSemana,
            hora: hora,
            userId: userId
        };

        fetch("http://localhost:4567/api/tarefas", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(tarefa)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Erro ao criar tarefa");
                }
                return response.json();
            })
            .then(data => {
                Swal.fire("Sucesso!", "Tarefa criada com sucesso!", "success");
                form.reset();
            })
        /*.catch(error => {
            Swal.fire("Erro", error.message, "error");
        });*/
    });
});
