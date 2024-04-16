package com.zinoveva.planer.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.zinoveva.planer.domain.Target;
import com.zinoveva.planer.domain.Task;

/**
 * Главная страница программы
 */
@Route("")
public class RootView extends VerticalLayout {

    /**
     * Название программы
     */
    private Text label = new Text("Удобный планер");

    /**
     * Обертка "Цели"
     */
    private HorizontalLayout targetWrapper = new HorizontalLayout();

    /**
     * Список целей
     */
    private ComboBox<Target> targetComboBox = new ComboBox<>("Цели");

    /**
     * Кнопка "Добавить цель в список"
     */
    private Button targetCreateButton = new Button(VaadinIcon.PLUS.create());

    /**
     * Кнопка "Удалить цель из списка"
     */
    private Button targetDeleteButton = new Button(VaadinIcon.MINUS.create());

    /**
     * Информация о выбраной цели
     */
    private Text targetInfo = new Text("");

    /**
     * Таблица задач целей
     */
    private Grid<Task> taskGrid = new Grid<>();

    /**
     * Кнопка добавления задач
     */
    private Button createTaskButton = new Button("Добавить задачу");

    /**
     * Конструктор страницы
     */
    public RootView() {
        this.setSizeFull();
        this.add(label);
        this.drawTarget();
        this.add(targetInfo);
        this.drawTaskGrid();
        this.createTaskButton.addClickListener(event -> {
            CreateTaskDialog dialog = new CreateTaskDialog();
            dialog.open();
        });
        this.add(createTaskButton);
    }

    /**
     * Компановка целей с кнопками "добавить/удалить"
     */
    private void drawTarget() {
        this.targetWrapper.setWidth("100%");
        this.targetWrapper.setAlignItems(Alignment.END);
        this.targetCreateButton.addClickListener(event -> {
            CreateTargetDialog dialog = new CreateTargetDialog();
            dialog.open();
        });
        this.targetWrapper.add(this.targetComboBox, this.targetCreateButton, this.targetDeleteButton);
        this.add(targetWrapper);
    }

    /**
     * Компоновка таблицы с задачами
     */
    private void drawTaskGrid() {
        this.taskGrid.setSizeFull();
        this.taskGrid.addColumn(Task::getName).setHeader("Новые задачи");
        this.taskGrid.addColumn(Task::getName).setHeader("Активные задачи");
        this.taskGrid.addColumn(Task::getName).setHeader("Завершенные задачи");
        this.add(taskGrid);
    }
}

