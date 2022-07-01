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


    @GetMapping("api/restorani/prikaz")
    public ResponseEntity Prikaz(HttpSession session) {
        List<RestoranDto> informacijeoRestoranima = new ArrayList<>();
        List<Restoran> restorani = restoranRepository.findAll();

        for (Restoran restoran : restorani) {
            RestoranDto informacijeoRestoranu = new RestoranDto();
            informacijeoRestoranu.setRestoran(restoran);
            informacijeoRestoranu.setArtikli(restoran.getArtikli());
            informacijeoRestoranu.setKomentar(restoran.getKomentari());
            informacijeoRestoranima.add(informacijeoRestoranu);

        }
        return new ResponseEntity(informacijeoRestoranima, HttpStatus.OK);
    }


    @PostMapping("api/restorani/kreiraj-restoran")
    public ResponseEntity<?> kreirajRestoran(@RequestBody RestoranDto2 restoranDto, HttpSession session) {
        if (!sessionService.validateSession(session)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        if (!sessionService.getRole(session).equals(Uloga.ADMIN))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        String poruka;
        ResponseEntity<String> kreirajRestoran;

        Restoran restoran = new Restoran();
        restoran.setNaziv(restoranDto.getNaziv());
        restoran.setTip(restoranDto.getTip());
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
            poruka = "Restoran uspesno kreiran!";
            kreirajRestoran = ResponseEntity.ok(poruka);
        } catch (Exception e) {
            poruka = "Neuspesno kreiranje restorana, pokusaj ponovo.";
            System.out.println(e.getMessage());
            kreirajRestoran = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(poruka);
        }

        return kreirajRestoran;
    }

    @DeleteMapping("/api/admin/brisiRestoran/{nazivRestorana}")
    public ResponseEntity obrisiRestoran(@PathVariable String nazivRestorana, HttpSession session) {

        if(!sessionService.validateSession(session))
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        if(!sessionService.getRole(session).equals(Uloga.ADMIN))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        Restoran restoran = restoranRepository.getByNaziv(nazivRestorana);

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