package dgi.nifonline.backend.dtos.imports;

import lombok.AllArgsConstructor;
import dgi.nifonline.backend.models.FormeJuridique;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class FormeJuridiqueDTO {

    private String abreviation;
    private String intitule;
    private String description;
    private int etat;
    private String etatImport;
    private String etatCouleur;
    private String etatIntitule;

    public FormeJuridiqueDTO(FormeJuridique formeJuridique) {
        this.abreviation = formeJuridique.getAbreviation();
        this.intitule = formeJuridique.getIntitule();
        this.description = formeJuridique.getDescription();
        this.etat = formeJuridique.getEtat(); 
        switch (formeJuridique.getEtat()) {
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

    public FormeJuridiqueDTO(String abreviation, String intitule, String description, String etatImport) {
        this.abreviation = abreviation;
        this.intitule = intitule;
        this.description = description;
        this.etatImport = etatImport;
    }

    public void validate(int lineNumber) throws Exception {
        
        if (abreviation == null || abreviation.trim().isEmpty()) {
            throw new Exception("Erreur à la ligne " + lineNumber + " : abreviation est obligatoire");
        }
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
