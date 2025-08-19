package com.critik.api.service;

import com.critik.api.model.Realisateur;
import com.critik.api.repository.RealisateurRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RealisateurService {
    private final RealisateurRepository realisateurRepository;

    public List<Realisateur> tousRealisateurs() {
        return realisateurRepository.findAll();
    }

    public Optional<Realisateur> trouverParId(Long id) {
        return realisateurRepository.findById(id);
    }

    public Realisateur sauvegarder(Realisateur realisateur) {
        return realisateurRepository.save(realisateur);
    }

    public Realisateur modifier(Realisateur realisateur) {
        return realisateurRepository.save(realisateur);
    }

    public void supprimer(Long id) {
        realisateurRepository.deleteById(id);
    }

    public Realisateur existe(Long id) {
        return realisateurRepository.findById(id).orElse(null);
    }
}
