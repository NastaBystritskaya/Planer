package com.zinoveva.planer.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.zinoveva.planer.domain.Status;
import com.zinoveva.planer.domain.Task;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskInfoView extends VerticalLayout {

    /**
     * Задача
     */
    Task task;

    /**
     * Поле имени задачи
     */
    Text name = new Text("Задача");

    /**
     * Поле даты начала
     */
    DatePicker dateFrom = new DatePicker("с");

    /**
     * Поле дата окончания
     */
    DatePicker dateTo = new DatePicker("по");

    /**
     * Выбор статуса
     */
    ComboBox<String> status = new ComboBox<>("Статус");

    /**
     * Статусы
     */
    List<String> statuses = List.of("Новая", "Закрыта", "Активная");

    /**
     * Кнопка "Сохранить"
     */
    Button saveButton = new Button("Сохранить", VaadinIcon.CHECK.create());

    /**
     * Конструктор
     * @param task Задача
     */
    @Autowired(required = false)
    public TaskInfoView(Task task) {
        this.task = task;
    }

    /**
     * Инициализация формы
     */
    @PostConstruct
    private void init() {
        this.getStyle().setBorder("1px solid hsla(214, 45%, 20%, 0.52)");
        this.name.setText(this.task.getName());
        this.dateFrom.setValue(this.task.getStartDate());
        this.dateTo.setValue(this.task.getStartDate());
        this.add(this.name, this.dateFrom, this.dateTo, this.status, this.saveButton);
        this.initStatusCombo();
    }

    /**
     * Инициаилазия поля "Статус"
     */
    private void initStatusCombo() {
        this.status.setItems(this.statuses);
        switch (task.getStatus()) {
            case create -> this.status.setValue("Новая");
            case close -> this.status.setValue("Закрыта");
            case active -> this.status.setValue("Активная");
            default -> throw new RuntimeException("Статуса не существует");
        }

    }
}
