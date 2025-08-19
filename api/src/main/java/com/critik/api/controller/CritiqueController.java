package com.critik.api.controller;

import com.critik.api.dto.CritiqueDTO;
import com.critik.api.mapper.EntityMapper;
import com.critik.api.model.Critique;
import com.critik.api.service.CritiqueService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/api/critiques")
public class CritiqueController {
    private CritiqueService critiqueService;
    private EntityMapper mapper;

    @GetMapping
    public ResponseEntity<List<Critique>> tousCritiques() {
        List<Critique> critiques = critiqueService.tousCritiques();
        return ResponseEntity.ok(critiques);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CritiqueDTO> trouverParId(@PathVariable Long id) {
        return critiqueService.trouverParId(id)
                .map(critique -> ResponseEntity.ok(mapper.toDTO(critique)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Critique> sauvegarder(@RequestBody Critique critique) {
        Critique sauvegardeCritique = critiqueService.sauvegarder(critique);
        return ResponseEntity.ok(sauvegardeCritique);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Critique> sauvegarder(@PathVariable Long id, @RequestBody Critique critique) {
        Critique critiqueExistante = critiqueService.existe(id);
        if (critiqueExistante != null) {
            critique.setId(id);
            Critique critiqueModifier = critiqueService.modifier(critique);
            return ResponseEntity.ok(critiqueModifier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        Critique critiqueExistante = critiqueService.existe(id);
        if (critiqueExistante != null) {
            critiqueService.supprimer(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
