<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Logout Form</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body>
<div class="logout-form">
    <form action="${pageContext.request.contextPath}/in/home/logout" method="post">

        <h4 class="text-center">Click to end the session</h4>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block">Log out</button>
        </div>
    </form>
    <h4 class="text-center"><a href="${pageContext.request.contextPath}/in/home">return back</a></h4>
</div>
</body>
<style>
    .logout-form {
        width: 340px;
        margin: 50px auto;
    }
    .logout-form form {
        margin-bottom: 15px;
        background: #f7f7f7;
        box-shadow: 0 2px 2px rgba(0, 0, 0, 0.3);
        padding: 30px;
    }
    .logout-form h2 {
        margin: 0 0 15px;
    }
    .logout-form, .btn {
        min-height: 38px;
        border-radius: 2px;
    }
    .btn {
        font-size: 15px;
        font-weight: bold;
    }
</style>
</html>