package dgi.nifonline.backend.services;
import dgi.nifonline.backend.models.Utilisateur;
import dgi.nifonline.backend.repositories.UtilisateurRepository;
import dgi.nifonline.backend.utils.JWTUtil;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;
    private final JWTUtil jwtUtil;

    public UtilisateurService(UtilisateurRepository utilisateurRepository, JWTUtil jwtUtil) {
        this.utilisateurRepository = utilisateurRepository;
        this.jwtUtil = jwtUtil;
    }

    public Utilisateur getCurrentUser(String token) {
        String email = jwtUtil.extractEmail(token);
        return utilisateurRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }
}

