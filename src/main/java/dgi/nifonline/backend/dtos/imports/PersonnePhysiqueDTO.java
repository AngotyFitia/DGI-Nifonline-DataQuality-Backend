package dgi.nifonline.backend.dtos.imports;
import dgi.nifonline.backend.models.PersonnePhysique;
import dgi.nifonline.backend.models.Coordonnees;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class PersonnePhysiqueDTO {
    private Long id;
    private String nom;
    private String prenoms;
    private String dateNaissance;
    private String numeroCIN;
    private String numeroPasseport;
    private String sexe;

    private String email;
    private String telephone;
    private String telephoneSecondaire;
    private String adresse;
    private int codePostal;

    public PersonnePhysiqueDTO(PersonnePhysique pp) {
        this.id = pp.getIdPersonne();
        this.nom = pp.getNom();
        this.prenoms = pp.getPrenoms();
        this.dateNaissance = pp.getDateNaissance() != null ? pp.getDateNaissance().toString() : null;
        this.numeroCIN = pp.getNumeroCIN();
        this.numeroPasseport = pp.getNumeroPasseport();
        this.sexe = pp.getSexe() != null ? pp.getSexe().getIntitule() : null;

        Coordonnees c = pp.getPersonne().getCoordonnees();
        this.email = c.getEmail();
        this.telephone = c.getTelephone();
        this.telephoneSecondaire = c.getTelephoneSecondaire();
        this.adresse = c.getAdresse();
        this.codePostal = c.getCodePostal();
    }
}
