<%--suppress HtmlFormInputWithoutLabel --%>
<%@ page import="static com.electricity.enumeration.attribute.ContextAttribute.LOGIN" %>
<%@ page import="static com.electricity.enumeration.attribute.ContextAttribute.PASSWORD" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login Form</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="login-form">
    <form action="${pageContext.request.contextPath}/login" method="post">
        <h2 class="text-center">Log in</h2>
        <div class="form-group">
            <input type="text" name="<%=LOGIN.getAttribute()%>" class="form-control" placeholder="Login"
                   required="required">
        </div>
        <div class="form-group">
            <input type="password" name="<%=PASSWORD.getAttribute()%>" class="form-control" placeholder="Password"
                   required="required">
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block">Log in</button>
        </div>
    </form>
    <p class="text-center"><a href="${pageContext.request.contextPath}/register">Create an Account</a></p>
</div>
</body>
<style>
    .login-form {
        width: 340px;
        margin: 50px auto;
    }

    .login-form form {
        margin-bottom: 15px;
        background: #f7f7f7;
        box-shadow: 0 2px 2px rgba(0, 0, 0, 0.3);
        padding: 30px;
    }

    .login-form h2 {
        margin: 0 0 15px;
    }

    .form-control, .btn {
        min-height: 38px;
        border-radius: 2px;
    }

    .btn {
        font-size: 15px;
        font-weight: bold;
    }
</style>
</html>