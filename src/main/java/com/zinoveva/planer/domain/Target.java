package com.zinoveva.planer.domain;

import com.zinoveva.planer.service.converters.StatusConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DialectOverride;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
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
    LocalDate startDate = LocalDate.now();

    /**
     * дата окончания цели
     */
    LocalDate endDate;

    /**
     * список задач
     */
    @OneToMany(fetch = FetchType.EAGER)
    List<Task> tasks = new LinkedList<>();

    /**
     * статус цели
     */
    @Convert(converter = StatusConverter.class)
    Status status = Status.create;

    /**
     * планер, в котором хранится цель
     */
    @ManyToOne
    @JoinColumn(name = "planer_id")
    Planner planner;

    /**
     * Строчное представление
     * @return Представление
     */
    @Override
    public String toString() {
        return "[" + this.endDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + "] " + this.getName();
    }
}
