package com.example.keycloakadapterdemo;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fhir")
public class MyController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint";
    }

    @GetMapping("/protected")
    public String protectedEndpoint() {
    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

         if (authentication != null && authentication.getPrincipal() instanceof KeycloakPrincipal) {
             KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
             AccessToken accessToken = principal.getKeycloakSecurityContext().getToken();

             // Access custom claims
             String customClaimValue = accessToken.getOtherClaims().get("fhirScopes").toString();
             System.out.println(customClaimValue);

             // Use custom claim value for business logic
             // ...
         }
        return "This is a protected endpoint";
    }
}

