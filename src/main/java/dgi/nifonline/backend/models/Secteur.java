package dgi.nifonline.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="secteur")
@Getter @Setter
public class Secteur {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_secteur")
    private Long idSecteur;

    @Column(name="intitule", nullable=false, length=255)
    private String intitule;

    @Column(name="description", nullable=false, length=255)
    private String description;

    @Column(name="etat", nullable=false)
    private int etat;
}
