package com.zinoveva.planer.domain;

import com.zinoveva.planer.service.converters.StatusConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

/**
 * Задача
 */
@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
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
    LocalDate startDate = LocalDate.now();

    /**
     * дата окончания задачи
     */
    LocalDate endDate;

    /**
     * статус задачи
     */
    @Convert(converter = StatusConverter.class)
    Status status = Status.create;

    /**
     * цель, которая хранит задачу
     */
    @ManyToOne
    @JoinColumn(name = "target_id")
    Target target;
}
