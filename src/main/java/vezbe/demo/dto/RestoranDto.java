package vezbe.demo.dto;

import vezbe.demo.entity.Artikal;
import vezbe.demo.entity.Lokacija;
import vezbe.demo.entity.Menadzer;
import vezbe.demo.entity.Restoran;

import java.util.Set;

public class RestoranDto
{
    private Restoran restoran;
    private Set<Artikal> artikli;
    private String naziv;

    private String tip_restorana;

    private Lokacija lokacija;

    private Menadzer menadzer;

    public RestoranDto() {
    }

    public RestoranDto(String naziv, String tip_restorana, Lokacija lokacija) {

        this.naziv = naziv;
        this.tip_restorana = tip_restorana;
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

    public Restoran ToRestoran() { return new Restoran(naziv, tip_restorana, lokacija); }
}