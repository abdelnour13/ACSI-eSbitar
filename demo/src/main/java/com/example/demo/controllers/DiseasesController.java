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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Details.DiseaseDetails;
import com.example.demo.dtos.DiseaseDTO;
import com.example.demo.models.Disease;
import com.example.demo.models.Symptom;
import com.example.demo.services.DiseaseService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/diseases")
public class DiseasesController {
    private DiseaseService diseaseService;
    public DiseasesController(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiseaseDTO> getDiseaseById(
        @PathVariable("id") Long id
    ) {
        Disease disease = diseaseService.getDisease(id);
        return new ResponseEntity<DiseaseDTO>(new DiseaseDTO(disease) , HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<DiseaseDTO>> getAllDisease(
        @RequestParam("search") String search
    ) {
        List<Disease> diseases = diseaseService.getAllDiseases(search);
        List<DiseaseDTO> diseaseDTOs = new ArrayList<>();

        for(Disease d : diseases) {
            diseaseDTOs.add(new DiseaseDTO(d));
        }

        return new ResponseEntity<List<DiseaseDTO>>(
            diseaseDTOs , 
            HttpStatus.OK
        );
    }

    @PostMapping("")
    public ResponseEntity<Long> createDisease(@RequestBody DiseaseDetails details) {

        Long id = diseaseService.insertDisease(details.name, details.symptoms);

        return new ResponseEntity<Long>(
            id,
            HttpStatus.CREATED
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DiseaseDTO> updateService(
        @RequestBody DiseaseDetails details,
        @PathVariable("id") Long id
    ) {
        Disease disease = diseaseService.updateDisease(id, details.name);
        return new ResponseEntity<DiseaseDTO>(
            new DiseaseDTO(disease),
            HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DiseaseDTO> deleteDisease(
        @PathVariable("id") Long id
    ) {
        Disease disease = diseaseService.deleteDisease(id);
        return new ResponseEntity<DiseaseDTO>(new DiseaseDTO(disease) , HttpStatus.OK);
    }

    @PostMapping("{id}/symptoms/{symptomId}")
    public ResponseEntity<DiseaseDTO> addSymptom(
        @PathVariable("id") Long id,
        @RequestBody Long symptom
    ) {
        Disease disease = diseaseService.addSymptom(id, symptom);
        return new ResponseEntity<DiseaseDTO>(
            new DiseaseDTO(disease),
            HttpStatus.OK
        );
    }

    @DeleteMapping("{id}/symptoms/{symptomId}")
    public ResponseEntity<DiseaseDTO> deleteSymptom(
        @PathVariable("id") Long id,
        @PathVariable("symptomId") Long symptomId
    ) {
        Disease disease = diseaseService.deleteSymptom(id, symptomId);
        return new ResponseEntity<DiseaseDTO>(
            new DiseaseDTO(disease),
            HttpStatus.OK
        );
    }

}