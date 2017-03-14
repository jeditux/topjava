package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User u = em.getReference(User.class, userId);
        if(meal.isNew()){
            meal.setUser(u);
            em.persist(meal);
            return meal;
        }
        else {
            if(em.createNamedQuery(Meal.UPDATE).setParameter("dateTime", meal.getDateTime())
                    .setParameter("description", meal.getDescription())
                    .setParameter("calories", meal.getCalories())
                    .setParameter("id", meal.getId())
                    .setParameter("user", u)
                    .executeUpdate() != 0)
                return meal;
            else
                return null;
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        User u = em.getReference(User.class, userId);
        return em.createNamedQuery(Meal.DELETE).setParameter("id", id).setParameter("user", u).executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        User u = em.getReference(User.class, userId);
        List<Meal> res = em.createNamedQuery(Meal.GET_SINGLE, Meal.class)
                .setParameter("id", id)
                .setParameter("user", u)
                .getResultList();
        return DataAccessUtils.singleResult(res);
    }

    @Override
    public List<Meal> getAll(int userId) {
        User u = em.getReference(User.class, userId);
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class).setParameter("user", u).getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        User u = em.getReference(User.class, userId);
        return em.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
                .setParameter("user", u)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}