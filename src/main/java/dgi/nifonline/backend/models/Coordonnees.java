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
@Table(name="coordonnees")
@Getter @Setter
public class Coordonnees {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_coordonnees")
    private Long idCoordonnees;

    @Column(name="email", nullable=false, length=255)
    private String email;

    @Column(name="telephone", nullable=false, length=10)
    private String telephone;

    @Column(name = "telephone_secondaire", nullable=true, length=10)
    private String telephoneSecondaire;

    @Column(name="site_web", nullable=true, length=255)
    private String siteWeb;

    @Column(name="adresse", nullable=false, length=255)
    private String adresse;

    @Column(name="code_postal", nullable=false)
    private int codePostal;

    @Column(name="latitude", nullable=false)
    private double latitude;

    @Column(name="longitude", nullable=false)
    private double longitude;
    
    @Column(name="etat", nullable=false)
    private int etat;

    @ManyToOne
    @JoinColumn(name="id_commune", nullable=false)
    private Commune commune;
}
