package dgi.nifonline.backend.config;

import dgi.nifonline.backend.models.Utilisateur;
import dgi.nifonline.backend.models.Profil;
import dgi.nifonline.backend.models.Sexe;
import dgi.nifonline.backend.models.StatutMatrimonial;
import dgi.nifonline.backend.repositories.UtilisateurRepository;
import dgi.nifonline.backend.repositories.ProfilRepository;
import dgi.nifonline.backend.repositories.SexeRepository;
import dgi.nifonline.backend.repositories.StatutMatrimonialRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import java.time.LocalDateTime;


@Configuration
public class DataInitializer {

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${pepper}")
    private String pepper;

    @Bean
    CommandLineRunner initData(UtilisateurRepository utilisateurRepository,
                               ProfilRepository profilRepository,
                               SexeRepository sexeRepository,
                               StatutMatrimonialRepository statutMatrimonialRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            Profil adminProfil = profilRepository.findByIntitule("administrateur").orElseGet(() -> {
                Profil administrateur = new Profil();
                administrateur.setIntitule("administrateur");
                return profilRepository.save(administrateur);
            });

            profilRepository.findByIntitule("chef").orElseGet(() -> {
                Profil chef = new Profil();
                chef.setIntitule("chef");
                return profilRepository.save(chef);
            });

            profilRepository.findByIntitule("agent").orElseGet(() -> {
                Profil agent = new Profil();
                agent.setIntitule("agent");
                return profilRepository.save(agent);
            });

            sexeRepository.findByIntitule("Homme").orElseGet(() ->{
                Sexe homme = new Sexe();
                homme.setIntitule("Homme");
                return sexeRepository.save(homme);
            });
            sexeRepository.findByIntitule("Femme").orElseGet(() ->{
                Sexe femme = new Sexe();
                femme.setIntitule("Femme");
                return sexeRepository.save(femme);
            });
            statutMatrimonialRepository.findByIntitule("Marié(e)").orElseGet(() ->{
                StatutMatrimonial marié = new StatutMatrimonial();
                marié.setIntitule("Marié(e)");
                return statutMatrimonialRepository.save(marié);
            });
            statutMatrimonialRepository.findByIntitule("Célibrataire").orElseGet(() ->{
                StatutMatrimonial célibataire = new StatutMatrimonial();
                célibataire.setIntitule("Célibrataire");
                return statutMatrimonialRepository.save(célibataire);
            });
            statutMatrimonialRepository.findByIntitule("Divorcé(e)").orElseGet(() ->{
                StatutMatrimonial divorcé = new StatutMatrimonial();
                divorcé.setIntitule("Divorcé(e)");
                return statutMatrimonialRepository.save(divorcé);
            });
            statutMatrimonialRepository.findByIntitule("Veuf(ve)").orElseGet(() ->{
                StatutMatrimonial veuf = new StatutMatrimonial();
                veuf.setIntitule("Veuf(ve)");
                return statutMatrimonialRepository.save(veuf);
            });

            if (utilisateurRepository.findByEmail(adminEmail).isEmpty()) {
                Utilisateur admin = new Utilisateur();
                admin.setEmail(adminEmail);
                admin.setMotDePasse(passwordEncoder.encode(adminPassword + pepper));
                admin.setEtat(10);
                admin.setProfil(adminProfil);
                admin.setDateCreation(LocalDateTime.now());
                admin.setTentativesEchouees(0);
                utilisateurRepository.save(admin);
                System.out.println("Compte administrateur créé au démarrage avec mot de passe sécurisé");
            }
        };
    }
}
