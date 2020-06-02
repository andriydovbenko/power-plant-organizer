<%--suppress HtmlFormInputWithoutLabel --%>
<%--suppress ELValidationInJSP --%>
<%@ page import="com.electricity.enumeration.ContextAttribute" %>
<%@ page import="static com.electricity.enumeration.ContextAttribute.NUMBER_OF_EMPLOYEES" %>
<%@ page import="static com.electricity.enumeration.ContextAttribute.COUNTRY" %>
<%@ page import="static com.electricity.enumeration.ContextAttribute.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
    <title>Power Plant</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="update-plant-form">

    <form action="${pageContext.request.contextPath}/in/plant/update" method="post">
        <h2>Plant</h2>
        <div class="form-group">
            <div class="row">
                <div class="hint-text">
                    <table class="table">
                        <tbody>
                        <tr>
                            <td>
                                <strong>
                                    <span class="text-primary"></span>
                                    Type
                                </strong>
                            </td>
                            <td class="text-primary">
                                <c:out value="${requestScope.powerPlant.type}"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <strong>
                                    <span class="text-primary"></span>
                                    Country
                                </strong>
                            </td>
                            <td class="text-primary">
                                <c:out value="${requestScope.powerPlant.country}"/>
                            </td>
                        </tr>

                        <tr>
                            <td>
                                <strong>
                                    <span class="text-primary"></span>
                                    Number of employee
                                </strong>
                            </td>
                            <td class="text-primary">
                                <c:out value="${requestScope.powerPlant.numberOfEmployees}"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <strong>
                                    <span class="text-primary"></span>
                                    Works
                                </strong>
                            </td>
                            <td class="text-primary">
                                <c:out value="${requestScope.powerPlant.working}"/>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <p class="hint-text" style="font-size:20px">Modify information</p>
                <div class="col-xs-6">
                    <input type="text" class="form-control" name="<%=COUNTRY.getAttribute()%>"
                           value="${requestScope.powerPlant.country}"
                           required="required">
                </div>
                <div class="col-xs-6">
                    <input type="number" class="form-control" name="<%=NUMBER_OF_EMPLOYEES.getAttribute()%>"
                           value="${requestScope.powerPlant.numberOfEmployees}" required="required">
                </div>
                <div class="col-xs-6">
                    <input type="radio" id="<%=START.getAttribute()%>" name="<%=CHOICE.getAttribute()%>"
                           value="<%=START.getAttribute()%>" required="required">
                    <label for="<%=START.getAttribute()%>">Start</label><br>
                    <input type="radio" id="<%=STOP.getAttribute()%>" name="<%=CHOICE.getAttribute()%>"
                           value="<%=STOP.getAttribute()%>">
                    <label for="<%=STOP.getAttribute()%>">Stop</label><br><br>
                </div>
                <label>
                    <input type="text" hidden name="<%=PLANT_ID.getAttribute()%>"
                           value="${requestScope.powerPlant.id}"/>
                </label>
            </div>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-success btn-lg btn-block">Save</button>
        </div>
    </form>
    <h4 class="text-center"><a href="http://localhost:8080/in/plants">return back</a></h4>
</div>
</body>
<style>
    body {
        color: #d46363;
        background: #415845;
        font-family: 'Roboto', sans-serif;
    }

    .form-control {
        height: 40px;
        box-shadow: none;
        color: #969fa4;
    }

    .form-control:focus {
        border-color: #5cb85c;
    }

    .form-control, .btn {
        border-radius: 3px;
    }

    .update-plant-form {
        width: 400px;
        margin: 0 auto;
        padding: 30px 0;
    }

    .update-plant-form h2 {
        color: #636363;
        margin: 0 0 15px;
        position: relative;
        text-align: center;
    }

    .update-plant-form h2:before, .update-plant-form h2:after {
        content: "";
        height: 2px;
        width: 30%;
        background: #d4d4d4;
        position: absolute;
        top: 50%;
        z-index: 2;
    }

    .update-plant-form h2:before {
        left: 0;
    }

    .update-plant-form h2:after {
        right: 0;
    }

    .update-plant-form .hint-text {
        color: #999;
        margin-bottom: 30px;
        text-align: center;
    }

    .update-plant-form form {
        color: #999;
        border-radius: 3px;
        margin-bottom: 15px;
        background: #f2f3f7;
        box-shadow: 0 2px 2px rgba(0, 0, 0, 0.3);
        padding: 30px;
    }

    .update-plant-form .form-group {
        margin-bottom: 20px;
    }

    .update-plant-form input[type="checkbox"] {
        margin-top: 3px;
    }

    .update-plant-form .btn {
        font-size: 16px;
        font-weight: bold;
        min-width: 140px;
        outline: none !important;
    }

    .update-plant-form .row div:first-child {
        padding-right: 10px;
    }

    .update-plant-form .row div:last-child {
        padding-left: 10px;
    }

    .update-plant-form a {
        color: #fff;
        text-decoration: underline;
    }

    .update-plant-form a:hover {
        text-decoration: none;
    }

    .update-plant-form form a {
        color: #5cb85c;
        text-decoration: none;
    }

    .update-plant-form form a:hover {
        text-decoration: underline;
    }
</style>
</html>