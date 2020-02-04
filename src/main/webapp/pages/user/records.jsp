<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale/messages"/>
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
                    <a href="records"> <em class="menu-icon fa fa-calendar"></em><fmt:message key="diary.btn"/></a>
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
            <div class="row">
                <div class="col-lg-3 col-md-6">
                    <div class="card">
                        <div class="card-body">
                            <div class="stat-widget-five">
                                <div class="stat-icon dib flat-color-1">
                                    <em class="pe-7s-gleam"></em>
                                </div>
                                <div class="stat-content">
                                    <div class="text-left dib">
                                        <div class="stat-text">
                                            <span id="energy" class="count">${requestScope.dateSums.sumEnergy}</span>
                                            <fmt:message key="kcal.field"/>
                                        </div>
                                        <div class="stat-heading"><fmt:message key="energy.label"/></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-lg-3 col-md-6">
                    <div class="card">
                        <div class="card-body">
                            <div class="stat-widget-five">
                                <div class="stat-icon dib flat-color-2">
                                    <em class="pe-7s-graph1"></em>
                                </div>
                                <div class="stat-content">
                                    <div class="text-left dib">
                                        <div class="stat-text"><span id="protein"
                                                                     class="count">${requestScope.dateSums.sumProtein}</span>
                                            <fmt:message key="weight.label"/>
                                        </div>
                                        <div class="stat-heading"><fmt:message key="protein.label"/></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-lg-3 col-md-6">
                    <div class="card">
                        <div class="card-body">
                            <div class="stat-widget-five">
                                <div class="stat-icon dib flat-color-4">
                                    <em class="pe-7s-leaf"></em>
                                </div>
                                <div class="stat-content">
                                    <div class="text-left dib">
                                        <div class="stat-text"><span id="carb"
                                                                     class="count">${requestScope.dateSums.sumCarbohydrates}</span>
                                            <fmt:message
                                                    key="weight.label"/>
                                        </div>
                                        <div class="stat-heading"><fmt:message key="carbohydrate.label"/></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-lg-3 col-md-6">
                    <div class="card">
                        <div class="card-body">
                            <div class="stat-widget-five">
                                <div class="stat-icon dib flat-color-3">
                                    <em class="pe-7s-drop"></em>
                                </div>
                                <div class="stat-content">
                                    <div class="text-left dib">
                                        <div class="stat-text"><span id="water"
                                                                     class="count">${requestScope.dateSums.sumWater}</span>
                                            <fmt:message key="volume.label"/>
                                        </div>
                                        <div class="stat-heading"><fmt:message key="water.label"/></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="orders">
                <div class="row">
                    <div class="col-xl-12">
                        <div class="card">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-lg-2">
                                        <h4 class="box-title" id="title">
                                            <fmt:message key="daily.food"/> ${sessionScope.currentDate.toString()}:
                                        </h4>
                                    </div>
                                    <div class="col-lg-10">
                                        <a
                                                href="?date=${sessionScope.currentDate.minusDays(1)}" class="btn"><i
                                                class="fa fa-arrow-left"
                                                aria-hidden="true"></i></a>
                                        <a
                                                href="?date=${sessionScope.currentDate.plusDays(1)}" class="btn"><i
                                                class="fa fa-arrow-right"
                                                aria-hidden="true"></i></a>
                                        <a href="../user/records/add" class="btn float-right"><i class="fa fa-plus"
                                                                                                 aria-hidden="true"></i></a>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body--">
                                <div class="table-stats order-table ov-h">
                                    <table class="table ">
                                        <thead>
                                        <tr>
                                            <th><fmt:message key="name.label"/></th>
                                            <th><fmt:message key="protein.label"/>, <fmt:message
                                                    key="weight.label"/></th>
                                            <th><fmt:message key="carbohydrate.label"/>, <fmt:message
                                                    key="weight.label"/></th>
                                            <th><fmt:message key="fat.label"/>, <fmt:message key="weight.label"/></th>
                                            <th><fmt:message key="energy.label"/>, <fmt:message key="kcal.field"/></th>
                                            <th><fmt:message key="weight.column.label"/></th>
                                            <th><fmt:message key="water.label"/>, <fmt:message key="volume.label"/></th>
                                            <th><fmt:message key="action.label"/></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="record" items="${requestScope.records}">
                                            <tr>
                                                <td hidden>${record.id}</td>
                                                <td>${record.meal.name}</td>
                                                <td>${record.meal.protein}</td>
                                                <td>${record.meal.carbohydrate}</td>
                                                <td>${record.meal.fat}</td>
                                                <td>${record.meal.calculateEnergy()}</td>
                                                <td>${record.meal.weight}</td>
                                                <td>${record.meal.water}</td>
                                                <td><a href="../user/records/delete?id=${record.id}"
                                                       class="btn float-right"><i
                                                        class="fa fa-minus"
                                                        aria-hidden="true"></i></a>
                                                    <a href="../user/records/edit?id=${record.id}"
                                                       class="btn float-right"><i
                                                            class="fa fa-pencil"
                                                            aria-hidden="true"></i></a>
                                                </td>
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
<script type="text/javascript">
</script>
</body>
</html>