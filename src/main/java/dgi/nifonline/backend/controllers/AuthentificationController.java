package dgi.nifonline.backend.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.http.ResponseEntity; 
import jakarta.validation.Valid; 

import dgi.nifonline.backend.dtos.RegisterRequestDTO; 
import dgi.nifonline.backend.dtos.LoginRequestDTO; 
import dgi.nifonline.backend.services.AuthentificationService; 

@RestController
@RequestMapping("/auth")
public class AuthentificationController {
    private final AuthentificationService authService;

    public AuthentificationController(AuthentificationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
