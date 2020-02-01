<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Food Tracker</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <link rel="icon" type="image/png" href="../assets/images/logo-002.png"/>
    <link rel="stylesheet" type="text/css" href="../assets/css/login.css">
</head>
<body>
<div class="limiter">
    <div class="container-login  bg-gra-02">
        <div class="wrap-login">
            <form class="login-form" action="login" method="post">
					<span class="login-form-title">
						Account Login
					</span>

                <div class="wrap-input rs1-wrap-input">
                    <label>
                        <input class="input" type="text" name="username" placeholder="User name" required>
                    </label>
                    <span class="focus-input"></span>
                </div>
                <div class="wrap-input rs2-wrap-input">
                    <label>
                        <input class="input" type="password" name="pass" placeholder="Password" required>
                    </label>
                    <span class="focus-input"></span>
                </div>

                <div class="container-login-form-btn">
                    <button class="login-form-btn" type="submit">
                        Sign in
                    </button>
                </div>

                <div class="w-full text-center forgot-block">
						<span class="txt1">
							Forgot
						</span>

                    <a href="#" class="txt2">
                        User name / password?
                    </a>
                </div>

                <div class="w-full text-center">
                    <a href="register.jsp" class="txt3">
                        Sign Up
                    </a>
                </div>
            </form>

            <div class="login-more" style="background-image: url('/assets/images/bg-001.jpg');"></div>
        </div>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script src="https://use.fontawesome.com/59810e450d.js"></script>
</body>
</html>