package com.example.demo.dtos;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.models.Disease;
import com.example.demo.models.Symptom;

public class SymptomDTO {

    private Long symptom_id;


    private String name;

    public SymptomDTO() {
        
    }

    public SymptomDTO(Symptom symptom) {
        this.name = symptom.getName();
        this.symptom_id = symptom.getSymptom_id();
        for(Disease d : symptom.getDiseases()) {
            DiseaseDTO diseaseDTO = new DiseaseDTO();
            diseaseDTO.setDisease_id(d.getDisease_id());
            diseaseDTO.setName(d.getName());
            this.diseases.add(diseaseDTO);
        }
    }


    private Set<DiseaseDTO> diseases = new HashSet<>();

    public Set<DiseaseDTO> getDiseases() {
        return diseases;
    }

    public void setDiseases(Set<DiseaseDTO> diseases) {
        this.diseases = diseases;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSymptom_id() {
        return symptom_id;
    }

    public void setSymptom_id(Long symptom_id) {
        this.symptom_id = symptom_id;
    }

}
