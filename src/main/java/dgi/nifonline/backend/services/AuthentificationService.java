package dgi.nifonline.backend.services;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import dgi.nifonline.backend.dtos.RegisterRequestDTO;
import dgi.nifonline.backend.dtos.LoginRequestDTO;
import dgi.nifonline.backend.dtos.TokenResponseDTO;
import dgi.nifonline.backend.dtos.ExceptionDTO;
import dgi.nifonline.backend.dtos.ApiResponseDTO;
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
import java.time.LocalDateTime;


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

    public ApiResponseDTO register(RegisterRequestDTO request) {
        if (!reCaptcha.validate(request.getRecaptchaToken())) {
            return new ApiResponseDTO(false, "Veuillez actualiser la page et revérifier que vous êtes un humain.");
        }
    
        if (utilisateurRepository.findByEmail(request.getEmail()).isPresent()) {
            return new ApiResponseDTO(false, "Un utilisateur avec cet email existe déjà.");
        }
    
        Profil profil = profilRepository.findById(request.getIdProfil()).orElseThrow(() -> new RuntimeException("Profil introuvable"));
        Utilisateur user = new Utilisateur();
        user.setEmail(request.getEmail());
        user.setMotDePasse(encodePassword(request.getMotDePasse()));
        user.setEtat(0);
        user.setProfil(profil);
        user.setDateCreation(LocalDateTime.now());
        utilisateurRepository.save(user);
    
        return new ApiResponseDTO(true, "Compte créé avec succès !");
    }
    

    public TokenResponseDTO login(LoginRequestDTO request) {
        // if (!reCaptcha.validate(request.getRecaptchaToken())) {
        //     throw new RuntimeException("Veuillez confirmer que vous n'êtes pas un robot.");
        // }
    
        Utilisateur user = utilisateurRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Email ou mot de passe invalide."));
        if (user.getTentativesEchouees() >= 5) {
            throw new RuntimeException("Compte temporairement bloqué après trop de tentatives.");
        }
    
        if (!matches(request.getMotDePasse(), user.getMotDePasse())) {
            user.setTentativesEchouees(user.getTentativesEchouees() + 1);
            utilisateurRepository.save(user);
            throw new RuntimeException("Email ou mot de passe invalide.");
        }

        if(user.getEtat() == 0){
            throw new ExceptionDTO("Votre compte est encore en attente de validation", "warning");
        } else if(user.getEtat() == 5){
            throw new ExceptionDTO("Nous avons banni ce compte. Veuillez vous réinscrire.", "error");
        }        
    
        user.setTentativesEchouees(0);
        utilisateurRepository.save(user);
        String role = user.getProfil().getIntitule();
        String token = jwtUtil.generateToken(user.getEmail(), role);
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
