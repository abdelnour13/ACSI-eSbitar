package com.example.demo.services;

import java.util.List;

import com.example.demo.models.Disease;

public interface DiseaseService {
    Long insertDisease(String name,List<Long> symptoms);
    Disease deleteDisease(Long id);
    Disease updateDisease(Long id, String name);
    Disease addSymptom(Long idDisease,Long idSymptom);
    List<Disease> getAllDiseases(String query);
    Disease getDisease(Long id);
    Disease deleteSymptom(Long idDisease,Long idSymptom);
}
