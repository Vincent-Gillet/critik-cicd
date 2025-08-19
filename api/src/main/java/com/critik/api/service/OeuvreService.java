package com.critik.api.service;

import com.critik.api.model.Oeuvre;
import com.critik.api.repository.OeuvreRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
