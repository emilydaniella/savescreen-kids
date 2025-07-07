package model;

import java.sql.Timestamp;

public class OCR {
    private int id;
    private String conteudo;
    private Timestamp criadoEm;

    public OCR(int id, String conteudo, Timestamp criadoEm) {
        this.id = id;
        this.conteudo = conteudo;
        this.criadoEm = criadoEm;
    }

    public int getId() {
        return id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public Timestamp getCriadoEm() {
        return criadoEm;
    }
}
