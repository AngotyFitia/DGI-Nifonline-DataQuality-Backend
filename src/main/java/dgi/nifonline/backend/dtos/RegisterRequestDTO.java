package dgi.nifonline.backend.dtos;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

@Getter @Setter
public class RegisterRequestDTO {
    
    @NotBlank(message="L'adresse email est obligatoire") 
    @Email(message="Veuillez entrer une adresse email valide")
    private String email;

    @NotBlank(message="Le mot de passe est obligatoire")
    @Size(min = 12, message = "Le mot de passe doit contenir au moins 12 caractères.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$",
             message = "Votre mot de passe doit contenir au moins une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial")
    private String motDePasse;

    @NotEmpty(message = "Veuillez confirmer que vous n'êtes pas un robot.")
    private String recaptchaToken;

    private Long idProfil;
}
