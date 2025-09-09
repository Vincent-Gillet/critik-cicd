package com.critik.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "genres")
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du genre ne peut pas être vide")
    private String nom_genre;

    @NotBlank(message = "La description du genre ne peut pas être vide")
    private String description_genre;

/*    @ManyToMany(mappedBy = "genres")
    private Set<Oeuvre> oeuvres;*/

    @ManyToMany(mappedBy = "genres")
    @JsonBackReference
    private List<Oeuvre> oeuvres;

}
