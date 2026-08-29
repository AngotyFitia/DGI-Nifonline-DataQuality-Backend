package dgi.nifonline.backend.repositories;

import dgi.nifonline.backend.models.TypeImpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface TypeImpotRepository extends JpaRepository<TypeImpot, Long> {
    Optional<TypeImpot> findByIntitule (String intitule);
    Page<TypeImpot> findByIntituleContainingIgnoreCase(String intitule, Pageable pageable);
    Page<TypeImpot> findByCodeContainingIgnoreCase(String code, Pageable pageable);
    Page<TypeImpot> findByEtatContainingIgnoreCase(int etat, Pageable pageable);
    Page<TypeImpot> findByIntituleContainingIgnoreCaseAndCodeContainingIgnoreCase(String intitule, String description, Pageable pageable);
    Page<TypeImpot> findByIntituleContainingIgnoreCaseAndCodeContainingIgnoreCaseAndEtat(String code, String intitule, int etat, Pageable pageable);
}
