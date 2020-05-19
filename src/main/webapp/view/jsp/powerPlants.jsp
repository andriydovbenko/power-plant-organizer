<%--suppress ALL --%>
<%@ page import="static com.electricity.enumeration.ContextAttribute.PLANT_ID" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Power plants</title>
</head>
<body>
<input style="background: #f6f6c9" size="16px" type="button" class="btn btn-lg btn-success" onclick="location.href='/in/home';" value="Return Home">
<br/><br/>
<form>
    <table class="style1">
        <tr>
            <td>Login</td>
            <td>Current Funds</td>
        </tr>
        <tr>
            <td><c:out value="${requestScope.user.login}"/></td>
            <td><fmt:formatNumber type="number" groupingUsed="false"
                                  value="${requestScope.user.currentFundsAmount}"/></td>
        </tr>
    </table>
</form>

<h1> Power plants </h1><br/>

<table class="style2">
    <tr>
        <th>Type</th>
        <th>Country</th>
        <th>Number</th>
        <th>Max Power</th>
        <th>Resource Amount</th>
        <th>Resource Consumption</th>
        <th>Work</th>
        <th>Start/Stop</th>
        <th>Edit</th>
        <th>Delete</th>

    </tr>
<%--suppress ELValidationInJSP --%>
    <c:forEach var="powerPlant" items="${requestScope.powerPlants}">
        <tr>
            <td><c:out value="${powerPlant.type}"/></td>
            <td><c:out value="${powerPlant.country}"/></td>
            <td><c:out value="${powerPlant.numberOfEmployees}"/></td>
            <td><c:out value="${powerPlant.maxPower}"/></td>
            <td><c:out value="${powerPlant.resourceAmount}"/></td>
            <td><c:out value="${powerPlant.resourceConsumption}"/></td>
            <td><c:out value="${powerPlant.working}"/></td>

            <form method="post" action="<c:url value='/in/plant/switch'/>">
                <label>
                    <input type="text" hidden name="<%=PLANT_ID.getAttribute()%>" value="${powerPlant.id}"/>
                </label>
                <td><input type="submit" value="Switch"/></td>
            </form>
            <form method="get" action="<c:url value='/in/plant/update'/>">
                <label>
                    <input type="text" hidden name="<%=PLANT_ID.getAttribute()%>" value="${powerPlant.id}"/>
                </label>
                <td><input type="submit" value="Edit"/></td>
            </form>

            <form method="post" action="<c:url value='/in/plants'/>">
                <label>
                    <input hidden name="<%=PLANT_ID.getAttribute()%>" value="${powerPlant.id}"/>
                </label>
                <td><input type="submit" name="delete" value="Delete"/></td>
            </form>
        </tr>

    </c:forEach>
</table>
</body>
<style>
    table.style2 {
        font-family: arial, sans-serif;
        border-collapse: collapse;
        width: 100%;
    }

    td, th {
        border: 1px solid #dddddd;
        text-align: left;
        padding: 8px;
    }

    tr:nth-child(even) {
        background-color: #dddddd;
    }

    table.style1 {
        font-family: arial, sans-serif;
        border-collapse: collapse;
        width: 30%;
    }

    td, th {
        border: 1px solid #dddddd;
        text-align: left;
        padding: 8px;
    }

    tr:nth-child(even) {
        background-color: #dddddd;
    }

</style>
</html>