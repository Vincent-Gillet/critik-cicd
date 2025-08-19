package com.critik.api.repository;

import com.critik.api.model.Critique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CritiqueRepository extends JpaRepository<Critique, Long> {
}
