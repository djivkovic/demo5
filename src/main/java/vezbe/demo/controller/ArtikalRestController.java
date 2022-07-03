package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vezbe.demo.dto.ArtikalDto;
import vezbe.demo.entity.*;
import vezbe.demo.repository.ArtikalRepository;
import vezbe.demo.repository.KorisnikRepository;
import vezbe.demo.repository.MenadzerRepository;
import vezbe.demo.service.RestoranService;
import vezbe.demo.service.SessionService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class ArtikalRestController {

    @Autowired
    MenadzerRepository menadzerRepository;

    @Autowired
    ArtikalRepository artikalRepository;

    @Autowired
    SessionService sessionService;

    @Autowired
    RestoranService restoranService;

    @Autowired
    KorisnikRepository korisnikRepository;


//    @PostMapping("/api/artikli/kreiraj-artikal")
//    public ResponseEntity<Artikal> kreirajArtikal(@RequestParam String username, @RequestBody Artikal artikal, HttpSession session) {
//        Korisnik loggedKorisnik = korisnikRepository.getByUsername(username);
//
//        if(loggedKorisnik == null)
//            return new ResponseEntity("Nema korisnika!", HttpStatus.NOT_FOUND);
//
//        if(loggedKorisnik.isAuth() == false)
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//
//        if (!loggedKorisnik.getUloga().equals(Uloga.MENADZER))
//            return new ResponseEntity(HttpStatus.FORBIDDEN);
//
//        HashMap<String, String> greska = validate(artikal);
//
//        if(!greska.isEmpty())
//            return new ResponseEntity(greska, HttpStatus.BAD_REQUEST);
//
//        Menadzer menadzer = menadzerRepository.getByUsername(username);
//        menadzer.getRestoran().getArtikli().add(artikal);
//        artikal.setRestaurant(menadzer.getRestoran());
//        artikalRepository.save(artikal);
//        return new ResponseEntity(artikal, HttpStatus.OK);
//    }
//
//    private HashMap<String, String> validate(Artikal artikal) {
//
//        HashMap<String, String> greska = new HashMap<>();
//
//        if(artikal.getNaziv() == null || artikal.getNaziv().isEmpty())
//            greska.put("naziv", "OBAVEZNO");
//        if(artikal.getCena() == null)
//            greska.put("cena", "OBAVEZNO");
//        if(artikal.getTip() == null || artikal.getTip().toString().isEmpty())
//            greska.put("tip", "OBAVEZNO");
//
//        return greska;
//    }

    @PostMapping("/api/artikli/kreiraj-artikal")
    public ResponseEntity kreirajArtikal(@RequestParam String username, @RequestBody ArtikalDto artikalDto, HttpSession session) {
        Korisnik loggedKorisnik = korisnikRepository.getByUsername(username);

        if(loggedKorisnik == null)
            return new ResponseEntity("Nema korisnika!", HttpStatus.NOT_FOUND);

        if(loggedKorisnik.isAuth() == false)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        if (!loggedKorisnik.getUloga().equals(Uloga.MENADZER))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        String poruka;
        ResponseEntity<String> kreiranjeArtikla;

       Artikal a = new Artikal();
       a.setNaziv(artikalDto.getNaziv());
       a.setCena(artikalDto.getCena());
       a.setTip(artikalDto.getTip());
       a.setKolicina(artikalDto.getKolicina());
       a.setOpis(artikalDto.getOpis());


        try {
            artikalRepository.save(a);
            poruka = "Uspesno kreiranje artikla!";
            kreiranjeArtikla = ResponseEntity.ok(poruka);
        } catch (Exception e) {
            poruka = "Neuspesno, pokusajte ponovo...";
            System.out.println(e.getMessage());
            kreiranjeArtikla = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(poruka);
        }
        return kreiranjeArtikla;
    }



    private HashMap<String, String> validate(Artikal artikal) {

        HashMap<String, String> greska = new HashMap<>();

        if(artikal.getNaziv() == null || artikal.getNaziv().isEmpty())
            greska.put("naziv", "OBAVEZNO");
        if(artikal.getCena() == null)
            greska.put("cena", "OBAVEZNO");
        if(artikal.getTip() == null || artikal.getTip().toString().isEmpty())
            greska.put("tip", "OBAVEZNO");

        return greska;
    }


    @DeleteMapping("/api/artikal/obrisi/{id}")
    public ResponseEntity obrisiArtikal(@PathVariable UUID id, HttpSession session) {
        if (!sessionService.validateSession(session))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if (!sessionService.getRole(session).equals(Uloga.MENADZER))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        Korisnik korisnik = korisnikRepository.getByUsername((String) session.getAttribute("username"));
        Restoran restoran = ((Menadzer) korisnik).getRestoran();
        Artikal artikal;
        try {
            artikal = artikalRepository.findById(id).get();
        } catch(Exception e ) {
            return new ResponseEntity("Neispravan id", HttpStatus.BAD_REQUEST);
        }
        if(restoran.getArtikli().contains(artikal)) {
            restoran.getArtikli().remove(artikal);
            restoranService.save(restoran);
            return new ResponseEntity("Uspesno obrisan artikal", HttpStatus.OK);
        }
        return new ResponseEntity("Neuspesno pronalazenje artikla!", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/api/artikli/ispis")
    public ResponseEntity getArtikli(HttpSession session) {

        List<Artikal> artikalList = artikalRepository.findAll();

        return ok(artikalList);
    }
}
