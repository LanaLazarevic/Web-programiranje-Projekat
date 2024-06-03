package com.example.raf.projekatrafturistickivodic.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Aktivnost {

    private Integer aktivnost_id;
    @NotNull(message = "Morate da popunite polje za ime aktivnosti")
    @NotEmpty(message = "Morate da popunite polje za ime aktivnosti")
    private String naziv;

    public Aktivnost(){}

    public Aktivnost(Integer aktivnost_id, String naziv) {
        this.aktivnost_id = aktivnost_id;
        this.naziv = naziv;
    }

    public Integer getAktivnost_id() {
        return aktivnost_id;
    }

    public void setAktivnost_id(Integer aktivnost_id) {
        this.aktivnost_id = aktivnost_id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
