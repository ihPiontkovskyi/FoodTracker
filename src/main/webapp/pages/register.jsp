<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale/messages"/>
<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <meta charset="UTF-8">
    <title>Register Your Account</title>
    <link href="https://cdn.jsdelivr.net/npm/select2@4.0.12/dist/css/select2.min.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css"/>
    <link rel="icon" type="image/png" href="../assets/images/logo-002.png"/>
    <link href="../assets/css/register.css" rel="stylesheet" media="all">
</head>

<body>
<div class="page-wrapper bg-gra-02 p-t-130 p-b-100 font-poppins">
    <div class="lang-block">
        <a href="?lang=en">EN</a>
        <a href="?lang=ru">RU</a>
    </div>
    <div class="wrapper wrapper--w680">
        <div class="card card-4">
            <div class="card-body">
                <h2 class="title"><fmt:message key="registration.label"/></h2>
                <form action="register" method="POST">
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label"><fmt:message key="first.name.field"/>
                                    <input class="input--style-4" type="text" name="first_name" required>
                                </label>
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label"><fmt:message key="last.name.field"/>
                                    <input class="input--style-4" type="text" name="last_name" required>
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label"><fmt:message key="birthday.field"/>
                                    <div class="input-group-icon">
                                        <input class="input--style-4 js-datepicker" type="text" name="birthday"
                                               required>
                                        <em class="zmdi zmdi-calendar-note input-icon js-btn-calendar"></em>
                                    </div>
                                </label>
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label"><fmt:message key="gender.field"/></label>
                                <div class="p-t-10">
                                    <label class="radio-container m-r-45"><fmt:message key="male.checkbox"/>
                                        <input value="1" type="radio" checked="checked" name="gender">
                                        <span class="checkmark"></span>
                                    </label>
                                    <label class="radio-container"><fmt:message key="female.checkbox"/>
                                        <input value="2" type="radio" name="gender">
                                        <span class="checkmark"></span>
                                    </label>
                                    <label class="radio-container"><fmt:message key="other.checkbox"/>
                                        <input value="3" type="radio" name="gender">
                                        <span class="checkmark"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label"><fmt:message key="username.field"/>
                                    <input class="input--style-4" type="email" name="email" required>
                                </label>
                            </div>
                        </div>

                        <div class="col-2">
                            <div class="input-group ">
                                <label class="label"><fmt:message key="password.field"/>
                                    <input class="input--style-4" type="password" name="pass" required>
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group ">
                                <label class="label"><fmt:message key="repeat.password.field"/>
                                    <input class="input--style-4" type="password" name="repeat-pass" required>
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label"><fmt:message key="weight.field"/>
                                    <input class="input--style-4" type="number" name="weight" required>
                                </label>
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label"><fmt:message key="height.field"/>
                                    <input class="input--style-4" type="number" name="height" required>
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="input-group">
                        <label class="label"><fmt:message key="lifestyle.select"/>
                            <div class="rs-select2 js-select-simple select--no-search">
                                <select name="lifestyle">
                                    <option value="0" disabled="disabled" selected="selected"><fmt:message
                                            key="choose.option"/></option>
                                    <option value="0"><fmt:message key="sedentary.option"/></option>
                                    <option value="1"><fmt:message key="lightly.active.option"/></option>
                                    <option value="2"><fmt:message key="active.option"/>
                                    </option>
                                    <option value="3"><fmt:message key="very.active.option"/>
                                    </option>
                                </select>
                                <div class="select-dropdown"></div>
                            </div>
                        </label>
                    </div>
                    <div class="p-t-15">
                        <button class="btn btn--radius-2 btn--blue" type="submit"><fmt:message
                                key="submit.btn"/></button>
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
        let locale_lang;
        if ("ru" === "${param.lang}") {
            locale_lang = {
                format: 'YYYY-MM-DD',
                "daysOfWeek": [
                    "Вс",
                    "Пн",
                    "Вт",
                    "Ср",
                    "Чт",
                    "Пт",
                    "Сб"
                ],
                "monthNames": [
                    "Январь",
                    "Февраль",
                    "Март",
                    "Апрель",
                    "Май",
                    "Июнь",
                    "Июль",
                    "Август",
                    "Сентябрь",
                    "Октябрь",
                    "Ноябрь",
                    "Декабрь"
                ],
            }
        } else {
            locale_lang = {
                format: 'YYYY-MM-DD'
            }
        }
        try {
            $('.js-datepicker').daterangepicker({
                "singleDatePicker": true,
                "showDropdowns": true,
                "autoUpdateInput": false,
                locale: locale_lang,
            });
            let myCalendar = $('.js-datepicker');
            let isClick = 0;
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
            let selectSimple = $('.js-select-simple');
            selectSimple.each(function () {
                let that = $(this);
                let selectBox = that.find('select');
                let selectDropdown = that.find('.select-dropdown');
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