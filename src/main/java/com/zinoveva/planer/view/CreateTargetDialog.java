package com.zinoveva.planer.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextAreaVariant;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Окно добавление цели
 */
public class CreateTargetDialog extends Dialog {

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
     * Конструкор (компоновка элементов на форме)
     */
    public CreateTargetDialog() {
        this.setHeaderTitle("Добавить цель");
        this.wrapper.add(nameField, descriptionField, dateStart, dateEnd);
        this.getFooter().add(saveButton, closeButton);
        this.wrapper.setSizeFull();
        this.add(wrapper);
        this.closeButton.addClickListener(event -> this.close());
    }
}
