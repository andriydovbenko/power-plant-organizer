<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en"
      xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Error Page</title>
    <link href="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css" rel="stylesheet" media="screen"/>
    <script src="/webjars/jquery/3.2.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <div class="jumbotron alert-danger">
        <h1>Oops... something went wrong.</h1>
        <h1>Problem occurred.</h1>
        <a href="${pageContext.request.contextPath}/in/home">Back home</a>
    </div>
</div>
</body>
</html>