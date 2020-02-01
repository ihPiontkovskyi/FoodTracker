<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/pages/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Settings</title>
    <link href="https://cdn.jsdelivr.net/npm/select2@4.0.12/dist/css/select2.min.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css"/>
    <link rel="icon" type="image/png" href="<c:url value="/assets/images/logo-002.png"/>"/>
    <link href="<c:url value="/assets/css/register.css"/>" rel="stylesheet" media="all">
</head>

<body>
<div class="page-wrapper bg-gra-02 p-t-130 p-b-100 font-poppins">
    <div class="wrapper wrapper--w680">
        <div class="card card-4">
            <div class="card-body">
                <h2 class="title"></h2>
                <form action="updateUser" method="POST">
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">first name</label>
                                <input class="input--style-4" type="text" name="first_name" required
                                       value="${requestScope.first_name}">
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">last name</label>
                                <input class="input--style-4" type="text" name="last_name" required
                                       value="${requestScope.last_name}">
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Birthday</label>
                                <div class="input-group-icon">
                                    <input class="input--style-4 js-datepicker" type="text" name="birthday" required
                                           value="${requestScope.birthday}">
                                    <em class="zmdi zmdi-calendar-note input-icon js-btn-calendar"></em>
                                </div>
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Gender</label>
                                <div class="p-t-10">
                                    <label class="radio-container m-r-45">Male
                                        <input value="1" type="radio" ${requestScope.gender?'checked="checked"':''}
                                               name="gender">
                                        <span class="checkmark"></span>
                                    </label>
                                    <label class="radio-container">Female
                                        <input value="0" type="radio" ${!requestScope.gender?'checked="checked"':''}
                                               name="gender">
                                        <span class="checkmark"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Email</label>
                                <input class="input--style-4" type="email" name="email" disabled required
                                       value="${requestScope.username}">
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Weight</label>
                                <input class="input--style-4" type="number" name="weight" required
                                       value="${requestScope.weight}">
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Height</label>
                                <input class="input--style-4" type="number" name="height" required
                                       value="${requestScope.height}">
                            </div>
                        </div>
                    </div>
                    <div class="input-group">
                        <label class="label">Lifestyle</label>
                        <div class="rs-select2 js-select-simple select--no-search">
                            <select name="lifestyle">
                                <option value="0" ${requestScope.lifestyle == 0?'selected="true"':''}>Sedentary: spend
                                    most of the day sitting
                                </option>
                                <option value="1"${requestScope.lifestyle == 1?'selected="true"':''}>Lightly active:
                                    spend a good part of the day on your feet
                                </option>
                                <option value="2"${requestScope.lifestyle == 2?'selected="true"':''}>Active: spend a
                                    good part of the day doing some physically activity
                                </option>
                                <option value="3"${requestScope.lifestyle == 3?'selected="true"':''}>Very active: spend
                                    most of the day doing some physically activity
                                </option>
                            </select>
                            <div class="select-dropdown"></div>
                        </div>
                    </div>
                    <div class="p-t-15">
                        <button class="btn btn--radius-2 btn--blue" type="submit">Submit</button>
                    </div>
                </form>
                <form action="deleteUser" method="POST">
                    <div class="p-t-15">
                        <button class="btn btn--radius-2 btn--red" type="submit">Delete user</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script src="https://use.fontawesome.com/59810e450d.js"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.0.12/dist/js/select2.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
<script>
    (function ($) {
        'use strict';
        try {
            $('.js-datepicker').daterangepicker({
                "singleDatePicker": true,
                "showDropdowns": true,
                "autoUpdateInput": false,
                locale: {
                    format: 'YYYY-MM-DD'
                },
            });
            var myCalendar = $('.js-datepicker');
            var isClick = 0;
            $(window).on('click', function () {
                isClick = 0;
            });
            $(myCalendar).on('apply.daterangepicker', function (ev, picker) {
                isClick = 0;
                $(this).val(picker.startDate.format('YYYY-MM-DD'));

            });
            $('.js-btn-calendar').on('click', function (e) {
                e.stopPropagation();
                if (isClick === 1) isClick = 0;
                else if (isClick === 0) isClick = 1;
                if (isClick === 1) {
                    myCalendar.focus();
                }
            });
            $(myCalendar).on('click', function (e) {
                e.stopPropagation();
                isClick = 1;
            });
            $('.daterangepicker').on('click', function (e) {
                e.stopPropagation();
            });
        } catch (er) {
            console.log(er);
        }
        try {
            var selectSimple = $('.js-select-simple');
            selectSimple.each(function () {
                var that = $(this);
                var selectBox = that.find('select');
                var selectDropdown = that.find('.select-dropdown');
                selectBox.select2({
                    dropdownParent: selectDropdown
                });
            });
        } catch (err) {
            console.log(err);
        }
    })(jQuery);
</script>
</body>

</html>