package com.critik.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealisateurminimalDTO {
    @NotNull(message = "Le nom du réalisateur ne peut pas être vide")
    private String nom_realisateur;

    @NotBlank(message = "La description du réalisateur ne peut pas être vide")
    private String description_realisateur;

    @NotNull(message = "La date de naissance du réalisateur ne peut pas être vide")
    private Date date_naissance;

}