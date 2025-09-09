package com.critik.api.controller;

import com.critik.api.dto.OeuvreDTO;
import com.critik.api.dto.OeuvreGetIdDTO;
import com.critik.api.mapper.EntityMapper;
import com.critik.api.model.Oeuvre;
import com.critik.api.model.Type;
import com.critik.api.service.OeuvreService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/api/oeuvres")
public class OeuvreController {
    private OeuvreService oeuvreService;
    private EntityMapper mapper;

    @GetMapping
    public ResponseEntity<List<OeuvreGetIdDTO>> tousUtilisateurs() {
        List<Oeuvre> oeuvres = oeuvreService.tousOeuvres();
        List<OeuvreGetIdDTO> oeuvreDTOS = oeuvres.stream()
                .map(mapper::toIdDTO)
                .toList();
        return ResponseEntity.ok(oeuvreDTOS);
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

/*    @GetMapping("/recherche")
    @ResponseBody
    public ResponseEntity<List<Oeuvre>> triOeuvres(
            @RequestParam (required = false) String titre,
            @RequestParam (required = false) String description_oeuvre,
            @RequestParam (required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date_sortie) {
        List<Oeuvre> oeuvres = oeuvreService.triOeuvres(titre, description_oeuvre, date_sortie);
        return ResponseEntity.ok(oeuvres);
    }

    @GetMapping("/recherche-type")
    @ResponseBody
    public ResponseEntity<List<Oeuvre>> triTypeOeuvres(
            @RequestParam (required = false) String titre,
            @RequestParam (required = false) String description_oeuvre,
            @RequestParam (required = false) Type type,
            @RequestParam (required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date_sortie) {
        System.out.println("Paramètre type : " + type);
        List<Oeuvre> oeuvres = oeuvreService.triTypeOeuvres(titre, description_oeuvre, type, date_sortie);
        return ResponseEntity.ok(oeuvres);
    }*/

/*    @GetMapping("/recherche-global")
    @ResponseBody
    public ResponseEntity<List<Oeuvre>> triGlobalOeuvres(
            @RequestParam (required = false) String titre,
            @RequestParam (required = false) String description_oeuvre,
            @RequestParam (required = false) Type type,
            @RequestParam (required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date_sortie,
            @RequestParam (required = false) String nom_genre) {
        List<Oeuvre> oeuvres = oeuvreService.triGlobalOeuvres(titre, description_oeuvre, type, date_sortie, nom_genre);
        return ResponseEntity.ok(oeuvres);
    }*/

    @GetMapping("/recherche")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> triGlobalOeuvres(
            @RequestParam (required = false) String titre,
            @RequestParam (required = false) String description_oeuvre,
            @RequestParam (required = false) Type type,
            @RequestParam (required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date_sortie,
            @RequestParam (required = false) String nom_genre,
            @RequestParam (required = false) String nom_realisateur,
            @RequestParam (required = false, defaultValue = "0") Integer page,
            @RequestParam (required = false, defaultValue = "4") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Oeuvre> oeuvres = oeuvreService.triOeuvres(titre, description_oeuvre, type, date_sortie, nom_genre, nom_realisateur, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("oeuvres", oeuvres);
        response.put("total", oeuvres.getTotalElements()); // total filtré
        response.put("pages", oeuvres.getTotalPages());    // nombre de pages
        response.put("currentPage", oeuvres.getNumber());
        return ResponseEntity.ok(response);
    }
    
}
