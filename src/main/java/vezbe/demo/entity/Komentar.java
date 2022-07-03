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

    @Column(name = "komentar")
    private String komentar;

    @Column(name = "ocena")
    private Integer ocena;

    public Komentar(Kupac kupac, Restoran restoran, String komentar, Integer ocena) {
        this.kupac = kupac;
        this.restoran = restoran;
        this.komentar = komentar;
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

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public Integer getOcena() {
        return ocena;
    }

    public void setOcena(Integer ocena) {
        this.ocena = ocena;
    }
}

