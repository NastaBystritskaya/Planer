package com.zinoveva.planer.repositories;

import com.zinoveva.planer.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Репозиторий с методами для работы с базой  данных
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Получение списка просроченных задач из базы данных
     * @param endDate дата, после которой задача считается просроченной
     * @return список просроченных задач
     */
    List<Task> findByEndDateAfter(Date endDate);
}
