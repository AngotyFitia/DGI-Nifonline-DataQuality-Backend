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
    private String status;

    public ImportReportDTO(int total, int success, int error, String message) {
        this.total = total;
        this.success = success;
        this.error = error;
        this.message = message;
        this.status = (error > 0) ? "error" : "success";
    }
}
