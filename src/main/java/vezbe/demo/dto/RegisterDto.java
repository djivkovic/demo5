package vezbe.demo.dto;

import vezbe.demo.entity.Korisnik;
import vezbe.demo.entity.Kupac;
import vezbe.demo.entity.Pol;

import java.util.Date;

public class RegisterDto
{
    private String username;
    private String password;
    private String name;
    private String surname;


    public RegisterDto(String username, String password, String name, String surname) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }

    public RegisterDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public Kupac ToKupac()
    {
        return new Kupac(username, password, name, surname);
    }
}
