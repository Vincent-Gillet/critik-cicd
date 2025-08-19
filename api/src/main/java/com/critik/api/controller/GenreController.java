package com.critik.api.controller;

import com.critik.api.dto.GenreDTO;
import com.critik.api.mapper.EntityMapper;
import com.critik.api.model.Critique;
import com.critik.api.model.Genre;
import com.critik.api.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {
    private GenreService genreService;
    private EntityMapper mapper;

    @GetMapping
    public ResponseEntity<List<Genre>> tousGenres() {
        List<Genre> genres = genreService.tousGenres();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> trouverParId(@PathVariable Long id, Map map) {
        return genreService.trouverParId(id)
                .map(genre -> ResponseEntity.ok(mapper.toDTO(genre)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Genre> sauvegarder(@RequestBody Genre genre) {
        Genre sauvegardeGenre = genreService.sauvegarder(genre);
        return ResponseEntity.ok(sauvegardeGenre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genre> sauvegarder(@PathVariable Long id, @RequestBody Genre genre) {
        Genre genreExistante = genreService.existe(id);
        if (genreExistante != null) {
            genre.setId(id);
            Genre genreModifier = genreService.modifier(genre);
            return ResponseEntity.ok(genreModifier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        Genre genreExistante = genreService.existe(id);
        if (genreExistante != null) {
            genreService.supprimer(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
