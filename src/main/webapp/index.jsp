<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Home</title>
        <style>
            <%@include file="styles/style.css" %>
            <%@include file="bootstrap-4.6.0-dist/css/bootstrap.min.css" %>
        </style>
    </head>
    <body>
        <h1 class="event-title">Sport Events</h1>
        <ul class="centered list-group">
            <li class="list-group-item"><a href="games">Games</a></li>
            <li class="list-group-item"><a href="matches">Matches</a></li>
            <li class="list-group-item"><a href="competitions">Competitions</a></li>
            <li class="list-group-item"><a href="competitions_sorted">Sorted Competitions</a></li>
        </ul>
        <p class="dao-select">
            <a href="dao_select">SELECT DAO</a>
        </p>
    </body>
</html>