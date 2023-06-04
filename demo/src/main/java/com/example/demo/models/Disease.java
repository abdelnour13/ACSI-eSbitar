package com.example.demo.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;



@Entity
@Table(name="diseases")
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long disease_id;

    @Column(name="name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "diseases_symptoms", 
        joinColumns = @JoinColumn(name = "disease_id"), 
        inverseJoinColumns = @JoinColumn(name = "symptom_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    Set<Symptom> symptoms = new HashSet<>();

    public void setSymptoms(Set<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public Set<Symptom> getSymptoms() {
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
