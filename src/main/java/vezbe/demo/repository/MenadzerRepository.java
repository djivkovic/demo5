package vezbe.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vezbe.demo.entity.Menadzer;

@Repository
public interface MenadzerRepository extends JpaRepository<Menadzer, Long>
{
    Menadzer getByUsername(String username);
}
