package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */

@Controller
public class MealRestController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll() {
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getFiltered(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return MealsUtil.getFilteredWithExceeded(service.getBetweenDates(
                startDate != null ? startDate : LocalDate.MIN,
                endDate != null ? endDate : LocalDate.MAX,
                AuthorizedUser.id()),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay());
    }

    public Meal get(int id) {
        LOG.info("Get " + id);
        return service.get(id, AuthorizedUser.id());
    }

    public void delete(int id) {
        LOG.info("Delete " + id);
        service.delete(id, AuthorizedUser.id());
    }

    public Meal create(Meal meal) {
        LOG.info("Create meal");
        return service.save(meal, AuthorizedUser.id());
    }

    public Meal update(Meal meal) {
        LOG.info("Update meal");
        return service.save(meal, AuthorizedUser.id());
    }

}
