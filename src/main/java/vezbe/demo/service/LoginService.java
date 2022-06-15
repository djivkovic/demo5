package vezbe.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.entity.Korisnik;
import vezbe.demo.repository.KorisnikRepository;
import vezbe.demo.repository.KupacRepository;

import javax.management.InvalidAttributeValueException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Service
public class LoginService {

    @Autowired
    private KorisnikRepository korisnikRepository;


    public Korisnik Login(String username, String password)throws AccountNotFoundException, InvalidAttributeValueException
    {

        Korisnik korisnik = pronadjiKorisnika(username, (List<Korisnik>) korisnikRepository.findAll());

        if(korisnik == null)
        {
            throw new AccountNotFoundException("Korisnik sa tim korisnickim namenom nije pronadjen");
        }

        if(!korisnik.getPassword().equals(password))
        {
            throw new InvalidAttributeValueException("password se ne poklapa");
        }

        return korisnik;
    }



    private Korisnik pronadjiKorisnika(String username, List<Korisnik> set) {
        for(Korisnik korisnik : set){
            if (korisnik.getUsername().equals(username))
                return korisnik;
        }
        return null;
    }
}
