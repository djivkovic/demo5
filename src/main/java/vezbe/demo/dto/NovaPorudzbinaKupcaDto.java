package vezbe.demo.dto;

public class NovaPorudzbinaKupcaDto {

    private Long id;

    private int kolicina;

    public NovaPorudzbinaKupcaDto(Long id, int kolicina) {
        this.id = id;
        this.kolicina = kolicina;
    }

    public NovaPorudzbinaKupcaDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

}
