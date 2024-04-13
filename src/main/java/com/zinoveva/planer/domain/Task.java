package com.zinoveva.planer.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Задача
 */
@Entity
@Getter
@Setter
public class Task {

    /**
     * id
     */
    @Id
    @GeneratedValue
    Long id;

    /**
     * имя задачи
     */
    String name;

    /**
     * описание задачи
     */
    String description;

    /**
     * дата начала задачи
     */
    Date startDate = new Date();

    /**
     * дата окончания задачи
     */
    Date endDate;

    /**
     * статус задачи
     */
    Status status = Status.create;

    /**
     * цель, которая хранит задачу
     */
    @ManyToOne
    @JoinColumn(name = "target_id")
    Target target;
}
