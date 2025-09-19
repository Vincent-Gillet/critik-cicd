package com.critik.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

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

    @Temporal(TemporalType.DATE)
    @NotNull(message = "La date de naissance du réalisateur ne peut pas être vide")
    private Date date_naissance;

    @OneToMany(mappedBy = "realisateur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference("oeuvre-realisateur")  // This side won't be serialized
    private List<Oeuvre> oeuvres = new ArrayList<>();

}
