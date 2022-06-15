package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.entity.Korisnik;
import vezbe.demo.entity.Menadzer;
import vezbe.demo.entity.Restoran;
import vezbe.demo.repository.MenadzerRepository;

@Service
public class MenadzerService {

    @Autowired
    private MenadzerRepository menadzerRepository;

    public Restoran findRestoran(Korisnik korisnik){
        Menadzer menadzer = menadzerRepository.getById(korisnik.getID());
        return menadzer.getRestoran();
    }
}
