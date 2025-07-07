package service;

import java.util.List;

import dao.UserDAO;
import model.User;

public class UserService {
    public final UserDAO dao = new UserDAO();

    public User criar(User u, String type) {
        return dao.inserir(u, type);
    }
    

    public List<User> listar() {
        return dao.listar();
    }

    public User buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public User atualizar(User u) {
        return dao.atualizar(u);
    }

    public boolean deletar(int id) {
        return dao.deletar(id);
    }
}
