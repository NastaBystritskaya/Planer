package com.zinoveva.planer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Planner {

    @Id
    @GeneratedValue
    Long id;

    @OneToMany
    List<Target> listTarget;



}
