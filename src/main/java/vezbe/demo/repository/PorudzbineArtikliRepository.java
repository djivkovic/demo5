package vezbe.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vezbe.demo.entity.PorudzbineArtikli;

@Repository
public interface PorudzbineArtikliRepository extends JpaRepository<PorudzbineArtikli, Long> {
}

