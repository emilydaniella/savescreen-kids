package model;

public class User {
    private int id;
    private String username;  // corresponde a "nome" no banco
    private String password;  // corresponde a "senha" no banco
    private String email;
    private String telefone;  // novo campo

    // Construtor completo
    public User(int id, String username, String password, String email, String telefone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.telefone = telefone;
    }

    // Construtor sem o ID (gerado pelo banco)
    public User(String username, String password, String email, String telefone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.telefone = telefone;
    }

    // Construtor vazio
    public User() {}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}
