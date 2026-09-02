package dgi.nifonline.backend.repositories;

import dgi.nifonline.backend.models.PersonnePhysique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface PersonnePhysiqueRepository extends JpaRepository<PersonnePhysique, Long> {

    @Query("SELECT pp FROM PersonnePhysique pp " +"JOIN FETCH pp.personne p " +"JOIN FETCH p.coordonnees c")
    List<PersonnePhysique> findAllWithCoordonnees();

}