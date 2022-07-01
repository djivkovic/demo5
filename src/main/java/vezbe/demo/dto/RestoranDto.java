package vezbe.demo.dto;

import vezbe.demo.entity.*;

import java.util.Set;

public class RestoranDto
{
    private Restoran restoran;
    private Set<Artikal> artikli;
    private String naziv;

    private String tip_restorana;

    private Lokacija lokacija;

    private Menadzer menadzer;

    private Set<Komentar> komentar;

    public RestoranDto() {
    }

    public RestoranDto(String naziv, String tipRestorana, Lokacija lokacija) {
        this.naziv = naziv;
        this.tip_restorana = tipRestorana;
        this.lokacija = lokacija;
    }

    public Restoran getRestoran() {
        return restoran;
    }

    public void setRestoran(Restoran restoran) {
        this.restoran = restoran;
    }

    public Set<Artikal> getArtikli() {
        return artikli;
    }

    public void setArtikli(Set<Artikal> artikli) {
        this.artikli = artikli;
    }

    public Restoran ToRestoran() { return new Restoran(this.naziv, this.tip_restorana, this.lokacija); }

    public Set<Komentar> getKomentar() {
        return komentar;
    }

    public void setKomentar(Set<Komentar> komentar) {
        this.komentar = komentar;
    }


}