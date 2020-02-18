<%@include file="../../jspf/directive.jsp" %>
<html lang="${sessionScope.locale}">
<head>
    <%@include file="../../jspf/head.jsp" %>
</head>

<body class="bg-gra-02">
<%@include file="../../jspf/aside.jsp" %>
<div id="right-panel" class="right-panel">
    <header id="header" class="header">
        <div class="top-left">
            <div class="navbar-header">
                <a class="navbar-brand" href="home"><img src="../../foodtracker.ua/assets/images/logo-001.png"
                                                         alt="Logo"></a>
            </div>
        </div>
        <div class="top-right">
            <div class="header-menu">
                <div class="user-area dropdown">
                    <a href="#" class="dropdown-toggle active" data-toggle="dropdown" aria-haspopup="true"
                       aria-expanded="false">
                        <em class="fa fa-user-circle"></em>
                    </a>
                    <div class="user-menu dropdown-menu">
                        <a class="nav-link" href="profile"><em class="fa fa -cog"></em><fmt:message
                                key="profile.btn"/></a>

                        <a class="nav-link" href="logout"><em class="fa fa-power -off"></em><fmt:message
                                key="logout.btn"/></a>
                    </div>
                </div>

                <div class="lang-block">
                    <a href="?lang=en&id=${requestScope.meal.id}">EN</a>
                    <a href="?lang=ru&id=${requestScope.meal.id}">RU</a>
                </div>
            </div>
        </div>
    </header>
    <div class="content">
        <div class="wrapper wrapper--w680">
            <div class="card card-4">
                <div class="card-body">
                    <h2 class="title">
                        <fmt:message key="edit.meal.label"/>
                    </h2>
                    <form action="edit-meal" method="POST">
                        <input hidden="hidden" name="id" value="${requestScope.meal.id}">
                        <div class="row row-space">
                            <div class="col-6">
                                <div class="input-group">
                                    <label class="label">
                                        <fmt:message key="meal.name"/>
                                        <input class="input--style-4" type="text"
                                               name="name" value="${requestScope.meal.name}">
                                    </label>
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="input-group">
                                    <label class="label">
                                        <fmt:message key="weight.column.label"/>
                                        <input class="input--style-4"
                                               type="number"
                                               name="weight"
                                               value="${requestScope.meal.weight}">
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="row row-space">
                            <div class="col-6">
                                <div class="input-group">
                                    <label class="label">
                                        <fmt:message key="protein.label"/>
                                        <fmt:message key="weight.label"/>
                                        <input class="input--style-4"
                                               type="number"
                                               name="protein"
                                               value="${requestScope.meal.protein}">
                                    </label>
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="input-group">
                                    <label class="label">
                                        <fmt:message key="water.label"/>
                                        <fmt:message key="volume.label"/>
                                        <input class="input--style-4"
                                               type="number"
                                               name="water"
                                               value="${requestScope.meal.water}">
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="row row-space">
                            <div class="col-6">
                                <div class="input-group">
                                    <label class="label">
                                        <fmt:message key="carbohydrate.label"/>
                                        <fmt:message key="weight.label"/>
                                        <input class="input--style-4"
                                               type="number"
                                               name="carbohydrate"
                                               value="${requestScope.meal.carbohydrate}">
                                    </label>
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="input-group">
                                    <label class="label">
                                        <fmt:message key="fat.label"/>
                                        <fmt:message key="weight.label"/>
                                        <input class="input--style-4"
                                               type="number"
                                               name="fat"
                                               value="${requestScope.meal.fat}">
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="p-t-15">
                            <button class="btn btn--radius-2 btn--blue" type="submit">
                                <fmt:message key="edit.btn"/>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <footer class="site-footer">
        <div class="footer-inner bg-white">
            <div class="row">
                <div class="col-lg-12 text-center">
                    Made by <a href="https://github.com/ihPiontkovskyi/">Ihor Piontkovskyi</a>
                </div>
            </div>
        </div>
    </footer>
</div>
</body>
</html>