package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.entity.Komentar;
import vezbe.demo.repository.KomentarRepository;

@Service
public class KomentarService {
    @Autowired
    KomentarRepository komentarRepository;
    public void sacuvaj(Komentar komentar){
        komentarRepository.save(komentar);
    }
}
