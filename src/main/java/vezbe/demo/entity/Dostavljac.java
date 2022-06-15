package vezbe.demo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Dostavljac extends Korisnik implements Serializable
{
    @OneToMany(mappedBy = "dostavljac")
    private Set<Porudzbina> porudzbine;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    public Dostavljac(String username, String password, String name, String surname, Pol pol, Date datum_rodjenja)
    {
        super(username, password, name, surname);
        porudzbine = new HashSet<>();
        setUloga(Uloga.DOSTAVLJAC);
    }

    public Dostavljac(String username, String password, String name, String surname)
    {
        super(username, password, name, surname);
        this.uloga = Uloga.DOSTAVLJAC;
    }

    public Dostavljac() {
    }

    public Set<Porudzbina> getPorudzbine() {
        return porudzbine;
    }

    public void setPorudzbine(Set<Porudzbina> porudzbine) {
        this.porudzbine = porudzbine;
    }
}