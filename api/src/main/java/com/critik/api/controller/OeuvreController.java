package com.critik.api.controller;

import com.critik.api.dto.OeuvreDTO;
import com.critik.api.mapper.EntityMapper;
import com.critik.api.model.Critique;
import com.critik.api.model.Oeuvre;
import com.critik.api.service.OeuvreService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/api/oeuvres")
public class OeuvreController {
    private OeuvreService oeuvreService;
    private EntityMapper mapper;

    @GetMapping
    public ResponseEntity<List<Oeuvre>> tousUtilisateurs() {
        List<Oeuvre> oeuvres = oeuvreService.tousOeuvres();
        return ResponseEntity.ok(oeuvres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OeuvreDTO> trouverParId(@PathVariable Long id) {
        return oeuvreService.trouverParId(id)
                .map(oeuvre -> ResponseEntity.ok(mapper.toDTO(oeuvre)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Oeuvre> sauvegarder(@RequestBody Oeuvre oeuvre) {
        Oeuvre sauvegardeCritique = oeuvreService.sauvegarder(oeuvre);
        return ResponseEntity.ok(sauvegardeCritique);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Oeuvre> sauvegarder(@PathVariable Long id, @RequestBody Oeuvre oeuvre) {
        Oeuvre oeuvreExistante = oeuvreService.existe(id);
        if (oeuvreExistante != null) {
            oeuvre.setId(id);
            Oeuvre oeuvreModifier = oeuvreService.modifier(oeuvre);
            return ResponseEntity.ok(oeuvreModifier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        Oeuvre oeuvreExistante = oeuvreService.existe(id);
        if (oeuvreExistante != null) {
            oeuvreService.supprimer(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}
