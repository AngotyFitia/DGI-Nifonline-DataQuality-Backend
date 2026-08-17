package dgi.nifonline.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="activite")
@Getter @Setter
public class Activite {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_activite")
    private Long idActivite;

    @Column(name="intitule", nullable=false, length=255)
    private String intitule;

    @Column(name="code_activite", nullable=false)
    private int codeActivite;

    @Column(name="section", nullable=false, length=10)
    private String section;

    @Column(name="etat", nullable=false)
    private int etat;

    @ManyToOne
    @JoinColumn(name="id_secteur", nullable=false)
    private Secteur secteur;
}
