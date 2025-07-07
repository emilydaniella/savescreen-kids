document.addEventListener('DOMContentLoaded', () => {
    preencherSelectCriancas();

    const form = document.getElementById('formCriarTarefa');
    const btnProcessarFoto = document.getElementById('btnProcessarFoto');
    const resultadoDiv = document.getElementById('ocrResultado');

    // Envio da tarefa
    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const criancaId = document.getElementById('criancaTarefa').value;
        const titulo = document.getElementById('tituloTarefa').value;
        const diaSemana = document.getElementById('diaDaSemana').value;
        const horario = document.getElementById('horaTarefa').value;
        const foto = document.getElementById('foto').files[0];

        if (!criancaId || !titulo || !diaSemana || !horario) {
            Swal.fire('Erro', 'Preencha todos os campos!', 'error');
            return;
        }

        const formData = new FormData();
        formData.append('id_crianca', criancaId);
        formData.append('titulo', titulo);
        formData.append('diaSemana', diaSemana);
        formData.append('horario', horario);
        if (foto) {
            formData.append('foto', foto);
        }

        try {
            const response = await fetch('http://localhost:8082/atividades', {
                method: 'POST',
                body: formData
            });

            if (response.ok) {
                Swal.fire('Sucesso!', 'Tarefa criada com sucesso.', 'success')
                    .then(() => window.location.href = 'rotina.html');
            } else {
                const erro = await response.text();
                Swal.fire('Erro', erro, 'error');
            }
        } catch (error) {
            console.error('Erro:', error);
            Swal.fire('Erro', 'Erro na conexão com o servidor.', 'error');
        }
    });

    // Botão de processar foto (OCR)
    btnProcessarFoto.addEventListener('click', async (e) => {
        e.preventDefault();

        const fotoInput = document.getElementById('foto');

        if (!fotoInput.files.length) {
            Swal.fire('Atenção', 'Selecione uma imagem antes de processar.', 'warning');
            return;
        }

        const formData = new FormData();
        formData.append('foto', fotoInput.files[0]);

        try {
            const response = await fetch('http://localhost:8082/ocr', {
                method: 'POST',
                body: formData
            });

            const texto = await response.text();

            if (response.ok) {
                Swal.fire('Foto processada!', 'Texto extraído com sucesso.', 'success');
                resultadoDiv.textContent = texto;

                // Opcional: preencher automaticamente o título da tarefa com o texto OCR
                document.getElementById('tituloTarefa').value = texto.split('\n')[0]; // 1ª linha do texto
            } else {
                Swal.fire('Erro', texto || 'Erro ao processar a foto.', 'error');
            }
        } catch (error) {
            console.error(error);
            Swal.fire('Erro', 'Falha na requisição. Verifique o servidor.', 'error');
        }
    });
});

function preencherSelectCriancas() {
    const select = document.getElementById('criancaTarefa');
    select.innerHTML = '';

    fetch('http://localhost:8082/criancas')
        .then(res => res.json())
        .then(criancas => {
            criancas.forEach(c => {
                const option = document.createElement('option');
                option.value = c.id;
                option.textContent = c.nome;
                select.appendChild(option);
            });
        })
        .catch(err => {
            console.error('Erro ao buscar crianças:', err);
            Swal.fire('Erro', 'Erro ao carregar crianças.', 'error');
        });
}
