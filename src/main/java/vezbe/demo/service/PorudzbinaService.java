package vezbe.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.dto.NovaPorudzbinaDto;
import vezbe.demo.dto.NovaPorudzbinaKupcaDto;
import vezbe.demo.entity.*;
import vezbe.demo.repository.KupacRepository;
import vezbe.demo.repository.PorudzbinaRepository;
import vezbe.demo.repository.PorudzbineArtikliRepository;
import vezbe.demo.repository.RestoranRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PorudzbinaService {

    @Autowired
    private PorudzbinaRepository porudzbinaRepository;

    @Autowired
    private KupacRepository kupacRepository;

    @Autowired
    private RestoranRepository restoranRepository;

    @Autowired
    private PorudzbineArtikliRepository porudzbineArtikliRepository;


    public void sacuvajPorudzbinu(NovaPorudzbinaDto novaPorudzbinaDto, String korisncikoname) throws Exception {
        Kupac kupac = kupacRepository.getByUsername(korisncikoname);
        Optional<Restoran> restoranOptional = restoranRepository.findById(novaPorudzbinaDto.getIdRestorana());

        if(restoranOptional.isEmpty())
            throw new Exception("Restoran ne postoji!");

        Porudzbina porudzbina = new Porudzbina(restoranOptional.get(), kupac);

        porudzbina.setArtikli(nadjiPorudzbinu(porudzbina, restoranOptional, novaPorudzbinaDto));
        porudzbinaRepository.save(porudzbina);

    }

    public void obrisiPorudzbinu(Long id) {
        Optional<PorudzbineArtikli> porudzbineArtikli = porudzbineArtikliRepository.findById(id);
        porudzbineArtikliRepository.delete(porudzbineArtikli.get());
        porudzbinaRepository.delete(porudzbineArtikli.get().getPorudzbina());
    }

    private Set<PorudzbineArtikli> nadjiPorudzbinu(Porudzbina porudzbina, Optional<Restoran> restoranOptional, NovaPorudzbinaDto novaPorudzbinaDto) throws Exception {
        Set<PorudzbineArtikli> porudzbineArtiklis = new HashSet<>();
        for(NovaPorudzbinaKupcaDto artikal : novaPorudzbinaDto.getNovePorudzbine()) {
            Artikal pronadjen = null;
            for(Artikal a : restoranOptional.orElse(null).getArtikli()) {
                if(a.getId().equals(artikal.getId())) {
                    pronadjen = a;
                    break;
                }
            }

            if(pronadjen == null)
                throw new Exception("Artikal sa id " + artikal.getId() + "nije pronadjen.");

            NovaPorudzbinaKupcaDto novaPorudzbinaKupcaDto = null;

            for(NovaPorudzbinaKupcaDto np : novaPorudzbinaDto.getNovePorudzbine())
                if(np != null)
                    novaPorudzbinaKupcaDto = np;
            PorudzbineArtikli pa = new PorudzbineArtikli(porudzbina, pronadjen, artikal.getKolicina(), pronadjen.getCena()*novaPorudzbinaKupcaDto.getKolicina());
            porudzbineArtiklis.add(pa);
            porudzbineArtikliRepository.save(pa);
        }


        return porudzbineArtiklis;
    }

}
