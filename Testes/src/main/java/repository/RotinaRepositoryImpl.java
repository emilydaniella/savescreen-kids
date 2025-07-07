package repository;

import java.util.ArrayList;
import java.util.List;

import model.RotinaModel;

public class RotinaRepositoryImpl implements RotinaRepository {
    private List<RotinaModel> banco = new ArrayList<>();

    public void criar(RotinaModel nova) {
        banco.add(nova);
    }

    public List<RotinaModel> listar() {
        return banco;
    }

    public RotinaModel buscarPorId(int id) {
        for (RotinaModel r : banco) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    public void atualizar(RotinaModel atividade) {
        for (int i = 0; i < banco.size(); i++) {
            if (banco.get(i).getId() == atividade.getId()) {
                banco.set(i, atividade);
                return;
            }
        }
    }

    public void deletar(int id) {
        banco.removeIf(r -> r.getId() == id);
    }
}
