package dgi.nifonline.backend.repositories;

import dgi.nifonline.backend.models.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    Optional<Province> findByIntitule(String intitule);
}
