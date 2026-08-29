package dgi.nifonline.backend.dtos.imports;

import lombok.AllArgsConstructor;
import dgi.nifonline.backend.models.RegimeFiscal;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class RegimeFiscalDTO {

    private String intitule;
    private String description;
    private int etat;
    private String etatImport;
    private String etatCouleur;
    private String etatIntitule;

    public RegimeFiscalDTO(RegimeFiscal regimeFiscal) {
        this.intitule = regimeFiscal.getIntitule();
        this.description = regimeFiscal.getDescription();
        this.etat = regimeFiscal.getEtat(); 
        switch (regimeFiscal.getEtat()) {
            case 0 -> {
                this.etatIntitule = "en attente";
                this.etatCouleur = "text-yellow-600";
            }
            case 1 -> {
                this.etatIntitule = "validé";
                this.etatCouleur = "text-green-600";
            }
            case -1 -> {
                this.etatIntitule = "refusé";
                this.etatCouleur = "text-red-600";
            }
            default -> {
                this.etatIntitule = "inconnu";
                this.etatCouleur = "text-gray-600";
            }
        }
    }

    public RegimeFiscalDTO(String intitule, String description, String etatImport) {
        this.intitule = intitule;
        this.description = description;
        this.etatImport = etatImport;
    }

    public void validate(int lineNumber) throws Exception {

        if (intitule == null || intitule.trim().isEmpty()) {
            throw new Exception("Erreur à la ligne " + lineNumber + " : intitule est obligatoire");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new Exception("Erreur à la ligne " + lineNumber + " : description est obligatoire");
        }
        if(etatImport == null || etatImport.trim().isEmpty()) {
            throw new Exception("Erreur à la ligne " + lineNumber + " : etatImport est obligatoire");
        }
    }
}
