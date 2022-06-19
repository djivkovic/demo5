package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vezbe.demo.entity.Artikal;
import vezbe.demo.entity.Menadzer;
import vezbe.demo.entity.Uloga;
import vezbe.demo.repository.ArtikalRepository;
import vezbe.demo.repository.MenadzerRepository;
import vezbe.demo.service.SessionService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
public class ArtikalRestController {

    @Autowired
    MenadzerRepository menadzerRepository;

    @Autowired
    ArtikalRepository artikalRepository;

    @Autowired
    SessionService sessionService;

    @PostMapping("/api/artikli/kreiraj-artikal")
    public ResponseEntity<Artikal> kreirajArtikal(@RequestParam String username, @RequestBody Artikal artikal, HttpSession session) {
        if(!sessionService.validateSession(session))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if(!sessionService.getRole(session).equals(Uloga.MENADZER))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        HashMap<String, String> greska = validate(artikal);

        if(!greska.isEmpty())
            return new ResponseEntity(greska, HttpStatus.BAD_REQUEST);

        Menadzer menadzer = menadzerRepository.getByUsername(username);
        menadzer.getRestoran().getArtikli().add(artikal);
        artikal.setRestaurant(menadzer.getRestoran());
        artikalRepository.save(artikal);
        return new ResponseEntity(artikal, HttpStatus.OK);
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
}
