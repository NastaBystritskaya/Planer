package com.zinoveva.planer.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Окно добавления задачи
 */
public class CreateTaskDialog extends Dialog {

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
     * Конструкор (компоновка элементов на форме)
     */
    public CreateTaskDialog() {
        this.setHeaderTitle("Добавить задачу");
        this.wrapper.add(nameField, descriptionField, dateStart, dateEnd);
        this.getFooter().add(saveButton, closeButton);
        this.wrapper.setSizeFull();
        this.add(wrapper);
        this.closeButton.addClickListener(event -> this.close());
    }
}
