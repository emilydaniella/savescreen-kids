package service;

import java.util.List;

import model.RotinaModel;
import repository.RotinaRepository;

public class RotinaService {
    private RotinaRepository repo;

    public RotinaService(RotinaRepository repo) {
        this.repo = repo;
    }

    public void criar(RotinaModel nova) {
        repo.criar(nova);
    }

    public List<RotinaModel> listar() {
        return repo.listar();
    }

    public RotinaModel buscarPorId(int id) {
        return repo.buscarPorId(id);
    }

    public void atualizar(RotinaModel atividade) {
        repo.atualizar(atividade);
    }

    public void deletar(int id) {
        repo.deletar(id);
    }
}
