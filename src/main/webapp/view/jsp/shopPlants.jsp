<%--suppress ELValidationInJSP --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.electricity.enumeration.plant.PowerPlantCost" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.electricity.enumeration.plant.PowerPlantType" %>
<%@ page import="static com.electricity.enumeration.attribute.ContextAttribute.COUNTRY" %>
<%@ page import="static com.electricity.enumeration.attribute.ContextAttribute.NUMBER_OF_EMPLOYEES" %>
<%@ page import="static com.electricity.enumeration.attribute.ContextAttribute.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Power plant shop</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>

<div class="content_box">
    <div class="left_bar">
        <ul class=" nav-tabs--vertical nav" role="navigation">
            <li class="nav-item">
                <a href="#coal" class="nav-link active" data-toggle="tab" role="tab" aria-controls="coal">Coal Fired
                    Power Plant</a>
            </li>
            <li class="nav-item">
                <a href="#hydro" class="nav-link" data-toggle="tab" role="tab" aria-controls="hydro">Hydro Power
                    Plant</a>
            </li>
            <li class="nav-item">
                <a href="#nuclear" class="nav-link " data-toggle="tab" role="tab" aria-controls="nuclear">Nuclear Power
                    Plant</a>
            </li>
            <li class="nav-item">
                <a href="#solar" class="nav-link" data-toggle="tab" role="tab" aria-controls="solar">Solar Power
                    Plant</a>
            </li>
            <li class="nav-item">
                <a href="#wind" class="nav-link" data-toggle="tab" role="tab" aria-controls="wind">Wind Power
                    Plant</a>
            </li>
            <li class="nav-item">
                <a href="http://localhost:8080/in/home">Return back</a>
            </li>

        </ul>
    </div>
    <div class="right_bar ">
        <div class="tab-content ">
            <div class="tab-pane fade show active" id="coal" role="tabpanel">

                <table class="table table-bordered">
                    <div class="row">
                        <div class="col-md-4">
                            <p><b>Current funds: </b> <label> <fmt:formatNumber type="number" groupingUsed="false"
                                                                                value="${requestScope.user.currentFundsAmount}"/> </label>
                            </p>
                            <p><b>Power Plant Cost: </b> <label><%=PowerPlantCost.COAL.getCost()%>
                            </label></p>
                        </div>
                    </div>

                    <thead>
                    <tr>
                        <th>Country</th>
                        <th>Number of employees</th>
                        <th>Start working</th>
                    </tr>
                    </thead>
                    <tbody>
                    <form action="${pageContext.request.contextPath}/in/home/shop/plant" method="post">
                        <label>
                            <input type="text" hidden name="type" value="<%=PowerPlantType.COAL%>"/>
                        </label>
                        <tr>
                            <td><label>
                                <input type="text" name="<%=COUNTRY.getAttribute()%>" class="form-control" required="required">
                            </label></td>
                            <td><label>
                                <input type="number" name="<%=NUMBER_OF_EMPLOYEES.getAttribute()%>" class="form-control" required="required">
                            </label>
                            </td>
                            <td><label>
                                <input type="checkbox" name="<%=START.getAttribute()%>" checked>
                            </label></td>
                        </tr>
                        <td>
                            <button type="submit" class="btn btn-primary btn-block">Payment</button>
                        </td>
                    </form>
                    </tbody>
                </table>
            </div>

            <div class="tab-pane fade" id="hydro" role="tabpanel">
                <table class="table table-bordered">

                    <div class="row">
                        <div class="col-md-4">
                            <p><b>Current funds: </b> <label> <fmt:formatNumber type="number" groupingUsed="false"
                                                                                value="${requestScope.user.currentFundsAmount}"/> </label>
                            </p>
                            <p><b>Power Plant Cost: </b> <label><%=PowerPlantCost.HYDRO.getCost()%>
                            </label></p>
                        </div>
                    </div>

                    <thead>
                    <tr>
                        <th>Country</th>
                        <th>Number of employees</th>
                        <th>Start working</th>
                    </tr>
                    </thead>
                    <tbody>
                    <form action="${pageContext.request.contextPath}/in/home/shop/plant" method="post">
                        <label>
                            <input type="text" hidden name="type" value="<%=PowerPlantType.HYDRO%>">
                        </label>
                        <tr>
                            <td><label>
                                <input type="text" name="<%=COUNTRY.getAttribute()%>" class="form-control" required="required">
                            </label></td>
                            <td><label>
                                <input type="number" name="<%=NUMBER_OF_EMPLOYEES.getAttribute()%>" class="form-control" required="required">
                            </label>
                            </td>
                            <td><label>
                                <input type="checkbox" name="<%=START.getAttribute()%>" checked>
                            </label></td>
                        </tr>
                        <td>
                            <button type="submit" class="btn btn-primary btn-block">Payment</button>
                        </td>
                    </form>
                    </tbody>
                </table>
            </div>

            <div class="tab-pane fade" id="nuclear" role="tabpanel">
                <table class="table table-bordered">
                    <div class="row">
                        <div class="col-md-4">
                            <p><b>Current funds: </b> <label> <fmt:formatNumber type="number" groupingUsed="false"
                                                                                value="${requestScope.user.currentFundsAmount}"/> </label>
                            </p>
                            <p><b>Power Plant Cost: </b> <label><%=PowerPlantCost.NUCLEAR.getCost()%>
                            </label></p>
                        </div>
                    </div>
                    <thead>
                    <tr>
                        <th>Country</th>
                        <th>Number of employees</th>
                        <th>Start working</th>
                    </tr>
                    </thead>
                    <tbody>
                    <form action="${pageContext.request.contextPath}/in/home/shop/plant" method="post">
                        <label>
                            <input type="text" hidden name="type" value="<%=PowerPlantType.NUCLEAR%>"/>
                        </label>
                        <tr>
                            <td><label>
                                <input type="text" name="<%=COUNTRY.getAttribute()%>" class="form-control" required="required">
                            </label></td>
                            <td><label>
                                <input type="number" name="<%=NUMBER_OF_EMPLOYEES.getAttribute()%>" class="form-control" required="required">
                            </label>
                            </td>
                            <td><label>
                                <input type="checkbox" name="<%=START.getAttribute()%>" checked>
                            </label></td>
                        </tr>
                        <td>
                            <button type="submit" class="btn btn-primary btn-block">Payment</button>
                        </td>
                    </form>
                    </tbody>
                </table>
            </div>

            <div class="tab-pane fade" id="solar" role="tabpanel">
                <table class="table table-bordered">
                    <div class="row">
                        <div class="col-md-4">
                            <p><b>Current funds: </b> <label> <fmt:formatNumber type="number" groupingUsed="false"
                                                                                value="${requestScope.user.currentFundsAmount}"/> </label>
                            </p>
                            <p><b>Power Plant Cost: </b> <label><%=PowerPlantCost.SOLAR.getCost()%>
                            </label></p>
                        </div>
                    </div>
                    <thead>
                    <tr>
                        <th>Country</th>
                        <th>Number of employees</th>
                        <th>Start working</th>
                    </tr>
                    </thead>
                    <tbody>
                    <form action="${pageContext.request.contextPath}/in/home/shop/plant" method="post">
                        <label>
                            <input type="text" hidden name="type" value="<%=PowerPlantType.SOLAR%>"/>
                        </label>
                        <tr>
                            <td><label>
                                <input type="text" name="<%=COUNTRY.getAttribute()%>" class="form-control" required="required">
                            </label></td>
                            <td><label>
                                <input type="number" name="<%=NUMBER_OF_EMPLOYEES.getAttribute()%>" class="form-control" required="required">
                            </label>
                            </td>
                            <td><label>
                                <input type="checkbox" name="<%=START.getAttribute()%>" checked>
                            </label></td>
                        </tr>
                        <td>
                            <button type="submit" class="btn btn-primary btn-block">Payment</button>
                        </td>
                    </form>
                    </tbody>
                </table>
            </div>

            <div class="tab-pane fade" id="wind" role="tabpanel">
                <table class="table table-bordered">
                    <div class="row">
                        <div class="col-md-4">
                            <p><b>Current funds: </b> <label> <fmt:formatNumber type="number" groupingUsed="false"
                                                                                value="${requestScope.user.currentFundsAmount}"/> </label>
                            </p>
                            <p><b>Power Plant Cost: </b> <label><%=PowerPlantCost.WIND.getCost()%>
                            </label></p>
                        </div>
                    </div>
                    <thead>
                    <tr>
                        <th>Country</th>
                        <th>Number of employees</th>
                        <th>Start working</th>
                    </tr>
                    </thead>
                    <tbody>
                    <form action="${pageContext.request.contextPath}/in/home/shop/plant" method="post">
                        <label>
                            <input type="text" hidden name="type" value="<%=PowerPlantType.WIND%>"/>
                        </label>
                        <tr>
                            <td><label>
                                <input type="text" name="<%=COUNTRY.getAttribute()%>" class="form-control" required="required">
                            </label></td>
                            <td><label>
                                <input type="number" name="<%=NUMBER_OF_EMPLOYEES.getAttribute()%>" class="form-control" required="required">
                            </label>
                            </td>
                            <td><label>
                                <input type="checkbox" name="<%=START.getAttribute()%>" checked>
                            </label></td>
                        </tr>
                        <td>
                            <button type="submit" class="btn btn-primary btn-block">Payment</button>
                        </td>
                    </form>
                    </tbody>
                </table>
            </div>

        </div>
    </div>
</div>
</body>
<style>

    .content_box {
        float: left;
        width: 100%;
    }

    .left_bar {
        float: left;
        width: 15%;
        background: #eaf4ff;
        height: 100vh;
    }

    .right_bar {
        float: left;
        width: 85%;
        padding: 15px;
        /*border-left:1px solid #935252;*/
        height: 100%;
    }

    .nav-tabs--vertical li {
        float: left;
        width: 100%;
        padding: 0;
        position: relative;
    }


    .nav-tabs--vertical li a {
        float: left;
        width: 100%;
        padding: 15px;
        border-bottom: 1px solid #adcff7;
        color: #1276F0;
    }

    .nav-tabs--vertical li a.active::after {
        content: "";
        border-color: #1276F0;
        border-style: solid;
        position: absolute;
        right: -8px;
        /* border-top: transparent; */
        border-right: transparent;
        border-left: 15px solid transparent;
        border-right: 15px solid transparent;
        /*border-bottom: 16px solid #1276F0;*/
        border-bottom: 16px solid #fff;
        border-top: 0;
        transform: rotate(270deg);
        z-index: 999;
    }
</style>
</html>