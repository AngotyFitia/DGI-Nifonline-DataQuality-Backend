package dgi.nifonline.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="centre_gestionnaire")
@Getter @Setter
public class CentreGestionnaire {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_centre_gestionnaire")
    private Long idCentreGestionnaire;

    @Column(name="code_bureau", nullable=false, length=10)
    private String codeBureau;

    @Column(name="abreviation", nullable=false, length=255)
    private String abreviation;

    @Column(name = "nom_centre_gestionnaire", nullable=true, length=255)
    private String nom;

    @Column(name="compte_bancaire", nullable=true, length=30)
    private String compteBancaire;
    
    @Column(name="etat", nullable=false)
    private int etat;

    @ManyToOne
    @JoinColumn(name="id_coordonnees", nullable=false)
    private Coordonnees coordonnees;
}
