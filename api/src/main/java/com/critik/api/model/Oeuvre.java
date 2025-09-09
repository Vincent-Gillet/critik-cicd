package com.critik.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.*;

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

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le type de l'oeuvre ne peut pas être vide")
    private Type type;

    @NotNull(message = "La date de sortie ne peut pas être vide")
    private LocalDate date_sortie;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "realisateur_id")
    @JsonManagedReference("oeuvre-realisateur")
    private Realisateur realisateur;


    @OneToMany(mappedBy = "oeuvre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @ToString.Exclude
    private List<Critique> critiques = new ArrayList<>();


    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "oeuvres_genres",
            joinColumns = @JoinColumn(name = "oeuvre_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres = new ArrayList<>();


/*    @ManyToMany
    @JoinTable(
            name = "oeuvres_genres",
            joinColumns = @JoinColumn(name = "oeuvre_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    public Set<Genre> getGenres() {
        return new HashSet<>(genres); // Defensive copy to avoid ConcurrentModificationException
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }*/
}
