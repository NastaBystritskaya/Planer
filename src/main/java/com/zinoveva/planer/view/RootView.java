package com.zinoveva.planer.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.zinoveva.planer.domain.Status;
import com.zinoveva.planer.domain.Target;
import com.zinoveva.planer.domain.Task;
import com.zinoveva.planer.view.controllers.RootController;
import com.zinoveva.planer.view.dialogs.target.CreateTargetDialog;
import com.zinoveva.planer.view.dialogs.task.CreateTaskDialog;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

/**
 * Главная страница программы
 */
@Route("")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RootView extends VerticalLayout {

    /**
     * Название программы
     */
    @SuppressWarnings("FieldCanBeLocal")
    Text label = new Text("Удобный планер");

    /**
     * Обертка "Цели"
     */
    HorizontalLayout targetWrapper = new HorizontalLayout();

    /**
     * Список целей
     */
    ComboBox<Target> targetComboBox = new ComboBox<>("Цели");

    /**
     * Кнопка "Добавить цель в список"
     */
    Button targetCreateButton = new Button(VaadinIcon.PLUS.create());

    /**
     * Кнопка "Показать цель"
     */
    Button showTargetButton = new Button(VaadinIcon.EYE.create());

    /**
     * Кнопка "Удалить цель из списка"
     */
    Button targetCloseButton = new Button(VaadinIcon.CLOSE.create());

    /**
     * Информация о выбраной цели
     */
    Text targetInfo = new Text("");

    /**
     * Таблица задач целей
     */
    Grid<Task> taskGrid = new Grid<>();

    /**
     * Кнопка добавления задач
     */
    Button createTaskButton = new Button("Добавить задачу");

    /**
     * Контроллер
     */
    RootController controller;

    /**
     * Конструктор страницы
     */
    public RootView(RootController controller) {
        this.controller = controller;
        this.controller.setView(this);
        this.controller.init();

        this.setSizeFull();
        this.add(label);
        this.drawTarget();
        this.add(targetInfo);
        this.drawTaskGrid();
        this.add(createTaskButton);
    }

    /**
     * Компановка целей с кнопками "добавить/удалить"
     */
    private void drawTarget() {
        this.targetWrapper.setWidth("100%");
        this.targetWrapper.setAlignItems(Alignment.END);
        this.targetComboBox.setWidth("20%");
        this.targetComboBox.setMinWidth("400px");
        this.targetWrapper.getThemeList().add("spacing-xs");
        this.targetCreateButton.setTooltipText("Создание новой цели");
        this.showTargetButton.setTooltipText("Показать цель");
        this.targetCloseButton.setTooltipText("Закрыть цель");
        this.targetWrapper.add(this.targetComboBox, this.showTargetButton, this.targetCreateButton, this.targetCloseButton);

        this.add(targetWrapper);
    }

    /**
     * Компоновка таблицы с задачами
     */
    private void drawTaskGrid() {
        this.taskGrid.setSizeFull();
        this.taskGrid.addColumn(this.getNewTaskRender()).setHeader("Новые задачи").setTextAlign(ColumnTextAlign.CENTER);
        this.taskGrid.addColumn(this.getActiveRender()).setHeader("Активные задачи").setTextAlign(ColumnTextAlign.CENTER);
        this.taskGrid.addColumn(this.getCloseRender()).setHeader("Завершенные задачи").setTextAlign(ColumnTextAlign.CENTER);
        this.add(taskGrid);
    }

    /**
     * Отрисовка новых задач в таблице
     * @return Новые задачи
     */
    private ComponentRenderer<VerticalLayout, Task> getNewTaskRender() {
        return new ComponentRenderer<>(VerticalLayout::new, (wrapper, task) -> {
            if(task.getStatus() == Status.create) {
                wrapper.add(this.controller.getTaskView(task));
            }
        });
    }

    /**
     * Отрисовка новых задач в таблице
     * @return Новые задачи
     */
    private ComponentRenderer<VerticalLayout, Task> getActiveRender() {
        return new ComponentRenderer<>(VerticalLayout::new, (wrapper, task) -> {
            if(task.getStatus() == Status.active) {
                wrapper.add(this.controller.getTaskView(task));
            }
        });
    }

    /**
     * Отрисовка новых задач в таблице
     * @return Новые задачи
     */
    private ComponentRenderer<VerticalLayout, Task> getCloseRender() {
        return new ComponentRenderer<>(VerticalLayout::new, (wrapper, task) -> {
            if(task.getStatus() == Status.close) {
                wrapper.add(this.controller.getTaskView(task));
            }
        });
    }

}

