package vezbe.demo.entity;




import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "porudzbina_table")
public class Porudzbina implements Serializable
{

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy =
            "org.hibernate.id.UUIDGenerator")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;


    @OneToMany(mappedBy = "porudzbina", cascade = CascadeType.ALL)
    private Set<PorudzbineArtikli> orderedItems;


    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restoran restoran;



    @ManyToOne
    @JoinColumn(name = "korisnicko_username", nullable = false)
    private Korisnik korisnik;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Restoran restoran_poruceno;

    @Column(name = " vreme_porudzbine")
    private Date vreme_porudzbine;

    @Column(name = "username")
    private String username;

    @Column(name = "status")
    @Enumerated
    private Status status;

    @Column(name = "cena")
    private double Cena;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private  Kupac kupac;

    @ManyToOne
    @JoinColumn(name = "dostavljac_id")
    private Dostavljac dostavljac;



    public Restoran getRestoran() {
        return restoran;
    }

    public void setRestoran(Restoran restoran) {
        this.restoran = restoran;
    }

    public Dostavljac getDostavljac() {
        return dostavljac;
    }

    public void setDostavljac(Dostavljac dostavljac) {
        this.dostavljac = dostavljac;
    }

    public Porudzbina(UUID UUID, Restoran restoran_poruceno, Date vreme_porudzbine, String username, Status status) {
        this.id = UUID;
        this.restoran_poruceno = restoran_poruceno;
        this.vreme_porudzbine = vreme_porudzbine;
        this.username = username;
        this.status = status;
    }

    public Porudzbina(Restoran restoran_poruceno, String username, Status status) {

        this.restoran_poruceno = restoran_poruceno;
        this.username = username;
        this.status = status;
    }

    public Porudzbina(Restoran restaurant, Korisnik client) {
        this.restoran = restaurant;
        this.vreme_porudzbine = new Date();
        this.korisnik = client;
    }


    public Porudzbina() {
    }


    public void setPorudzbina(Set<PorudzbineArtikli> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<PorudzbineArtikli> getOrderedItems() {
        if (orderedItems == null)
            orderedItems = new HashSet<>();
        return orderedItems;
    }


    public Restoran getRestoran_poruceno() {
        return restoran_poruceno;
    }

    public void setRestoran_poruceno(Restoran restoran_poruceno) {
        this.restoran_poruceno = restoran_poruceno;
    }

    public Date getVreme_porudzbine() {
        return vreme_porudzbine;
    }

    public void setVreme_porudzbine(Date vreme_porudzbine) {
        this.vreme_porudzbine = vreme_porudzbine;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getCena() {
        return Cena;
    }

    public void setCena(double cena) {
        Cena = cena;
    }

    public Kupac getKupac() {
        return kupac;
    }

    public void setKupac(Kupac kupac) {
        this.kupac = kupac;
    }

    public Set<PorudzbineArtikli> getArtikli() {
        return orderedItems;
    }

    public void setArtikli(Set<PorudzbineArtikli> artikli) {
        this.orderedItems = orderedItems;
    }

}
