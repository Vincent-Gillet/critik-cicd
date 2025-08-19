package com.critik.api.dto;

import com.critik.api.model.Oeuvre;
import com.critik.api.model.Utilisateur;
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

    @OneToOne
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id")
    private Long utilisateur_id;

    @OneToOne
    @JoinColumn(name = "oeuvre_id", referencedColumnName = "id")
    private Long oeuvre_id;
}
