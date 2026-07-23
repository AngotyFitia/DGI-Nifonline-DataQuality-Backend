package dgi.nifonline.backend.controllers;

import dgi.nifonline.backend.models.Utilisateur;
import dgi.nifonline.backend.repositories.UtilisateurRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import dgi.nifonline.backend.services.UtilisateurService;
import dgi.nifonline.backend.dtos.UtilisateursResponseDTO;
import dgi.nifonline.backend.dtos.UtilisateurKpiDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import dgi.nifonline.backend.dtos.SecuriteKpiDTO;
import dgi.nifonline.backend.dtos.ProfilKpiDTO;
import dgi.nifonline.backend.dtos.InscriptionsParMoisDTO;
import java.util.stream.Collectors;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurRepository utilisateurRepository, UtilisateurService utilisateurService) {
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('administrateur')")
    public Page<UtilisateursResponseDTO> getUtilisateurs( @RequestParam(defaultValue = "tous") String profil, @RequestParam(defaultValue = "tous") String etat, @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "") String email) {
        Pageable pageable = PageRequest.of(page, size);
        return utilisateurService.getUtilisateurs(profil, etat, email, pageable).map(UtilisateursResponseDTO::new);
    }


    @PutMapping("/{id}/etat")
    @PreAuthorize("hasAuthority('administrateur')")
    public UtilisateursResponseDTO updateEtat(@PathVariable Long id, @RequestParam int etat) {
        Utilisateur utilisateurAcces = utilisateurService.updateEtat(id, etat);
        return new UtilisateursResponseDTO(utilisateurAcces);
    }

    @GetMapping("/kpi")
    @PreAuthorize("hasAuthority('administrateur')")
    public UtilisateurKpiDTO getUtilisateurKpi() {
        return utilisateurService.getUtilisateurKpi();
    }

    @GetMapping("/kpi-securite")
    @PreAuthorize("hasAuthority('administrateur')")
    public SecuriteKpiDTO getSecuriteKpi() {
        return utilisateurService.getSecuriteKpi();
    }

    @GetMapping("/alertes-securite")
    @PreAuthorize("hasAuthority('administrateur')")
    public List<UtilisateursResponseDTO> getAlertesSecurite() {
        return utilisateurService.getUtilisateursSuspects().stream().map(UtilisateursResponseDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/kpi-profil")
    @PreAuthorize("hasAuthority('administrateur')")
    public ProfilKpiDTO getRepartitionParProfil() {
        return utilisateurService.getRepartitionParProfil();
    }

    @GetMapping("/kpi-inscriptions-range")
    @PreAuthorize("hasAuthority('administrateur')")
    public List<InscriptionsParMoisDTO> getInscriptionsParMoisRange(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return utilisateurService.getInscriptionsParMoisRange(startDate, endDate);
    }
    
}
