package vezbe.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vezbe.demo.entity.Dostavljac;

@Repository
public interface DostavljacRepository extends JpaRepository<Dostavljac, Long>
{
}
