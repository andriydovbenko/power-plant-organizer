<%--suppress ELValidationInJSP --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.electricity.enumeration.resource.PurchasableResourceType" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.electricity.enumeration.resource.ResourcePrice" %>
<%@ page import="com.electricity.model.storage.impl.CoalStorage" %>
<%@ page import="com.electricity.model.storage.impl.UraniumStorage" %>
<%@ page import="static com.electricity.enumeration.attribute.ContextAttribute.PLANT_ID" %>
<%@ page import="static com.electricity.enumeration.attribute.ContextAttribute.RESOURCE_TYPE" %>
<%@ page import="static com.electricity.enumeration.attribute.ContextAttribute.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Resource shop</title>
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
                    Power Plants</a>
            </li>
            <li class="nav-item">
                <a href="#nuclear" class="nav-link " data-toggle="tab" role="tab" aria-controls="nuclear">Nuclear Power
                    Plants</a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/in/home">Return back</a>
            </li>

        </ul>
    </div>
    <div class="right_bar ">
        <div class="tab-content ">
            <div class="tab-pane fade show active" id="coal" role="tabpanel">
                <table class="table table-bordered">
                    <div class="row">
                        <div class="col-md-4">
                            <p><b>Current funds: </b><fmt:formatNumber type="number" groupingUsed="false"
                                                                       value="${requestScope.user.currentFundsAmount}"/>
                            </p>
                            <p><b>Uranium price per item: </b><c:out value="<%=ResourcePrice.COAL.getPrice()%>"/>
                            </p>
                        </div>
                    </div>
                    <thead>
                    <tr>
                        <th>Country</th>
                        <th>Resource Amount</th>
                        <th>Storage Capacity</th>
                        <th>Amount</th>
                        <th>Payment</th>
                    </tr>
                    </thead>
                    <c:forEach var="coalFiredPlant" items="${requestScope.coalFiredPlants}">
                        <tbody>
                        <form action="${pageContext.request.contextPath}/in/home/shop/resource" method="post">
                            <label>
                                <input type="text" hidden name="<%=PLANT_ID.getAttribute()%>" value="${coalFiredPlant.id}"/>
                            </label>
                            <label>
                                <input type="text" hidden name="<%=RESOURCE_TYPE.getAttribute()%>" value="<%=PurchasableResourceType.COAL%>"/>
                            </label>
                            <tr>
                                <td><c:out value="${coalFiredPlant.country}"/></td>
                                <td><c:out value="${coalFiredPlant.resourceAmount}"/></td>
                                <td><c:out value="<%=CoalStorage.CAPACITY%>"/></td>
                                <td><label>
                                    <input type="number" name="<%=AMOUNT.getAttribute()%>" class="form-control" required="required">
                                </label></td>
                                <td>
                                    <button type="submit" class="btn btn-primary btn-block">Pay</button>
                                </td>
                            </tr>
                        </form>
                        </tbody>
                    </c:forEach>
                </table>
            </div>
            <div class="tab-pane fade" id="nuclear" role="tabpanel">
                <table class="table table-bordered">
                    <div class="row">
                        <div class="col-md-4">
                            <p><b>Current funds: </b><fmt:formatNumber type="number" groupingUsed="false"
                                                                       value="${requestScope.user.currentFundsAmount}"/>
                            </p>
                            <p><b>Uranium price per item: </b><c:out value="<%=ResourcePrice.URANIUM.getPrice()%>"/>
                            </p>
                        </div>
                    </div>
                    <thead>
                    <tr>
                        <th>Country</th>
                        <th>Resource Amount</th>
                        <th>Storage Capacity</th>
                        <th>Amount</th>
                        <th>Payment</th>
                    </tr>
                    </thead>
                    <c:forEach var="nuclearPlant" items="${requestScope.nuclearPlants}">
                        <tbody>
                        <form action="${pageContext.request.contextPath}/in/home/shop/resource" method="post">
                            <label>
                                <input type="text" hidden name="<%=PLANT_ID.getAttribute()%>" value="${nuclearPlant.id}"/>
                            </label>
                            <label>
                                <input type="text" hidden name="<%=RESOURCE_TYPE.getAttribute()%>" value="<%=PurchasableResourceType.URANIUM%>"/>
                            </label>
                            <tr>
                                <td><c:out value="${nuclearPlant.country}"/></td>
                                <td><c:out value="${nuclearPlant.resourceAmount}"/></td>
                                <td><c:out value="<%=UraniumStorage.CAPACITY%>"/></td>
                                <td><label>
                                    <input type="number" name="<%=AMOUNT.getAttribute()%>" class="form-control" required="required">
                                </label></td>
                                <td>
                                    <button type="submit" class="btn btn-primary btn-block">Pay</button>
                                </td>
                            </tr>
                        </form>
                        </tbody>
                    </c:forEach>
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