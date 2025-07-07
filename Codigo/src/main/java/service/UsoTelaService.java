package service;

import dao.UsoTelaDAO;
import model.UsoTela;

import java.time.LocalDateTime;
import java.util.List;

public class UsoTelaService {
    private UsoTelaDAO dao = new UsoTelaDAO();

    public void registrarUso(int id, LocalDateTime inicio, LocalDateTime fim) {
        UsoTela uso = new UsoTela(id, inicio, fim);
        dao.salvar(uso);
    }

    public List<UsoTela> listarUsos() {
        return dao.listar();
    }
}
