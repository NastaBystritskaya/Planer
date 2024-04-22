package com.zinoveva.planer.repositories;

import com.zinoveva.planer.domain.Status;
import com.zinoveva.planer.domain.Target;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Репозиторий для работы с целями в базе данных
 */
public interface TargetRepository extends JpaRepository<Target, Long> {

    /**
     * Получение списка просроченных целей из базы данных
     * @param endDate дата, после которой цель считается просроченной
     * @return список просроченных целей
     */
    List<Target> findByEndDateAfter(Date endDate);

    /**
     * Поиск по статусу
     * @param status Статус
     * @return Цели
     */
    List<Target> findByStatusNot(Status status);




}
