package dgi.nifonline.backend.dtos.imports;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ImportReportDTO {
    private int total;
    private int success;
    private int error;
    private String message;
}
