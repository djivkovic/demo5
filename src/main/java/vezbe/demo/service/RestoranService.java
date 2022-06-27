package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.entity.Restoran;
import vezbe.demo.repository.RestoranRepository;

import java.util.List;

@Service
public class RestoranService {
    @Autowired
    private RestoranRepository restoranRepository;

    public void dodaj(Restoran restoran) throws Exception {
        invalid(restoran.getNaziv(), restoranRepository.findAll());
    }
    private void invalid(String naziv, List<Restoran> restoranList) throws Exception {
        for(Restoran r : restoranList)
            if(r.getNaziv().equals(naziv))
                pogresanNaziv(naziv);
    }
    private void pogresanNaziv(String naziv) throws Exception {
        throw new Exception("Restoran sa imenom '" + naziv + "' vec postoji!");
    }

    public Restoran save(Restoran restoran){
        return restoranRepository.save(restoran);
    }
}
