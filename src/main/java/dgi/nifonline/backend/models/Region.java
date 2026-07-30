package dgi.nifonline.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="region")
@Getter @Setter
public class Region {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_region")
    private Long idRegion;

    @Column(name="intitule", nullable=false, length=255)
    private String intitule;

    @Column(name="etat", nullable=false)
    private int etat;

    @ManyToOne
    @JoinColumn(name="id_province", nullable=false)
    private Province province;
}
