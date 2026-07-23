package dgi.nifonline.backend.dtos;

import lombok.Getter;
import lombok.Setter;
public class ExceptionDTO extends RuntimeException {
    private final String type;

    public ExceptionDTO(String message, String type) {
        super(message);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
