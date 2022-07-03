package vezbe.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vezbe.demo.entity.Dostavljac;
import vezbe.demo.entity.Korisnik;

@Repository
public interface DostavljacRepository extends JpaRepository<Dostavljac, Long>
{
    Dostavljac getByUsername(String username);
}
