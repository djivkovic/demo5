package vezbe.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vezbe.demo.dto.ArtikalDto;
import vezbe.demo.dto.RegisterDto;
import vezbe.demo.entity.*;
import vezbe.demo.repository.*;
import vezbe.demo.service.KorisnikService;
import vezbe.demo.service.MenadzerService;
import vezbe.demo.service.SessionService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class KorisnikRestController
{
    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private MenadzerService menadzerService;

    @Autowired
    private RestoranRepository restoranRepository;

    @Autowired
    private MenadzerRepository menadzerRepository;

    @Autowired
    private DostavljacRepository dostavljacRepository;

    @Autowired
    private ArtikalRepository artikalRepository;

    @GetMapping("/api/login/info")
    public ResponseEntity getInfo(HttpSession session){


        if(!sessionService.validateSession(session))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if(!sessionService.getRole(session).equals(Uloga.KUPAC))
            return new ResponseEntity(HttpStatus.FORBIDDEN);


        return ResponseEntity.status(HttpStatus.OK).body(session.getAttribute("korisnik"));
    }

    @PutMapping("/api/login/info/izmena")
    public ResponseEntity<Korisnik> setKorisnik(HttpSession session, @RequestBody RegisterDto registerDto) {

        if (!sessionService.validateSession(session))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        Korisnik k = (Korisnik) session.getAttribute("korisnik");

        k.setUsername(registerDto.getUsername() == null ? k.getName() : registerDto.getUsername());
        k.setPassword(registerDto.getPassword() == null ? k.getPassword() : registerDto.getPassword());
        k.setName(registerDto.getName() == null ? k.getName() : registerDto.getName());
        k.setSurname(registerDto.getSurname() == null ? k.getSurname() : registerDto.getSurname());

        korisnikRepository.save(k);

        try {
            System.out.println("Uspesna izmena.");
        } catch (Exception e) {
            System.out.println("Neuspesna izmena.");
        }

        return ResponseEntity.ok(k);
    }




    @GetMapping("/api/korisnici/ispis")
    public ResponseEntity getKorisnici(HttpSession session) {
        if(!sessionService.validateSession(session))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if(!sessionService.getRole(session).equals(Uloga.ADMIN))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        List<Korisnik> korisnikList = korisnikRepository.findAll();
        List<RegisterDto> registrationRequestList = new ArrayList<>();

        for (Korisnik korisnik : korisnikList) {
            if (korisnik.getUloga() == null || !korisnik.getUloga().equals(Uloga.ADMIN)){
                RegisterDto r = new RegisterDto();
                r.setUsername(korisnik.getUsername());
                r.setPassword(korisnik.getPassword());
                r.setName(korisnik.getName());
                r.setSurname(korisnik.getSurname());


                registrationRequestList.add(r);
            }
        }

        return ok(registrationRequestList);
    }

    @GetMapping("/api/menadzer/restoran")
    public ResponseEntity<Restoran> menadzerovRestoran(HttpSession session){
        Korisnik loggedUser = (Menadzer)session.getAttribute("korisnik");
        if(loggedUser == null) {
            return new ResponseEntity("Please, login first.", HttpStatus.FORBIDDEN);
        }
        if(loggedUser.getUloga() != (Uloga.MENADZER)){
            return new ResponseEntity("Logged user must be MENADZER.", HttpStatus.FORBIDDEN);
        }
        Restoran restoran = menadzerService.findRestoran(loggedUser);
        return ResponseEntity.status(HttpStatus.OK).body(restoran);
    }

    @GetMapping("/api/restorani/{id}/postavi-menadzera") //id - od restorana, a username od menadzera
    public ResponseEntity postaviMenadzera(@PathVariable(name = "id") Long id, @RequestParam String username, HttpSession session) {
        if(!sessionService.validateSession(session))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if(!sessionService.getRole(session).equals(Uloga.ADMIN))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        Restoran restoran = restoranRepository.getById(id);

        if (restoran == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Restoran ne postoji.");
        }

        Menadzer menadzer = menadzerRepository.getByUsername(username);
        menadzer.setRestoran(restoran);
        menadzerRepository.save(menadzer);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Menadzer uspesno postavljen.");
    }

    @PostMapping("/api/kreiraj-menadzera")
    public ResponseEntity kreirajMenadzera(@RequestBody RegisterDto registerDto, HttpSession session) {
        if(!sessionService.validateSession(session))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if(!sessionService.getRole(session).equals(Uloga.ADMIN))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        String poruka;
        ResponseEntity<String> registracija;

        if (korisnikRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            poruka = "Korisnik vec postoji.";
            return ResponseEntity.status(HttpStatus.PRECONDITION_REQUIRED).body(poruka);
        }

        Menadzer m = new Menadzer();
        m.setUsername(registerDto.getUsername());
        m.setPassword(registerDto.getPassword());
        m.setName(registerDto.getName());
        m.setSurname(registerDto.getSurname());
        m.setUloga(Uloga.MENADZER);

        try {
            menadzerRepository.save(m);
            poruka = "Uspesna registracija!";
            registracija = ResponseEntity.ok(poruka);
        } catch (Exception e) {
            poruka = "Neuspesna registracija, pokusajte ponovo...";
            System.out.println(e.getMessage());
            registracija = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(poruka);
        }

        return registracija;
    }

    @PostMapping("/api/kreiraj-dostavljaca")
    public ResponseEntity kreirajDostavljaca(@RequestBody RegisterDto registerDto, HttpSession session) {
        if(!sessionService.validateSession(session))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if(!sessionService.getRole(session).equals(Uloga.ADMIN))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        String poruka;
        ResponseEntity<String> registracija;

        if (korisnikRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            poruka = "Korisnik vec postoji.";
            return ResponseEntity.status(HttpStatus.PRECONDITION_REQUIRED).body(poruka);
        }

        Dostavljac d = new Dostavljac();
        d.setUsername(registerDto.getUsername());
        d.setPassword(registerDto.getPassword());
        d.setName(registerDto.getName());
        d.setSurname(registerDto.getSurname());
        d.setUloga(Uloga.DOSTAVLJAC);

        try {
            dostavljacRepository.save(d);
            poruka = "Uspesna registracija!";
            registracija = ResponseEntity.ok(poruka);
        } catch (Exception e) {
            poruka = "Neuspesna registracija, pokusajte ponovo...";
            System.out.println(e.getMessage());
            registracija = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(poruka);
        }

        return registracija;
    }

    @GetMapping("/api/admin/korisnik/name/{naziv}")
    public ResponseEntity getKorisnikName(@PathVariable(name = "naziv") String name, HttpSession session) {

        if(!sessionService.validateSession(session))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if(!sessionService.getRole(session).equals(Uloga.ADMIN))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        Korisnik korisnik = korisnikRepository.getByName(name);

        if(korisnik == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ne postoji takav korisnik.");

        return ResponseEntity.status(HttpStatus.OK).body(korisnik);
    }

    @GetMapping("/api/admin/korisnik/surname/{naziv}")
    public ResponseEntity getKorisnikSurnameName(@PathVariable(name = "naziv") String surname, HttpSession session) {

        if(!sessionService.validateSession(session))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if(!sessionService.getRole(session).equals(Uloga.ADMIN))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        Korisnik korisnik = korisnikRepository.getBySurname(surname);

        if(korisnik == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ne postoji takav korisnik.");

        return ResponseEntity.status(HttpStatus.OK).body(korisnik);
    }

    @GetMapping("/api/admin/korisnik/username/{naziv}")
    public ResponseEntity getKorisnikUsername(@PathVariable(name = "naziv") String username, HttpSession session) {

        if(!sessionService.validateSession(session))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if(!sessionService.getRole(session).equals(Uloga.ADMIN))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        Korisnik korisnik = korisnikRepository.getByUsername(username);

        if(korisnik == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ne postoji takav korisnik.");

        return ResponseEntity.status(HttpStatus.OK).body(korisnik);
    }

}







