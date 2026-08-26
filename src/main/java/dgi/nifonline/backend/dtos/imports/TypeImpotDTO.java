package dgi.nifonline.backend.dtos.imports;

import lombok.AllArgsConstructor;
import dgi.nifonline.backend.models.TypeImpot;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class TypeImpotDTO {

    private String code;
    private String intitule;
    private int etat;
    private String etatImport;
    private String etatCouleur;
    private String etatIntitule;

    public TypeImpotDTO(TypeImpot typeImpot) {
        this.code = typeImpot.getCode();
        this.intitule = typeImpot.getIntitule();
        this.etat = typeImpot.getEtat(); 
        switch (typeImpot.getEtat()) {
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

    public TypeImpotDTO(String code, String intitule, String etatImport) {
        this.code = code;
        this.intitule = intitule;
        this.etatImport = etatImport;
    }

    public void validate(int lineNumber) throws Exception {

        if (intitule == null || intitule.trim().isEmpty()) {
            throw new Exception("Erreur à la ligne " + lineNumber + " : intitule est obligatoire");
        }
        if (code == null || code.trim().isEmpty()) {
            throw new Exception("Erreur à la ligne " + lineNumber + " : code est obligatoire");
        }
        if(etatImport == null || etatImport.trim().isEmpty()) {
            throw new Exception("Erreur à la ligne " + lineNumber + " : etatImport est obligatoire");
        }
    }
}
