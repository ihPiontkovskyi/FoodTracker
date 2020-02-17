<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/pages/error.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale/messages"/>
<!doctype html>
<html class="no-js" lang="${sessionScope.locale}">
<head>
    <title>Food Tracker</title>
    <link rel="icon" type="image/png" href="../../../assets/images/logo-002.png"/>
    <link rel="stylesheet" href="../../../assets/css/style.css">
    <link href="../../../assets/css/form.css" rel="stylesheet" media="all">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/pixeden-stroke-7-icon@1.2.3/pe-icon-7-stroke/dist/pe-icon-7-stroke.min.css">
    <link href="https://cdn.jsdelivr.net/npm/chartist@0.11.0/dist/chartist.min.css" rel="stylesheet">
</head>

<body class="bg-gra-02">
<aside id="left-panel" class="left-panel">
    <nav class="navbar navbar-expand-sm navbar-default">
        <div id="main-menu" class="main-menu collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li>
                    <a href="home"> <em class="menu-icon fa fa-bar-chart"></em><fmt:message key="highlights.btn"/></a>
                </li>
                <li>
                    <a href="records"> <em class="menu-icon fa fa-calendar"></em><fmt:message key="diary.btn"/> </a>
                </li>
                <li>
                    <a href="meals"> <em class="menu-icon fa fa-cutlery"></em><fmt:message key="meals.btn"/></a>
                </li>
            </ul>
        </div>
    </nav>
</aside>
<div id="right-panel" class="right-panel">
    <header id="header" class="header">
        <div class="top-left">
            <div class="navbar-header">
                <a class="navbar-brand" href="home"><img src="../../../assets/images/logo-001.png" alt="Logo"></a>
            </div>
        </div>
        <div class="top-right">
            <div class="header-menu">
                <div class="user-area dropdown float-right">
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
                    <a href="?lang=en">EN</a>
                    <a href="?lang=ru">RU</a>
                </div>
            </div>
        </div>
    </header>
    <div class="content">
        <div class="wrapper wrapper--w680">
            <div class="card card-4">
                <div class="card-body">
                    <h2 class="title">
                        <fmt:message key="add.meal.label"/>
                    </h2>
                    <form action="add-meal" method="POST">
                        <div class="row row-space">
                            <div class="col-6">
                                <div class="input-group">
                                    <label class="label">
                                        <fmt:message key="meal.name"/>
                                        <input class="input--style-4" type="text"
                                               name="name">
                                    </label>
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="input-group">
                                    <label class="label">
                                        <fmt:message key="weight.column.label"/>
                                        <input class="input--style-4"
                                               type="number"
                                               name="weight">
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
                                               name="protein"/>
                                    </label>
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="input-group">
                                    <label class="label">
                                        <fmt:message key="water.label"/>
                                        <fmt:message key="volume.label"/>
                                        <input class="input--style-4" type="number"
                                               name="water"/>
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
                                               name="carbohydrate"/>
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
                                               name="fat">
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="p-t-15">
                            <button class="btn btn--radius-2 btn--blue" type="submit">
                                <fmt:message key="add.meal.label"/>
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

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script src="https://use.fontawesome.com/59810e450d.js"></script>
</body>
</html>