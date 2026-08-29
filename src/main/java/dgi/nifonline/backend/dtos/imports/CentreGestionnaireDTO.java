package dgi.nifonline.backend.dtos.imports;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CentreGestionnaireDTO {

    private String coordonnees;
    private String codeBureau;
    private String abreviation;
    private String nom;
    private String compteBancaire;
    private int etat;
    private String etatImport;

    public CentreGestionnaireDTO(String coordonnees, String codeBureau, String abreviation, String nom, String compteBancaire, String etatImport){
        this.coordonnees=coordonnees;
        this.codeBureau=codeBureau;
        this.abreviation=abreviation;
        this.nom=nom;
        this.compteBancaire=compteBancaire;
        this.etatImport=etatImport;
    }
}
