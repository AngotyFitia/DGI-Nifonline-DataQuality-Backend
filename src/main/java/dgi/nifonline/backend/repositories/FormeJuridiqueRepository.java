package dgi.nifonline.backend.repositories;

import dgi.nifonline.backend.models.FormeJuridique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface FormeJuridiqueRepository extends JpaRepository<FormeJuridique, Long> {
    Optional<FormeJuridique> findByIntitule (String intitule);
    Page<FormeJuridique> findByIntituleContainingIgnoreCase(String intitule, Pageable pageable);
    Page<FormeJuridique> findByAbreviationContainingIgnoreCase(String abreviation, Pageable pageable);
    Page<FormeJuridique> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
    Page<FormeJuridique> findByEtatContainingIgnoreCase(int etat, Pageable pageable);
    Page<FormeJuridique> findByIntituleContainingIgnoreCaseAndDescriptionContainingIgnoreCase(String intitule, String description, Pageable pageable);
    Page<FormeJuridique> findByAbreviationContainingIgnoreCaseAndIntituleContainingIgnoreCaseAndDescriptionContainingIgnoreCaseAndEtat(String abreviation, String intitule, String description, int etat, Pageable pageable);
}
