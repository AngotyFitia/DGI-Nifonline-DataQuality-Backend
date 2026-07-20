package dgi.nifonline.backend.controllers;

import dgi.nifonline.backend.models.Utilisateur;
import dgi.nifonline.backend.repositories.UtilisateurRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import dgi.nifonline.backend.services.UtilisateurService;
import java.util.List;

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
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurService.getAllUtilisateurs();
    }
}
