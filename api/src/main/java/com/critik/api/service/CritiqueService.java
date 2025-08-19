package com.critik.api.service;

import com.critik.api.model.Critique;
import com.critik.api.repository.CritiqueRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CritiqueService {
    private final CritiqueRepository critiqueRepository;

    public List<Critique> tousCritiques() {
        return critiqueRepository.findAll();
    }

    public Optional<Critique> trouverParId(Long id) {
        return critiqueRepository.findById(id);
    }

    public Critique sauvegarder(Critique critique) {
        return critiqueRepository.save(critique);
    }

    public Critique modifier(Critique critique) {
        return critiqueRepository.save(critique);
    }

    public void supprimer(Long id) {
        critiqueRepository.deleteById(id);
    }

    public Critique existe(Long id) {
        return critiqueRepository.findById(id).orElse(null);
    }

}
