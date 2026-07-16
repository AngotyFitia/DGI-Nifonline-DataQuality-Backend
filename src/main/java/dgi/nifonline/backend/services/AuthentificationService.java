package dgi.nifonline.backend.services;

import org.springframework.stereotype.Service; 
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthentificationService {
    private final PasswordEncoder passwordEncoder;
    private final String pepper = "SecretPepperKey123!"; 

    public AuthentificationService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword + pepper);
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword + pepper, encodedPassword);
    }
}
