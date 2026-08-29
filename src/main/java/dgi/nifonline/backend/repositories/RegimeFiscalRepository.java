package dgi.nifonline.backend.repositories;

import dgi.nifonline.backend.models.RegimeFiscal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface RegimeFiscalRepository extends JpaRepository<RegimeFiscal, Long> {
    Optional<RegimeFiscal> findByIntitule (String intitule);
    Page<RegimeFiscal> findByIntituleContainingIgnoreCase(String intitule, Pageable pageable);
    Page<RegimeFiscal> findByIntituleContainingIgnoreCaseAndDescriptionContainingIgnoreCase(String intitule, String description, Pageable pageable);
    Page<RegimeFiscal> findByIntituleContainingIgnoreCaseAndDescriptionContainingIgnoreCaseAndEtat(String intitule, String description, int etat, Pageable pageable);
}
