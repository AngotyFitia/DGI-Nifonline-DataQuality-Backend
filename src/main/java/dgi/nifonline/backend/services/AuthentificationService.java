package dgi.nifonline.backend.services;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import dgi.nifonline.backend.dtos.RegisterRequestDTO;
import dgi.nifonline.backend.dtos.LoginRequestDTO;
import dgi.nifonline.backend.dtos.TokenResponseDTO;
import dgi.nifonline.backend.models.Utilisateur;
import dgi.nifonline.backend.models.Profil;
import dgi.nifonline.backend.repositories.UtilisateurRepository;
import dgi.nifonline.backend.utils.JWTUtil;
import dgi.nifonline.backend.utils.ReCaptcha;
import org.springframework.beans.factory.annotation.Value;
import dgi.nifonline.backend.models.SessionToken;
import java.util.Date;
import dgi.nifonline.backend.repositories.SessionTokenRepository;
import dgi.nifonline.backend.repositories.ProfilRepository;

@Service
public class AuthentificationService {
    private final UtilisateurRepository utilisateurRepository;
    private final SessionTokenRepository sessionTokenRepository;
    private final ProfilRepository profilRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final ReCaptcha reCaptcha;

    @Value("${pepper}")
    private String pepper;

    public AuthentificationService(UtilisateurRepository utilisateurRepository, SessionTokenRepository sessionTokenRepository, ProfilRepository profilRepository,  PasswordEncoder passwordEncoder, JWTUtil jwtUtil, ReCaptcha reCaptcha) {
        this.utilisateurRepository= utilisateurRepository;
        this.passwordEncoder= passwordEncoder;
        this.jwtUtil= jwtUtil;
        this.reCaptcha= reCaptcha;
        this.sessionTokenRepository= sessionTokenRepository;
        this.profilRepository= profilRepository;
    }

    private String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword + pepper);
    }

    private boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword + pepper, encodedPassword);
    }

    public String register(RegisterRequestDTO request) {
        // if (!reCaptcha.validate(request.getRecaptchaToken())) {
        //     throw new RuntimeException("Invalid captcha");
        // }
        Profil profil= profilRepository.findById(request.getIdProfil()).orElseThrow(() -> new RuntimeException("Profil not found"));
        Utilisateur user= new Utilisateur();
        user.setEmail(request.getEmail());
        user.setMotDePasse(encodePassword(request.getMotDePasse()));
        user.setEtat(0);
        user.setProfil(profil);
        utilisateurRepository.save(user);
        return "User registered securely";
    }

    public TokenResponseDTO login(LoginRequestDTO request) {
        Utilisateur user= utilisateurRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Email ou mot de passe invalide."));
        if (!matches(request.getMotDePasse(), user.getMotDePasse())) {
            throw new RuntimeException("Email ou mot de passe invalide.");
        }
    
        String role= user.getProfil().getIntitule();
        String token= jwtUtil.generateToken(user.getEmail(), role);
        SessionToken session = new SessionToken();
        session.setToken(token);
        session.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60));
        session.setUtilisateur(user);
        sessionTokenRepository.save(session);
    
        return new TokenResponseDTO(token);
    }
    
    public void logout(String token) {
        sessionTokenRepository.deleteByToken(token);
    }    
    
}
