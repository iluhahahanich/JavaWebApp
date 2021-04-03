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
    <table class="table">
        <tr>
            <th>Title</th>
            <th>Palace</th>
            <th>Date</th>
        </tr>
        <%--@elvariable id="data" type="java.util.List"--%>
        <c:forEach items="${data}" var="game"><%--@elvariable id="var" type="Game"--%>
            <tr>
                <td>${game.title}</td>
                <td>${game.place}</td>
                <td>${game.date}</td>
            </tr>
        </c:forEach>
    </table>

<%--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>--%>
<%--    <script>--%>
<%--        <%@include file="../bootstrap-4.6.0-dist/js/bootstrap.min.js" %>--%>
<%--    </script>--%>
</body>
</html>
