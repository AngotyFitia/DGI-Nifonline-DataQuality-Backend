package dgi.nifonline.backend.controllers;

import dgi.nifonline.backend.models.Commune;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.security.access.prepost.PreAuthorize;
import dgi.nifonline.backend.services.imports.CommuneService;
import dgi.nifonline.backend.dtos.CommuneListeDTO;
import java.util.List;

@RestController
@RequestMapping("/api/communes")
public class CommuneController {

    private final CommuneService communeService;

    public CommuneController(CommuneService communeService) {
        this.communeService = communeService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('administrateur')")
    public List<CommuneListeDTO> getCommunes() {
        return communeService.getCommunesAvecHierarchie();
    }
}
