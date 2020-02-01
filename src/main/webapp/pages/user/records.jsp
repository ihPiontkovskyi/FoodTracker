<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/pages/error.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="messages" scope="request"/>
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
                    <a href="home"> <em class="menu-icon fa fa-bar-chart"></em>Highlights</a>
                </li>
                <li>
                    <a href="records.jsp"> <em class="menu-icon fa fa-calendar"></em>Diary</a>
                </li>
                <li>
                    <a href="meal"> <em class="menu-icon fa fa-cutlery"></em>Meals</a>
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
                        <a class="nav-link" href="settings"><em class="fa fa -cog"></em>Settings</a>

                        <a class="nav-link" href="logout" ><em class="fa fa-power -off"></em>Logout</a>
                    </div>
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
                                            <span id="energy" class="count"></span>
                                            kcal
                                        </div>
                                        <div class="stat-heading">Energy</div>
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
                                                                     class="count"></span>
                                            g
                                        </div>
                                        <div class="stat-heading">Protein</div>
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
                                                                     class="count"></span> g
                                        </div>
                                        <div class="stat-heading">Carbohydrates</div>
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
                                                                     class="count"></span>
                                            ml
                                        </div>
                                        <div class="stat-heading">Water</div>
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
                                        <h4 class="box-title" id="title"></h4>
                                    </div>
                                    <div class="col-lg-10">
                                        <a onclick="previous()" class="btn"><i class="fa fa-arrow-left"
                                                                               aria-hidden="true"></i></a>
                                        <a onclick="next()" class="btn"><i class="fa fa-arrow-right"
                                                                           aria-hidden="true"></i></a>
                                        <a class="btn float-right"><i class="fa fa-plus"
                                                                      aria-hidden="true"></i></a>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body--">
                                <div class="table-stats order-table ov-h">
                                    <table class="table ">
                                        <thead>
                                        <tr>
                                            <th>Name</th>
                                            <th>Protein</th>
                                            <th>Carbohydrates</th>
                                            <th>Fat</th>
                                            <th>Energy</th>
                                            <th>Weight</th>
                                            <th>Water</th>
                                            <th>Action</th>
                                        </tr>
                                        </thead>
                                        <tbody id="tb">

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
    <footer class="page-footer fixed-bottom">
        <div class="footer-inner bg-white">
            <div class="row">
                <div class="col-lg-12 text-right">
                    Designed by <a href="https://github.com/ihPiontkovskyi/">Ihor Piontkovskyi</a>
                </div>
            </div>
        </div>
    </footer>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script src="https://use.fontawesome.com/59810e450d.js"></script>
<script type="text/javascript">
    let date;

    function deleteRecord() {
        let btnrow = event.target;
        jQuery(function ($) {
            let row = btnrow.closest('tr');
            let id = $(row).find('.recordId').html();
            $.ajax({
                type: "POST",
                url: '/pages/authenticated/deleteRecord',
                data: {recordId: id},
                success: function () {
                    row.remove();
                }
            });
        });
    }

    function setContentByDate() {
        jQuery(function ($) {
            $.post('/pages/authenticated/getRecordsByDate', {recordsByDate: date.getTime()}, function (data) {
                    let json = JSON.parse(data);
                    $(document).find('#title').html('Daily energy, ' + date.toJSON().slice(0, 10).split('-').reverse().join('/'));
                    $(document).find('#energy').html(json[0].sumEnergy);
                    $(document).find('#protein').html(json[0].sumProtein);
                    $(document).find('#carb').html(json[0].sumCarbohydrates);
                    $(document).find('#water').html(json[0].sumWater);
                    for (let i = 1; i < json.length; ++i) {
                        //recordId
                        let rt = document.createElement('tr');
                        rt.className = 'pb-0';
                        let tdid = document.createElement('td');
                        $(tdid).attr('hidden', 'hidden');
                        tdid.className = 'recordId';
                        $(tdid).html(json[i].recordId);
                        $(rt).append(tdid);
                        //meal name
                        let tdName = document.createElement('td');
                        let name = document.createElement('span');
                        name.className = 'text';
                        name.className = 'count';
                        $(name).html(json[i].name);
                        $(tdName).append(name);
                        $(rt).append(tdName);
                        //meal protein
                        let tdProtein = document.createElement('td');
                        let protein = document.createElement('span');
                        protein.className = 'text';
                        protein.className = 'count';
                        $(protein).html(json[i].protein);
                        $(tdProtein).append(protein);
                        $(rt).append(tdProtein);
                        //meal carb
                        let tdCarb = document.createElement('td');
                        let carb = document.createElement('span');
                        carb.className = 'text';
                        carb.className = 'count';
                        $(carb).html(json[i].carb);
                        $(tdCarb).append(carb);
                        $(rt).append(tdCarb);
                        //meal fat
                        let tdFat= document.createElement('td');
                        let fat = document.createElement('span');
                        fat.className = 'text';
                        fat.className = 'count';
                        $(fat).html(json[i].fat);
                        $(tdFat).append(fat);
                        $(rt).append(tdFat);
                        //meal energy
                        let tdEnergy= document.createElement('td');
                        let energy = document.createElement('span');
                        energy.className = 'text';
                        energy.className = 'count';
                        $(energy).html(json[i].energy);
                        $(tdEnergy).append(energy);
                        $(rt).append(tdEnergy);
                        //meal weight
                        let tdWeight= document.createElement('td');
                        let weight = document.createElement('span');
                        weight.className = 'text';
                        weight.className = 'count';
                        $(weight).html(json[i].weight);
                        $(tdWeight).append(weight);
                        $(rt).append(tdWeight);
                        //meal water
                        let tdWater = document.createElement('td');
                        let water = document.createElement('span');
                        water.className = 'text';
                        water.className = 'count';
                        $(water).html(json[i].water);
                        $(tdWater).append(water);
                        $(rt).append(tdWater);
                        //action
                        let td_d = document.createElement('td');
                        let btn = document.createElement('a');
                        btn.className = 'btn float-right';
                        $(btn).on('click', deleteRecord);
                        let icon = document.createElement('i');
                        icon.className = 'fa fa-minus';
                        $(icon).attr('aria-hidden', 'true');
                        $(btn).append(icon);
                        $(td_d).append(btn);
                        $(rt).append(td_d);
                        $(document).find('#tb').append(rt);
                    }
                }
            )
        });
    }

    jQuery(document).ready(function ($) {
        date = new Date(Date.now());
        setContentByDate();
    });

    function next() {
        date.setTime(date.getTime() + 24 * 60 * 60 * 1000);
        jQuery(function ($) {
            $(document).find('#tb').html('');
        });
        setContentByDate();
    }

    function previous() {
        date.setTime(date.getTime() - 24 * 60 * 60 * 1000);
        jQuery(function ($) {
            $(document).find('#tb').html('');
        });
        setContentByDate();
    }
</script>
</body>
</html>