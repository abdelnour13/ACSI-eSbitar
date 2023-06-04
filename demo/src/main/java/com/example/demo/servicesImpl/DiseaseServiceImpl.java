package com.example.demo.servicesImpl;

import java.util.List;
import java.util.Optional;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Disease;
import com.example.demo.models.Symptom;
import com.example.demo.repositories.DiseaseRepository;
import com.example.demo.repositories.SymptomRepository;
import com.example.demo.services.DiseaseService;
import com.example.errors.ResourceNotFoundException;

@Service
public class DiseaseServiceImpl implements DiseaseService {

    private DiseaseRepository diseaseRepository;
    private SymptomRepository symptomRepository;

    @Autowired
    public DiseaseServiceImpl(DiseaseRepository diseaseRepository,
    SymptomRepository symptomRepository) {
        super();
        this.diseaseRepository = diseaseRepository;
        this.symptomRepository = symptomRepository;
    }

    @Override
    public Long insertDisease(String name, List<Long> symptoms) {
        
        Disease disease = new Disease();
        disease.setName(name);
        
        System.out.println("\n\n----------service----------\n\n"+symptoms);
        List<Symptom> diseaseSymptoms = symptomRepository.findAllById(symptoms);
        
        if(diseaseSymptoms.size() < symptoms.size()) {
            throw new ResourceNotFoundException(
                "symptome", 
                "symptome_id", 
                "unknown"
            );
        }

        disease.setSymptoms(new HashSet<>(diseaseSymptoms));

        disease = diseaseRepository.save(disease);

        return disease.getDisease_id();

    }

    @Override
    public Disease deleteDisease(Long id) {
        
        Optional<Disease> disease = diseaseRepository.findById(id);

        if(disease.isPresent()) {
            diseaseRepository.delete(disease.get());
            return disease.get();
        }
        throw new ResourceNotFoundException(
            "disease",
            "disease_id", 
            id
        );

    }

    @Transactional
    @Override
    public Disease updateDisease(Long id, String name) {
        
        Optional<Disease> disease = diseaseRepository.findById(id);

        if(!disease.isPresent()) {
            throw new ResourceNotFoundException(
                "disease",
                "disease_id", 
                id
            );
        }

        disease.get().setName(name);
        diseaseRepository.save(disease.get());

        return disease.get();

    }

    @Transactional
    @Override
    public Disease addSymptom(Long idDisease,Long idSymptom) {
        
        Optional<Symptom> symptom = symptomRepository.findById(idSymptom);

        if(!symptom.isPresent()) {
            throw new ResourceNotFoundException(
                "symptom",
                "symptom_id",
                idSymptom
            );
        }

        Optional<Disease> disease = diseaseRepository.findById(idDisease);

        if(!disease.isPresent()) {
            throw new ResourceNotFoundException(
                "disease",
                "disease_id",
                idDisease
            );
        }

        disease.get()
               .getSymptoms()
               .add(symptom.get());

        diseaseRepository.save(disease.get());

        return disease.get();

    }

    @Override
    public List<Disease> getAllDiseases(String query) {
        
        return diseaseRepository.findByNameIgnoreCaseContaining(query);

    }

    @Override
    public Disease getDisease(Long id) {

        Optional<Disease> disease = diseaseRepository.findById(id);

        if(!disease.isPresent()) {
            throw new ResourceNotFoundException(
                "disease",
                "disease_id",
                id
            );
        }

        return disease.get();

    }

    @Transactional
    @Override
    public Disease deleteSymptom(Long idDisease,Long idSymptom) {

        Optional<Symptom> symptom = symptomRepository.findById(idSymptom);

        if(!symptom.isPresent()) {
            throw new ResourceNotFoundException(
                "symptom",
                "symptom_id",
                idSymptom
            );
        }

        Optional<Disease> disease = diseaseRepository.findById(idDisease);

        if(!disease.isPresent()) {
            throw new ResourceNotFoundException(
                "disease",
                "disease_id",
                idDisease
            );
        }

        disease.get()
               .getSymptoms()
               .remove(symptom.get());

        diseaseRepository.save(disease.get());

        return disease.get();

    }
    
}