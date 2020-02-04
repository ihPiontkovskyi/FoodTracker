<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale/messages"/>
<!DOCTYPE html>

<html lang="${sessionScope.locale}">
<head>
    <title>Food Tracker</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <link rel="icon" type="image/png" href="../assets/images/logo-002.png"/>
    <link rel="stylesheet" type="text/css" href="../assets/css/login.css">
</head>
<body>
<div class="limiter">
    <div class="lang-block">
        <a href="?lang=en">EN</a>
        <a href="?lang=ru">RU</a>
    </div>
    <div class="container-login  bg-gra-02">
        <div class="wrap-login">
            <form class="login-form" action="login" method="post">
					<span class="login-form-title">
						<fmt:message key="account.login.field"/>
					</span>

                <div class="wrap-input rs1-wrap-input">
                    <label>
                        <input class="input" type="text" name="username"
                               placeholder="<fmt:message key="username.field"/>" required>
                    </label>
                    <span class="focus-input"></span>
                </div>
                <div class="wrap-input rs2-wrap-input">
                    <label>
                        <input class="input" type="password" name="pass"
                               placeholder="<fmt:message key="password.field" />" required>
                    </label>
                    <span class="focus-input"></span>
                </div>

                <div class="container-login-form-btn">
                    <button class="login-form-btn" type="submit">
                        <fmt:message key="login.btn"/>
                    </button>
                </div>
                <div class="w-full text-center forgot-block">
                    <a class="txt2"></a>
                </div>
                <div class="w-full text-center">
                    <a href="register.jsp" class="txt3">
                        <fmt:message key="sign.up.btn"/>
                    </a>
                </div>
            </form>
            <div class="login-more"
                 style="background-image: url(${"ru"==sessionScope.locale?'../assets/images/bg-001-ru.jpg':'../assets/images/bg-001.jpg'})"></div>
        </div>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script src="https://use.fontawesome.com/59810e450d.js"></script>
</body>
</html>