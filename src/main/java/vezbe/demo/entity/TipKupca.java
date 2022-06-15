package vezbe.demo.entity;

import javax.persistence.*;
import java.io.Serializable;



@Entity
public class TipKupca implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "name")
    @Enumerated
    private name name;

    @Column(name = "popust")
    private Double popust;

    @Column(name = "trazeni_broj_bodova")
    private Integer trazeni_broj_bodova;


    public TipKupca(name name, Double popust, Integer trazeni_broj_bodova) {
        super();
        this.name = name;
        this.popust = popust;
        this.trazeni_broj_bodova = trazeni_broj_bodova;
    }

    public TipKupca() {
    }

    public name getName() {
        return name;
    }

    public void setName(name name) {
        this.name = name;
    }

    public Double getPopust() {
        return popust;
    }

    public void setPopust(Double popust) {
        this.popust = popust;
    }

    public Integer getTrazeni_broj_bodova() {
        return trazeni_broj_bodova;
    }

    public void setTrazeni_broj_bodova(Integer trazeni_broj_bodova) {
        this.trazeni_broj_bodova = trazeni_broj_bodova;
    }
}
