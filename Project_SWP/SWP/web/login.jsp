<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">
        <title>Login</title>
        <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <script>

            function toggleForm() {
                const userType = document.getElementById('userType').value;
                document.getElementById('userForm').style.display = userType === 'user' ? 'block' : 'none';
                document.getElementById('employeeForm').style.display = userType === 'employee' ? 'block' : 'none';
            }

            function togglePasswordVisibility(passwordFieldId, toggleButtonId) {
                const passwordField = document.getElementById(passwordFieldId);
                const icon = document.getElementById(toggleButtonId).querySelector('i');

                if (passwordField.type === 'password') {
                    passwordField.type = 'text';
                    icon.classList.remove('fa-eye');
                    icon.classList.add('fa-eye-slash');
                } else {
                    passwordField.type = 'password';
                    icon.classList.remove('fa-eye-slash');
                    icon.classList.add('fa-eye');
                }
            }
            function handleEmailLogin(event) {
                event.preventDefault();
                const formData = new FormData(event.target);
                fetch('LoginServlet', {
                    method: 'POST',
                    body: new URLSearchParams(formData)
                }).then(response => response.json())
                        .then(data => {
                            if (data.success) {
                                window.location.href = 'home.jsp';
                            } else {
                                alert('Email login failed. Please check your credentials.');
                            }
                        }).catch(error => console.error('Error:', error));
            }

        </script>
    </head>

    <body class="bg-light">
        <%
            Cookie[] cookies = request.getCookies();
            String savedUsername = "";
            String savedPassword = "";

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("username".equals(cookie.getName())) {
                        savedUsername = cookie.getValue();
                    }
                    if ("password".equals(cookie.getName())) {
                        savedPassword = cookie.getValue();
                    }
                }
            }
        %>
        <div class="container d-flex justify-content-center align-items-center vh-100">
            <div class="card shadow-lg p-4" style="max-width: 400px; width: 100%;">
                <div class="card-body">
                    <div class="text-center mb-4">
                        <a href="dashboard.html">
                            <img src="assets/img/logo-dark.png" alt="Logo" class="mb-3" style="max-width: 120px;">
                        </a>
                        <h5 class="card-title">Welcome Back</h5>
                    </div>

                    <div id="userForm" style="display: block;">
                        <!-- User Login -->
                        <form action="login" method="post" class="form-signin">
                            <p class="text-danger text-center">${error}</p>
                            <div class="form-group">
                                <label for="userType">Select User Type</label>
                                <select class="form-control" id="userType" name="userType" onchange="toggleForm()">
                                    <option value="customer" <%= "customer".equals(request.getAttribute("userType")) ? "selected" : "" %>>Customer</option>
                                    <option value="staff" <%= "staff".equals(request.getAttribute("userType")) ? "selected" : "" %>>Staff</option>
                                </select>

                            </div>

                            <div class="form-group">
                                <label id="userLabel" for="user">Username</label>
                                <input type="text" name="user" id="user" value="<%= savedUsername %>" class="form-control" placeholder="Enter your username" autofocus>                            </div>

                            <div class="form-group">
                                <label for="password">Password</label>
                                <div class="input-group">
                                    <input type="password" name="password" id="password" value="<%= savedPassword %>" class="form-control" placeholder="Enter your password">
                                    <div class="input-group-append">
                                        <button type="button" class="btn btn-outline-secondary" id="togglePasswordUser" tabindex="-1" onclick="togglePasswordVisibility('password', 'togglePasswordUser')">
                                            <i class="fa fa-eye"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group text-right">
                                <a href="forgot-password.jsp" class="small">Forgot your password?</a>
                            </div>

                            <div class="form-group checkbox" id="rememberMeChkBox">
                                <label><input type="checkbox" name="rememberMe" <% if (!savedUsername.isEmpty()) { %> checked <% } %>> Remember me</label>
                            </div>



                            <div class="form-group text-center">
                                <button type="submit" class="btn btn-primary btn-block">Login</button>
                            </div>

                            <div class="text-center mt-3" id="registerLink">
                                <p class="small">Don't have an account? <a href="register.jsp">Register Now</a></p>
                            </div>
                        </form>
                    </div>
                    <div class="mt-4 text-center">
                        <button type="button" class="btn btn-danger btn-block mb-2" id="googleLoginBtn">
                            <a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid&redirect_uri=http://localhost:8080/SWP/LoginGoogle&response_type=code&client_id=322004880766-ljaf0a60isb2782d47uegdvjbfgjoc9t.apps.googleusercontent.com&approval_prompt=force">
                                <i class="fa fa-google me-2"></i>Login with Google
                            </a>
                            
                        </button>
                    </div>

                </div>
            </div>
        </div>

        <script src="assets/js/jquery-3.2.1.min.js"></script>
        <script src="assets/js/popper.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/app.js"></script>
        <script>
                                    function toggleForm() {
                                        var userType = document.getElementById("userType").value;
                                        var userLabel = document.getElementById("userLabel");
                                        var userInput = document.getElementById("user");
                                        var googleLogin = document.querySelector(".btn-danger"); // N�t Google login
                                        var registerLink = document.getElementById("registerLink");
                                        var rememberMeChkBox = document.getElementById("rememberMeChkBox");

                                        if (userType === "customer") {
                                            userLabel.innerText = "Username";
                                            userInput.placeholder = "Enter your username";
                                            userInput.type = "text";
                                            googleLogin.style.display = "block";
                                            registerLink.style.display = "block";
                                            rememberMeChkBox.style.display = "block";
                                        } else {
                                            userLabel.innerText = "Email";
                                            userInput.placeholder = "Enter your email";
                                            userInput.type = "email";
                                            googleLogin.style.display = "none";
                                            registerLink.style.display = "none";
                                            rememberMeChkBox.style.display = "none";
                                        }
                                    }
                                    window.onload = function () {
                                        toggleForm();
                                    };

        </script>
    </body>
</html>
