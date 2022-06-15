package vezbe.demo.entity;




import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Lokacija implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;


    @Column
    private String geografska_duzina;

    @Column
    private String geografska_sirina;

    @Column
    private String adresa;



    @OneToOne
    private Restoran restoran;

    public Lokacija(String geografska_duzina, String geografska_sirina, String adresa)
    {
        this.geografska_duzina = geografska_duzina;
        this.geografska_sirina = geografska_sirina;
        this.adresa = adresa;
    }


    public Lokacija(String geografska_duzina, String geografska_sirina, String adresa, Long ID)
    {
        this.geografska_duzina = geografska_duzina;
        this.geografska_sirina = geografska_sirina;
        this.adresa = adresa;
        this.ID = ID;
    }

    public Lokacija() {
    }

    public String getGeografska_duzina()
    {
        return geografska_duzina;
    }

    public void setGeografska_duzina(String geografska_duzina)
    {
        this.geografska_duzina = geografska_duzina;
    }

    public String getGeografska_sirina()
    {
        return geografska_sirina;
    }

    public void setGeografska_sirina(String geografska_sirina)
    {
        this.geografska_sirina = geografska_sirina;
    }

    public String getAdresa()
    {
        return adresa;
    }

    public void setAdresa(String adresa)
    {
        this.adresa = adresa;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "Lokacija{" +
                "ID=" + ID +
                ", geografska_duzina='" + geografska_duzina + '\'' +
                ", geografska_sirina='" + geografska_sirina + '\'' +
                ", adresa='" + adresa + '\'' +
                ", restoran=" + restoran +
                '}';
    }
}

