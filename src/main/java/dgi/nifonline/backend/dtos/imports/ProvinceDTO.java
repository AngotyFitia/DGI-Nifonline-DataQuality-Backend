package dgi.nifonline.backend.dtos.imports;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ProvinceDTO {
    private String intitule;
    private String etat;

    public void validate(int lineNumber) throws Exception {
        if (intitule == null || intitule.trim().isEmpty()) {
            throw new Exception("Erreur à la ligne " + lineNumber + " : intitule est obligatoire");
        }
        if (intitule.length() > 255) {
            throw new Exception("Erreur à la ligne " + lineNumber + " : intitule trop long (>255 caractères)");
        }
        if(etat == null || etat.trim().isEmpty()) {
            throw new Exception("Erreur à la ligne " + lineNumber + " : etat est obligatoire");
        }
    }
}
