package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {
    public static final int BREAKFAST_ID = START_SEQ + 2;
    public static final int DINNER_ID = START_SEQ + 3;
    public static final int ADMIN_BREAKFAST_ID = START_SEQ + 4;

    public static final Meal BREAKFAST = new Meal(BREAKFAST_ID, LocalDateTime.of(2017, Month.FEBRUARY, 3, 8, 0, 0), "Breakfast", 500);
    public static final Meal DINNER = new Meal(DINNER_ID, LocalDateTime.of(2017, Month.MARCH, 9, 14, 13, 0), "Dinner", 1200);
    public static final Meal ADMIN_BREAKFAST = new Meal(ADMIN_BREAKFAST_ID, LocalDateTime.of(2017, Month.FEBRUARY, 14, 9, 0, 0), "Admin Breakfast", 1000);

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                    && Objects.equals(expected.getDateTime(), actual.getDateTime())
                    && Objects.equals(expected.getDescription(), actual.getDescription())
                    && Objects.equals(expected.getCalories(), actual.getCalories())
                    )
    );

}
