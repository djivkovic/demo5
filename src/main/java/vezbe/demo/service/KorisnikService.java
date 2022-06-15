package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.entity.Korisnik;
import vezbe.demo.repository.KorisnikRepository;

import java.util.List;
import java.util.Optional;

@Service
public class KorisnikService {
    @Autowired
    private KorisnikRepository korisnikRepository;

    public Korisnik findKorisnik(Long id){
        Optional<Korisnik> foundKorisnik = korisnikRepository.findById(id);
        if(foundKorisnik.isPresent())
            return foundKorisnik.get();
        return null;
    }

    public List<Korisnik> findAll(){
        return korisnikRepository.findAll();
    }

    public Korisnik save(Korisnik korisnik){
        return korisnikRepository.save(korisnik);
    }

    public void deleteById(Long id){
        korisnikRepository.deleteById(id);
    }

    public Korisnik login(String korisnickoIme, String lozinka) {
        Korisnik korisnik = korisnikRepository.getByUsername(korisnickoIme);
        if(korisnik == null || !korisnik.getPassword().equals(lozinka))
            return null;
        return korisnik;
    }

    public List<Korisnik> getAll(){
        return korisnikRepository.findAll();
    }

}
