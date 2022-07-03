package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vezbe.demo.dto.RestoranDto;
import vezbe.demo.dto.RestoranDto2;
import vezbe.demo.entity.*;
import vezbe.demo.repository.*;
import vezbe.demo.service.RestoranService;
import vezbe.demo.service.SessionService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class RestoranController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private RestoranRepository restoranRepository;

    @Autowired
    private RestoranService restoranService;

    @Autowired
    private LokacijaRepository lokacijaRepository;

    @Autowired
    private MenadzerRepository menadzerRepository;

    @Autowired
    private ArtikalRepository artikalRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private PorudzbinaRepository porudzbinaRepository;



    @GetMapping("/api/restorani/info/{id}")
    public Restoran ispisiRestoran(@PathVariable(name = "id") Long id) {
        List<Restoran> restoranList = restoranRepository.findAll();

        for (Restoran r : restoranList)
            if (Objects.equals(id, r.getID()))
                return r;

        return null;
    }

    @GetMapping("/api/restorani/name/{naziv}")
    public Restoran getRestoranname(@PathVariable(name = "naziv") String name, HttpSession session) {
        Restoran restoran = restoranRepository.getByNaziv(name);
        return restoran;
    }


    @GetMapping("/api/restorani/tip/{tip_restorana}")
    public Restoran getRestoranTipRestorana(@PathVariable(name = "tip_restorana") String tip, HttpSession session) {
        Restoran restoran = restoranRepository.getByTip(tip);
        return restoran;
    }

    @GetMapping("/api/restoran/pretragaLokacija/{id}")
    public Restoran pretraziRestoranPoLokaciji(@PathVariable(name = "id") Long id, HttpSession session) {

        Lokacija lokacija = lokacijaRepository.getByID(id);

        Restoran restoran = null;

        for (Restoran r : restoranRepository.findAll())
            if (r.getLokacija() == lokacija)
                restoran = r;

        return restoran;
    }


    @GetMapping("api/restorani/ispis")
    public  List<Restoran> Prikaz(HttpSession session) {
        List<Restoran> restorani = restoranRepository.findAll();

        return restorani;

    }


    @PostMapping("api/restorani/kreiraj-restoran")
    public ResponseEntity<?> kreirajRestoran(@RequestBody RestoranDto2 restoranDto, @RequestParam String username) {
        Korisnik loggedKorisnik = korisnikRepository.getByUsername(username);

        if(loggedKorisnik == null)
            return new ResponseEntity("Nema korisnika!", HttpStatus.NOT_FOUND);

        if(loggedKorisnik.isAuth() == false)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        if (!loggedKorisnik.getUloga().equals(Uloga.ADMIN))
            return new ResponseEntity(HttpStatus.FORBIDDEN);


        String poruka;
        ResponseEntity<String> kreirajRestoran;

        Restoran restoran = new Restoran();
        restoran.setNaziv(restoranDto.getNaziv());
        restoran.setTip_restorana(restoranDto.getTip());
        restoran.setLokacija(restoranDto.getLokacija());


        List<Menadzer> menadzerList = menadzerRepository.findAll();
        Menadzer menadzer = new Menadzer();

        for (Menadzer m : menadzerList)
            if (m.getRestoran() == restoran)
                menadzer = m;

        try {
            restoranService.dodaj(restoran);
        } catch (Exception e) {
            poruka = "Restoran vec postoji.";
            kreirajRestoran = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(poruka);

        }
        try {
            lokacijaRepository.saveAndFlush(restoran.getLokacija());
            restoranRepository.saveAndFlush(restoran);
            menadzerRepository.saveAndFlush(menadzer);

            poruka = "Restoran uspesno kreiran!";
            kreirajRestoran = ResponseEntity.ok(poruka);
        } catch (Exception e) {
            poruka = "Neuspesno kreiranje restorana, pokusaj ponovo.";
            System.out.println(e.getMessage());
            kreirajRestoran = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(poruka);
        }

        return kreirajRestoran;
    }

    @DeleteMapping("/api/admin/brisiRestoran/{naziv}")
    public ResponseEntity obrisiRestoran(@PathVariable String naziv, @RequestParam String username) {

        Korisnik loggedKorisnik = korisnikRepository.getByUsername(username);

        if(loggedKorisnik == null)
            return new ResponseEntity("Nema korisnika!", HttpStatus.NOT_FOUND);

        if(loggedKorisnik.isAuth() == false)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        if (!loggedKorisnik.getUloga().equals(Uloga.ADMIN))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        Restoran restoran = restoranRepository.getByNaziv(naziv);

        if(restoran == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Neuspesno pronalazenje restorana.");

        List<Porudzbina> plist = porudzbinaRepository.findAll();

        for(Porudzbina p : plist) {
            if(p.getRestoran().equals(restoran))
                p.setRestoran(null);
        }


        Menadzer menadzer = menadzerRepository.getByRestoran(restoran);
        if(menadzer != null)
            menadzer.setRestoran(null);

        restoranRepository.deleteById(restoran.getID());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Uspesno obrisan restoran!");
    }




}