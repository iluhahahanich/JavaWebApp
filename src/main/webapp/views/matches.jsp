<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Matches</title>
        <style>
            <%@include file="../styles/style.css" %>
            <%@include file="../bootstrap-4.6.0-dist/css/bootstrap.min.css" %>
        </style>
    </head>
    <body>
        <p class="btn position-absolute">
            <a href="home">&larr;Home</a>
        </p>
        <h1 class="event-title">Matches</h1>
        <table class="table table-hover table-bordered">
            <thead>
            <tr>
                <th>Title</th>
                <th>Palace</th>
                <th>Date</th>
                <th>Children</th>
                <th>Adults</th>
                <th>Elderly</th>
                <th>Winner</th>
            </tr>
            </thead>
            <tbody>
                <%--@elvariable id="data" type="java.util.List"--%>
                <c:forEach items="${data}" var="match"><%--@elvariable id="var" type="models.Match"--%>
                    <tr>
                        <td>${match.title}</td>
                        <td>${match.place}</td>
                        <td>${match.dateString}</td>
                        <td>${match.attendance.children}</td>
                        <td>${match.attendance.adults}</td>
                        <td>${match.attendance.elderly}</td>
                        <td>${match.winner}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    <%--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>--%>
    <%--    <script>--%>
    <%--        <%@include file="../bootstrap-4.6.0-dist/js/bootstrap.min.js" %>--%>
    <%--    </script>--%>
    </body>
</html>
