package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Details.SymptomDetails;
import com.example.demo.dtos.SymptomDTO;
import com.example.demo.models.Symptom;
import com.example.demo.services.SymptomService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/symptoms")
public class SymotomController {
    
    public SymptomService symptomService;

    public SymotomController(SymptomService symptomService) {
        this.symptomService = symptomService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SymptomDTO> getAllSymptoms(
        @PathVariable(name = "id") Long id
    ) {
        Symptom symptom = symptomService.getSymptom(id);
        ResponseEntity<SymptomDTO> response = 
            new ResponseEntity<SymptomDTO>(
                new SymptomDTO(symptom), 
                HttpStatus.OK
            );
        return response;
    }

    @GetMapping("")
    public ResponseEntity<List<SymptomDTO>> getAllSymptoms() {
        List<Symptom> symptoms = symptomService.getAllSymptoms();
        List<SymptomDTO> symptomDTOs = new ArrayList<>();

        for(Symptom s : symptoms) {
            symptomDTOs.add(new SymptomDTO(s));
        }

        return new ResponseEntity<List<SymptomDTO>>(
            symptomDTOs , 
            HttpStatus.OK
        );
    }

    @PostMapping("")
    public ResponseEntity<Long> createSymptom(
        @RequestBody SymptomDetails details
    ) {
        Long id = symptomService.insertSymptom(details.name);
        return new ResponseEntity<Long>(id, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SymptomDTO> updateSymptom(
        @PathVariable("id") Long id,
        @RequestBody SymptomDetails details
    ) {
        Symptom symptom = symptomService.updateSymptom(id, details.name);
        return new ResponseEntity<SymptomDTO>(
            new SymptomDTO(symptom), 
            HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SymptomDTO> deleteSymptom(
        @PathVariable("id") Long id
    ) {
        Symptom symptom = symptomService.deleteSymptom(id);
        return new ResponseEntity<SymptomDTO>(
            new SymptomDTO(symptom), 
            HttpStatus.OK
        );
    }

}