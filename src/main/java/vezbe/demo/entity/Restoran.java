package vezbe.demo.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Restoran implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "naziv")
    private String naziv;

    @Column(name ="tip")
    private String tip;

    @OneToMany(mappedBy = "restoran", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Artikal> artikli = new HashSet<>();

    @OneToMany(mappedBy = "restoran", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Porudzbina> porudzbine;

    @OneToOne
    @JoinColumn(name = "lokacija")
    private Lokacija lokacija;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Komentar> komentari;
    


    public Restoran(String naziv, String tip_restorana, Lokacija lokacija)
    {
        this.naziv = naziv;
        this.tip = tip_restorana;
        this.lokacija = lokacija;
    }

    public Long getID() {
        return ID;
    }
    public void setID(Long iD) {
        ID = iD;
    }

    public Restoran() {
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }


    public Set<Artikal> getArtikli() {
        return artikli;
    }

    public void setArtikli(Set<Artikal> artikli) {
        this.artikli = artikli;
    }

    public Lokacija getLokacija() {
        return lokacija;
    }

    public void setLokacija(Lokacija lokacija) {
        this.lokacija = lokacija;
    }

    public Set<Porudzbina> getPorudzbine() {
        if(porudzbine == null)
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


