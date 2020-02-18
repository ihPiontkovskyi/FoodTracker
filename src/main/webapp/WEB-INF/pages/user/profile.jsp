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
                        ${sessionScope.user.firstName} ${sessionScope.user.lastName}
                    </h2>
                    <form action="profile-modify" method="POST">
                        <div class="row row-space">
                            <div class="col-6">
                                <div class="input-group">
                                    <label class="label">first name</label>
                                    <input class="input--style-4" type="text" name="first_name" required
                                           value="${sessionScope.user.firstName}">
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="input-group">
                                    <label class="label">last name</label>
                                    <input class="input--style-4" type="text" name="last_name" required
                                           value="${sessionScope.user.lastName}">
                                </div>
                            </div>
                        </div>
                        <div class="row row-space">
                            <div class="col-6">
                                <div class="input-group">
                                    <label class="label">Birthday</label>
                                    <div class="input-group-icon">
                                        <input class="input--style-4 js-datepicker" type="text" name="birthday" required
                                               value="${sessionScope.user.birthday}">
                                        <em class="zmdi zmdi-calendar-note input-icon js-btn-calendar"></em>
                                    </div>
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="input-group">
                                    <label class="label">Gender</label>
                                    <div class="p-t-10">
                                        <label class="radio-container m-r-45">Male
                                            <input value="MALE"
                                                   type="radio" ${sessionScope.user.gender.name()=='MALE'?'checked="checked"':''}
                                                   name="genderEntity">
                                            <span class="checkmark"></span>
                                        </label>
                                        <label class="radio-container">Female
                                            <input value="FEMALE"
                                                   type="radio" ${sessionScope.user.gender.name()=='FEMALE'?'checked="checked"':''}
                                                   name="genderEntity">
                                            <span class="checkmark"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row row-space">
                            <div class="col-6">
                                <div class="input-group">
                                    <label class="label">Weight</label>
                                    <input class="input--style-4" type="number" name="weight" required
                                           value="${sessionScope.user.weight}">
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="input-group">
                                    <label class="label">Height</label>
                                    <input class="input--style-4" type="number" name="height" required
                                           value="${sessionScope.user.height}">
                                </div>
                            </div>
                        </div>
                        <div class="input-group">
                            <label class="label">Lifestyle</label>
                            <div class="rs-select2 js-select-simple select--no-search">
                                <select name="lifestyleEntity">
                                    <option value="SEDENTARY" ${sessionScope.user.lifestyle.name() == 'SEDENTARY'?'selected="true"':''}>
                                        <fmt:message key="sedentary.option"/>
                                    </option>
                                    <option value="LIGHTLY_ACTIVE"${sessionScope.user.lifestyle.name() == 'LIGHTLY_ACTIVE'?'selected="true"':''}>
                                        <fmt:message key="lightly.active.option"/>
                                    </option>
                                    <option value="ACTIVE"${sessionScope.user.lifestyle.name() == 'ACTIVE'?'selected="true"':''}>
                                        <fmt:message key="active.option"/>
                                    </option>
                                    <option value="VERY_ACTIVE"${sessionScope.user.lifestyle.name() == 'VERY_ACTIVE'?'selected="true"':''}>
                                        <fmt:message key="very.active.option"/>
                                    </option>
                                    <option value="NOT_SELECTED"${sessionScope.user.lifestyle.name() == 'NOT_SELECTED'?'selected="true"':''}>
                                        <fmt:message key="choose.option"/>
                                    </option>
                                </select>
                                <div class="select-dropdown"></div>
                            </div>
                        </div>
                        <div class="p-t-15">
                            <button class="btn btn--radius-2 btn--blue" type="submit">Submit</button>
                        </div>
                    </form>
                </div>
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