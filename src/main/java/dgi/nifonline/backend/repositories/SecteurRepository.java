package dgi.nifonline.backend.repositories;

import dgi.nifonline.backend.models.Secteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecteurRepository extends JpaRepository<Secteur, Long> {
    Optional<Secteur> findByIntitule(String intitule);
}
