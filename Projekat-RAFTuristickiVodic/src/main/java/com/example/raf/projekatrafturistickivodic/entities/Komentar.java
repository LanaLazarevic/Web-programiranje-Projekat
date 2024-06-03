package com.example.raf.projekatrafturistickivodic.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class Komentar {

    @NotNull(message = "Morate da popunite polje za autora")
    @NotEmpty(message = "Morate da popunite polje za autora")
    private String autor;

    @NotNull(message = "Morate da popunite polje za komentar")
    @NotEmpty(message = "Morate da popunite polje za komentar")
    private String tekst;

    private Integer komentar_id;

    private String datum;


    public Komentar() {

    }

    public Komentar(String autor, String tekst, Integer komentar_id, String datum) {
        this.autor = autor;
        this.tekst = tekst;
        this.komentar_id = komentar_id;
        this.datum = datum;
    }


    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }
    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public Integer getKomentar_id() {
        return komentar_id;
    }

    public void setKomentar_id(Integer komentar_id) {
        this.komentar_id = komentar_id;
    }
}
