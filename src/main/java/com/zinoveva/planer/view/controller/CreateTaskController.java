package com.zinoveva.planer.view.controller;

import com.vaadin.flow.component.notification.Notification;
import com.zinoveva.planer.domain.Task;
import com.zinoveva.planer.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Контроллер диалогового окана "создать/редактировать задачу"
 */
@Controller
public class CreateTaskController {

    /**
     * Сервис задач
     */
    @Autowired
    TaskService service;

    /**
     * Метод сохранения задач
     * @param task задача
     */
    public void saveTask(Task task) {
        service.createTask(task);
        Notification.show("Задача успешно сохранена");
    }

}
