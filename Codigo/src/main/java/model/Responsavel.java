package model;

public class Responsavel extends User {

    public Responsavel() {
        super();
    }

    public Responsavel(int id, String username, String password, String email, String telefone) {
        super(id, username, password, email, telefone);
    }

    public Responsavel(String username, String password, String email, String telefone) {
        super(username, password, email, telefone);
    }

    @Override
    public String toString() {
        return "Responsavel{" +
                "id=" + getId() +
                ", username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", telefone='" + getTelefone() + '\'' +
                '}';
    }
}
