package com.zinoveva.planer.view.dialogs.target;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextAreaVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.zinoveva.planer.domain.Planner;
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
 * Окно добавление цели
 */
@Component
@Lazy
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateTargetDialog extends Dialog {

    /**
     * Заполнитель формы
     */
    Binder<Target> targetBinder = new Binder<>();

    /**
     * Обертка компонетнов по вертикали
     */
    VerticalLayout wrapper = new VerticalLayout();

    /**
     * Поле название цели
     */
    TextField nameField = new TextField("Название цели");

    /**
     * Поле описание цели
     */
    TextArea descriptionField = new TextArea("Описание цели");

    /**
     * Поле дата начала цели
     */
    DatePicker dateStart = new DatePicker("Дата начала цели");

    /**
     * Поле дата завершения цели
     */
    DatePicker dateEnd = new DatePicker("Дата завершения цели");

    /**
     * Кнопка "сохранить"
     */
    Button saveButton = new Button("Сохранить");

    /**
     * Кнопка "закрыть"
     */
    Button closeButton = new Button("Закрыть");

    /**
     * Планер, в котором создается цель
     */
    @NonFinal
    Planner currentPlaner;

    /**
     * Цель для просмотра или измененеия
     */
    @NonFinal
    Target target;

    /**
     * Конструктор для создания новой задачи
     * @param currentPlaner Текущий планер
     */
    @Autowired(required = false)
    public CreateTargetDialog(Planner currentPlaner) {
        this.currentPlaner = currentPlaner;
        this.initBinder();
    }

    /**
     * Конструктор для просмотра или изменения цели
     * @param target Цель
     */
    @Autowired(required = false)
    public CreateTargetDialog(Target target) {
        this.target = target;
        this.initBinder();
        this.targetBinder.readBean(target);
        this.currentPlaner = target.getPlanner();
    }

    /**
     * Конструкор (компоновка элементов на форме)
     */
    @PostConstruct
    public void init() {
        this.setHeaderTitle("Добавить цель");
        this.wrapper.add(nameField, descriptionField, dateStart, dateEnd);
        this.getFooter().add(saveButton, closeButton);
        this.wrapper.setSizeFull();
        this.add(wrapper);
    }

    /**
     * Инициализация заполнения полей
     */
    private void initBinder() {
        this.targetBinder
                .forField(this.nameField)
                .asRequired("Поле наименование должно быть заполнено")
                .bind(Target::getName, Target::setName);

        this.targetBinder
                .forField(this.descriptionField)
                .bind(Target::getDescription, Target::setDescription);

        this.targetBinder
                .forField(this.dateStart)
                .bind(Target::getStartDate, Target::setStartDate);

        this.targetBinder
                .forField(this.dateEnd)
                .asRequired("Требуется ввести дату окончания задачи")
                .withValidator(
                        date -> date.isAfter(this.dateStart.getValue()),
                        "Дата окончания не должна быть меньше чем дата начала"
                )
                .bind(Target::getEndDate, Target::setEndDate);
    }
}
