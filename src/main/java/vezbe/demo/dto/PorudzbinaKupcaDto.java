package vezbe.demo.dto;

public class PorudzbinaKupcaDto {

    private String nazivPorudzbine;

    private double cenaPorudzbine;

    private int kolicina;

    public PorudzbinaKupcaDto(String nazivPorudzbine, double cenaPorudzbine, int kolicina) {
        this.nazivPorudzbine = nazivPorudzbine;
        this.cenaPorudzbine = cenaPorudzbine;
        this.kolicina = kolicina;
    }

    public String getNazivPorudzbine() {
        return nazivPorudzbine;
    }

    public void setNazivPorudzbine(String nazivPorudzbine) {
        this.nazivPorudzbine = nazivPorudzbine;
    }

    public double getCenaPorudzbine() {
        return cenaPorudzbine;
    }

    public void setCenaPorudzbine(double cenaPorudzbine) {
        this.cenaPorudzbine = cenaPorudzbine;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }
}
