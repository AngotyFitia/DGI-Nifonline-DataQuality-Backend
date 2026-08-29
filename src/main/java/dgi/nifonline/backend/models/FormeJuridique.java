package dgi.nifonline.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="forme_juridique")
@Getter @Setter
public class FormeJuridique {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_forme_juridique")
    private Long idFormeJuridique;

    @Column (name="abreviation", nullable = false, length=100)
    private String abreviation;

    @Column(name="intitule", nullable=false, length=255)
    private String intitule;
    
    @Column(name="description", nullable=false, length=255)
    private String description;

    @Column(name="etat", nullable=false)
    private int etat;
}
