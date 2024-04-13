package com.zinoveva.planer.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
/**
 * Задача
 */
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
    Date startDate;

    /**
     * дата окончания задачи
     */
    Date endDate;

    /**
     * статус задачи
     */
    Status status;

    /**
     * цель, которая хранит задачу
     */
    @ManyToOne
    @JoinColumn(name = "target_id")
    Target target;
}
