package vezbe.demo.dto;

import vezbe.demo.entity.Korisnik;

public class KorisnikDto
{
    private Long id;

    private String name;

    private String surname;


    public KorisnikDto(Long id, String name, String surname)
    {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public KorisnikDto(Korisnik korisnik)
    {
        this.id = korisnik.getID();
        this.name = korisnik.getName();
        this.surname = korisnik.getSurname();
    }


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
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
}
