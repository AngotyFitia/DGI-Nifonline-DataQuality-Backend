package dgi.nifonline.backend.services;

import dgi.nifonline.backend.models.Profil;
import dgi.nifonline.backend.repositories.ProfilRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfilService {
    private final ProfilRepository profilRepository;

    public ProfilService(ProfilRepository profilRepository) {
        this.profilRepository = profilRepository;
    }

    public List<Profil> getProfilsAgentEtChef() {
        return profilRepository.findAll().stream()
                .filter(p -> p.getIntitule().equalsIgnoreCase("agent")|| p.getIntitule().equalsIgnoreCase("chef")).toList();
    }
}
