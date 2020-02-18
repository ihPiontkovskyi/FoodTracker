<%@include file="../../jspf/directive.jsp" %>
<html lang="${sessionScope.locale}">
<head>
    <%@include file="../../jspf/head.jsp" %>
</head>

<body class="bg-gra-02">
<%@include file="../../jspf/aside.jsp" %>
<div id="right-panel" class="right-panel">
    <%@include file="../../jspf/header.jsp" %>
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
                                            <span class="count">${requestScope.homeModel.dailySums.sumEnergy}</span>
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
                                                class="count">${requestScope.homeModel.dailySums.sumProtein}</span>
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
                                                class="count">${requestScope.homeModel.dailySums.sumCarbohydrate}</span>
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
                                                class="count">${requestScope.homeModel.dailySums.sumWater}</span>
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
                                        <div class="progress-bar bg-flat-color-1"
                                             style="width:${requestScope.homeModel.dailyGoal.dailyEnergyGoal}%"
                                             aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                </div>
                                <div class="progress-box progress-2">
                                    <h4 class="por-title"><fmt:message key="water.label"/></h4>
                                    <div class="progress mb-2" style="height: 5px;">
                                        <div class="progress-bar bg-flat-color-2"
                                             style="width: ${requestScope.homeModel.dailyGoal.dailyWaterGoal}%"
                                             aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                </div>
                                <div class="progress-box progress-2">
                                    <h4 class="por-title"><fmt:message key="carbohydrate.label"/></h4>
                                    <div class="progress mb-2" style="height: 5px;">
                                        <div class="progress-bar bg-flat-color-3"
                                             style="width: ${requestScope.homeModel.dailyGoal.dailyCarbohydratesGoal}%"
                                             aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                </div>
                                <div class="progress-box progress-2">
                                    <h4 class="por-title"><fmt:message key="protein.label"/></h4>
                                    <div class="progress mb-2" style="height: 5px;">
                                        <div class="progress-bar bg-flat-color-4"
                                             style="width: ${requestScope.homeModel.dailyGoal.dailyProteinGoal}%"
                                             aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                </div>
                                <div class="progress-box progress-2">
                                    <h4 class="por-title"><fmt:message key="fat.label"/></h4>
                                    <div class="progress mb-2" style="height: 5px;">
                                        <div class="progress-bar bg-flat-color-5"
                                             style="width: ${requestScope.homeModel.dailyGoal.dailyFatGoal}%"
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
<script>
    jQuery(document).ready(function ($) {
        if ($('#traffic-chart').length) {
            let labels = [];
            <c:forEach items="${requestScope.homeModel.labels}" var="label">
            labels.push("${label}");
            </c:forEach>
            let protein = ${requestScope.homeModel.weeklyProteinStat};
            let carb = ${requestScope.homeModel.weeklyCarbohydrateStat};
            let energy = ${requestScope.homeModel.weeklyEnergyStat};
            let water = ${requestScope.homeModel.weeklyWaterStat};
            let fat = ${requestScope.homeModel.weeklyFatStat};
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