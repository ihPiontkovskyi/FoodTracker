<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/pages/error.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="messages" scope="request"/>
${param.put("page",1)}
<!doctype html>
<html class="no-js" lang="">
<head>
    <title>Food Tracker</title>
    <link rel="icon" type="image/png" href="../../assets/images/logo-002.png"/>
    <link rel="stylesheet" href="../../assets/css/style.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/pixeden-stroke-7-icon@1.2.3/pe-icon-7-stroke/dist/pe-icon-7-stroke.min.css">
    <link href="https://cdn.jsdelivr.net/npm/chartist@0.11.0/dist/chartist.min.css" rel="stylesheet">
</head>

<body>
<aside id="left-panel" class="left-panel">
    <nav class="navbar navbar-expand-sm navbar-default">
        <div id="main-menu" class="main-menu collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li>
                    <a href="home"> <em class="menu-icon fa fa-bar-chart"></em><fmt:message key="highlights.btn"/></a>
                </li>
                <li>
                    <a href="records.jsp"> <em class="menu-icon fa fa-calendar"></em><fmt:message key="diary.btn"/> </a>
                </li>
                <li>
                    <a href="meal"> <em class="menu-icon fa fa-cutlery"></em><fmt:message key="meals.btn"/></a>
                </li>
            </ul>
        </div>
    </nav>
</aside>
<div id="right-panel" class="right-panel">
    <header id="header" class="header">
        <div class="top-left">
            <div class="navbar-header">
                <a class="navbar-brand" href="home"><img src="../../assets/images/logo-001.png" alt="Logo"></a>
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
                        <a class="nav-link" href="settings"><em class="fa fa -cog"></em><fmt:message
                                key="settings.btn"/></a>

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
    <div class="content bg-gra-02">
        <div class="animated fadeIn">
            <div class="orders">
                <div class="row">
                    <div class="col-xl-12">
                        <div class="card">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-lg-2">
                                        <h4 class="box-title">Meal</h4>
                                    </div>
                                    <div class="col-lg-10">
                                        <a class="btn float-right"><i class="fa fa-plus" aria-hidden="true"></i></a>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body--">
                                <div class="table-stats order-table ov-h">
                                    <table class="table ">
                                        <thead>
                                        <tr>
                                            <th><fmt:message key="name.label"/></th>
                                            <th><fmt:message key="energy.label"/></th>
                                            <th><fmt:message key="protein.label"/></th>
                                            <th><fmt:message key="carbohydrate.label"/></th>
                                            <th><fmt:message key="fat.label"/></th>
                                            <th><fmt:message key="water.label"/></th>
                                            <th><fmt:message key="weight.column.label"/></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${requestScope.meals}" var="meal">
                                            <tr class=" pb-0">
                                                <td hidden class="mealId">${meal.id}</td>
                                                <td><span class="name">${meal.name}</span></td>
                                                <td><span class="count">${meal.energy}</span> <fmt:message
                                                        key="weight.label"/></td>
                                                <td><span class="count">${meal.protein}</span> <fmt:message
                                                        key="weight.label"/></td>
                                                <td><span class="count">${meal.carb}</span> <fmt:message
                                                        key="weight.label"/></td>
                                                <td><span class="count">${meal.fat}</span> <fmt:message
                                                        key="weight.label"/></td>
                                                <td><span class="count">${meal.water}</span> <fmt:message
                                                        key="volume.label"/></td>
                                                <td><span class="count">${meal.weight}</span> <fmt:message
                                                        key="weight.label"/></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script src="https://use.fontawesome.com/59810e450d.js"></script>
</body>
</html>