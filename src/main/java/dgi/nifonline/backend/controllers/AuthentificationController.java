package dgi.nifonline.backend.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.http.ResponseEntity; 
import jakarta.validation.Valid; 
import dgi.nifonline.backend.dtos.RegisterRequestDTO; 
import dgi.nifonline.backend.dtos.LoginRequestDTO; 
import dgi.nifonline.backend.dtos.UtilisateursResponseDTO;
import dgi.nifonline.backend.dtos.ApiResponseDTO;
import dgi.nifonline.backend.dtos.ProfilResponseDTO;
import dgi.nifonline.backend.services.AuthentificationService;
import dgi.nifonline.backend.services.UtilisateurService;
import dgi.nifonline.backend.services.ProfilService;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import dgi.nifonline.backend.models.Utilisateur;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthentificationController {
    private final AuthentificationService authService;
    private final UtilisateurService utilisateurService;
    private final ProfilService profilService;

    public AuthentificationController(AuthentificationService authService, UtilisateurService utilisateurService, ProfilService profilService) {
        this.authService = authService;
        this.utilisateurService = utilisateurService;
        this.profilService = profilService;
    }

    @GetMapping("/profils")
    public ResponseEntity<List<ProfilResponseDTO>> getAllProfils() {
        List<ProfilResponseDTO> profils = profilService.getProfilsAgentEtChef().stream().map(ProfilResponseDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(profils);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        ApiResponseDTO response = authService.register(request);
        if (response.isSuccess()) { 
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        authService.logout(token);
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/me")
    public ResponseEntity<UtilisateursResponseDTO> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Utilisateur user = utilisateurService.getCurrentUser(token);
        return ResponseEntity.ok(new UtilisateursResponseDTO(user));
    }

}
