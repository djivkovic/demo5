package vezbe.demo.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Komentar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "kupac_id", referencedColumnName = "id")
    private Kupac kupac;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "restoran_id", referencedColumnName = "id")
    private Restoran restoran;

    @Column(name = "tekst")
    private String tekst;

    @Column(name = "ocena")
    private Integer ocena;

    public Komentar(Kupac kupac, Restoran restoran, String tekst, Integer ocena) {
        this.kupac = kupac;
        this.restoran = restoran;
        this.tekst = tekst;
        this.ocena = ocena;
    }

    public Komentar(String tekst, Integer ocena) {
        this.tekst = tekst;
        this.ocena = ocena;
    }

    public Komentar() {

    }

    public Kupac getKupac() {
        return kupac;
    }

    public void setKupac(Kupac kupac) {
        this.kupac = kupac;
    }

    public Restoran getRestoran() {
        return restoran;
    }

    public void setRestoran(Restoran restoran) {
        this.restoran = restoran;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public Integer getOcena() {
        return ocena;
    }

    public void setOcena(Integer ocena) {
        this.ocena = ocena;
    }
}

