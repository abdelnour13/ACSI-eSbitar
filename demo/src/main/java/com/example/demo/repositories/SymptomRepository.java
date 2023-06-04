package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Symptom;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom,Long> {

    @Query("SELECT s FROM Symptom s WHERE s.id = :id")
    Optional<Symptom> findByIdWithLazyFetching(@Param("id") Long id);
    
    @Query("SELECT s FROM Symptom s")
    List<Symptom> findAllWithLazyFetching();
}