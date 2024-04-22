package com.zinoveva.planer.service.converters;

import com.zinoveva.planer.domain.Status;
import jakarta.persistence.AttributeConverter;

public class StatusConverter implements AttributeConverter<Status, Integer> {


    /**
     * Конвертирование аттрибута в колону БД
     * @param status Статус
     * @return Значение в БД
     */
    @Override
    public Integer convertToDatabaseColumn(Status status) {
        switch (status) {
            case create -> {
                return 1;
            }
            case active -> {
                return 2;
            }
            case close -> {
                return 3;
            }
            default -> throw new RuntimeException("Статуса не существет");
        }
    }

    /**
     * Конвертирование значения из БД в поле сущности
     * @param integer Значение из БД
     * @return Поле
     */
    @Override
    public Status convertToEntityAttribute(Integer integer) {
        switch (integer) {
            case 1 -> {
                return Status.create;
            }
            case 2 -> {
                return Status.active;
            }
            case 3 -> {
                return Status.close;
            }
            default -> throw new RuntimeException("Статуса не существет");
        }
    }
}
