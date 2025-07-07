package service;

import java.util.List;

import dao.CriancaDAO;
import model.Crianca;

public class CriancaService {
    private final CriancaDAO criancaDAO = new CriancaDAO();

    public void criar(Crianca crianca) {
        criancaDAO.inserir(crianca);
    }

    public List<Crianca> listarPorResponsavel(int idResponsavel) {
        return criancaDAO.buscarPorResponsavel(idResponsavel);
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
