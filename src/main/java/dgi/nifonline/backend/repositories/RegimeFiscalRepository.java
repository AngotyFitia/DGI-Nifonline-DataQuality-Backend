package dgi.nifonline.backend.repositories;

import dgi.nifonline.backend.models.RegimeFiscal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegimeFiscalRepository extends JpaRepository<RegimeFiscal, Long> {
    Optional<RegimeFiscal> findByIntitule (String intitule);
}
