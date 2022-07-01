package vezbe.demo.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("Kupac")
public class Kupac extends  Korisnik implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @OneToMany(mappedBy =  "kupac", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Porudzbina> porudzbine = new HashSet<>();

    @Column(name = "bodovi")
    private Integer bodovi;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tip_kupca", referencedColumnName = "id")
    private TipKupca tip_kupca;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Komentar> komentari;


    public Kupac(String username, String password, String name, String surname) {
        super(username, password, name, surname);
        this.uloga = Uloga.KUPAC;
    }


    public Kupac() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }


    public Integer getBodovi() {
        return bodovi;
    }

    public void setBodovi(Integer bodovi) {
        this.bodovi = bodovi;
    }

    public TipKupca getTip_kupca() {
        return tip_kupca;
    }

    public void setTip_kupca(TipKupca tip_kupca) {
        this.tip_kupca = tip_kupca;
    }

    public Set<Porudzbina> getPorudzbine() {
        if (porudzbine == null)
            porudzbine = new HashSet<>();
        return porudzbine;
    }

    public void setPorudzbine(Set<Porudzbina> porudzbine) {
        this.porudzbine = porudzbine;
    }

    @JsonIgnore
    public Set<Komentar> getKomentari() {
        return komentari;
    }
    @JsonIgnore
    public void setKomentari(Set<Komentar> komentari) {
        this.komentari = komentari;
    }
}

