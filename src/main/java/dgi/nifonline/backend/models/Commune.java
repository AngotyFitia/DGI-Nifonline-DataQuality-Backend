package dgi.nifonline.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="commune")
@Getter @Setter
public class Commune {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_commune")
    private Long idDistrict;

    @Column(name="intitule", nullable=false, length=255)
    private String intitule;

    @Column(name="etat", nullable=false)
    private int etat;

    @ManyToOne
    @JoinColumn(name="id_district", nullable=false)
    private District district;
}
