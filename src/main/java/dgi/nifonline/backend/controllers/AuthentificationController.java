package dgi.nifonline.backend.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.http.ResponseEntity; 
import jakarta.validation.Valid; 
import dgi.nifonline.backend.dtos.RegisterRequestDTO; 
import dgi.nifonline.backend.dtos.LoginRequestDTO; 
import dgi.nifonline.backend.dtos.UserResponseDTO;
import dgi.nifonline.backend.services.AuthentificationService;
import dgi.nifonline.backend.services.UserService;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import dgi.nifonline.backend.models.Utilisateur;

@RestController
@RequestMapping("/auth")
public class AuthentificationController {
    private final AuthentificationService authService;
    private final UserService userService;

    public AuthentificationController(AuthentificationService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        authService.logout(token);
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Utilisateur user = userService.getCurrentUser(token);
        return ResponseEntity.ok(new UserResponseDTO(user));
    }

}
