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

    private String tipRestorana;

    private Lokacija lokacija;

    private Menadzer menadzer;

    public RestoranDto() {
    }

    public RestoranDto(String naziv, String tipRestorana, Lokacija lokacija) {

        this.naziv = naziv;
        this.tipRestorana = tipRestorana;
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

    public Restoran ToRestoran() { return new Restoran(naziv, tipRestorana, lokacija); }
}