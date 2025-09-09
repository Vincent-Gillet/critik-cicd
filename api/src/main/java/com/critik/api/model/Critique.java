package com.critik.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "critiques")
@NoArgsConstructor
@AllArgsConstructor
public class Critique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La note ne peut pas être vide")
    private Integer note;

    @NotBlank(message = "Le commentaire ne peut pas être vide")
    private String commentaire;

    @NotNull(message = "La date ne peut pas être vide")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    @JsonBackReference
    private Utilisateur utilisateur;

/*    @OneToOne
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id")
    private Utilisateur utilisateur;*/

/*    @OneToOne
    @JoinColumn(name = "oeuvre_id", referencedColumnName = "id")
    private Oeuvre oeuvre;*/

    @ManyToOne
    @JoinColumn(name = "oeuvre_id")
    @JsonBackReference
    private Oeuvre oeuvre;

}
