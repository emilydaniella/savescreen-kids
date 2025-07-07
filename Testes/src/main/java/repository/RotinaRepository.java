package repository;

import java.util.List;

import model.RotinaModel;

public interface RotinaRepository {
    void criar(RotinaModel nova);
    List<RotinaModel> listar();
    RotinaModel buscarPorId(int id);
    void atualizar(RotinaModel atividade);
    void deletar(int id);
}
