package com.zinoveva.planer.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Цель
 */
@Entity
@Getter
@Setter
public class Target {
    /**
     * id
     */
    @Id
    @GeneratedValue
    Long id;

    /**
     * имя цели
     */
    String name;

    /**
     * описание цели
     */
    String description;

    /**
     * дата начала цели
     */
    Date startDate;

    /**
     * дата окончания цели
     */
    Date endDate;

    /**
     * список задач
     */
    @OneToMany
    List<Task> tasks;

    /**
     * статус цели
     */
    Status status;

    /**
     * планер, в котором хранится цель
     */
    @ManyToOne
    @JoinColumn(name = "planer_id")
    Planner planner;
}
