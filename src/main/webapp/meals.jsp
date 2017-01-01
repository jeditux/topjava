<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals list</title>
    <style>
        .normal {color : green;}
        .exceeded {color : red;}
    </style>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>
<a href="/meals?action=create">New meal</a>
<table border="1">
    <thead>
    <tr>
        <th>Date Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed" />
        <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
            <%-- <td><c:out value="${meal.dateTime}" /></td>--%>
            <td>
                <fmt:parseDate value="${meal.dateTime}" pattern="y-M-dd'T'H:m" var="parsedDate" />
                <fmt:formatDate value="${parsedDate}" pattern="yyyy.MM.dd HH:mm" />
            </td>
            <td><c:out value="${meal.description}" /></td>
            <td><c:out value="${meal.calories}" /></td>
            <td><a href="/meals?action=update&id=${meal.id}">Update</a></td>
            <td><a href="/meals?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>