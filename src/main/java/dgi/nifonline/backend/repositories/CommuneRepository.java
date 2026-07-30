package dgi.nifonline.backend.repositories;

import dgi.nifonline.backend.models.Commune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommuneRepository extends JpaRepository<Commune, Long> {
    Optional<Commune> findByIntitule(String intitule);
}
