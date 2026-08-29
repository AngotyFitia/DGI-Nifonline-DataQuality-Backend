package dgi.nifonline.backend.repositories;

import dgi.nifonline.backend.models.CentreGestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CentreGestionnaireRepository extends JpaRepository<CentreGestionnaire, Long> {
    Optional<CentreGestionnaire> findByCodeBureau(String codeBureau);
}