package vezbe.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vezbe.demo.entity.Porudzbina;



@Repository
public interface PorudzbinaRepository extends JpaRepository<Porudzbina, Long>
{

}

