package com.zinoveva.planer.service;

import com.zinoveva.planer.domain.Planner;
import com.zinoveva.planer.domain.Target;
import com.zinoveva.planer.repositories.PlannerRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис планера
 */
@Service
@RequiredArgsConstructor
@Getter
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlannerService {

    /**
     * Репозиторий планнера
     */
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
     * @param target цель
     */
    public void addTargetToPlanner(Target target) {
        Optional<Planner> currentPlaner = this.getCurrentPlaner();
        currentPlaner.ifPresent(planner -> {
            planner.getListTarget().add(target);
            target.setPlanner(planner);
            this.createPlan(planner);
        });
    }

    /**
     * Удаляет цель из списка целей планера
     * @param target цель
     */
    public void deleteTargetFromPlanner(Target target) {
        Optional<Planner> currentPlaner = this.getCurrentPlaner();
        currentPlaner.ifPresent(planner -> {
            planner.getListTarget().remove(target);
            this.createPlan(planner);
        });
    }

    /**
     * Поучает текущий планнер
     * @return Планнер
     */
    public Optional<Planner> getCurrentPlaner() {
        log.info("Получение текущего планнера");
        return this.plannerRepository.findById(1L);
    }




}
