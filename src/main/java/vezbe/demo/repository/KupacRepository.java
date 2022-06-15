package vezbe.demo.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vezbe.demo.entity.Korisnik;
import vezbe.demo.entity.Kupac;

@Repository
public interface KupacRepository extends JpaRepository<Kupac, Long>
{
    Kupac getByUsername(String username);

}
