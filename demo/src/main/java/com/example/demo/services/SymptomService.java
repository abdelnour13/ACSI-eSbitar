package com.example.demo.services;


import java.util.List;

import com.example.demo.models.Symptom;

public interface SymptomService {
    Long insertSymptom(String name);
    List<Symptom> getAllSymptoms();
    Symptom getSymptom(Long id);
    Symptom deleteSymptom(Long id);
    Symptom updateSymptom(Long id,String name);
}
