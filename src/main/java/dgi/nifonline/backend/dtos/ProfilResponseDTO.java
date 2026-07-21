package dgi.nifonline.backend.dtos;

import dgi.nifonline.backend.models.Profil;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProfilResponseDTO {
    private Long id;
    private String intitule;

    public ProfilResponseDTO(Profil profil) {
        this.id = profil.getIdProfil();
        this.intitule = profil.getIntitule();
    }
}
