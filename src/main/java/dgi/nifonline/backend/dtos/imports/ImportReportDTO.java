package dgi.nifonline.backend.dtos.imports;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ImportReportDTO {
    private int total;
    private int succes;
    private int echec;
    private String message;
}
