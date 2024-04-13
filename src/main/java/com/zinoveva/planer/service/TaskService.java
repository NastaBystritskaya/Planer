package com.zinoveva.planer.service;

import com.zinoveva.planer.domain.Status;
import com.zinoveva.planer.domain.Target;
import com.zinoveva.planer.domain.Task;
import com.zinoveva.planer.repositories.TargetRepository;
import com.zinoveva.planer.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Сервис Задачи
 */
@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    /**
     * Создает или обновляет задачу в базе данных
     * @param task сохраненная задача
     */
    public void createTask(Task task) {
        this.taskRepository.save(task);
    }

    /**
     * Получает список задач в базе данных
     * @return список задач из базы данных
     */
    public List<Task> getAll() {
        return this.taskRepository.findAll();
    }

    /**
     * Удаляет задачу из базы данных
     * @param id id задачи
     */
    public void deleteTask(Long id) {
        this.taskRepository.deleteById(id);
    }

    /**
     * Меняет статус задачи
     * @param task задача
     * @param status новый статус
     */
    public void changeStatus(Task task, Status status) {
        task.setStatus(status);
        this.createTask(task);
    }

    /**
     * Закрыть задачу
     * @param task закрытая задача
     */
    public void closeTask(Task task) {
        task.setStatus(Status.close);
        task.setEndDate(new Date());
        this.createTask(task);
    }

    /**
     * Получить список просроченных задач
     * @return список просроченных задач
     */
    public List<Task> getOldTasks() {
        return this.taskRepository.findByEndDateAfter(new Date());
    }




}
