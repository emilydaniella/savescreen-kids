package model;

public class RotinaModel {
    private int id;
    private String nome;
    private String descricao;
    private String horario;

    public RotinaModel() {
        // Construtor vazio necessário para o Gson funcionar
    }

    public RotinaModel(int id, String nome, String descricao, String horario) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.horario = horario;
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
}
