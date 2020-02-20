<%@include file="../../jspf/directive.jsp" %>
>
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
                    <a href="?lang=en&date=${sessionScope.date}">EN</a>
                    <a href="?lang=ru&date=${sessionScope.date}">RU</a>
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
                                            <span id="energy" class="count">${requestScope.dateSums.sumEnergy}</span>
                                            <fmt:message key="kcal.field"/>
                                        </div>
                                        <div class="stat-heading"><fmt:message key="energy.label"/></div>
                                    </div>
                                </div>
                                message
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
                                                                     class="count">${requestScope.dateSums.sumCarbohydrate}</span>
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
            <div class="add">
                <div class="row">
                    <div class="col-xl-12">
                        <div class="card">
                            <div class="card-body">
                                <div class="row">
                                    <h4 class="box-title">
                                        <fmt:message key="add.meal.label"/>
                                    </h4>
                                </div>

                                <form action="records/add" method="POST">
                                    <div class="row">
                                        <label>
                                            <input name="meal_id" id="meal_id" hidden>
                                        </label>
                                        <div class="col-5">
                                            <label class="label">
                                                <fmt:message key="start.typing"/>
                                                <input class="input--style-4"
                                                       id="meals_autocomplete">
                                            </label>
                                        </div>
                                        <div class="col-5">
                                            <label class="label">
                                                <fmt:message key="weight.column.label"/>
                                                <input class="input--style-4" id="weight"
                                                       name="weight"
                                                       type="number">
                                            </label>
                                        </div>
                                        <div class="col-2">
                                            <button type="submit" class="btn float-right"><i class="fa fa-plus"
                                                                                             aria-hidden="true"></i>
                                            </button>
                                        </div>
                                    </div>
                                </form>
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
                                            <fmt:message key="daily.food"/> ${sessionScope.date.toString()}:
                                        </h4>
                                    </div>
                                    <div class="col-lg-5">
                                        <h4 class="box-title" ${requestScope.exceedingTheGoal==true?'':'hidden'} style="color:red">
                                            <fmt:message key="exceeding.message"/> ${requestScope.exceedingValue}
                                            <fmt:message key="kcal.field"/>
                                        </h4>
                                    </div>
                                    <div class="col-lg-10">
                                        <a
                                                href="?date=${sessionScope.date.minusDays(1)}" class="btn"><i
                                                class="fa fa-arrow-left"
                                                aria-hidden="true"></i></a>
                                        <a
                                                href="?date=${sessionScope.date.plusDays(1)}" class="btn"><i
                                                class="fa fa-arrow-right"
                                                aria-hidden="true"></i></a>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body--">
                                <div class="table-stats order-table ov-h">
                                    <table class="table ">
                                        <thead>
                                        <tr>
                                            <th id="name-col"><fmt:message key="name.label"/></th>
                                            <th id="protein-col"><fmt:message key="protein.label"/>, <fmt:message
                                                    key="weight.label"/></th>
                                            <th id="carb-col"><fmt:message key="carbohydrate.label"/>, <fmt:message
                                                    key="weight.label"/></th>
                                            <th id="fat-col"><fmt:message key="fat.label"/>, <fmt:message
                                                    key="weight.label"/></th>
                                            <th id="energy-col"><fmt:message key="energy.label"/>, <fmt:message
                                                    key="kcal.field"/></th>
                                            <th id="weight-col"><fmt:message key="weight.column.label"/></th>
                                            <th id="water-col"><fmt:message key="water.label"/>, <fmt:message
                                                    key="volume.label"/></th>
                                            <th id="action-col"><fmt:message key="action.label"/></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="record" items="${requestScope.records}">
                                            <tr>
                                                <td hidden>${record.id}</td>
                                                <td>${record.meal.name}</td>
                                                <td>${record.calculateProtein()}</td>
                                                <td>${record.calculateCarbohydrate()}</td>
                                                <td>${record.calculateFat()}</td>
                                                <td>${record.calculateEnergy()}</td>
                                                <td>${record.weight}</td>
                                                <td>${record.calculateWater()}</td>
                                                <td><a href="../user/records/delete?id=${record.id}"
                                                       class="btn float-right"><i
                                                        class="fa fa-minus"
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
<script>
    jQuery(document).ready(function ($) {
        $(function () {
            $("#meals_autocomplete").autocomplete({
                source: 'records/byTerm',
                minLength: 3,
                select: function (event, ui) {
                    $("#meals_autocomplete").val(ui.item.label);
                    $("#meal_id").val(ui.item.id);
                    return false;
                }
            });
        });
    });
</script>
</body>
</html>