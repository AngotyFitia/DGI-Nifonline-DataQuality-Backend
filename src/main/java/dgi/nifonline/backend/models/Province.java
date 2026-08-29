package dgi.nifonline.backend.models;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import java.util.List;


@Entity
@Table(name="province")
@Getter @Setter
public class Province {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_province")
    private Long idProvince;

    @Column(name = "intitule")
    private String intitule;

    @Column(name= "etat")
    private int etat;

    @OneToMany(mappedBy ="province") // Cela veut dire que cette relation est déjà mappée par l'attribut province dans l'entité Region 
    private List<Region> regions;
}
