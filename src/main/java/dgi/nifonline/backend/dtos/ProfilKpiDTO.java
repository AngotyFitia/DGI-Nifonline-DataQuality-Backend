package dgi.nifonline.backend.dtos;

import lombok.Getter; 
import lombok.Setter; 
import java.util.Map;

@Getter @Setter
public class ProfilKpiDTO {
    private Map<String, Long> repartition;
}
