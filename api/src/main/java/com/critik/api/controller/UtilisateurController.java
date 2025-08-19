package com.critik.api.controller;

import com.critik.api.dto.UtilisateurCreationDTO;
import com.critik.api.dto.UtilisateurDTO;
import com.critik.api.mapper.EntityMapper;
import com.critik.api.model.Utilisateur;
import com.critik.api.service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {
    private UtilisateurService utilisateurService;
    private EntityMapper mapper;

    @GetMapping
    public ResponseEntity<List<UtilisateurDTO>> tousUtilisateur() {
        List<Utilisateur> utilisateurs = utilisateurService.tousUtilisateurs();
        List<UtilisateurDTO> utilisateursDTO = utilisateurs.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(utilisateursDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> trouverParId(@PathVariable Long id) {
        return utilisateurService.trouverParId(id)
                .map(utilisateur -> ResponseEntity.ok(mapper.toDTO(utilisateur)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UtilisateurDTO> sauvegarder(@RequestBody UtilisateurCreationDTO utilisateurDTO) {
        Utilisateur utilisateur = mapper.toEntity(utilisateurDTO);
        Utilisateur savedUtilisateur = utilisateurService.sauvegarder(utilisateur);
        UtilisateurDTO savedUtilisateurDTO = mapper.toDTO(savedUtilisateur);
        return ResponseEntity.ok(savedUtilisateurDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> sauvegarder(@PathVariable Long id, @RequestBody UtilisateurCreationDTO utilisateurDTO) {
        Utilisateur utilisateur = mapper.toEntity(utilisateurDTO);
        Utilisateur existingUtilisateur = utilisateurService.existe(id);
        if (existingUtilisateur != null) {
            utilisateur.setId(id);
            Utilisateur updatedUtilisateur = utilisateurService.modifier(utilisateur);
            return ResponseEntity.ok(updatedUtilisateur);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        Utilisateur existingUtilisateur = utilisateurService.existe(id);
        if (existingUtilisateur != null) {
            utilisateurService.supprimer(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
