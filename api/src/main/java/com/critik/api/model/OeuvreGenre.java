package com.critik.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "oeuvres_genres")
@NoArgsConstructor
@AllArgsConstructor
public class OeuvreGenre {
    @EmbeddedId
    private OeuvreGenreId id;

    @ManyToOne
    @MapsId("oeuvreId")
    @JoinColumn(name = "oeuvre_id")
    private Oeuvre oeuvre;

    @ManyToOne
    @MapsId("genreId")
    @JoinColumn(name = "genre_id")
    private Genre genre;
}
