package vezbe.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vezbe.demo.dto.RegisterDto;
import vezbe.demo.entity.*;
import vezbe.demo.repository.*;
import vezbe.demo.service.KorisnikService;
import vezbe.demo.service.MenadzerService;
import vezbe.demo.service.SessionService;

import javax.servlet.http.HttpSession;
import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class KorisnikRestController {
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

    @GetMapping("/api/korisnici/korisnik/{id}")
    public Optional<Korisnik> ispisiKorisnika(@PathVariable(name = "id") Long id) {
        return korisnikRepository.findById(id);
    }



    @PostMapping("api/korisnik")
    public ResponseEntity UpdateProfile(@RequestBody RegisterDto registerDto, HttpSession session){
        String username = sessionService.getUsername(session);
        if(username == null)
            return new ResponseEntity("Forbidden", HttpStatus.FORBIDDEN);
        if (username.isEmpty())
            return new ResponseEntity("Forbidden", HttpStatus.FORBIDDEN);

        Uloga role = sessionService.getRole(session);
        registerDto.setUsername(username);

        HashMap<String, String> errorDic =  Validate(registerDto);
        if (!errorDic.isEmpty())
            return new ResponseEntity(errorDic, HttpStatus.BAD_REQUEST);

        return new ResponseEntity(HttpStatus.OK);
    }


    private HashMap<String, String> Validate(RegisterDto registerDto){
        HashMap<String, String> errorDic = new HashMap<>();

        if (registerDto.getName() == null)
            errorDic.put("name", "Name is mandatory");
        else if(registerDto.getName().isEmpty())
            errorDic.put("name", "Name is mandatory");

        if (registerDto.getSurname() == null)
            errorDic.put("surname", "Surname is mandatory");
        else if(registerDto.getSurname().isEmpty())
            errorDic.put("surname", "Surname is mandatory");

        if (registerDto.getUsername() == null)
            errorDic.put("username", "Username is mandatory");
        else if(registerDto.getUsername().isEmpty())
            errorDic.put("username", "Username is mandatory");

        if (registerDto.getPassword() == null)
            errorDic.put("password", "Password is mandatory");
        else if(registerDto.getPassword().isEmpty())
            errorDic.put("password", "Password is mandatory");

        return errorDic;
    }

   /* @GetMapping("/api/korisnici/korisnik/{id}")
    public Korisnik ispisiKorisnika(@PathVariable(name = "id") Long id) {
        List<Korisnik> korisnikList = korisnikRepository.findAll();

        for (Korisnik k : korisnikList)
            if (Objects.equals(id, k.getID()))
                return k;

        return null;
    }
    */


    @GetMapping("/api/korisnici/ispis")
    public ResponseEntity getKorisnici(HttpSession session, @RequestParam String username) {

//        if(!sessionService.validateSession(session))
//            return new ResponseEntity(HttpStatus.FORBIDDEN);
//
//        if(!sessionService.getRole(session).equals(Uloga.ADMIN))
//            return new ResponseEntity(HttpStatus.FORBIDDEN);
        Korisnik loggedKorisnik = korisnikRepository.getByUsername(username);

        if(loggedKorisnik == null)
            return new ResponseEntity("Nema korisnika!", HttpStatus.NOT_FOUND);

        if(loggedKorisnik.isAuth() == false)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        if (!loggedKorisnik.getUloga().equals(Uloga.ADMIN))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        List<Korisnik> korisnikList = korisnikRepository.findAll();



        return ok(korisnikList);
    }


    @GetMapping("/api/dostavljaci/ispis")
    public ResponseEntity getDostavljaci() {

//        if(!sessionService.validateSession(session))
//            return new ResponseEntity(HttpStatus.FORBIDDEN);
//
//        if(!sessionService.getRole(session).equals(Uloga.ADMIN))
//            return new ResponseEntity(HttpStatus.FORBIDDEN);

        List<Dostavljac> dostavljacList = dostavljacRepository.findAll();

        return ok(dostavljacList);
    }

    @GetMapping("/api/menadzeri/ispis")
    public ResponseEntity getMenadzere() {

//        if(!sessionService.validateSession(session))
//            return new ResponseEntity(HttpStatus.FORBIDDEN);
//
//        if(!sessionService.getRole(session).equals(Uloga.ADMIN))
//            return new ResponseEntity(HttpStatus.FORBIDDEN);

        List<Menadzer> menadzerList = menadzerRepository.findAll();

        return ok(menadzerList);
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
    public ResponseEntity kreirajMenadzera(@RequestBody RegisterDto registerDto, @RequestParam String username) {
        Korisnik loggedKorisnik = korisnikRepository.getByUsername(username);

        if(loggedKorisnik == null)
            return new ResponseEntity("Nema korisnika!", HttpStatus.NOT_FOUND);

        if(loggedKorisnik.isAuth() == false)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        if (!loggedKorisnik.getUloga().equals(Uloga.ADMIN))
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
            poruka = "Uspesno kreiranje menadzera!";
            registracija = ResponseEntity.ok(poruka);
        } catch (Exception e) {
            poruka = "Neuspesna, pokusajte ponovo...";
            System.out.println(e.getMessage());
            registracija = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(poruka);
        }

        return registracija;
    }

    @PostMapping("/api/kreiraj-dostavljaca")
    public ResponseEntity kreirajDostavljaca( @RequestBody RegisterDto registerDto,@RequestParam String username) {
        Korisnik loggedKorisnik = korisnikRepository.getByUsername(username);

        if(loggedKorisnik == null)
            return new ResponseEntity("Nema korisnika!", HttpStatus.NOT_FOUND);

        if(loggedKorisnik.isAuth() == false)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        if (!loggedKorisnik.getUloga().equals(Uloga.ADMIN))
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
            poruka = "Uspesno kreiranje dostavljaca!";
            registracija = ResponseEntity.ok(poruka);
        } catch (Exception e) {
            poruka = "Neuspesna, pokusajte ponovo...";
            System.out.println(e.getMessage());
            registracija = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(poruka);
        }

        return registracija;
    }


    @DeleteMapping(
            value = "/{id}"
    )
    public ResponseEntity deleteKorisnik(@PathVariable(name = "id") long id){
        korisnikService.deleteById(id);

        return new ResponseEntity(HttpStatus.OK);
    }



    @GetMapping("/api/admin/korisnik/name/{naziv}")
    public ResponseEntity getKorisnikName(@PathVariable(name = "naziv") String name, @RequestParam String username) {

        Korisnik loggedKorisnik = korisnikRepository.getByUsername(username);

        if(loggedKorisnik == null)
            return new ResponseEntity("Nema korisnika!", HttpStatus.NOT_FOUND);

        if(loggedKorisnik.isAuth() == false)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        if (!loggedKorisnik.getUloga().equals(Uloga.ADMIN))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        Korisnik korisnik = korisnikRepository.getByName(name);

        if(korisnik == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ne postoji takav korisnik.");

        return ResponseEntity.status(HttpStatus.OK).body(korisnik);
    }

    @GetMapping("/api/admin/korisnik/surname/{naziv}")
    public ResponseEntity getKorisnikSurnameName(@PathVariable(name = "naziv") String surname, @RequestParam String username) {

        Korisnik loggedKorisnik = korisnikRepository.getByUsername(username);

        if(loggedKorisnik == null)
            return new ResponseEntity("Nema korisnika!", HttpStatus.NOT_FOUND);

        if(loggedKorisnik.isAuth() == false)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        if (!loggedKorisnik.getUloga().equals(Uloga.ADMIN))
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







