package com.zinoveva.planer.view.dialogs.task;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.zinoveva.planer.domain.Target;
import com.zinoveva.planer.domain.Task;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Окно добавления задачи
 */
@Component
@Lazy
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class CreateTaskDialog extends Dialog {

    /**
     * Заполнение
     */
    Binder<Task> taskBinder = new Binder<>();

    /**
     * Обертка компонетнов по вертикали
     */
    VerticalLayout wrapper = new VerticalLayout();

    /**
     * Поле название задачи
     */
    TextField nameField = new TextField("Название задачи");

    /**
     * Поле описание задачи
     */
    TextArea descriptionField = new TextArea("Описание задачи");

    /**
     * Поле дата начала задачи
     */
    DatePicker dateStart = new DatePicker("Дата начала задачи");

    /**
     * Поле дата завершения задачи
     */
    DatePicker dateEnd = new DatePicker("Дата завершения задачи");

    /**
     * Кнопка "сохранить"
     */
    Button saveButton = new Button("Сохранить");

    /**
     * Кнопка "Закрыть"
     */
    Button closeButton = new Button("Закрыть");

    /**
     * Цель, под которую создается задача
     */
    @NonFinal
    Target currentTarget;

    /**
     * Задача для отображения или изменения
     */
    @NonFinal
    Task task;

    /**
     * Конструктор для отображения или изменения
     * @param task Задача
     */
    @Autowired(required = false)
    public CreateTaskDialog(Task task) {
        this.task = task;
        this.initBinder();
        this.taskBinder.readBean(task);
        this.currentTarget = task.getTarget();

    }

    /**
     * Конструктор для создания новой задачи
     * @param currentTarget Цель, под которую заводится задача
     */
    @Autowired(required = false)
    public CreateTaskDialog(Target currentTarget) {
        this.currentTarget = currentTarget;
        this.initBinder();
    }

    /**
     * Конструкор (компоновка элементов на форме)
     */
    @PostConstruct
    public void init() {
        this.setHeaderTitle("Добавить задачу");
        this.wrapper.add(nameField, descriptionField, dateStart, dateEnd);
        this.getFooter().add(saveButton, closeButton);
        this.wrapper.setSizeFull();
        this.add(wrapper);
    }

    /**
     * Инициализация заполнения полей
     */
    private void initBinder() {
        this.taskBinder
                .forField(this.nameField)
                .asRequired("Имя задачи должно быть заполнено")
                .bind(Task::getName, Task::setName);

        this.taskBinder
                .forField(this.descriptionField)
                .bind(Task::getDescription, Task::setDescription);

        this.taskBinder
                .forField(this.dateStart)
                .bind(Task::getStartDate, Task::setStartDate);

        this.taskBinder
                .forField(this.dateEnd)
                .asRequired("Требуется ввести дату окончания задачи")
                .withValidator(
                        date -> date.isAfter(this.dateStart.getValue()),
                        "Дата окончания не должна быть меньше чем дата начала"
                )
                .bind(Task::getEndDate, Task::setEndDate);
    }


}
