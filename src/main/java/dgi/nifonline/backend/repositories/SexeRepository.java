package dgi.nifonline.backend.repositories;

import dgi.nifonline.backend.models.Sexe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface SexeRepository extends JpaRepository<Sexe, Long> {
    Optional<Sexe> findByIntitule(String intitule);
}
