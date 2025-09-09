package com.critik.api.repository;

import com.critik.api.model.Oeuvre;
import com.critik.api.model.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OeuvreRepository extends JpaRepository<Oeuvre, Long> {
/*    @Query("SELECT o FROM Oeuvre o " +
            "WHERE (:titre IS NULL OR o.titre LIKE %:titre%) " +
            "AND (:description_oeuvre IS NULL OR o.description_oeuvre LIKE %:description_oeuvre%) " +
            "AND (:date_sortie IS NULL OR o.date_sortie = :date_sortie)")
    List<Oeuvre> rechercheOeuvres(
            @Param("titre") String titre,
            @Param("description_oeuvre") String description_oeuvre,
            @Param("date_sortie") LocalDate date_sortie
    );

    // Repository
    @Query("SELECT o FROM Oeuvre o " +
            "WHERE (:titre IS NULL OR o.titre LIKE :titre) " +
            "AND (:description_oeuvre IS NULL OR o.description_oeuvre LIKE :description_oeuvre) " +
            "AND (:type IS NULL OR UPPER(o.type) = UPPER(:type)) " +
            "AND (:date_sortie IS NULL OR o.date_sortie = :date_sortie)")
    List<Oeuvre> rechercheTypeOeuvres(
            @Param("titre") String titre,
            @Param("description_oeuvre") String description_oeuvre,
            @Param("type") Type type,
            @Param("date_sortie") LocalDate date_sortie
    );*/


/*
    @Query("SELECT o FROM Oeuvre o " +
            "JOIN o.genres g " +
            "WHERE (:titre IS NULL OR o.titre LIKE :titre) " +
            "AND (:description_oeuvre IS NULL OR o.description_oeuvre LIKE :description_oeuvre) " +
            "AND (:type IS NULL OR o.type = :type) " +
            "AND (:date_sortie IS NULL OR o.date_sortie = :date_sortie) " +
            "AND (:nom_genre IS NULL OR g.nom_genre LIKE :nom_genre)")
    List<Oeuvre> rechercheGlobalOeuvres(@Param("titre") String titre, @Param("description_oeuvre") String description_oeuvre, @Param("type") Type type, @Param("date_sortie") LocalDate date_sortie, @Param("nom_genre") String nom_genre);
*/
/*    @Query("SELECT o FROM Oeuvre o " +
            "JOIN o.genres g " +
            "WHERE (:titre IS NULL OR o.titre LIKE :titre) " +
            "AND (:description_oeuvre IS NULL OR o.description_oeuvre LIKE :description_oeuvre) " +
            "AND (:type IS NULL OR o.type = :type) " +
            "AND (:date_sortie IS NULL OR o.date_sortie = :date_sortie) " +
            "AND (:nom_genre IS NULL OR UPPER(g.nom_genre) LIKE UPPER(:nom_genre))")
    List<Oeuvre> rechercheGlobalOeuvres(
            @Param("titre") String titre,
            @Param("description_oeuvre") String description_oeuvre,
            @Param("type") Type type,
            @Param("date_sortie") LocalDate date_sortie,
            @Param("nom_genre") String nom_genre
    );*/


    @Query("SELECT DISTINCT o FROM Oeuvre o " +
            "LEFT JOIN FETCH o.genres g " +
            "LEFT JOIN FETCH o.realisateur r " +
            "WHERE (:titre IS NULL OR o.titre LIKE :titre) " +
            "AND (:description_oeuvre IS NULL OR o.description_oeuvre LIKE :description_oeuvre) " +
            "AND (:type IS NULL OR o.type = :type) " +
            "AND (:date_sortie IS NULL OR o.date_sortie = :date_sortie) " +
            "AND (:nom_genre IS NULL OR UPPER(g.nom_genre) LIKE UPPER(:nom_genre))" +
            "AND (:nom_realisateur IS NULL OR (r IS NOT NULL AND r.nom_realisateur LIKE :nom_realisateur))")
    Page<Oeuvre> rechercheOeuvres(
            @Param("titre") String titre,
            @Param("description_oeuvre") String description_oeuvre,
            @Param("type") Type type,
            @Param("date_sortie") LocalDate date_sortie,
            @Param("nom_genre") String nom_genre,
            @Param("nom_realisateur") String nom_realisateur,
            Pageable pageable
    );

}
