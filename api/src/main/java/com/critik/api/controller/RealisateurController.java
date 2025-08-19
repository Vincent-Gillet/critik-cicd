package com.critik.api.controller;

import com.critik.api.dto.RealisateurDTO;
import com.critik.api.mapper.EntityMapper;
import com.critik.api.model.Realisateur;
import com.critik.api.service.RealisateurService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/api/realisateurs")
public class RealisateurController {
    private RealisateurService realisateurService;
    private EntityMapper mapper;

    @GetMapping
    public ResponseEntity<List<Realisateur>> tousRealisateurs() {
        List<Realisateur> realisateurs = realisateurService.tousRealisateurs();
        return ResponseEntity.ok(realisateurs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RealisateurDTO> trouverParId(@PathVariable Long id) {
        return realisateurService.trouverParId(id)
                .map(realisateur -> ResponseEntity.ok(mapper.toDTO(realisateur)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Realisateur> sauvegarder(@RequestBody Realisateur realisateur) {
        Realisateur sauvegardeRealisateur = realisateurService.sauvegarder(realisateur);
        return ResponseEntity.ok(sauvegardeRealisateur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Realisateur> sauvegarder(@PathVariable Long id, @RequestBody Realisateur realisateur) {
        Realisateur realisateurExistante = realisateurService.existe(id);
        if (realisateurExistante != null) {
            realisateur.setId(id);
            Realisateur realisateurModifier = realisateurService.modifier(realisateur);
            return ResponseEntity.ok(realisateurModifier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        Realisateur realisateurExistante = realisateurService.existe(id);
        if (realisateurExistante != null) {
            realisateurService.supprimer(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
