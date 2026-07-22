package dgi.nifonline.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UtilisateurKpiDTO {
    private long total;
    private long actifs;
    private long inactifs;
    private long nouveaux7Jours;
}
