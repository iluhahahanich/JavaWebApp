<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Competitions</title>
        <style>
            <%@include file="../styles/style.css" %>
            <%@include file="../bootstrap-4.6.0-dist/css/bootstrap.min.css" %>
        </style>
    </head>
    <body>
        <h1 class="event-title">Competitions</h1>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Palace</th>
                    <th>Date</th>
                </tr>
            </thead>
            <tbody>
                <%--@elvariable id="data" type="java.util.List"--%>
                <c:forEach items="${data}" var="comp"><%--@elvariable id="var" type="models.sportEvents.Competition"--%>
                    <tr>
                        <td>${comp.title}</td>
                        <td>${comp.place}</td>
                        <td>${comp.date}</td>
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
