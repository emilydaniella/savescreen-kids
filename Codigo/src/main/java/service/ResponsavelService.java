package service;

import dao.ResponsavelDAO;
import model.Responsavel;
import java.util.List;

public class ResponsavelService {
    private final ResponsavelDAO responsavelDAO = new ResponsavelDAO();

    public void criar(Responsavel responsavel) {
        responsavelDAO.inserir(responsavel);
    }

    public List<Responsavel> listar() {
        return responsavelDAO.listar();
    }

    public Responsavel buscarPorId(int id) {
        return responsavelDAO.buscarPorId(id);
    }

    public void atualizar(Responsavel responsavel) {
        responsavelDAO.atualizar(responsavel);
    }

    public void deletar(int id) {
        responsavelDAO.deletar(id);
    }
}
