package vezbe.demo.dto;

import vezbe.demo.entity.Lokacija;

public class RestoranDto2 {
    private String naziv;
    private String tip_restorana;
    private Lokacija lokacija;

    public RestoranDto2(String naziv, String tip_restorana, Lokacija lokacija) {
        this.naziv = naziv;
        this.tip_restorana = tip_restorana;
        this.lokacija = lokacija;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getTip() {
        return tip_restorana;
    }

    public void setTip(String tip) {
        this.tip_restorana = tip_restorana;
    }

    public Lokacija getLokacija() {
        return lokacija;
    }

    public void setLokacija(Lokacija lokacija) {
        this.lokacija = lokacija;
    }
}