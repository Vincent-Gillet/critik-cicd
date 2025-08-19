package com.critik.api.controller;

import com.critik.api.config.CustomUserDetailService;
import com.critik.api.dto.UtilisateurDTO;
import com.critik.api.model.RefreshToken;
import com.critik.api.model.Utilisateur;
import com.critik.api.model.Role;
import com.critik.api.service.JwtService;
import com.critik.api.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailService customUserDetailService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.mot_de_passe())
        );

        final UserDetails userDetails = customUserDetailService.loadUserByUsername(request.email());

        final String jwt = jwtService.generateAccessToken(userDetails.getUsername());

        final RefreshToken refreshToken = refreshTokenService.generateRefreshTokenBdd((Utilisateur) userDetails);

        return ResponseEntity.ok(
                Map.of(
                        "accessToken", jwt,
                        "refreshToken", String.valueOf(refreshToken.getIdRefreshToken())
                )
        );
    }


    record AuthRequest (String email, String mot_de_passe) {}

/*
    @PostMapping("/refresh")
    public ResponseEntity<String> refreshAccessToken(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
*/

    @GetMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshAccessToken(@RequestHeader("X-Refresh-Token") String refreshToken) {

        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Le refresh token est manquant ou invalide."));
        }

        Optional<RefreshToken> refreshTokenSave = refreshTokenService.getRefreshTokenByToken(refreshToken);

        if (refreshTokenSave == null || refreshTokenSave.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error","Un refresh token est requis."));
        }

        try {
            Utilisateur utilisateur = refreshTokenSave.get().getUtilisateur();
            String username = utilisateur.getUsername();

            if (!refreshTokenService.isTokenValid(refreshToken, utilisateur)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error","Le refresh token est invalide."));
            }

            String nouveauAccessToken = jwtService.generateAccessToken(username);

            return ResponseEntity.ok(Map.of("accessToken", nouveauAccessToken));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error","Une erreur de traitement du refresh token."));
        }

    }


    @GetMapping("/me/{token}")
    public ResponseEntity<UtilisateurDTO> getUserByTokenUrl(@PathVariable String token) {
        return refreshTokenService.getUserByRefreshToken(token)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }



    @GetMapping("/me-refresh")
    public ResponseEntity<UtilisateurDTO> getUserByTokenRefresh(@RequestHeader("X-Refresh-Token") String refreshToken) {
        return refreshTokenService.getUserByRefreshToken(refreshToken)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }


    @GetMapping("/me")
    public ResponseEntity<UtilisateurDTO> getUserByTokenAccess(@RequestHeader("Authorization") String authHeader) {
        try {
            if (!authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            String accessToken = authHeader.substring(7);
            Optional<UtilisateurDTO> userDTO = jwtService.getUserByAccessToken(accessToken);

            if (userDTO.isPresent()) {
                return ResponseEntity.ok(userDTO.get());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GetMapping("/test-dto")
    public ResponseEntity<UtilisateurDTO> testDto() {
        UtilisateurDTO test = new UtilisateurDTO();
        test.setNom_utilisateur("Test");
        test.setEmail("test@gmail.com");
        return ResponseEntity.ok(test);
    }
}
