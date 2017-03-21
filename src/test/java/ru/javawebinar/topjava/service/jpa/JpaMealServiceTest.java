package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealServiceTest;

/**
 * Created by Анатолий Гостев on 18.03.2017.
 */
@ActiveProfiles(Profiles.JPA)
public class JpaMealServiceTest extends MealServiceTest {
}
