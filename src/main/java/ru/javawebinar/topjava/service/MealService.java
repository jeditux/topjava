package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

/**
 * GKislin
 * 15.06.2015.
 */
public interface MealService {
    Meal save(Meal meal, int userId);
    void delete(int id, int userId) throws NotFoundException;
    Meal get(int id, int userId) throws NotFoundException;
    Collection<Meal> getAll(int userId);
    default Collection<Meal> getBetweenDates(LocalDate start, LocalDate end, int userId) {
        return getFiltered(LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX), userId);
    }
    Collection<Meal> getFiltered(LocalDateTime start, LocalDateTime end, int userId);
}
