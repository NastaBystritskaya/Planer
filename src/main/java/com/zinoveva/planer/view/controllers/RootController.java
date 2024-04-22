package com.zinoveva.planer.view.controllers;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.zinoveva.planer.domain.Planner;
import com.zinoveva.planer.domain.Status;
import com.zinoveva.planer.domain.Target;
import com.zinoveva.planer.domain.Task;
import com.zinoveva.planer.service.PlannerService;
import com.zinoveva.planer.view.RootView;
import com.zinoveva.planer.view.TaskInfoView;
import com.zinoveva.planer.view.dialogs.target.CreateTargetDialog;
import com.zinoveva.planer.view.dialogs.task.CreateTaskDialog;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Контроллер главной страницы
 */
@Controller
@Lazy
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Getter
public class RootController {

    /**
     * Сервис планнера
     */
    PlannerService service;


    /**
     * Контекст
     */
    ApplicationContext context;

    /**
     * Главная страница
     */
    @Setter
    @NonFinal
    RootView view;

    /**
     * Инициализация контроллера
     */
    public void init() {
        this.view.getTargetComboBox().addValueChangeListener(this::fireTargetChange);
        this.view.getCreateTaskButton().addClickListener(this::fireOpenTaskDialog);
        this.fillPlanerLabel();
        this.view.getTargetCreateButton().addClickListener(this::fireOpenTargetDialog);
        this.view.getShowTargetButton().addClickListener(this::fireShowTargetDialog);
        this.view.getTargetCloseButton().addClickListener(this::fireTargetClose);
        this.fillTargetData();
        this.fillGridValues();
    }

    /**
     * Нажатие на кнопку "Закрыть цель"
     * @param event Обработчик событий
     */
    private void fireTargetClose(ClickEvent<Button> event) {
        Target value = this.view.getTargetComboBox().getValue();
        if (value == null) {
            Notification.show("Выбирете цель из списка для закрытия");
        } else {
            try {
                this.getTargetController().getService().closeTarget(value);
            } catch (Throwable th) {
                Notification message = new Notification(th.getMessage(), 2000, Notification.Position.MIDDLE);
                message.addThemeVariants(NotificationVariant.LUMO_ERROR);
                message.open();
            }
        }
    }

    /**
     * Измена значения поля со списком "Цели"
     * @param event Обработчик событий
     */
    private void fireTargetChange(AbstractField.ComponentValueChangeEvent<ComboBox<Target>, Target> event) {
        Target val = event.getValue();
        this.fillGridValues(val);
    }

    private void fillGridValues() {
        Target value = this.view.getTargetComboBox().getValue();
        this.fillGridValues(value);
    }

    /**
     * Заполняет таблицу задач значениями
     * @param current Текущая цели
     */
    private void fillGridValues(Target current) {
        if(current != null) {
            List<Task> tasks = current.getTasks();
            this.view.getTaskGrid().setItems(
                    tasks
                            .stream()
                            .sorted(Comparator.comparing(Task::getEndDate))
                            .toList());
        }
    }

    /**
     * Получает контроллер цели
     * @return Контроллер
     */
    public TargetController getTargetController() {
        TargetController controller = this.context.getBean(TargetController.class);
        controller.setRootController(this);
        return controller;
    }

    /**
     * Получает контроллер задач
     * @return Контроллер
     */
    public TaskController getTaskController() {
        TaskController taskController = this.context.getBean(TaskController.class);
        taskController.setRootController(this);
        return taskController;
    }

    /**
     * Нажатие на кноку "Просмотр цели"
     * @param event Обработчик событий
     */
    private void fireShowTargetDialog(ClickEvent<Button> event) {
        Target value = this.view.getTargetComboBox().getValue();
        if(value != null) {
            CreateTargetDialog dialog = this.context.getBean(CreateTargetDialog.class, value);
            TargetController controller = this.getTargetController();
            controller.setCreateTargetDialog(dialog);
            controller.init();
            controller.getCreateTargetDialog().open();
        } else {
            Notification.show("Выберите одну из целей для просмотра");
        }

    }

    /**
     * Нажатие на кнопку "Создать задачу"
     * @param event Обработчик событий
     */
    private void fireOpenTargetDialog(ClickEvent<Button> event) {
        Optional<Planner> currentPlaner = this.service.getCurrentPlaner();
        currentPlaner.ifPresentOrElse(
                planner -> {
                    CreateTargetDialog dialog = this.context.getBean(CreateTargetDialog.class, planner);
                    TargetController controller = this.context.getBean(TargetController.class);
                    controller.setRootController(this);
                    controller.setCreateTargetDialog(dialog);
                    controller.init();
                    controller.getCreateTargetDialog().open();
                },
                () -> {
                    Notification notification = new Notification("Не найден планнер для создания цели", 2000, Notification.Position.MIDDLE);
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    notification.open();
                });
    }

    /**
     * Заполняет данными поле со списком "Цели"
     */
    public void fillTargetData() {
        Optional<Planner> currentPlaner = this.service.getCurrentPlaner();
        currentPlaner.ifPresentOrElse(
                planner -> {
                    List<Target> data = planner.getListTarget();
                    this.view.getTargetComboBox().setItems(
                            planner.getListTarget()
                                    .stream()
                                    .sorted(Comparator.comparing(Target::getEndDate))
                                    .toList());
                    if(!data.isEmpty())
                        this.view.getTargetComboBox().setValue(data.get(0));
                },
                () -> {
                    Notification notification = new Notification("Не найден планнер", 2000, Notification.Position.MIDDLE);
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    notification.open();
                }
        );
    }

    /**
     * Заполнение заголовка планнера
     */
    private void fillPlanerLabel() {
        Optional<Planner> currentPlaner = this.service.getCurrentPlaner();
        currentPlaner.ifPresentOrElse(
                planner -> this.view.getLabel().setText(planner.getName()),
                () -> {
                    Notification notification = new Notification("Не найден планнер", 2000, Notification.Position.MIDDLE);
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    notification.open();
                });
    }

    /**
     * Нажатие на кнопку "Создать задачу"
     *
     * @param event Обработчик событий
     */
    private void fireOpenTaskDialog(ClickEvent<Button> event) {
        Target currentTarget = this.view.getTargetComboBox().getValue();
        if (currentTarget == null) {
            Notification message = new Notification();
            message.addThemeVariants(NotificationVariant.LUMO_ERROR);
            message.setText("Необходимо выбрать цель из списка");
            message.setDuration(2000);
            message.setPosition(Notification.Position.MIDDLE);
            message.open();
            return;
        }
        TaskController taskController = this.context.getBean(TaskController.class);
        taskController.setRootController(this);
        CreateTaskDialog dialog = this.context.getBean(CreateTaskDialog.class, currentTarget);
        taskController.setCreateTaskDialog(dialog);
        taskController.init();
        init();
        taskController.getCreateTaskDialog().open();
    }

    /**
     * Получает представление о задачи
     * @param task Задача
     * @return Представление
     */
    public TaskInfoView getTaskView(Task task) {
        TaskInfoView view = this.context.getBean(TaskInfoView.class, task);
        view.addDoubleClickListener(event -> this.fireShowTask(view));
        view.getStatus().addValueChangeListener(event -> this.fireTaskStatusChange(event, view));
        return view;
    }

    /**
     * Изменяет статус задачи
     * @param event Обработчик событий
     * @param view Представление задачи
     */
    private void fireTaskStatusChange(AbstractField.ComponentValueChangeEvent<ComboBox<String>, String> event, TaskInfoView view) {
        Status status = null;
        switch (event.getValue()) {
            case "Новая" -> status = Status.create;
            case "Активная" -> status = Status.active;
            case "Закрыта" -> status = Status.close;
            default -> Notification.show("Статуса '%s' не существует".formatted(event.getValue()));
        }
        this.getTaskController().getService().changeStatus(view.getTask(), status);
        this.fillGridValues();
    }

    /**
     * Двойное нажатие на форму "Задача"
     * @param view Представление задачи
     */
    private void fireShowTask(TaskInfoView view) {
        TaskController taskController = this.context.getBean(TaskController.class);
        taskController.setRootController(this);
        CreateTaskDialog dialog = this.context.getBean(CreateTaskDialog.class, view.getTask());
        taskController.setCreateTaskDialog(dialog);
        taskController.init();
    }

}
