package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vezbe.demo.dto.RestoranDto;
import vezbe.demo.entity.*;
import vezbe.demo.repository.ArtikalRepository;
import vezbe.demo.repository.LokacijaRepository;
import vezbe.demo.repository.MenadzerRepository;
import vezbe.demo.repository.RestoranRepository;
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
    private ArtikalRepository artikalRepository;


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
            informacijeoRestoranima.add(informacijeoRestoranu);
        }
        return new ResponseEntity(informacijeoRestoranima, HttpStatus.OK);
    }


    @PostMapping("api/restorani/kreiraj-restoran")
    public ResponseEntity<?> kreirajRestoran(@RequestBody RestoranDto restoranDto, HttpSession session) {
        if (!sessionService.validateSession(session)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        if (!sessionService.getRole(session).equals(Uloga.ADMIN))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        String poruka;
        ResponseEntity<String> kreirajRestoran;

        Restoran restoran = restoranDto.ToRestoran();

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

}