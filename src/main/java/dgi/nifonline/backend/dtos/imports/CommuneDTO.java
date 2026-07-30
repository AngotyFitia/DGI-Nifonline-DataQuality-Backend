package dgi.nifonline.backend.dtos.imports;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CommuneDTO {
    private String districtIntitule;
    private String intitule;
    private String etat;

    public void validate(int lineNumber) throws Exception {
        if (intitule == null || intitule.trim().isEmpty()) {
            throw new Exception("Erreur à la ligne " + lineNumber + " : intitule est obligatoire");
        }
        if (districtIntitule == null || districtIntitule.trim().isEmpty()) {
            throw new Exception("Erreur à la ligne " + lineNumber + " : district est obligatoire");
        }
    }
}
