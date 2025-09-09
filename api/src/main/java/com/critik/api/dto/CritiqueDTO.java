package com.critik.api.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CritiqueDTO {
    @NotNull(message = "La note ne peut pas être vide")
    private Integer note;

    @NotBlank(message = "Le commentaire ne peut pas être vide")
    private String commentaire;

    @NotNull(message = "La date ne peut pas être vide")
    private Date date;

    @ManyToOne
    @JsonBackReference
    private Long utilisateur;

    @ManyToOne
    @JsonBackReference
    private Long oeuvre;
}
