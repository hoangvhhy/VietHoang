<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">
    <title>Preclinic - Medical & Hospital - Bootstrap 4 Admin Template</title>
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="assets/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="assets/css/style.css">
</head>

<body>
    <div class="main-wrapper account-wrapper">
        <div class="account-page">
            <div class="account-center">
                <div class="account-box">
                    <!-- Ensure the form action points to the correct servlet handling the password reset -->
                    <form class="form-signin" action="forgotpassword" method="post">
                        <div class="account-logo">
                            <a href="index_1.jsp"><img src="assets/img/logo-dark.png" alt=""></a>
                        </div>
                        <!-- Add a message area for showing error or success -->
                        <div class="form-group">
                            <label for="email">Enter Your Email</label>
                            <div>${requestScope.err}</div>
                            <input type="text" name="email" class="form-control" >
                        </div>
                        
                        <!-- Add error or success messages here -->
                        <div class="form-group text-center">
                            <!-- Placeholder for error/success message -->
                            <span class="text-danger" id="error-message"></span>
                        </div>

                        <div class="form-group text-center">
                            <button class="btn btn-primary account-btn" type="submit">Reset Password</button>
                        </div>
                        <div class="text-center register-link">
                            <a href="login.jsp">Back to Login</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</body>

</html>