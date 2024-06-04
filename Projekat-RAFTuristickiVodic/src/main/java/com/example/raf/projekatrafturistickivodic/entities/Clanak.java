package com.example.raf.projekatrafturistickivodic.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Clanak {

    private Integer clanak_id;

    @NotNull(message = "Morate da popunite polje za autora")
    @NotEmpty(message = "Morate da popunite polje za autora")
    private String autor;

    @NotNull(message = "Morate da popunite polje za naslov")
    @NotEmpty(message = "Morate da popunite polje za naslov")
    private String naslov;

    @NotNull(message = "Morate da popunite polje za sadrzaj")
    @NotEmpty(message = "Morate da popunite polje za sadrzaj")
    private String tekst;

    private String vreme;

    private Integer br_poseta;

    @NotNull(message = "Morate da popunite polje za destinaciju")
    @NotEmpty(message = "Morate da popunite polje za destinaciju")
    private Integer destinacija;

    private List<Komentar> komentari = new CopyOnWriteArrayList<>();

    private List<Integer> aktivnosti = new CopyOnWriteArrayList<>();

    public Clanak() {

    }

    public Clanak(String autor, Integer clanak_id, String naslov, String vreme, String tekst, Integer br_poseta, Integer destinacija) {
        this.autor = autor;
        this.clanak_id = clanak_id;
        this.naslov = naslov;
        this.vreme = vreme;
        this.tekst = tekst;
        this.br_poseta = br_poseta;
        this.destinacija = destinacija;
    }

    public Clanak(String autor, Integer clanak_id, String naslov, String vreme, String tekst, Integer br_poseta, Integer destinacija, List<Integer> aktivnosti) {
        this.autor = autor;
        this.clanak_id = clanak_id;
        this.naslov = naslov;
        this.vreme = vreme;
        this.tekst = tekst;
        this.br_poseta = br_poseta;
        this.destinacija = destinacija;
        this.aktivnosti = aktivnosti;
        System.out.println("prosao");
        System.out.println("Autor: " + autor);
        System.out.println("Naslov: " + naslov);
        System.out.println("Tekst: " + tekst);
        System.out.println("Broj poseta: " + br_poseta);
        System.out.println("Destinacija: " + destinacija);
        System.out.println("Vreme: " + vreme);
        System.out.println("Autor: " + aktivnosti);
    }

    public void setVreme(String date){
        this.vreme =date;
    }

    public Integer getClanak_id() {
        return clanak_id;
    }

    public void setClanak_id(Integer id) {
        this.clanak_id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public String getVreme() {
        return vreme;
    }

    public Integer getBr_poseta() {
        return br_poseta;
    }

    public void setBr_poseta(Integer br_poseta) {
        this.br_poseta = br_poseta;
    }

    public Integer getDestinacija() {
        return destinacija;
    }

    public void setDestinacija(Integer destinacija) {
        this.destinacija = destinacija;
    }

    public List<Komentar> getKomentari() {
        return komentari;
    }

    public void setKomentari(List<Komentar> komentari) {
        this.komentari = komentari;
    }

    public List<Integer> getAktivnosti() {
        return aktivnosti;
    }

    public void setAktivnosti(List<Integer> aktivnosti) {
        this.aktivnosti = aktivnosti;
    }
}
