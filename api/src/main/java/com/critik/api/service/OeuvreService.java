package com.critik.api.service;

import com.critik.api.model.Oeuvre;
import com.critik.api.model.Type;
import com.critik.api.repository.OeuvreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OeuvreService {
    private final OeuvreRepository oeuvreRepository;

    public List<Oeuvre> tousOeuvres() {
        return oeuvreRepository.findAll();
    }

    public Optional<Oeuvre> trouverParId(Long id) {
        return oeuvreRepository.findById(id);
    }

    public Oeuvre sauvegarder(Oeuvre oeuvre) {
        return oeuvreRepository.save(oeuvre);
    }

    public Oeuvre modifier(Oeuvre oeuvre) {
        return oeuvreRepository.save(oeuvre);
    }

    public void supprimer(Long id) {
        oeuvreRepository.deleteById(id);
    }

    public Oeuvre existe(Long id) {
        return oeuvreRepository.findById(id).orElse(null);
    }

/*    public List<Oeuvre> triOeuvres(String titre, String descriptionOeuvre, LocalDate dateSortie) {
        return oeuvreRepository.rechercheOeuvres(titre, descriptionOeuvre, dateSortie);
    }*/

    // Service
/*    public List<Oeuvre> triTypeOeuvres(String titre, String descriptionOeuvre, Type type, LocalDate dateSortie) {
        String titreLike = titre != null ? "%" + titre + "%" : null;
        String descriptionLike = descriptionOeuvre != null ? "%" + descriptionOeuvre + "%" : null;
        return oeuvreRepository.rechercheTypeOeuvres(titreLike, descriptionLike, type, dateSortie);
    }*/

/*    public List<Oeuvre> triGlobalOeuvres(String titre, String descriptionOeuvre, Type type, LocalDate dateSortie, String nom_genre) {
        String titreLike = titre != null ? "%" + titre + "%" : null;
        String descriptionLike = descriptionOeuvre != null ? "%" + descriptionOeuvre + "%" : null;
        String genreExact = nom_genre != null ? nom_genre : null;
        return oeuvreRepository.rechercheGlobalOeuvres(titreLike, descriptionLike, type, dateSortie, genreExact);
    }*/

    public Page<Oeuvre> triOeuvres(String titre, String descriptionOeuvre, Type type, LocalDate dateSortie, String nom_genre, String nom_realisateur, Pageable pageable) {
        String titreLike = titre != null ? "%" + titre + "%" : null;
        String descriptionLike = descriptionOeuvre != null ? "%" + descriptionOeuvre + "%" : null;
        String genreExact = nom_genre != null ? nom_genre : null;
        String nomRealisateurLike = nom_realisateur != null ? "%" + nom_realisateur + "%" : null;
        return oeuvreRepository.rechercheOeuvres(titreLike, descriptionLike, type, dateSortie, genreExact, nomRealisateurLike, pageable);
    }

}
