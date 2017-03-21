package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceTest;

/**
 * Created by Анатолий Гостев on 18.03.2017.
 */
/*@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))*/
//@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.JPA})
@ActiveProfiles({Profiles.JPA, Profiles.ACTIVE_DB})
public class JpaUserServiceTest extends UserServiceTest {
}
