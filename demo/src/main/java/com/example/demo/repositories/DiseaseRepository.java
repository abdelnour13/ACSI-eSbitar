package com.example.demo.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Disease;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease,Long> {
    /*@Query("SELECT * from Disease s where s.name LIKE '%:search%'")*/
    List<Disease> findByNameIgnoreCaseContaining(String search);
}