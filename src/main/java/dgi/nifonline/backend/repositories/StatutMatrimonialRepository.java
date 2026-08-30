package dgi.nifonline.backend.repositories;

import dgi.nifonline.backend.models.StatutMatrimonial;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface StatutMatrimonialRepository extends JpaRepository<StatutMatrimonial, Long> {
    Optional<StatutMatrimonial> findByIntitule(String intitule);
}
