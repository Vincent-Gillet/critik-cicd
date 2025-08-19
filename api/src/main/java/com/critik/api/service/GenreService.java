package com.critik.api.service;

import com.critik.api.model.Genre;
import com.critik.api.repository.GenreRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public List<Genre> tousGenres() {
        return genreRepository.findAll();
    }

    public Optional<Genre> trouverParId(Long id) {
        return genreRepository.findById(id);
    }

    public Genre sauvegarder(Genre critique) {
        return genreRepository.save(critique);
    }

    public Genre modifier(Genre critique) {
        return genreRepository.save(critique);
    }

    public void supprimer(Long id) {
        genreRepository.deleteById(id);
    }

    public Genre existe(Long id) {
        return genreRepository.findById(id).orElse(null);
    }

}
