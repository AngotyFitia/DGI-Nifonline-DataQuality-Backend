package dgi.nifonline.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.Setter;
import java.sql.Date;

@Entity
@Table(name="personne_physique")
@Getter @Setter
public class PersonnePhysique {
    
    @Id
    @Column(name="id_personne")
    private Long idPersonne;  

    @OneToOne
    @MapsId
    @JoinColumn(name="id_personne")
    private Personne personne;

    @Column(name="nom", nullable=false, length=255)
    private String nom;

    @Column(name="prenoms", length=255)
    private String prenoms;

    @Column(name="date_naissance")
    private Date dateNaissance;

    @Column(name="numero_cin", unique=true, length=50)
    private String numeroCIN;

    @Column(name="numero_passeport", length=50)
    private String numeroPasseport;

    @OneToOne
    @JoinColumn(name="id_sexe")
    private Sexe sexe;

    @OneToOne
    @JoinColumn(name="id_statut_matrimonial")
    private StatutMatrimonial statutMatrimonial;
}

