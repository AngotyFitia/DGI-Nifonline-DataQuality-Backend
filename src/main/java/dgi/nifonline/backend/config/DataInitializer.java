package dgi.nifonline.backend.config;

import dgi.nifonline.backend.models.Utilisateur;
import dgi.nifonline.backend.models.Profil;
import dgi.nifonline.backend.repositories.UtilisateurRepository;
import dgi.nifonline.backend.repositories.ProfilRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class DataInitializer {

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Bean
    CommandLineRunner initData(UtilisateurRepository utilisateurRepository,
                               ProfilRepository profilRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            Profil adminProfil = profilRepository.findByIntitule("administrateur") .orElseGet(() -> {
            Profil p = new Profil(); 
            p.setIntitule("administrateur");
            return profilRepository.save(p);
        });

            profilRepository.findByIntitule("chef").orElseGet(() -> {
                Profil p = new Profil();
                p.setIntitule("chef");
                return profilRepository.save(p);
            });

            profilRepository.findByIntitule("agent").orElseGet(() -> {
                Profil p = new Profil();
                p.setIntitule("agent");
                return profilRepository.save(p);
            });

            if (utilisateurRepository.findByEmail(adminEmail).isEmpty()) {
                Utilisateur admin = new Utilisateur();
                admin.setEmail(adminEmail);
                admin.setMotDePasse(passwordEncoder.encode(adminPassword));
                admin.setEtat(0);
                admin.setProfil(adminProfil);
                utilisateurRepository.save(admin);
                System.out.println("Compte administrateur créé au démarrage");
            }
        };
    }
}
