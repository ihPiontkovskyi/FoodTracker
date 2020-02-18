<%@include file="../jspf/directive.jsp" %>

<html lang="${sessionScope.locale}">
<head>
  <%@include file="../jspf/head.jsp"%>
</head>
<body>
<div class="limiter">
    <div class="lang-block-login">
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
                    <a href="register-page" class="txt3">
                        <fmt:message key="sign.up.btn"/>
                    </a>
                </div>
            </form>
            <div class="login-more"
                 style="background-image: url(${"ru"==sessionScope.locale?'../../foodtracker.ua/assets/images/bg-001-ru.jpg':
                 '../../foodtracker.ua/assets/images/bg-001.jpg'})"></div>
        </div>
    </div>
</div>
</body>
</html>