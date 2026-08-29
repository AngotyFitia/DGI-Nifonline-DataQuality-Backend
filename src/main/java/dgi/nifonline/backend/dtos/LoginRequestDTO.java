package dgi.nifonline.backend.dtos;

import lombok.Getter; 
import lombok.Setter; 
import jakarta.validation.constraints.NotBlank; 
import jakarta.validation.constraints.NotEmpty; 

@Getter @Setter
public class LoginRequestDTO {
    
    @NotBlank(message = "L'adresse email est obligatoire.")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire.")
    private String motDePasse;

    // @NotEmpty(message = "Veuillez confirmer que vous n'êtes pas un robot.")
    private String recaptchaToken;

}
