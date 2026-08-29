package dgi.nifonline.backend.dtos.imports;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ActiviteDTO {
    private String codeActivite;
    private String secteurIntitule;
    private String intitule;
    private String section;
    private String etat;

    public void validate(int lineNumber) throws Exception {

        if(codeActivite == null || codeActivite.trim().isEmpty()) {
            throw new Exception("Erreur à la ligne " + lineNumber + " : code activite est obligatoire");
        }
        if (intitule == null || intitule.trim().isEmpty()) {
            throw new Exception("Erreur à la ligne " + lineNumber + " : intitule est obligatoire");
        }
        if (secteurIntitule == null || secteurIntitule.trim().isEmpty()) {
            throw new Exception("Erreur à la ligne " + lineNumber + " : secteur est obligatoire");
        }
        if (section == null || section.trim().isEmpty()) {
            throw new Exception("Erreur à la ligne " + lineNumber + " : secteur est obligatoire");
        }
        if(etat == null || etat.trim().isEmpty()) {
            throw new Exception("Erreur à la ligne " + lineNumber + " : etat est obligatoire");
        }
    }
}
