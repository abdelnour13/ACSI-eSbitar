package com.example.demo.dtos;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.models.Disease;
import com.example.demo.models.Symptom;

public class DiseaseDTO {

    private Long disease_id;

    private String name;

    public DiseaseDTO() {

    }

    public DiseaseDTO(Disease disease) {
        this.disease_id = disease.getDisease_id();
        this.name = disease.getName();
        for(Symptom s : disease.getSymptoms()) {
            SymptomDTO symptomDTO = new SymptomDTO();
            symptomDTO.setSymptom_id(s.getSymptom_id());
            symptomDTO.setName(s.getName());
            this.symptoms.add(symptomDTO);
        }
    }

    Set<SymptomDTO> symptoms = new HashSet<>();

    public void setSymptoms(Set<SymptomDTO> symptoms) {
        this.symptoms = symptoms;
    }

    public Set<SymptomDTO> getSymptoms() {
        return symptoms;
    }

    public void setDisease_id(Long disease_id) {
        this.disease_id = disease_id;
    }

    public Long getDisease_id() {
        return disease_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
