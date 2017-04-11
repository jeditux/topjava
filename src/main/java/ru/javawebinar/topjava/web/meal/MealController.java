package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Created by Анатолий Гостев on 09.04.2017.
 */

@Controller
@RequestMapping(value = "/meals")
public class MealController extends AbstractMealController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String meals(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String filterMeals(HttpServletRequest request) {
        LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = DateTimeUtil.parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = DateTimeUtil.parseLocalTime(request.getParameter("endTime"));
        request.setAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createMeal(HttpServletRequest request) {
        request.setAttribute("meal", new Meal());
        return "meal";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String updateMeal(HttpServletRequest request) {
        String sId = Objects.requireNonNull(request.getParameter("id"));
        Integer id = Integer.valueOf(sId);
        Meal meal = get(id);
        request.setAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String saveMeal(HttpServletRequest request) {
        Integer id = request.getParameter("id").isEmpty() ? null : Integer.valueOf(request.getParameter("id"));
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        Integer calories = Integer.valueOf(request.getParameter("calories"));
        Meal meal = new Meal(id, dateTime, description, calories);
        if(meal.isNew()) create(meal);
        else update(meal, meal.getId());
        return "redirect:/meals";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteMeal(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        int mealId = Integer.valueOf(paramId);
        delete(mealId);
        return "redirect:/meals";
    }
}
