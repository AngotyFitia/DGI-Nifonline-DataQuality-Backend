package dgi.nifonline.backend.dtos;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

@Getter @Setter
public class RegisterRequestDTO {
    
    @NotBlank @Email
    private String email;

    @NotBlank
    @Size(min = 12, message = "Password must be at least 12 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$",
             message = "Password must contain upper, lower, digit, and special character")
    private String motDePasse;

    private String recaptchaToken;
}
