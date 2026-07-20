package dgi.nifonline.backend.dtos;

import lombok.Getter;
import lombok.Setter;
import dgi.nifonline.backend.models.Utilisateur;

@Getter @Setter
public class UserResponseDTO {
    private Long id;
    private String email;
    private int etat;
    private String profil;

    public UserResponseDTO(Utilisateur user) {
        this.id = user.getIdUtilisateur();
        this.email = user.getEmail();
        this.etat = user.getEtat();
        this.profil = user.getProfil().getIntitule();
    }

}
