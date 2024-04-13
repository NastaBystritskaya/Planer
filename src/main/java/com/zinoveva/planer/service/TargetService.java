package com.zinoveva.planer.service;

import com.zinoveva.planer.domain.Planner;
import com.zinoveva.planer.domain.Status;
import com.zinoveva.planer.domain.Target;
import com.zinoveva.planer.domain.Task;
import com.zinoveva.planer.repositories.PlannerRepository;
import com.zinoveva.planer.repositories.TargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Сервис Цели
 */
@Service
public class TargetService {
    @Autowired
    TargetRepository targetRepository;

    /**
     * Создает или обновляет цель в базе данных
     * @param target сохраненная цель
     */
    public void createTarget(Target target) {
        this.targetRepository.save(target);
    }

    /**
     * Получает список целей в базе данных
     * @return список целей из базы данных
     */
    public List<Target> getAll() {
        return this.targetRepository.findAll();
    }

    /**
     * Удаляет цель из базы данных
     * @param id id цели
     */
    public void deleteTarget(Long id) {
        this.targetRepository.deleteById(id);
    }

    /**
     * Добавляет задачу в список целей
     * @param task задача
     * @param target цель
     */
    public void addTaskToTarget(Task task, Target target) {
        target.getTasks().add(task);
        task.setTarget(target);
        this.createTarget(target);
    }

    /**
     * Удаляет задачу из списка целей
     * @param task задача
     * @param target цель
     */
    public void deleteTaskFromTarget(Target target, Task task){
        target.getTasks().remove(task);
        this.createTarget(target);
    }

    /**
     * Изменяет статус цели
     * @param target цель
     * @param status новый статус
     */
    public void changeStatus(Target target, Status status){
        target.setStatus(status);
        this.createTarget(target);
    }

    /**
     * Закрывает цель
     * @param target цель
     */
    public void closeTarget(Target target) {

        boolean check = target.getTasks().stream().anyMatch(task -> {
            return task.getStatus() != Status.close;
        });
        if (check) {
            throw new RuntimeException("В цели есть незакрытые задачи");
        }
        target.setStatus(Status.close);
        target.setEndDate(new Date());
    }

    /**
     * Список просроченных целей
     * @return просроченные цели
     */
    public List<Target> getOldTargets() {
        return this.targetRepository.findByEndDateAfter(new Date());
    }
}
