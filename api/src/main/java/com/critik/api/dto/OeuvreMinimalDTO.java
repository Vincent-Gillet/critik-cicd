package com.critik.api.dto;

import com.critik.api.model.Critique;
import com.critik.api.model.Genre;
import com.critik.api.model.Realisateur;
import com.critik.api.model.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OeuvreMinimalDTO {
    @NotBlank(message = "Le titre de l'oeuvre ne peut pas être vide")
    private String titre;

    @NotBlank(message = "La description de l'oeuvre ne peut pas être vide")
    private String description_oeuvre;

    @NotNull(message = "Le type de l'oeuvre ne peut pas être vide")
    private Type type;

    @NotNull(message = "La date de sortie ne peut pas être vide")
    private LocalDate date_sortie;

}
