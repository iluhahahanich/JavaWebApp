<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Games</title>
        <style>
            <%@include file="../styles/style.css" %>
            <%@include file="../bootstrap-4.6.0-dist/css/bootstrap.min.css" %>
        </style>
    </head>
    <body>
        <h1 class="event-title">Games</h1>
        <table class="table table-hover table-bordered">
            <thead>
            <tr>
                <th>Title</th>
                <th>Palace</th>
                <th>Date</th>
                <th>Children</th>
                <th>Adults</th>
                <th>Elderly</th>
                <th>Score</th>
            </tr>
            </thead>
            <tbody>
                <%--@elvariable id="data" type="java.util.List"--%>
                <c:forEach items="${data}" var="game"><%--@elvariable id="var" type="Game"--%>
                    <tr>
                        <td>${game.title}</td>
                        <td>${game.place}</td>
                        <td>${game.dateString}</td>
                        <td>${game.attendance.children}</td>
                        <td>${game.attendance.adults}</td>
                        <td>${game.attendance.elderly}</td>
                        <td>${game.score}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </body>
</html>
