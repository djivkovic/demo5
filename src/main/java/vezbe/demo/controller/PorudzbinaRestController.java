package vezbe.demo.controller;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vezbe.demo.dto.NovaPorudzbinaDto;
import vezbe.demo.dto.NovaPorudzbinaKupcaDto;
import vezbe.demo.dto.PorudzbinaDto;
import vezbe.demo.dto.PorudzbinaKupcaDto;
import vezbe.demo.entity.*;
import vezbe.demo.repository.*;
import vezbe.demo.service.PorudzbinaService;
import vezbe.demo.service.SessionService;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
public class PorudzbinaRestController
{

    @Autowired
    private SessionService sessionService;

    @Autowired
    private KupacRepository kupacRepository;

    @Autowired
    private DostavljacRepository dostavljacRepository;

    @Autowired
    private MenadzerRepository menadzerRepository;

    @Autowired
    private PorudzbinaService porudzbinaService;

    @Autowired
    private PorudzbineArtikliRepository porudzbineArtikliRepository;

    @Autowired
    private PorudzbinaRepository porudzbinaRepository;



    @GetMapping("/api/porudzbine")
    public ResponseEntity vratiPorudzbine(HttpSession session) {

        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            System.out.println("Nema sesije.");
            return ResponseEntity.ok(null);
        } else if (!loggedKorisnik.getUloga().equals(Uloga.KUPAC)) {
            System.out.println("Pristup nije odobren.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pristup nije odobren.");
        }

        Kupac kupac = kupacRepository.getById(loggedKorisnik.getID());

        if (kupac.getUsername() == null || kupac.getUsername().toString().isEmpty())
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        List<PorudzbinaDto> porudzbinaDtoList = new ArrayList<>();

        for (Porudzbina p : kupac.getPorudzbine()) {
            PorudzbinaDto porudzbinaDto = new PorudzbinaDto(p.getUuid(), p.getVreme_porudzbine());

            List<PorudzbinaKupcaDto> porudzbinaKupcaDtoList = new ArrayList<>();

            for (PorudzbineArtikli pa : p.getArtikli()) {
                Artikal a = pa.getArtikal();
                porudzbinaKupcaDtoList.add(new PorudzbinaKupcaDto(a.getNaziv(), a.getCena(), pa.getKolicina()));
            }

            porudzbinaDto.setPorudzbineKupca(porudzbinaKupcaDtoList);
            porudzbinaDtoList.add(porudzbinaDto);
        }

        return new ResponseEntity(porudzbinaDtoList, HttpStatus.OK);
    }


    @PostMapping("/api/porudzbine")
    public ResponseEntity poruci(@RequestBody NovaPorudzbinaDto novaPorudzbinaDto, HttpSession session) {
        if (!sessionService.validateSession(session))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if (!sessionService.getRole(session).equals(Uloga.KUPAC))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        Hashtable<String, String> greska = new Hashtable<>();

        if (novaPorudzbinaDto.getIdRestorana() == 0)
            greska.put("IdRestorana", "ID mora biti razlicit od 0!");

        if (novaPorudzbinaDto.getNovePorudzbine() == null || novaPorudzbinaDto.getNovePorudzbine().size() == 0)
            greska.put("NovePorudzbine", "Korpa ne sme biti prazna!");

        if (novaPorudzbinaDto.getNovePorudzbine() != null)
            for (int i = 0; i < novaPorudzbinaDto.getNovePorudzbine().size(); i++) {
                NovaPorudzbinaKupcaDto artikal = novaPorudzbinaDto.getNovePorudzbine().get(i);

                if (artikal.getId() == null)
                    greska.put("Artikal [" + (i + 1) + "]", "Nedostaje id.");
                if (artikal.getKolicina() == 0)
                    greska.put("Artikal [" + (i + 1) + "]", "Broj artikala ne sme biti 0!");
            }


        if (!greska.isEmpty())
            return new ResponseEntity(greska, HttpStatus.BAD_REQUEST);

        try {
            porudzbinaService.sacuvajPorudzbinu(novaPorudzbinaDto, sessionService.getUsername(session));
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.status(HttpStatus.OK).body(novaPorudzbinaDto);
    }

    @DeleteMapping("/api/porudzbine/{id}")
    public ResponseEntity skiniIzKorpe(@PathVariable Long id, HttpSession session) {
        if (!sessionService.validateSession(session))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if (!sessionService.getRole(session).equals(Uloga.KUPAC))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        try {
            porudzbinaService.obrisiPorudzbinu(id);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.status(HttpStatus.OK).body(porudzbinaRepository.findAll());
    }


    @GetMapping("/api/porudzbine/lista")
    public ResponseEntity listaPorudzbina(HttpSession session) {
        if (!sessionService.validateSession(session))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if (!sessionService.getRole(session).equals(Uloga.KUPAC))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        Kupac kupac = null;

        for (Kupac temp : kupacRepository.findAll())
            if (temp.getUsername().equals(loggedKorisnik.getUsername()))
                kupac = temp;

        if (kupac == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        Set<PorudzbineArtikli> setPorudzbineArtikli = new HashSet<>();

        for (PorudzbineArtikli pa : porudzbineArtikliRepository.findAll())
            for (Porudzbina p : kupac.getPorudzbine())
                if (pa.getPorudzbina().equals(p)) {
                    setPorudzbineArtikli.add(pa);
                    break;
                }


        return ResponseEntity.status(HttpStatus.OK).body(setPorudzbineArtikli);
    }


    @GetMapping("/api/porudzbine/naruci/{id}")
    public ResponseEntity poruci(HttpSession session, @PathVariable(name = "id") Long id) {
        if (!sessionService.validateSession(session))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if (!sessionService.getRole(session).equals(Uloga.KUPAC))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        Kupac kupac = null;

        for (Kupac temp : kupacRepository.findAll())
            if (temp.getUsername().equals(loggedKorisnik.getUsername()))
                kupac = temp;

        PorudzbineArtikli pa = null;

        for (PorudzbineArtikli porudzbineArtikli : porudzbineArtikliRepository.findAll())
            if(porudzbineArtikli.getId() == id)
                pa = porudzbineArtikli;

        if (pa == null || kupac == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        Porudzbina narudzbina = pa.getPorudzbina();
        narudzbina.setKupac((Kupac) loggedKorisnik);
        narudzbina.setStatus(Status.obrada);
        porudzbinaRepository.saveAndFlush(narudzbina);


        return ResponseEntity.status(HttpStatus.OK).body("Vasa porudzbina je prihvacena!");

    }

    @GetMapping("/api/porudzbine/priprema/{id}")
    public ResponseEntity uPripremi(@PathVariable(name = "id") Long id, HttpSession session) {
        if (!sessionService.validateSession(session))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if (!sessionService.getRole(session).equals(Uloga.MENADZER))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        PorudzbineArtikli porudzbina = null;

        for (PorudzbineArtikli pa : porudzbineArtikliRepository.findAll())
            if (Objects.equals(id, pa.getId()))
                porudzbina = pa;

        Porudzbina p = porudzbina.getPorudzbina();

        if (porudzbina == null)
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if (p.getStatus() == Status.obrada) {
            p.setStatus(Status.u_pripremi);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        porudzbinaRepository.saveAndFlush(p);

        return ResponseEntity.status(HttpStatus.OK).body("Vasa porudzbina je sada u statusu: " + p.getStatus());
    }

    @GetMapping("/api/porudzbine/ceka/{id}")
    public ResponseEntity cekaDostavljaca(@PathVariable(name = "id") Long id, HttpSession session) {
        if (!sessionService.validateSession(session))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if (!sessionService.getRole(session).equals(Uloga.MENADZER))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        PorudzbineArtikli porudzbina = null;

        for (PorudzbineArtikli pa : porudzbineArtikliRepository.findAll())
            if (Objects.equals(id, pa.getId()))
                porudzbina = pa;

        Porudzbina p = porudzbina.getPorudzbina();

        if (porudzbina == null)
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if (p.getStatus() == Status.u_pripremi) {
            p.setStatus(Status.ceka_dostavljaca);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        porudzbinaRepository.saveAndFlush(p);

        return ResponseEntity.status(HttpStatus.OK).body("Vasa porudzbina je sada u statusu: " + p.getStatus());
    }

    @GetMapping("/api/porudzbine/transport/{id}")
    public ResponseEntity uTransportu(@PathVariable(name = "id") Long id, HttpSession session) {
        if (!sessionService.validateSession(session))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if (!sessionService.getRole(session).equals(Uloga.DOSTAVLJAC))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        PorudzbineArtikli porudzbina = null;

        for (PorudzbineArtikli pa : porudzbineArtikliRepository.findAll())
            if (Objects.equals(id, pa.getId()))
                porudzbina = pa;

        Porudzbina p = porudzbina.getPorudzbina();

        if (porudzbina == null)
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if (p.getStatus() == Status.ceka_dostavljaca) {
            p.setStatus(Status.u_transportu);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dostavljac jos uvek ceka porudzbinu.");
        }

        porudzbinaRepository.saveAndFlush(p);

        return ResponseEntity.status(HttpStatus.OK).body("Vasa porudzbina je sada u statusu: " + p.getStatus());
    }

    @GetMapping("/api/porudzbine/dostavljeno/{id}")
    public ResponseEntity dostavljeno(@PathVariable(name = "id") Long id, HttpSession session) {
        if (!sessionService.validateSession(session))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if (!sessionService.getRole(session).equals(Uloga.DOSTAVLJAC))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        PorudzbineArtikli porudzbina = null;

        for (PorudzbineArtikli pa : porudzbineArtikliRepository.findAll())
            if (id == pa.getId())
                porudzbina = pa;

        Porudzbina p = porudzbina.getPorudzbina();

        if (porudzbina == null)
            return new ResponseEntity(HttpStatus.FORBIDDEN);


        if (p.getStatus() == Status.u_transportu) {
            p.setStatus(Status.dostavljena);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dostavljac je doneo porudzbinu.");
        }

        Kupac kupac = p.getKupac();
        if(kupac == null)
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        kupac.setBodovi((int) (porudzbina.getUkupnaCena() / 1000 * 133));

        kupacRepository.saveAndFlush(kupac);
        porudzbinaRepository.saveAndFlush(p);

        return ResponseEntity.status(HttpStatus.OK).body("Vasa porudzbina je sada u statusu: " + p.getStatus());
    }


    @GetMapping("/api/porudzbine/dostava")
    public ResponseEntity zaDostavljanje(HttpSession session) {

        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            System.out.println("Nema sesije.");
            return ResponseEntity.ok(null);
        } else if (!loggedKorisnik.getUloga().equals(Uloga.DOSTAVLJAC)) {
            System.out.println("Pristup nije odobren.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pristup nije odobren.");
        }

        Dostavljac dostavljac = dostavljacRepository.getById(loggedKorisnik.getID());
        if (dostavljac.getUsername() == null || dostavljac.getUsername().toString().isEmpty())
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        List<PorudzbinaDto> porudzbinaDtoList = new ArrayList<>();

        for (Porudzbina p : dostavljac.getPorudzbine()) {
            if (p.getStatus() == Status.ceka_dostavljaca) {
                PorudzbinaDto porudzbinaDto = new PorudzbinaDto(p.getUuid(), p.getVreme_porudzbine());

                List<PorudzbinaKupcaDto> porudzbinaKupcaDtoList = new ArrayList<>();

                for (PorudzbineArtikli pa : p.getArtikli()) {
                    Artikal a = pa.getArtikal();
                    porudzbinaKupcaDtoList.add(new PorudzbinaKupcaDto(a.getNaziv(), a.getCena(), pa.getKolicina()));
                }

                porudzbinaDto.setPorudzbineKupca(porudzbinaKupcaDtoList);
                porudzbinaDtoList.add(porudzbinaDto);
            }
        }

        return new ResponseEntity(porudzbinaDtoList, HttpStatus.OK);
    }



}
