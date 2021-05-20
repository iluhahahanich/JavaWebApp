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
        <table class="table table-hover table-bordered">
            <thead>
                <tr>
                    <th>Options</th>
                    <th>Title</th>
                    <th>Palace</th>
                    <th>Date</th>
                    <th>Children</th>
                    <th>Adults</th>
                    <th>Elderly</th>
                    <th>Gold</th>
                    <th>Silver</th>
                    <th>Bronze</th>
                </tr>
            </thead>
            <tbody>
                <jsp:useBean id="data" scope="request" type="java.util.List"/>
                <c:forEach items="${data}" var="comp">
                    <tr>
                        <td>
                            <form method="post" action="competitions_changing">
                                <button class="btn btn-primary" type="submit" name="changeId" value="${comp.id}">change</button>
                            </form>
                            <form method="post">
                                <button class="btn btn-primary" type="submit" name="deleteId" value="${comp.id}">delete</button>
                            </form>
                        </td>
                        <td>${comp.title}</td>
                        <td>${comp.place}</td>
                        <td>${comp.dateString}</td>
                        <td>${comp.attendance.children}</td>
                        <td>${comp.attendance.adults}</td>
                        <td>${comp.attendance.elderly}</td>
                        <td>${comp.pedestal.gold}</td>
                        <td>${comp.pedestal.silver}</td>
                        <td>${comp.pedestal.bronze}</td>
                    </tr>

                </c:forEach>
            </tbody>
        </table>
        <form method="post" action="competitions_changing">
            <button class="new-btn btn btn-primary" type="submit" name="new" value="1">new</button>
        </form>
    </body>
</html>
