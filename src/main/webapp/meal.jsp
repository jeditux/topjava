<%--
  Created by IntelliJ IDEA.
  User: Анатолий Гостев
  Date: 01.01.2017
  Time: 22:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
    <h2>${param.action == 'create' ? 'Create Meal' : 'Edit Meal'}</h2>
    <form method="post" action="meals">
        <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal" />
        <input type="hidden" name="id" value="${meal.id}" />
        <table>
            <tr>
                <td><p>DateTime:</p></td>
                <td><input type="datetime-local" name="dateTime" value="${meal.dateTime}" /></td>
            </tr>
            <tr>
                <td><p>Description:</p></td>
                <td><input type="text" name="description" value="${meal.description}" /></td>
            </tr>
            <tr>
                <td><p>Calories:</p></td>
                <td><input type="number" name="calories" value="${meal.calories}" /></td>
            </tr>
            <tr>
                <td><button type="submit">Save</button></td>
            </tr>
        </table>
    </form>
</body>
</html>
