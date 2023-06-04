package com.example.demo.servicesImpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Symptom;
import com.example.demo.repositories.SymptomRepository;
import com.example.demo.services.SymptomService;
import com.example.errors.ResourceNotFoundException;

@Service
public class SymptomServiceImpl implements SymptomService {

    private SymptomRepository symptomRepository;

    @Autowired
    public SymptomServiceImpl(SymptomRepository symptomRepository) {
        super();
        this.symptomRepository = symptomRepository;
    }

    @Override
    public Long insertSymptom(String name) {
        
        Symptom symptom = new Symptom();

        symptom.setName(name);
        symptom = symptomRepository.save(symptom);

        return symptom.getSymptom_id();

    }

    @Override
    public List<Symptom> getAllSymptoms() {
        return symptomRepository.findAllWithLazyFetching();
    }

    @Override
    @Transactional
    public Symptom getSymptom(Long id) {
        
        Optional<Symptom> symptom = symptomRepository.findById(id);

        if(!symptom.isPresent()) {
            throw new ResourceNotFoundException(
                "symptom", 
                "symptom_id", 
                id
            );
        }

        
        return symptom.get();

    }

    @Transactional
    @Override
    public Symptom deleteSymptom(Long id) {
        
        Optional<Symptom> symptom = symptomRepository.findById(id);

        if(!symptom.isPresent()) {
            throw new ResourceNotFoundException(
                "symptom", 
                "symptom_id", 
                id
            );
        }

        symptomRepository.delete(symptom.get());

        return symptom.get();

    }

    @Transactional
    @Override
    public Symptom updateSymptom(Long id, String name) {
        
        Optional<Symptom> symptom = symptomRepository.findById(id);

        if(!symptom.isPresent()) {
            throw new ResourceNotFoundException(
                "symptom", 
                "symptom_id", 
                id
            );
        }

        symptom.get().setName(name);
        symptomRepository.save(symptom.get());

        return symptom.get();

    }
    
}