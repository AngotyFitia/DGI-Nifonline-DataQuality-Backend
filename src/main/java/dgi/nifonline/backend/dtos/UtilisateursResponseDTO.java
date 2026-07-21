package dgi.nifonline.backend.dtos;

import lombok.Getter;
import lombok.Setter;
import dgi.nifonline.backend.models.Utilisateur;
import dgi.nifonline.backend.dtos.ProfilResponseDTO;

@Getter @Setter
public class UtilisateursResponseDTO {
    private Long id;
    private String email;
    private int etat;
    private ProfilResponseDTO profil;
    private String etatCouleur;
    private String etatIntitule;

    public UtilisateursResponseDTO(Utilisateur user) {
        this.id = user.getIdUtilisateur();
        this.email = user.getEmail();
        this.etat = user.getEtat();
        this.etatIntitule = user.getEtat() == 10 ? "actif" : "inactif";
        this.etatCouleur = user.getEtat() == 10 ? "text-green-600" : "text-red-600";
        this.profil = new ProfilResponseDTO(user.getProfil());
    }

}
