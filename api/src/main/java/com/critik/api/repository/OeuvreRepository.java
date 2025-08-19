package com.critik.api.repository;

import com.critik.api.model.Oeuvre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OeuvreRepository extends JpaRepository<Oeuvre, Long> {
}
