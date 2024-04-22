package com.zinoveva.planer.view.controllers;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.ValidationException;
import com.zinoveva.planer.domain.Status;
import com.zinoveva.planer.domain.Target;
import com.zinoveva.planer.service.TargetService;
import com.zinoveva.planer.view.dialogs.target.CreateTargetDialog;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

/**
 * Контроллер целей
 */
@Controller
@Lazy
@RequiredArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class TargetController {

    /**
     * Сервис целей
     */
    TargetService service;

    /**
     * Окно ввода задачи
     */
    @NonFinal
    @Setter
    CreateTargetDialog createTargetDialog;

    @NonFinal
    @Setter
    RootController rootController;

    /**
     * Инициализация контроллера
     */
    public void init() {
        this.createTargetDialog.getSaveButton().addClickListener(this::fireSaveTarget);
        this.createTargetDialog.getCloseButton().addClickListener(event -> this.closeDialog(this.createTargetDialog));
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
     * Событие нажатия на кнопку "Сохранить"
     *
     * @param event Обработчик событий
     */
    private void fireSaveTarget(ClickEvent<Button> event) {
        Target newTarget = new Target();
        newTarget.setPlanner(this.createTargetDialog.getCurrentPlaner());
        newTarget.setStatus(Status.create);
        try {

            log.debug("Чтение ввода от пользователя");
            this.createTargetDialog.getTargetBinder().writeBean(newTarget);
            log.debug("Сохранение в БД");
            Target saved = this.service.createTarget(newTarget);
            this.rootController.getService().addTargetToPlanner(saved);
            log.info("""
                    Цель успешно сохранена.
                    ИД: {}
                    """, saved.getId());
            Notification notification = new Notification();
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.setDuration(1000);
            notification.setText("Цель успешно сохранена");
            notification.open();
            this.createTargetDialog.close();
            this.rootController.fillTargetData();
        } catch (ValidationException e) {

            log.error(e);
            Notification message = new Notification();
            message.addThemeVariants(NotificationVariant.LUMO_ERROR);
            message.setDuration(1000);
            message.setPosition(Notification.Position.MIDDLE);
            message.setText("""
                    Не удалось сохранить Цель.
                    %s
                    """.formatted(e.getMessage()));
            message.open();
        }
    }
}
