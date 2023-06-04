package com.example.demo.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;



@Entity
@Table(name="symptomes")
public class Symptom {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long symptom_id;

    @Column(name="name")
    private String name;

    @ManyToMany(mappedBy = "symptoms",fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Set<Disease> diseases = new HashSet<>();

    public Set<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(Set<Disease> diseases) {
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
