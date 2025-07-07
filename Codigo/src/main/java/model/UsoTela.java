package model;

import java.time.LocalDateTime;

public class UsoTela {
    private int idUso;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;

    public UsoTela() {}

    public UsoTela(int idUso, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim) {
        this.idUso = idUso;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
    }

    public int getIdUso() {
        return idUso;
    }

    public void setIdUso(int idUso) {
        this.idUso = idUso;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(LocalDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }
}
