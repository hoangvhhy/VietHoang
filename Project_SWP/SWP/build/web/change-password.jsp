<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">


    <!-- forgot-password24:03-->
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">
        <title>Preclinic - Medical & Hospital - Bootstrap 4 Admin Template</title>
        <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <!--[if lt IE 9]>
                    <script src="assets/js/html5shiv.min.js"></script>
                    <script src="assets/js/respond.min.js"></script>
            <![endif]-->
    </head>

    <body>
    <div class="main-wrapper account-wrapper">
        <div class="account-page">
            <div class="account-center">
                <div class="account-box">
                    <form class="form-signin" action="changePassword" method="POST">
                        <p class="text-danger text-center">${error}</p> <!-- Error message display -->

                        <div class="account-logo">
                            <a href="dashboard.html">
                                <img src="assets/img/logo-dark.png" alt="Logo">
                            </a>
                        </div>

                        <div class="form-group">
                            <label for="old_password">Old Password</label>
                            <input id="old_password" type="password" name="old_password" class="form-control" required autofocus>
                        </div>

                        <div class="form-group">
                            <label for="new_password">New Password</label>
                            <input id="new_password" type="password" name="new_password" class="form-control" required>
                        </div>

                        <div class="form-group">
                            <label for="confirm_new_password">Confirm New Password</label>
                            <input id="confirm_new_password" type="password" name="confirm_new_password" class="form-control" required>
                        </div>

                        <div class="form-group text-center">
                            <button class="btn btn-primary account-btn" type="submit">Change Password</button>
                        </div>

                        <div class="text-center register-link">
                            <a href="profile.jsp">Back</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
<!--                        heeee-->


        <script src="assets/js/jquery-3.2.1.min.js"></script>
        <script src="assets/js/popper.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/app.js"></script>
    </body>


    <!-- forgot-password24:03-->
</html>
