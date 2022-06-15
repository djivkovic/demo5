package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.entity.*;
import vezbe.demo.repository.*;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class RegisterService {
    @Autowired
    private KupacRepository kupacRepository;



    public void Register(Korisnik korisnik, Uloga uloga)throws Exception
    {
        CheckUserAgainst(korisnik.getUsername(), kupacRepository.findAll());


           if(uloga == Uloga.KUPAC)
            kupacRepository.save((Kupac) korisnik);

           else {
               throw new Exception("Unknown role : " + uloga);
           }

    }



    private void CheckUserAgainst(String username, List<Kupac> set) throws Exception {
        for(Korisnik korisnik : set){
            if (korisnik.getUsername().equals(username))
                ThrowUsernameInvalid(username);
        }
    }

    private void ThrowUsernameInvalid(String username) throws Exception {
        throw new Exception("User with username '" + username + "' already exists");
    }

}
