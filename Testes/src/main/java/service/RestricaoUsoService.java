package service;

import dao.RestricaoUsoDAO;
import model.RestricaoUso;
import java.time.LocalTime;
import java.util.List;

public class RestricaoUsoService {
    private RestricaoUsoDAO dao = new RestricaoUsoDAO();

    public void registrarRestricao(int id, LocalTime inicio, LocalTime fim, String categoria, int limiteDiario) {
        RestricaoUso restricao = new RestricaoUso(id, inicio, fim, categoria, limiteDiario);
        dao.salvar(restricao);
    }

    public List<RestricaoUso> listarRestricoes() {
        return dao.listar();
    }
}
