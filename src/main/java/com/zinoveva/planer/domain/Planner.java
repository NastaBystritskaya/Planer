package com.zinoveva.planer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * Планер
 */
@Entity
@Getter
@Setter
public class Planner {

    /**
     * id планера
     */
    @Id
    @GeneratedValue
    Long id;

    /**
     * список целей планера
     */
    @OneToMany
    List<Target> listTarget = new LinkedList<>();



}
