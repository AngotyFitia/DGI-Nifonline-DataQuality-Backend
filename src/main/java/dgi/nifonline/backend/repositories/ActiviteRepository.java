package dgi.nifonline.backend.repositories;

import dgi.nifonline.backend.models.Activite;
import dgi.nifonline.backend.models.Secteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActiviteRepository extends JpaRepository<Activite, Long> {
    Optional<Activite> findByIntituleAndSecteurAndSection (String intitule, Secteur secteur, String section);
}
