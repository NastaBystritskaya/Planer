package com.zinoveva.planer.view.controller;

import com.vaadin.flow.component.notification.Notification;
import com.zinoveva.planer.domain.Planner;
import com.zinoveva.planer.domain.Target;
import com.zinoveva.planer.service.TargetService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Контроллер формы добавления задачи
 */
@Controller
@Log4j2
public class CreateTargetController {

    /**
     * Сервис целей
     */
    @Autowired
    TargetService service;

    /**
     * Метод, обрабатывающий нажатие кнопки "сохранить"
     * @param target цель
     */
    public void saveTarget(Target target) {
        log.info("Сохраняем цель {}", target.getName());
        this.service.createTarget(target);
        log.info("Цель успешно сохранена");
        Notification.show("Цель успешно сохранена");
    }

    /**
     * Метод, обрабатывающий нажатие нопки "удалить"
     * @param target цель
     */
    public void deleteTarget(Target target) {
        this.service.deleteTarget(target.getId());
        log.info("Цель удалена {}", target.getName());
        Notification.show("Цель удалена");
    }
}



