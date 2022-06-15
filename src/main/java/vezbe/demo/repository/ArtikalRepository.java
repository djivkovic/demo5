package vezbe.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vezbe.demo.entity.Artikal;

import java.util.Optional;

@Repository
public interface ArtikalRepository extends JpaRepository<Artikal, Long>
{

}
