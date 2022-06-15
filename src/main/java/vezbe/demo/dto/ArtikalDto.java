package vezbe.demo.dto;

import vezbe.demo.entity.Tip;

public class ArtikalDto {
    private String naziv;
    private double cena;
    private Tip tip;

    public ArtikalDto(String naziv, double cena, Tip tip) {
        this.naziv = naziv;
        this.cena = cena;
        this.tip = tip;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public Tip getTip() {
        return tip;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
    }
}
