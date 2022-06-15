package vezbe.demo.entity;



import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Menadzer extends Korisnik implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "restoran", referencedColumnName = "id")
    private Restoran restoran;


    public Menadzer(String username, String password, String name, String surname, Pol pol, Date datum_rodjenja)
    {
        super(username, password, name, surname);
        setUloga(Uloga.MENADZER);
    }

    public Menadzer() {
    }

    public Menadzer(String username, String password, String name, String surname)
    {
        super(username, password, name, surname);
        this.uloga = Uloga.MENADZER;

    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Restoran getRestoran() {
        return restoran;
    }

    public void setRestoran(Restoran restoran) {
        this.restoran = restoran;
    }
}

