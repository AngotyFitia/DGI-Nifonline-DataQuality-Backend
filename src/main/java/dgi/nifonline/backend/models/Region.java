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

    @OneToMany(mappedBy ="region")
    private List<District> districts;
}
