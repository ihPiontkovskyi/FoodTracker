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
                <a class="navbar-brand" href="home"><img src="../../foodtracker.ua/assets/images/logo-001.png" alt="Logo"></a>
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
                    <a href="?lang=en&page=${sessionScope.page}">EN</a>
                    <a href="?lang=ru&page=${sessionScope.page}">RU</a>
                </div>
            </div>
        </div>
    </header>
    <div class="content">
        <div class="animated fadeIn">
            <div class="orders">
                <div class="row">
                    <div class="col-xl-12">
                        <div class="card">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-lg-2">
                                        <h4 class="box-title">Meal</h4>
                                        <c:if test="${requestScope.totalPages>0}">
                                            <div class="inline">
                                                <c:forEach var="page" items="${requestScope.pageNumbers}">
                                                    <a href="../user/meals?page=${page+1}">
                                                            ${page+1}
                                                    </a>
                                                </c:forEach>
                                            </div>
                                        </c:if>
                                    </div>
                                    <div class="col-lg-10">
                                        <a href="../user/meal-add" class="btn float-right"><i class="fa fa-plus"
                                                                                               aria-hidden="true"></i></a>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body--">
                                <div class="table-stats order-table ov-h">
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th id="name"><fmt:message key="name.label"/></th>
                                            <th id="energy"><fmt:message key="energy.label"/></th>
                                            <th id="protein"><fmt:message key="protein.label"/></th>
                                            <th id="crab"><fmt:message key="carbohydrate.label"/></th>
                                            <th id="fat"><fmt:message key="fat.label"/></th>
                                            <th id="water"><fmt:message key="water.label"/></th>
                                            <th id="weight"><fmt:message key="weight.column.label"/></th>
                                            <th id="action"><fmt:message key="action.label"/></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${requestScope.meals}" var="meal">
                                            <c:if test="${meal.user == null || meal.user.id==sessionScope.user.id || sessionScope.user.role.name()=='ADMIN'}">
                                                <tr class=" pb-0">
                                                    <td hidden class="mealId">${meal.id}</td>
                                                    <td><span class="name">${meal.name}</span></td>
                                                    <td><span class="count">${meal.calculateEnergy()}</span>
                                                        <fmt:message
                                                                key="weight.label"/></td>
                                                    <td><span class="count">${meal.protein}</span> <fmt:message
                                                            key="weight.label"/></td>
                                                    <td><span class="count">${meal.carbohydrate}</span> <fmt:message
                                                            key="weight.label"/></td>
                                                    <td><span class="count">${meal.fat}</span> <fmt:message
                                                            key="weight.label"/></td>
                                                    <td><span class="count">${meal.water}</span> <fmt:message
                                                            key="volume.label"/></td>
                                                    <td><span class="count">${meal.weight}</span> <fmt:message
                                                            key="weight.label"/></td>
                                                    <c:if test="${meal.user != null || sessionScope.user.role.name()=='ADMIN'}">
                                                        <td><a href="../user/meals/delete?id=${meal.id}"
                                                               class="btn float-right"><i
                                                                class="fa fa-minus"
                                                                aria-hidden="true"></i></a>
                                                            <a href="../user/meal-edit?id=${meal.id}"
                                                               class="btn float-right"><i
                                                                    class="fa fa-pencil"
                                                                    aria-hidden="true"></i></a>
                                                        </td>
                                                    </c:if>
                                                    <c:if test="${meal.user == null && sessionScope.user.role.name()!='ADMIN'}">
                                                        <td>
                                                            <fmt:message key="access.action"/>
                                                        </td>
                                                    </c:if>
                                                </tr>
                                            </c:if>
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
</body>
</html>