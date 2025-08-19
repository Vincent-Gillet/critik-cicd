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
@Table(name = "oeuvres")
@NoArgsConstructor
@AllArgsConstructor
public class Oeuvre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre de l'oeuvre ne peut pas être vide")
    private String titre;

    @NotBlank(message = "La description de l'oeuvre ne peut pas être vide")
    private String description_oeuvre;

    @NotNull(message = "Le type de l'oeuvre ne peut pas être vide")
    private Type type;

    @NotNull(message = "La date de sortie ne peut pas être vide")
    private Date date_sortie;

/*
    @ManyToMany
*/
}
