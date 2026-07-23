package dgi.nifonline.backend.dtos;

import lombok.Getter; 
import lombok.Setter; 

@Getter @Setter
public class InscriptionsParMoisDTO {
    private String month;
    private long count;

    public InscriptionsParMoisDTO(String month, long count) {
        this.month = month;
        this.count = count;
    }

}
