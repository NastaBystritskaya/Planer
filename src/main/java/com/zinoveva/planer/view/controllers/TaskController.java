package com.zinoveva.planer.view.controllers;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.ValidationException;
import com.zinoveva.planer.domain.Status;
import com.zinoveva.planer.domain.Task;
import com.zinoveva.planer.service.TaskService;
import com.zinoveva.planer.view.dialogs.task.CreateTaskDialog;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

/**
 * Контроллер задач
 */
@Controller
@Lazy
@RequiredArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log4j2
public class TaskController {

    /**
     * Сервис задач
     */
    final TaskService service;

    @Getter
    @Setter
    CreateTaskDialog createTaskDialog;

    /**
     * Контроллер главной формы
     */
    @Getter
    @Setter
    RootController rootController;

    /**
     * Инициализация контроллера
     */
    public void init() {
        this.createTaskDialog.getSaveButton().addClickListener(this::fireTaskSaveButton);
        this.createTaskDialog.getCloseButton().addClickListener(event -> this.closeDialog(this.createTaskDialog));
    }

    /**
     * Нажатие на кнопку "Закрыть"
     *
     * @param dialog Закрываемое окно
     */
    private void closeDialog(Dialog dialog) {
        dialog.close();
    }

    /**
     * Нажатие на кнопку "Сохранить"
     *
     * @param event Обработчик событий
     */
    private void fireTaskSaveButton(ClickEvent<Button> event) {
        Task newTask = new Task();
        newTask.setTarget(this.createTaskDialog.getCurrentTarget());
        newTask.setStatus(Status.create);

        try {

            log.debug("Чтение ввода от пользователя");
            this.createTaskDialog.getTaskBinder().writeBean(newTask);
            log.debug("Сохранение в БД");
            Task saved = this.service.createTask(newTask);
            this.rootController.getTargetController().getService().addTaskToTarget(saved, this.createTaskDialog.getCurrentTarget());
            log.info("""
                    Задача успешно сохранена.
                    ИД: {}
                    """, saved.getId());
            Notification notification = new Notification();
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.setDuration(1000);
            notification.setText("Задача успешно сохранена");
            notification.open();
            this.createTaskDialog.close();
            this.rootController.fillTargetData();
        } catch (ValidationException e) {

            log.error(e);
            Notification message = new Notification();
            message.addThemeVariants(NotificationVariant.LUMO_ERROR);
            message.setDuration(1000);
            message.setPosition(Notification.Position.MIDDLE);
            message.setText("""
                    Не удалось сохранить задачу.
                    %s
                    """.formatted(e.getMessage()));
            message.open();
        }

    }
}
