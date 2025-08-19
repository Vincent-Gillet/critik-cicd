package com.critik.api.mapper;

import com.critik.api.dto.*;
import com.critik.api.model.*;
import com.critik.api.service.OeuvreService;
import com.critik.api.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

    private final UtilisateurService utilisateurService;
    private final OeuvreService oeuvreService;

    // === Utilisateur ===
    public UtilisateurDTO toDTO(Utilisateur utilisateur) {
        if (utilisateur == null) return null;
        return new UtilisateurDTO(
                utilisateur.getNom_utilisateur(),
                utilisateur.getEmail()
        );
    }

    public Utilisateur toEntity(UtilisateurDTO dto) {
        if (dto == null) return null;
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom_utilisateur(dto.getNom_utilisateur());
        utilisateur.setEmail(dto.getEmail());
        return utilisateur;
    }

    public Utilisateur toEntity(UtilisateurCreationDTO dto) {
        if (dto == null) return null;
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom_utilisateur(dto.getNom_utilisateur());
        utilisateur.setEmail(dto.getEmail());
        utilisateur.setMot_de_passe(passwordEncoder.encode(dto.getMot_de_passe()));
        return utilisateur;
    }

    // === Realisateur ===
    public RealisateurDTO toDTO(Realisateur realisateur) {
        if (realisateur == null) return null;
        return new RealisateurDTO(
                realisateur.getNom_realisateur(),
                realisateur.getDescription_realisateur(),
                realisateur.getDate_naissance()
        );
    }

    public Realisateur toEntity(RealisateurDTO dto) {
        if (dto == null) return null;
        Realisateur realisateur = new Realisateur();
        realisateur.setNom_realisateur(dto.getNom_realisateur());
        realisateur.setDescription_realisateur(dto.getDescription_realisateur());
        realisateur.setDate_naissance(dto.getDate_naissance());
        return realisateur;
    }

    // === Oeuvre ===
    public OeuvreDTO toDTO(Oeuvre oeuvre) {
        if (oeuvre == null) return null;
        return new OeuvreDTO(
                oeuvre.getTitre(),
                oeuvre.getDescription_oeuvre(),
                oeuvre.getType(),
                oeuvre.getDate_sortie()
        );
    }

    public Oeuvre toEntity(OeuvreDTO dto) {
        if (dto == null) return null;
        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre(dto.getTitre());
        oeuvre.setDescription_oeuvre(dto.getDescription_oeuvre());
        oeuvre.setType(dto.getType());
        oeuvre.setDate_sortie(dto.getDate_sortie());
        return oeuvre;
    }

    // === Genre ===
    public GenreDTO toDTO(Genre genre) {
        if (genre == null) return null;
        return new GenreDTO(
                genre.getNom_genre(),
                genre.getDescription_genre()
        );
    }

    public Genre toEntity(GenreDTO dto) {
        if (dto == null) return null;
        Genre genre = new Genre();
        genre.setNom_genre(dto.getNom_genre());
        genre.setDescription_genre(dto.getDescription_genre());
        return genre;
    }

    // === Critique ===
    public CritiqueDTO toDTO(Critique critique) {
        if (critique == null) return null;
        return new CritiqueDTO(
                critique.getNote(),
                critique.getCommentaire(),
                critique.getDate(),
                critique.getUtilisateur() != null ? critique.getUtilisateur().getId() : null,
                critique.getOeuvre() != null ? critique.getOeuvre().getId() : null
        );
    }

    public Critique toEntity(CritiqueDTO dto) {
        if (dto == null) return null;
        Critique critique = new Critique();
        critique.setNote(dto.getNote());
        critique.setCommentaire(dto.getCommentaire());
        critique.setDate(dto.getDate());
        critique.setUtilisateur(utilisateurService.trouverParId(dto.getUtilisateur_id()).orElse(null));
        critique.setOeuvre(oeuvreService.trouverParId(dto.getOeuvre_id()).orElse(null));
        return critique;
    }

}
