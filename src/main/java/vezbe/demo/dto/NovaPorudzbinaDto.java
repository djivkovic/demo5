package vezbe.demo.dto;

import java.util.List;

public class NovaPorudzbinaDto {

    private Long idRestorana;

    private List<NovaPorudzbinaKupcaDto> novePorudzbine;

    public NovaPorudzbinaDto(Long idRestorana, List<NovaPorudzbinaKupcaDto> novePorudzbine) {
        this.idRestorana = idRestorana;
        this.novePorudzbine = novePorudzbine;
    }

    public NovaPorudzbinaDto() {}

    public Long getIdRestorana() {
        return idRestorana;
    }

    public void setIdRestorana(Long idRestorana) {
        this.idRestorana = idRestorana;
    }

    public List<NovaPorudzbinaKupcaDto> getNovePorudzbine() {
        return novePorudzbine;
    }

    public void setNovePorudzbine(List<NovaPorudzbinaKupcaDto> novePorudzbine) {
        this.novePorudzbine = novePorudzbine;
    }

}


