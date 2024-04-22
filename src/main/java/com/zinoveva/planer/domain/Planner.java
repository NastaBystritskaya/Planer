package com.zinoveva.planer.domain;

import jakarta.persistence.*;
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
     * Имя планнера
     */
    String name;

    /**
     * список целей планера
     */
    @OneToMany(fetch = FetchType.EAGER)
    List<Target> listTarget = new LinkedList<>();

}
