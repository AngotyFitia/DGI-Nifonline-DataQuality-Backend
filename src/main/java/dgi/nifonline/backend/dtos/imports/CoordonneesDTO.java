package dgi.nifonline.backend.dtos.imports;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CoordonneesDTO {

    private String district;
    private String commune;
    private String email;
    private String telephone;
    private String telephoneSecondaire;
    private String siteWeb;
    private String adresse;
    private int codePostal;
    private double latitude;
    private double longitude;
    private int etat;
    private String etatImport;

    public CoordonneesDTO(String district, String commune, String email, String telephone, String telephoneSecondaire, String siteWeb, String adrese, int codePostal, double latitude, double longitude, String etatImport){
        this.district=district;
        this.commune=commune;
        this.email=email;
        this.telephone=telephone;
        this.telephoneSecondaire=telephoneSecondaire;
        this.siteWeb=siteWeb;
        this.adresse=adresse;
        this.codePostal=codePostal;
        this.latitude=latitude;
        this.longitude=longitude;
        this.etatImport=etatImport;
    }
}
