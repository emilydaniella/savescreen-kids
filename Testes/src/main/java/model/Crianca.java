package model;

import java.time.LocalDate;

public class Crianca extends User {
    private LocalDate dataNascimento;
    private int idResponsavel;
    private Responsavel responsavel;  // novo campo

    public Crianca() { super(); }

    public Crianca(int id, String username, String password, String email,
                   LocalDate dataNascimento, int idResponsavel) {
        super(id, username, password, email);
        
        this.dataNascimento = dataNascimento;
        this.idResponsavel = idResponsavel;
    }

    public Crianca(String username, String password, String email, 
                   LocalDate dataNascimento, int idResponsavel) {
        super(username, password, email);
        this.dataNascimento = dataNascimento;
        this.idResponsavel = idResponsavel;
    }

    public LocalDate getDataNascimento() { 
        return dataNascimento; 
    }
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getIdResponsavel() { 
        return idResponsavel; 
    }
    public void setIdResponsavel(int idResponsavel) {
        this.idResponsavel = idResponsavel;
    }


    public Responsavel getResponsavel() { 
        return responsavel; 
    }
    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
        this.idResponsavel = responsavel.getId();
    }

    @Override
    public String toString() {
        return " Crianca{" +
               "id=" + getId() +
               ", username='" + getUsername() + '\'' +
               ", email='" + getEmail() + '\'' +
               ", dataNascimento=" + dataNascimento +
               ", responsavel=" + responsavel +
               '}';
    }
}
