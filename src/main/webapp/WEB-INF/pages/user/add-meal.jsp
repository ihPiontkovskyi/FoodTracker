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
</body>
</html>