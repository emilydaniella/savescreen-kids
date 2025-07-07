package model;

import java.time.LocalTime;

public class RestricaoUso {
    private int idRestricao;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
    private String categoria;
    private int limiteDiario; // em minutos ou horas, dependendo da regra que nós aplicarmos

    public RestricaoUso() {}

    public RestricaoUso(int idRestricao, LocalTime horarioInicio, LocalTime horarioFim, String categoria, int limiteDiario) {
        this.idRestricao = idRestricao;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.categoria = categoria;
        this.limiteDiario = limiteDiario;
    }

    public int getIdRestricao() {
        return idRestricao;
    }

    public void setIdRestricao(int idRestricao) {
        this.idRestricao = idRestricao;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public LocalTime getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(LocalTime horarioFim) {
        this.horarioFim = horarioFim;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getLimiteDiario() {
        return limiteDiario;
    }

    public void setLimiteDiario(int limiteDiario) {
        this.limiteDiario = limiteDiario;
    }
}
