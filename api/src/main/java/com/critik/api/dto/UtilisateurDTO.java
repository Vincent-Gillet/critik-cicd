package com.critik.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurDTO {
    @NotBlank(message = "Le nom d'utilisateur ne peut pas être vide")
    private String nom_utilisateur;

    @NotBlank(message = "L'adresse e-mail ne peut pas être vide")
    private String email;
}
