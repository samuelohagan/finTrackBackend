package com.samproject.financeapp.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.samproject.financeapp.model.User;
import com.samproject.financeapp.service.GoogleTokenValidationService;
import com.samproject.financeapp.service.JwtUtil;
import com.samproject.financeapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


@RestController
public class AuthenticationController {

    private final GoogleTokenValidationService googleTokenValidationService;

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    public AuthenticationController(GoogleTokenValidationService googleTokenValidationService, UserService userService, JwtUtil jwtUtil) {
        this.googleTokenValidationService = googleTokenValidationService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/validateGoogleToken")
    public ResponseEntity<?> validateGoogleToken(@RequestBody Map<String,String> tokenMap) {
        try {
            String idTokenString = tokenMap.get("tokenId");
            if (idTokenString == null) {
                throw new Exception("Token not found");
            }
            GoogleIdToken.Payload payload = googleTokenValidationService.verifyToken(idTokenString);
            User googleUser = userService.createOrFetchFromGoogleProfile(payload);
            String jwtToken = jwtUtil.generateToken(googleUser.getGoogleId());

            logger.info("User ID: " + payload.getSubject());
            return ResponseEntity.ok(jwtToken);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
