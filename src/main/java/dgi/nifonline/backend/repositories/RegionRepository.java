package dgi.nifonline.backend.repositories;

import dgi.nifonline.backend.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    Optional<Region> findByIntitule(String intitule);
}
