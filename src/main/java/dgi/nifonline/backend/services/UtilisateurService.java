package dgi.nifonline.backend.services;
import dgi.nifonline.backend.models.Utilisateur;
import dgi.nifonline.backend.repositories.UtilisateurRepository;
import dgi.nifonline.backend.repositories.SessionTokenRepository;
import dgi.nifonline.backend.utils.JWTUtil;
import dgi.nifonline.backend.dtos.UtilisateurKpiDTO; 
import dgi.nifonline.backend.dtos.ProfilKpiDTO; 
import dgi.nifonline.backend.dtos.InscriptionsParMoisDTO; 
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Date;
import dgi.nifonline.backend.dtos.SecuriteKpiDTO;
import java.util.stream.Collectors;
import java.util.Map;
import java.time.format.DateTimeFormatter;
import org.springframework.format.annotation.DateTimeFormat;


@Service
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;
    private final JWTUtil jwtUtil;
    private final SessionTokenRepository sessionTokenRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository, JWTUtil jwtUtil, SessionTokenRepository sessionTokenRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.sessionTokenRepository = sessionTokenRepository;
        this.jwtUtil = jwtUtil;
    }

    public Utilisateur getCurrentUser(String token) {
        String email = jwtUtil.extractEmail(token);
        return utilisateurRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Page<Utilisateur> getUtilisateurs(String profil, String etat, String email, Pageable pageable) {
        boolean hasProfil = !profil.equals("tous");
        boolean hasEtat = !etat.equals("tous");
        boolean hasEmail = email != null && !email.isBlank();
    
        if (hasProfil && hasEtat && hasEmail) {
            int etatNum = mapEtatStringToInt(etat);
            return utilisateurRepository.findByProfil_IntituleAndEtatAndEmailContainingIgnoreCase(profil, etatNum, email, pageable);
        } else if (hasProfil && hasEmail) {
            return utilisateurRepository.findByProfil_IntituleAndEmailContainingIgnoreCase(profil, email, pageable);
        } else if (hasEtat && hasEmail) {
            int etatNum = mapEtatStringToInt(etat);
            return utilisateurRepository.findByEtatAndEmailContainingIgnoreCase(etatNum, email, pageable);
        } else if (hasEmail) {
            return utilisateurRepository.findByEmailContainingIgnoreCase(email, pageable);
        } else if (hasProfil && hasEtat) {
            int etatNum = mapEtatStringToInt(etat);
            return utilisateurRepository.findByProfil_IntituleAndEtat(profil, etatNum, pageable);
        } else if (hasProfil) {
            return utilisateurRepository.findByProfil_Intitule(profil, pageable);
        } else if (hasEtat) {
            int etatNum = mapEtatStringToInt(etat);
            return utilisateurRepository.findByEtat(etatNum, pageable);
        } else {
            return utilisateurRepository.findAll(pageable);
        }
    }
       
    
    private int mapEtatStringToInt(String etat) {
        return switch (etat.toLowerCase()) {
            case "actif" -> 10;
            case "inactif" -> 5;
            case "en attente" -> 0;
            default -> -1;
        };
    }

    public Utilisateur updateEtat(Long id, int nouvelEtat) {
        Utilisateur user = utilisateurRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setEtat(nouvelEtat); 
        return utilisateurRepository.save(user);
    }

    public UtilisateurKpiDTO getUtilisateurKpi() {
        UtilisateurKpiDTO dto = new UtilisateurKpiDTO();
        dto.setTotal(utilisateurRepository.count());
        dto.setActifs(utilisateurRepository.countByEtat(10));
        dto.setInactifs(utilisateurRepository.countByEtat(5));
        dto.setNouveaux7Jours(utilisateurRepository.countByDateCreationAfter(LocalDateTime.now().minusDays(7)));
        return dto;
    }

    public SecuriteKpiDTO getSecuriteKpi() {
        SecuriteKpiDTO dto = new SecuriteKpiDTO();
        dto.setTentativesEchouees(utilisateurRepository.sumTentativesEchouees());
        dto.setSessionsActives(sessionTokenRepository.countByExpirationAfter(new Date()));
        return dto;
    }
    
    public List<Utilisateur> getUtilisateursSuspects() {
        return utilisateurRepository.findByTentativesEchoueesGreaterThan(5);
    }    

    public ProfilKpiDTO getRepartitionParProfil() {
        Map<String, Long> repartition = utilisateurRepository.findAll().stream().collect(Collectors.groupingBy(
                u -> u.getProfil().getIntitule(),
                Collectors.counting()
            ));
        ProfilKpiDTO dto = new ProfilKpiDTO();
        dto.setRepartition(repartition);
        return dto;
    }

    public List<InscriptionsParMoisDTO> getInscriptionsParMoisRange(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);
        List<Utilisateur> utilisateurs = utilisateurRepository.findByDateCreationBetween(start, end);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM");
    
        return utilisateurs.stream()
            .collect(Collectors.groupingBy(
                u -> u.getDateCreation().format(formatter),
                Collectors.counting()
            ))
            .entrySet().stream()
            .map(e -> new InscriptionsParMoisDTO(e.getKey(), e.getValue()))
            .collect(Collectors.toList());
    }    
}

