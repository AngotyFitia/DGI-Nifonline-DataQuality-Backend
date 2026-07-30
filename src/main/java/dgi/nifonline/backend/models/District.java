package dgi.nifonline.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="district")
@Getter @Setter
public class District {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_district")
    private Long idDistrict;

    @Column(name="intitule", nullable=false, length=255)
    private String intitule;

    @Column(name="etat", nullable=false)
    private int etat;

    @ManyToOne
    @JoinColumn(name="id_region", nullable=false)
    private Region region;
}
