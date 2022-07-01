package vezbe.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vezbe.demo.entity.*;
import vezbe.demo.repository.KupacRepository;
import vezbe.demo.repository.PorudzbineArtikliRepository;
import vezbe.demo.repository.RestoranRepository;
import vezbe.demo.service.KomentarService;
import vezbe.demo.service.KorisnikService;
import vezbe.demo.service.RestoranService;
import vezbe.demo.service.SessionService;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Set;

@RestController
public class KomentarRestController {

    @Autowired
    RestoranRepository restoranRepository;

    @Autowired
    KomentarService komentarService;

    @Autowired
    SessionService sessionService;

    @Autowired
    KupacRepository kupacRepository;

    @Autowired
    PorudzbineArtikliRepository porudzbineArtikliRepository;

    @Autowired
    RestoranService restoranService;


    @PostMapping("/api/komentar/{id}")
    public ResponseEntity komentar(@PathVariable(name = "id") Long id, @RequestBody Komentar komentar, HttpSession session){
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if(loggedKorisnik == null)
            return ResponseEntity.ok("Nema sesije");

        Kupac kupac = kupacRepository.getById(loggedKorisnik.getID());

        if(kupac == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        PorudzbineArtikli porudzbina = null;

        for (PorudzbineArtikli pa : porudzbineArtikliRepository.findAll())
            if (Objects.equals(id, pa.getId()))
                porudzbina = pa;

        Porudzbina p = porudzbina.getPorudzbina();

        if (komentar == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        if(p.getStatus().equals(Status.dostavljena)) {
            Restoran restoran = porudzbina.getPorudzbina().getRestoran();

            komentar.setKupac(kupac);
            komentar.setRestoran(restoran);
            komentarService.sacuvaj(komentar);

            restoran.getKomentari().add(komentar);
            restoranService.save(restoran);

            kupac.getKomentari().add(komentar);
            kupacRepository.save(kupac);

            return ResponseEntity.status(HttpStatus.OK).body(komentar);
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }
    }

