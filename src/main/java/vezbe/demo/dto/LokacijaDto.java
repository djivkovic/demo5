package vezbe.demo.dto;

public class LokacijaDto {
    private String geografska_duzina;
    private String geografska_sirina;
    private String adresa;

    public LokacijaDto(String geografska_duzina, String geografska_sirina, String adresa) {
        this.geografska_duzina = geografska_duzina;
        this.geografska_sirina = geografska_sirina;
        this.adresa = adresa;
    }

    public String getGeografska_duzina() {
        return geografska_duzina;
    }

    public void setGeografska_duzina(String geografska_duzina) {
        this.geografska_duzina = geografska_duzina;
    }

    public String getGeografska_sirina() {
        return geografska_sirina;
    }

    public void setGeografska_sirina(String geografska_sirina) {
        this.geografska_sirina = geografska_sirina;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
}
