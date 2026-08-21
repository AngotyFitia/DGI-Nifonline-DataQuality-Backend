package dgi.nifonline.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="regime_fiscal")
@Getter @Setter
public class RegimeFiscal {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_regime_fiscal")
    private Long idRegimeFiscal;

    @Column(name="intitule", nullable=false, length=255)
    private String intitule;

    @Column(name="description", nullable=false)
    private String description;

    @Column(name="etat", nullable=false)
    private int etat;
}
