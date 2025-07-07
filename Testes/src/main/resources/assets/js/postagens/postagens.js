// mudar para tabulacao esquerda

// funcao java script para adicionar cards de atividades
    function adicionarAtividade() {
      const container = document.getElementById("atividadesContainer");

      const card = document.createElement("div");
      card.className = "col-md-4";

      card.innerHTML = `
        <div class="card h-100 shadow-sm">
          <div class="card-body">
            <h5 class="card-title">Nova Atividade</h5>
            <p class="card-text">Descrição da atividade que será exibida aqui.</p>
            <button class="btn btn-outline-danger btn-sm">Remover</button>
          </div>
        </div>
      `;

      container.appendChild(card);
    }