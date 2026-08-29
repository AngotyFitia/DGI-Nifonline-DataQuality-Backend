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
@Table(name="type_impot")
@Getter @Setter
public class TypeImpot {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_type_impot")
    private Long idTypeImpot;

    @Column (name="code", nullable = false, length=20)
    private String code;

    @Column(name="intitule", nullable=false, length=255)
    private String intitule;
    
    @Column(name="etat", nullable=false)
    private int etat;
}
