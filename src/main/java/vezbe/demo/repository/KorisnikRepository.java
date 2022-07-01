package vezbe.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vezbe.demo.entity.Korisnik;

import java.util.Optional;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long>
{
    Korisnik getByUsername(String username);
    Optional<Korisnik> findByUsername(@Param("username") String username);
    Korisnik getById(@Param("id") Long id);
    Korisnik getByName(String name);
    Korisnik getBySurname(String surname);
}
