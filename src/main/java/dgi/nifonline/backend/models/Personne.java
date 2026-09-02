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
@Table(name="personne")
@Getter @Setter
public class Personne {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_personne")
    private Long idPersonne;

    @ManyToOne
    @JoinColumn(name="id_coordonnees", nullable=false)
    private Coordonnees coordonnees;

    @Column(name="type_personne", nullable=false)
    private Integer typePersonne;
}
