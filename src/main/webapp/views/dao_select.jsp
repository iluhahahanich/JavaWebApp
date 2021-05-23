<%@ page import="app.DaoType" %>
<%@ page import="app.ServiceLayer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Select Dao</title>
    <style>
        <%@include file="../styles/style.css" %>
        <%@include file="../bootstrap-4.6.0-dist/css/bootstrap.min.css" %>
    </style>
</head>
<body>
    <h1 class="event-title">Dao Select</h1>
    <form action="dao_select" method="post">
        <select class="custom-select custom-select-lg" size="5" name="daoType" >
            <% for(DaoType type: DaoType.values()) { %>
                <option class="centered"
                        <% if(ServiceLayer.getDaoType() == type) {%> selected <% } %>
                        value = "<%= type.name()%>" >
                    <%= type.name() %>
                </option >
            <% } %>
        </select >
        <button class="cent-btn btn btn-primary" type="submit">OK</button>
    </form>
</body>
</html>
