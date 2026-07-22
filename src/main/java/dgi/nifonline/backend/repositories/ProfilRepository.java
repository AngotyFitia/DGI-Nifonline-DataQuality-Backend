package dgi.nifonline.backend.repositories;

import dgi.nifonline.backend.models.Profil;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProfilRepository extends JpaRepository<Profil, Long> {
    Optional<Profil> findByIntitule(String intitule);
}
