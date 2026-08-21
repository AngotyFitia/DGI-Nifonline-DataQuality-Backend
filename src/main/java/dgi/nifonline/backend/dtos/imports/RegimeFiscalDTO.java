package dgi.nifonline.backend.dtos.imports;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class RegimeFiscalDTO {
    private String intitule;
    private String description;
    private String etat;

    public void validate(int lineNumber) throws Exception {

        if (intitule == null || intitule.trim().isEmpty()) {
            throw new Exception("Erreur à la ligne " + lineNumber + " : intitule est obligatoire");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new Exception("Erreur à la ligne " + lineNumber + " : description est obligatoire");
        }
        if(etat == null || etat.trim().isEmpty()) {
            throw new Exception("Erreur à la ligne " + lineNumber + " : etat est obligatoire");
        }
    }
}
