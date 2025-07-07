package model;

import java.time.LocalDate;

public class Crianca extends User {
    private LocalDate dataNascimento;
    private int idResponsavel;
    private Responsavel responsavel; // objeto relacionado ao responsável

    public Crianca() { 
        super(); 
    }

    public Crianca(int id, String username, LocalDate dataNascimento, int idResponsavel) {
        super(id, username, "", "", "");  // telefone não conhecido na criação de Crianca
        this.dataNascimento = dataNascimento;
        this.idResponsavel = idResponsavel;
    }

    public Crianca(String username, LocalDate dataNascimento, int idResponsavel) {
        super(username, "", "", "");
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
        if (responsavel != null) {
            this.idResponsavel = responsavel.getId();
        }
    }

    // Esse método é importante para funcionar no DAO:
    public void setResponsavelId(int responsavelId) {
        this.idResponsavel = responsavelId;
    }

    public int getResponsavelId() {
        return idResponsavel;
    }

    @Override
    public String toString() {
        return "Crianca{" +
               "id=" + getId() +
               ", username='" + getUsername() + '\'' +
               ", email='" + getEmail() + '\'' +
               ", dataNascimento=" + dataNascimento +
               ", idResponsavel=" + idResponsavel +
               ", responsavel=" + responsavel +
               '}';
    }
}
