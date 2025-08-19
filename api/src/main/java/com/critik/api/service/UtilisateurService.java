package com.critik.api.service;

import com.critik.api.dto.UtilisateurDTO;
import com.critik.api.model.Utilisateur;
import com.critik.api.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;

    public List<Utilisateur> tousUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> trouverParId(Long id) {
        return utilisateurRepository.findById(id);
    }

    public Utilisateur sauvegarder(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur modifier(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public void supprimer(Long id) {
        utilisateurRepository.deleteById(id);
    }

    public Utilisateur existe(Long id) {
        return utilisateurRepository.findById(id).orElse(null);
    }

    public Optional<Utilisateur> trouverParEmailUtilisateur(String email) {
        return utilisateurRepository.findByEmail(email);
    }
}
