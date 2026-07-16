package dgi.nifonline.backend.dtos;

import lombok.Getter; 
import lombok.Setter; 
import jakarta.validation.constraints.NotBlank; 

@Getter @Setter
public class LoginRequestDTO {
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String motDePasse;
}
