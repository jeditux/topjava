package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Анатолий Гостев on 24.12.2016.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private MealRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new InMemoryMealRepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Forwarding to meal.jsp");
        String action = req.getParameter("action");
        if(action == null) {
            req.setAttribute("meals", MealsUtil.getWithExceeded(repository.getAll(), 2000));
            req.getRequestDispatcher("meals.jsp").forward(req, resp);
        }
        else if(action.equals("create")){
            Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 0);
            req.setAttribute("meal", meal);
            req.getRequestDispatcher("meal.jsp").forward(req, resp);
        }
        else if(action.equals("update")){
            String sId = req.getParameter("id");
            if(sId != null){
                int id = Integer.parseInt(sId);
                Meal meal = repository.get(id);
                req.setAttribute("meal", meal);
                req.getRequestDispatcher("meal.jsp").forward(req, resp);
            }
        }
        else if(action.equals("delete")){
            String sId = req.getParameter("id");
            if (sId != null){
                int id = Integer.parseInt(sId);
                LOG.info("Delete ()", id);
                repository.delete(id);
                resp.sendRedirect("/meals");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.parseInt(id),
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.valueOf(req.getParameter("calories")));
        repository.save(meal);
        resp.sendRedirect("meals");
    }
}
