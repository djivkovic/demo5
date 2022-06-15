package vezbe.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;


@Entity
public class Artikal implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "cena")
    private Double cena;

    @Column(name = "tip")
    @Enumerated
    private Tip tip;

    @Column(name = "kolicina")
    private Integer kolicina;

    @Column(name = "opis")
    private String opis;

    @ManyToOne
    @JoinColumn(name = "restoran_id")
    @JsonIgnore
    private Restoran restoran;

    private Date orderDate;

    @OneToMany
    @JoinColumn(name = "porudzbina_id")
    private Set<PorudzbineArtikli> porudzbine;

    public Restoran getRestoran() {
        return restoran;
    }



    public void setRestoran(Restoran restoran) {
        this.restoran = restoran;
    }


    public Artikal(String naziv, Double cena, Tip tip, Integer kolicina, String opis) {
        this.naziv = naziv;
        this.cena = cena;
        this.tip = tip;
        this.kolicina = kolicina;
        this.orderDate = new Date();
        this.opis = opis;
    }

    public Artikal() {

    }

    public String getNaziv() {

        return naziv;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public Tip getTip() {
        return tip;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Restoran getRestaurant() {
        return restoran;
    }

    public void setRestaurant(Restoran restoran) {
        this.restoran = restoran;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
