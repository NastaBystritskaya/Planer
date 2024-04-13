package com.zinoveva.planer.service;

import com.zinoveva.planer.domain.Planner;
import com.zinoveva.planer.domain.Target;
import com.zinoveva.planer.repositories.PlannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис планера
 */
@Service
public class PlannerService {
    @Autowired
    PlannerRepository plannerRepository;

    /**
     * Создает или обновляет планер в базе данных
     * @param planner сохраненный планер
     */
    public void createPlan(Planner planner) {
        this.plannerRepository.save(planner);
    }

    /**
     * Получает список планеров в базе данных
     * @return список планеров из базы данных
     */
    public List<Planner> getAll() {
        return this.plannerRepository.findAll();
    }

    /**
     * Удаляет планер из базы данных
     * @param id id планера
     */
    public void deletePlanner(Long id) {
        this.plannerRepository.deleteById(id);
    }

    /**
     * Добавляет цель в список целей планера
     * @param planner планер
     * @param target цель
     */
    public void addTargetToPlanner(Planner planner, Target target) {
        planner.getListTarget().add(target);
        target.setPlanner(planner);
        this.createPlan(planner);
    }

    /**
     * Удаляет цель из списка целей планера
     * @param planner планер
     * @param target цель
     */
    public void deleteTargetFromPlanner(Planner planner, Target target){
        planner.getListTarget().remove(target);
        this.createPlan(planner);
    }




}
