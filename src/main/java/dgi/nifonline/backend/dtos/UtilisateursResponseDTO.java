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
        this.profil = new ProfilResponseDTO(user.getProfil());
        switch (user.getEtat()) {
            case 0 -> {
                this.etatIntitule = "en attente";
                this.etatCouleur = "text-yellow-600";
            }
            case 5 -> {
                this.etatIntitule = "inactif";
                this.etatCouleur = "text-red-600";
            }
            case 10 -> {
                this.etatIntitule = "actif";
                this.etatCouleur = "text-green-600";
            }
            default -> {
                this.etatIntitule = "inconnu";
                this.etatCouleur = "text-gray-600";
            }
        }
    }

}
