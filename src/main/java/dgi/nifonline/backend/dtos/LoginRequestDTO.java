package dgi.nifonline.backend.dtos;

import lombok.Getter; 
import lombok.Setter; 
import jakarta.validation.constraints.NotBlank; 

@Getter @Setter
public class LoginRequestDTO {
    
    @NotBlank(message = "L'adresse email est obligatoireL'adresse email est obligatoire")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDePasse;
}
