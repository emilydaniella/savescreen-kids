package model;

public class Responsavel extends User {
    private String telefone;

    public Responsavel() {
        super();
    }

    public Responsavel(int id, String username, String password, String email, String telefone) {
        super(id, username, password, email);
        this.telefone = telefone;
    }

    public Responsavel(String username, String password, String email, String telefone) {
        super(username, password, email);
        this.telefone = telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getTelefone() {
        return telefone;
    }

    @Override
    public String toString() {
        return super.toString() + ", telefone='" + telefone + '\'' + '}';
    }
}
