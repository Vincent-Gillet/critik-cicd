package com.critik.api.dto;

import com.critik.api.model.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OeuvreDTO {
    @NotBlank(message = "Le titre de l'oeuvre ne peut pas être vide")
    private String titre;

    @NotBlank(message = "La description de l'oeuvre ne peut pas être vide")
    private String description_oeuvre;

    @NotNull(message = "Le type de l'oeuvre ne peut pas être vide")
    private Type type;

    @NotNull(message = "La date de sortie ne peut pas être vide")
    private Date date_sortie;
}
