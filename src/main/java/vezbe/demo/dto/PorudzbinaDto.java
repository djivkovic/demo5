package vezbe.demo.dto;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PorudzbinaDto {

    private UUID uuid;

    private Date datumPorudzbine;

    private List<PorudzbinaKupcaDto> porudzbineKupca;

    public PorudzbinaDto(UUID uuid, Date datumPorudzbine) {
        this.uuid = uuid;
        this.datumPorudzbine = datumPorudzbine;
        this.porudzbineKupca = new ArrayList<>();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Date getDatumPorudzbine() {
        return datumPorudzbine;
    }

    public void setDatumPorudzbine(Date datumPorudzbine) {
        this.datumPorudzbine = datumPorudzbine;
    }

    public List<PorudzbinaKupcaDto> getPorudzbineKupca() {
        return porudzbineKupca;
    }

    public void setPorudzbineKupca(List<PorudzbinaKupcaDto> porudzbineKupca) {
        this.porudzbineKupca = porudzbineKupca;
    }

}
