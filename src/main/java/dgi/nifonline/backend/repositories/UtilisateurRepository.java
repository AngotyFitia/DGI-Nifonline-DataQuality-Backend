package dgi.nifonline.backend.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dgi.nifonline.backend.models.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    long count();
    long countByEtat(int etat); 
    long countByDateCreationAfter(LocalDateTime date);
    
    
    Optional<Utilisateur> findByEmail(String email);

    Page<Utilisateur> findByEmailContainingIgnoreCase(String email, Pageable pageable);
    Page<Utilisateur> findByProfil_IntituleAndEmailContainingIgnoreCase(String profil, String email, Pageable pageable);
    Page<Utilisateur> findByEtatAndEmailContainingIgnoreCase(int etat, String email, Pageable pageable);
    Page<Utilisateur> findByProfil_IntituleAndEtatAndEmailContainingIgnoreCase(String profil, int etat, String email, Pageable pageable);

    Page<Utilisateur> findByProfil_Intitule(String profil, Pageable pageable);
    Page<Utilisateur> findByEtat(int etat, Pageable pageable);
    Page<Utilisateur> findByProfil_IntituleAndEtat(String profil, int etat, Pageable pageable);

    @Query("SELECT SUM(u.tentativesEchouees) FROM Utilisateur u")
    long sumTentativesEchouees();

    List<Utilisateur> findByTentativesEchoueesGreaterThan(int tentatives);

    List<Utilisateur> findByDateCreationBetween(LocalDateTime start, LocalDateTime end);

    List<Utilisateur> findTop5ByOrderByTentativesEchoueesDesc();
}


