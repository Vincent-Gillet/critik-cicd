package com.critik.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "realisateurs")
@NoArgsConstructor
@AllArgsConstructor
public class Realisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le nom du réalisateur ne peut pas être vide")
    private String nom_realisateur;

    @NotBlank(message = "La description du réalisateur ne peut pas être vide")
    private String description_realisateur;

    @NotNull(message = "La date de naissance du réalisateur ne peut pas être vide")
    private Date date_naissance;
}
