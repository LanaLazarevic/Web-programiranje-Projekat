package com.example.raf.projekatrafturistickivodic.entities;

public class Korisnik {

    private Integer korisnik_id;

    private String ime;

    private String prezime;

    private String lozinka;

    private String email;

    private String tip;

    private String status;


    public Korisnik() {
    }

    public Korisnik(Integer korisnik_id,String ime, String prezime, String lozinka, String email, String tip, String status) {}

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public Integer getKorisnik_id() {
        return korisnik_id;
    }

    public void setKorisnik_id(Integer korisnik_id) {
        this.korisnik_id = korisnik_id;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
