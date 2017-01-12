package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    private Comparator<Meal> comparator = Comparator.comparing(Meal::getDateTime).reversed();

    {
        MealsUtil.MEALS.forEach(m -> save(m, InMemoryUserRepositoryImpl.USER_ID));
        save(new Meal(LocalDateTime.of(2016, Month.DECEMBER, 31, 21, 0), "Оливье", 500), InMemoryUserRepositoryImpl.ADMIN_ID);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        meal.setUserId(userId);
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal m = repository.get(id);
        return m != null && m.getUserId() == userId && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal m = repository.get(id);
        return m.getUserId() == userId ? m : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.values().stream().filter(m -> (m.getUserId() == userId)).sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getFiltered(LocalDateTime start, LocalDateTime end, int userId) {
        return repository.values().stream().filter(m -> (m.getUserId() == userId)).sorted(comparator)
                .filter(m -> (DateTimeUtil.isBetween(m.getDateTime(), start, end)))
                .collect(Collectors.toList());
    }
}

