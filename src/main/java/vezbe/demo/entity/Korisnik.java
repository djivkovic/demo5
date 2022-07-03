package vezbe.demo.entity;



import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING )
public  class Korisnik implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "username", unique = true)
    private String username ;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "pol")
    @Enumerated
    private Pol pol;

    @Column(name = "datum_rodjenja")
    private Date datum_rodjenja;

    @Column(name = "uloga")
    @Enumerated(EnumType.STRING)
    protected Uloga uloga;

    private boolean auth;

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public Pol getPol() {
        return pol;
    }

    public void setPol(Pol pol) {
        this.pol = pol;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void getSurname(String surname)
    {
        this.surname = surname;
    }

    public Date getDatum_rodjenja()
    {
        return datum_rodjenja;
    }

    public void setDatum_rodjenja(Date datum_rodjenja)
    {
        this.datum_rodjenja = datum_rodjenja;
    }

    public Uloga getUloga()
    {
        return uloga;
    }

    public void setUloga(Uloga uloga)
    {
        this.uloga = uloga;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Korisnik(String username, String password, String name, String surname) {

        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.auth = false;

    }

    public Korisnik(String username, String password, String name, String surname, Pol pol, Date datumRodjenja, Uloga uloga) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.pol = pol;
        this.datum_rodjenja = datumRodjenja;
        this.uloga = uloga;
        this.auth = false;
    }

    public  Korisnik(){};

}