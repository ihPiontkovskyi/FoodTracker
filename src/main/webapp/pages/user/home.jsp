<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/pages/error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale/messages"/>
<!doctype html>
<html class="no-js" lang="${sessionScope.locale}">
<head>
    <title>Food Tracker</title>
    <link rel="icon" type="image/png" href="../../assets/images/logo-002.png"/>
    <link rel="stylesheet" href="../../assets/css/style.css">
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
                    <a href="records"> <em class="menu-icon fa fa-calendar"></em><fmt:message key="diary.btn"/></a>
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
                <a class="navbar-brand" href="home"><img src="../../assets/images/logo-001.png" alt="Logo"></a>
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
    <div class="content">
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
                                            <span class="count">${requestScope.homeModel.dsto.sumEnergy}</span>
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
                                        <div class="stat-text"><span
                                                class="count">${requestScope.homeModel.dsto.sumProtein}</span>
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
                                        <div class="stat-text"><span
                                                class="count">${requestScope.homeModel.dsto.sumCarbohydrates}</span>
                                            <fmt:message key="weight.label"/>
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
                                        <div class="stat-text"><span
                                                class="count">${requestScope.homeModel.dsto.sumWater}</span>
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
            <div class="row">
                <div class="col-xl-8">
                    <div class="card">
                        <div class="card-body">
                            <h4 class="box-title"><fmt:message key="last.week.label"/></h4>
                        </div>
                        <div class="row">
                            <div class="col-lg-8">
                                <div class="card-body">
                                    <div id="traffic-chart" class="traffic-chart"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xl-4">
                    <div class="card">
                        <div class="card-body">
                            <h4 class="box-title"><fmt:message key="daily.goal.label"/></h4>
                            <div class="card-body">
                                <div class="progress-box progress-1">
                                    <h4 class="por-title"><fmt:message key="energy.label"/></h4>
                                    <div class="progress mb-2" style="height: 5px;">
                                        <div class="progress-bar bg-flat-color-1" roleEntity="progressbar"
                                             style="width:${requestScope.homeModel.dailyEnergyGoal}%"
                                             aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                </div>
                                <div class="progress-box progress-2">
                                    <h4 class="por-title"><fmt:message key="water.label"/></h4>
                                    <div class="progress mb-2" style="height: 5px;">
                                        <div class="progress-bar bg-flat-color-2" roleEntity="progressbar"
                                             style="width: ${requestScope.homeModel.dailyWaterGoal}%"
                                             aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                </div>
                                <div class="progress-box progress-2">
                                    <h4 class="por-title"><fmt:message key="carbohydrate.label"/></h4>
                                    <div class="progress mb-2" style="height: 5px;">
                                        <div class="progress-bar bg-flat-color-3" roleEntity="progressbar"
                                             style="width: ${requestScope.homeModel.dailyCarbohydratesGoal}%"
                                             aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                </div>
                                <div class="progress-box progress-2">
                                    <h4 class="por-title"><fmt:message key="protein.label"/></h4>
                                    <div class="progress mb-2" style="height: 5px;">
                                        <div class="progress-bar bg-flat-color-4" roleEntity="progressbar"
                                             style="width: ${requestScope.homeModel.dailyProteinGoal}%"
                                             aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                </div>
                                <div class="progress-box progress-2">
                                    <h4 class="por-title"><fmt:message key="fat.label"/></h4>
                                    <div class="progress mb-2" style="height: 5px;">
                                        <div class="progress-bar bg-flat-color-5" roleEntity="progressbar"
                                             style="width: ${requestScope.homeModel.dailyFatGoal}%"
                                             aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
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
<script src="https://cdn.jsdelivr.net/npm/chartist@0.11.0/dist/chartist.min.js"></script>
<script>
    jQuery(document).ready(function ($) {
        if ($('#traffic-chart').length) {
            let labels = [];
            <c:forEach items="${requestScope.homeModel.labels}" var="label">
            labels.push("${label}");
            </c:forEach>
            let protein = ${requestScope.homeModel.proteinWeeklyStat};
            let carb = ${requestScope.homeModel.carbWeeklyStat};
            let energy = ${requestScope.homeModel.energyWeeklyStat};
            let water = ${requestScope.homeModel.waterWeeklyStat};
            let fat = ${requestScope.homeModel.fatWeeklyStat};
            let chart = new Chartist.Line('#traffic-chart', {
                labels: labels,
                series: [
                    energy,
                    water,
                    carb,
                    fat,
                    protein
                ]
            }, {
                low: 0,
                showArea: true,
                showLine: true,
                showPoint: false,
                fullWidth: true,
                axisX: {
                    showGrid: true
                }
            });
            chart.on('draw', function (data) {
                if (data.type === 'line' || data.type === 'area') {
                    data.element.animate({
                        d: {
                            begin: 2000 * data.index,
                            dur: 2000,
                            from: data.path.clone().scale(1, 0).translate(0, data.chartRect.height()).stringify(),
                            to: data.path.clone().stringify(),
                            easing: Chartist.Svg.Easing.easeOutQuint
                        }
                    });
                }
            });
        }
    });
</script>
</body>
</html>