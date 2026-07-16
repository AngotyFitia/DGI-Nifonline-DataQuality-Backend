package dgi.nifonline.backend.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity; 
import org.springframework.util.MultiValueMap; 
import org.springframework.util.LinkedMultiValueMap; 
import org.springframework.beans.factory.annotation.Value;
import java.util.Map;

@Component
public class ReCaptcha {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${recaptcha.secret}")
    private String secretKey;

    public boolean validate(String recaptchaToken) {
        String url = "https://www.google.com/recaptcha/api/siteverify";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("secret", secretKey);
        params.add("response", recaptchaToken);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, params, Map.class);
        return (Boolean) response.getBody().get("success");
    }
}


