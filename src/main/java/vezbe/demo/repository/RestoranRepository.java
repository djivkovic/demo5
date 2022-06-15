package vezbe.demo.repository;





import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vezbe.demo.entity.Artikal;
import vezbe.demo.entity.Lokacija;
import vezbe.demo.entity.Restoran;

import java.util.Set;


@Repository
public interface RestoranRepository extends JpaRepository<Restoran, Long>
{

    Restoran getByNaziv(String naziv);

    Restoran getByTip(String tip);

}

