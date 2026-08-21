package dgi.nifonline.backend.repositories;

import dgi.nifonline.backend.models.Commune;
import dgi.nifonline.backend.models.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface CommuneRepository extends JpaRepository<Commune, Long> {
    Optional<Commune> findByIntituleAndDistrict(String intitule, District district);
    Page<Commune> findAll(Pageable pageable);
    Page<Commune> findByDistrict_Region_Province_Intitule(String province, Pageable pageable);
    Page<Commune> findByDistrict_Region_Intitule(String region, Pageable pageable);
    Page<Commune> findByDistrict_Intitule(String district, Pageable pageable);
    Page<Commune> findByDistrict_Region_Province_IntituleAndDistrict_Region_Intitule(String province, String region, Pageable pageable);
    Page<Commune> findByDistrict_Region_Province_IntituleAndDistrict_Region_IntituleAndDistrict_Intitule(String province, String region, String district, Pageable pageable);
}