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
    <p class="btn position-absolute">
        <a href="home">&larr;Home</a>
    </p>
    <h1 class="event-title">Competitions</h1>
    <form method="post">
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
                            <c:choose>
                                <%--@elvariable id="changing" type="java.lang.Integer"--%>
                                <c:when test="${changing == comp.id}">
                                    <button class="btn btn-primary" type="submit" name="okId" value="${comp.id}">ok</button>
                                    <form method="post">
                                        <button class="btn btn-primary" type="submit" name="changingCancel" value="${comp.id}">cancel</button>
                                    </form>
                                </c:when>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <%--@elvariable id="changing" type="java.lang.Integer"--%>
                                <c:when test="${changing == comp.id}">
                                    <input type="text" name="title" value="${comp.title}">
                                </c:when>
                                <c:otherwise>
                                    ${comp.title}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <%--@elvariable id="changing" type="java.lang.Integer"--%>
                                <c:when test="${changing == comp.id}">
                                    <input type="text" name="place" value="${comp.place}">
                                </c:when>
                                <c:otherwise>
                                    ${comp.place}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <%--@elvariable id="changing" type="java.lang.Integer"--%>
                                <c:when test="${changing == comp.id}">
                                    <input type="date" name="date" value="${comp.dateString}">
                                </c:when>
                                <c:otherwise>
                                    ${comp.dateString}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <%--@elvariable id="changing" type="java.lang.Integer"--%>
                                <c:when test="${changing == comp.id}">
                                    <input type="number" name="children" value="${comp.attendance.children}">
                                </c:when>
                                <c:otherwise>
                                    ${comp.attendance.children}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <%--@elvariable id="changing" type="java.lang.Integer"--%>
                                <c:when test="${changing == comp.id}">
                                    <input type="number" name="adults" value="${comp.attendance.adults}">
                                </c:when>
                                <c:otherwise>
                                    ${comp.attendance.adults}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <%--@elvariable id="changing" type="java.lang.Integer"--%>
                                <c:when test="${changing == comp.id}">
                                    <input type="number" name="elderly" value="${comp.attendance.elderly}">
                                </c:when>
                                <c:otherwise>
                                    ${comp.attendance.elderly}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <%--@elvariable id="changing" type="java.lang.Integer"--%>
                                <c:when test="${changing == comp.id}">
                                    <input type="text" name="gold" value="${comp.pedestal.gold}">
                                </c:when>
                                <c:otherwise>
                                    ${comp.pedestal.gold}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <%--@elvariable id="changing" type="java.lang.Integer"--%>
                                <c:when test="${changing == comp.id}">
                                    <input type="text" name="silver" value="${comp.pedestal.silver}">
                                </c:when>
                                <c:otherwise>
                                    ${comp.pedestal.silver}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <%--@elvariable id="changing" type="java.lang.Integer"--%>
                                <c:when test="${changing == comp.id}">
                                    <input type="text" name="bronze" value="${comp.pedestal.bronze}">
                                </c:when>
                                <c:otherwise>
                                    ${comp.pedestal.bronze}
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </form>
</body>
</html>
