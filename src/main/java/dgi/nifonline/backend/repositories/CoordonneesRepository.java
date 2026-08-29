package dgi.nifonline.backend.repositories;

import dgi.nifonline.backend.models.Coordonnees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface CoordonneesRepository extends JpaRepository<Coordonnees, Long> {
    Optional<Coordonnees> findByEmail(String email);
    Optional<Coordonnees> findByEmailAndCommune(String email);
}