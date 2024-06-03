package com.example.raf.projekatrafturistickivodic.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Destinacija {

    private Integer destinacija_id;
    @NotNull(message = "Morate da popunite polje za ime destinacije")
    @NotEmpty(message = "Morate da popunite polje za ime destinacije")
    private String ime;

    @NotNull(message = "Morate da popunite polje za opis destinacije")
    @NotEmpty(message = "Morate da popunite polje za opis desitanije")
    private String opis;

    public Destinacija() {}

    public Destinacija(Integer destinacija_id, String ime, String opis) {
        this.destinacija_id = destinacija_id;
        this.ime = ime;
        this.opis = opis;
    }

    public Integer getDestinacija_id() {
        return destinacija_id;
    }

    public void setDestinacija_id(Integer destinacija_id) {
        this.destinacija_id = destinacija_id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
