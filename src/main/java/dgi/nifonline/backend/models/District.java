package dgi.nifonline.backend.models;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import java.util.List;


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

    @OneToMany(mappedBy = "district")
    private List<Commune> communes;
}
