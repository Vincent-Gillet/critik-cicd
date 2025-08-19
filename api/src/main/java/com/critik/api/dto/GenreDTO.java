package com.critik.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDTO {
    @NotBlank(message = "Le nom du genre ne peut pas être vide")
    private String nom_genre;

    @NotBlank(message = "La description du genre ne peut pas être vide")
    private String description_genre;
}
