package model;

public class RotinaModel {
    private int id;
    private String nome;
    private String descricao;
    private String horario;
    private int idResponsavel;

    public RotinaModel() {
        // Construtor vazio necessário para o Gson funcionar
    }

    public RotinaModel(int id, String nome, String descricao, String horario, int idResponsavel) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.horario = horario;
        this.idResponsavel = idResponsavel;
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public int getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(int idResponsavel) {
        this.idResponsavel = idResponsavel;
    }
}
