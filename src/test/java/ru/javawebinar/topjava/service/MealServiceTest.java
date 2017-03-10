package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by Анатолий Гостев on 09.03.2017.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {
    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(DINNER_ID, USER_ID);
        MATCHER.assertEquals(DINNER, meal);
    }

    @Test (expected = NotFoundException.class)
    public void testAlienGet() throws Exception {
        Meal meal = service.get(ADMIN_BREAKFAST_ID, USER_ID);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(BREAKFAST_ID, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(DINNER), service.getAll(USER_ID));
    }

    @Test (expected = NotFoundException.class)
    public void testAlienDelete() throws Exception {
        service.delete(ADMIN_BREAKFAST_ID, USER_ID);
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        Collection<Meal> meals = service.getBetweenDates(LocalDate.of(2017, Month.FEBRUARY, 1),
                LocalDate.of(2017, Month.FEBRUARY, 28), USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(BREAKFAST), meals);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        Collection<Meal> meals = service.getBetweenDateTimes(LocalDateTime.of(2017, Month.FEBRUARY, 1, 10, 0, 0),
                LocalDateTime.of(2017, Month.FEBRUARY, 28, 16, 0, 0), USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(BREAKFAST), meals);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<Meal> meals = service.getAll(USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(DINNER, BREAKFAST), meals);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(BREAKFAST);
        updated.setDescription("Second breakfast");
        updated.setCalories(900);
        service.update(updated, USER_ID);
        MATCHER.assertEquals(updated, service.get(BREAKFAST_ID, USER_ID));
    }

    @Test (expected = NotFoundException.class)
    public void testAlienUpdate() throws Exception {
        Meal updated = new Meal(ADMIN_BREAKFAST);
        updated.setDescription("Second breakfast");
        updated.setCalories(900);
        service.update(updated, USER_ID);
    }

    @Test
    public void testSave() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.of(2017, Month.MARCH, 1, 15, 0, 0), "Lunch", 400);
        Meal created = service.save(newMeal, USER_ID);
        newMeal.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(DINNER, newMeal, BREAKFAST), service.getAll(USER_ID));
    }

}