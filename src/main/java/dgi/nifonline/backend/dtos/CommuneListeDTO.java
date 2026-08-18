package dgi.nifonline.backend.dtos;
import dgi.nifonline.backend.models.Commune;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommuneListeDTO {
    private String commune;
    private String district;
    private String region;
    private String province;

    public CommuneListeDTO(Commune commune) {
        this.commune = commune.getIntitule();
        this.district = commune.getDistrict().getIntitule();
        this.region = commune.getDistrict().getRegion().getIntitule();
        this.province = commune.getDistrict().getRegion().getProvince().getIntitule();
    }
}
