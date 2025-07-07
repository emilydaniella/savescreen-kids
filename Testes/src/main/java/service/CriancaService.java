package service;

import dao.CriancaDAO;
import model.Crianca;
import java.util.List;

public class CriancaService {
    private final CriancaDAO criancaDAO = new CriancaDAO();

    public void criar(Crianca crianca) {
        criancaDAO.inserir(crianca);
    }

    public List<Crianca> listar() {
        return criancaDAO.listar();
    }

    public Crianca buscarPorId(int id) {
        return criancaDAO.buscarPorId(id);
    }

    public void atualizar(Crianca crianca) {
        criancaDAO.atualizar(crianca);
    }

    public void deletar(int id) {
        criancaDAO.deletar(id);
    }
}
