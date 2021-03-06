<%@ page import="java.time.temporal.ChronoUnit; mcs.parameters.JournalType; mcs.Journal; mcs.Planner; mcs.parameters.PlannerType" %>


<html lang="ar">

<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <title>Nibras dashboard</title>

    <!-- The javascript and css are managed by sprockets. The files can be found in the /assets folder-->

    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery-1.11.0_min.js')}"></script>

    <script type="application/javascript">

    setInterval(function() {
        jQuery.ajax({
            type: 'GET',
            url: '${request.contextPath}/page/heartbeat',
            dataType: 'html',
            success: function(html, textStatus) {
                if (html == 'ok') {
                    window.location.href = window.location;
                    jQuery('#logArea2').html("<span style='background: darkgray; color: darkgreen'>Online</span>");
                }
            },
            error: function(xhr, textStatus, errorThrown) {
                jQuery('#logArea2').html("<span style='background: darkgray; color: darkred'>Offline</span>");
//                        console.log('An error occurred! ' + ( errorThrown ? errorThrown :   xhr.status ));
            }
        });
    }, 30000);

</script>

    <link rel="stylesheet" href="${resource(dir: 'css', file: 'dashboard/application.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'dashboard/css.css')}"/>

    <link rel="icon" href="favicon.ico">

    <style type="text/css">


        .content {
            padding: 7px;
            direction: rtl !important;
            text-align: right !important;
        }


    [data-col="5"] {
        left: 1245px;
    }

    [data-col="4"] {
        left: 935px;
    }

    [data-col="3"] {
        left: 625px;
    }

    [data-col="2"] {
        left: 315px;
    }

    [data-col="1"] {
        left: 5px;
    }

    [data-row="16"] {
        top: 5555px;
    }

    [data-row="15"] {
        top: 5185px;
    }

    [data-row="14"] {
        top: 4815px;
    }

    [data-row="13"] {
        top: 4445px;
    }

    [data-row="12"] {
        top: 4075px;
    }

    [data-row="11"] {
        top: 3705px;
    }

    [data-row="10"] {
        top: 3335px;
    }

    [data-row="9"] {
        top: 2965px;
    }

    [data-row="8"] {
        top: 2595px;
    }

    [data-row="7"] {
        top: 2225px;
    }

    [data-row="6"] {
        top: 1855px;
    }

    [data-row="5"] {
        top: 1485px;
    }

    [data-row="4"] {
        top: 1115px;
    }

    [data-row="3"] {
        top: 745px;
    }

    [data-row="2"] {
        top: 375px;
    }

    [data-row="1"] {
        top: 5px;
    }

    [data-sizey="1"] {
        height: 360px;
    }

    [data-sizey="2"] {
        height: 730px;
    }

    [data-sizey="3"] {
        height: 1100px;
    }

    [data-sizey="4"] {
        height: 1470px;
    }

    [data-sizey="5"] {
        height: 1840px;
    }

    [data-sizey="6"] {
        height: 2210px;
    }

    [data-sizey="7"] {
        height: 2580px;
    }

    [data-sizey="8"] {
        height: 2950px;
    }

    [data-sizey="9"] {
        height: 3320px;
    }

    [data-sizey="10"] {
        height: 3690px;
    }

    [data-sizey="11"] {
        height: 4060px;
    }

    [data-sizey="12"] {
        height: 4430px;
    }

    [data-sizey="13"] {
        height: 4800px;
    }

    [data-sizey="14"] {
        height: 5170px;
    }

    [data-sizey="15"] {
        height: 5540px;
    }

    [data-sizex="1"] {
        width: 300px;
    }

    [data-sizex="2"] {
        width: 610px;
    }

    [data-sizex="3"] {
        width: 920px;
    }

    [data-sizex="4"] {
        width: 1230px;
    }
    </style></head>

<body style="direction: rtl; text-align: right;">
<div id="container">
    <div class="gridster ready" style="width: 100%;">
        <ul style="height: 370px; width: 930px; position: relative;">

            <li data-row="1" data-col="1" data-sizex="1" data-sizey="1" class="gs-w">
                <div data-id="welcome" data-title="اليوم" data-text="14 شعبان 1441هـ. " data-moreinfo=""
                     class="widget widget-text welcome"><h1 class="title" data-bind="title">
%{--                    مواقيت الصلاة--}%
                    <b>${((java.time.chrono.HijrahDate.now().plus(-2, java.time.temporal.ChronoUnit.DAYS))).format(java.time.format.DateTimeFormatter.ofPattern("dd MMMM yyyy").withLocale(Locale.forLanguageTag('ar')))}</b>
                    <br/>
                    <% Calendar c = new GregorianCalendar(); c.setLenient(false); c.setMinimalDaysInFirstWeek(4);
                    c.setFirstDayOfWeek(java.util.Calendar.MONDAY)
                    %>
                    <b>أ</b> ${c.get(Calendar.WEEK_OF_YEAR)}
                    ${new Date().format("E dd")}
                </h1>

                    <div class="content">
                        <ul style="direction: rtl; text-align: right;">
                            <g:each in="${prayersText.split('\n')}" var='l'>
                                <li style="text-align: center;">
                                    <span class="name">
                                        ${raw(l)}
                                    </span>

                                </li>
                            </g:each>

                    </div>

                    <p class="more-info" data-bind="moreinfo"></p>

                    <p class="updated-at" data-bind="updatedAtMessage"></p>
                </div>
            </li>

            <li data-row="1" data-col="2" data-sizex="1" data-sizey="1" class="gs-w widget-list">
                <div data-id="outboard-prayers" data-title="مواقيت الصلاة" style="direction: rtl; text-align: right; font-size: .8em;"
                     class="widget widget-outboard outboard-prayers"><h1 class="title"
                                                                         data-bind="title">
مناسبات هجرية
                </h1>


                    2 - فتح مكة المكرمة - 10هـ<br/>
                    7 - وفاة أبي طالب عم النبي(ص) - 10هـ<br/>
                    10 - وفاة أم المؤمنين خديجة بنت خويلد(ع) - 10للبعثة<br/>
                    12 - المؤاخاة بين المهاجرين والأنصار - 1هـ<br/>
                    15 - مولد الإمام الحسن المجتبى(ع) - 2هـ<br/>
                    15 - خروج مسلم بن عقيل(ع) إلى الكوفة - 60هـ<br/>
                    17 - معركة بدر الكبرى - 2هـ<br/>
                    19 - جرح الإمام علي أمير المؤمنين(ع) - 40هـ<br/>
                    21 - شهادة الإمام علي بن أبي طالب(ع) - 40هـ



%{--                    <p class="updated-at" data-bind="updatedAtMessage">Last updated at 15:28</p>--}%
                </div>
            </li>

            <li data-row="1" data-col="3" data-sizex="1" data-sizey="1" class="gs-w widget-meter">
                <div data-id="outboard-prayers" data-title=" " style="direction: rtl; text-align: right;"
                     class="widget widget-outboard outboard-prayers"><h1 class="title"
                                                                         data-bind="title">

                </h1>

                    <div class="content" style="font-family: Amiri; font-size: 1.2em">
                        <g:set var="aya"
                               value="${app.IndexCard.executeQuery('from IndexCard i where i.priority >= ? and i.type.code = ?', [3, 'aya'], [offset: Math.floor(Math.random()*100)])[0]}"/>
                        {
                        ${aya.shortDescription}
                        }
                        (${mcs.Writing.get(aya.recordId)?.summary}
                        ${aya.orderInWriting})



%{--                        <ul style="direction: rtl; text-align: right;">--}%
%{--                            <g:each in="${new File('/nbr/goals.txt').text.split('\n')}" var='l'>--}%
%{--                                <li>--}%
%{--                                    <span class="name">--}%
%{--                                        ${l}--}%
%{--                                    </span>--}%
%{----}%
%{--                                </li>--}%
%{--                            </g:each>--}%
%{----}%
                    </div>

%{--                    <p class="updated-at" data-bind="updatedAtMessage">Last updated at 15:28</p>--}%
                </div>
            </li>

            <li data-row="2" data-col="2" data-sizex="1" data-sizey="1" class="gs-w widget-comments">
                <div data-id="outboard-goals" data-title="الأهداف" class="widget widget-outboard outboard-goals" style="direction: rtl; text-align: right;"><h1
                        class="title" data-bind="title">

                    ${Planner.get(376).summary}
                </h1>

                    <div class="content">
                        ${Planner.get(376).description}

                        %{--                        <ul style="direction: rtl; text-align: right;">--}%
                        %{--                            <g:each in="${new File('/nbr/goals.txt').text.split('\n')}" var='l'>--}%
                        %{--                                <li>--}%
                        %{--                                    <span class="name">--}%
                        %{--                                        ${l}--}%
                        %{--                                    </span>--}%
                        %{----}%
                        %{--                                </li>--}%
                        %{--                            </g:each>--}%
                        %{----}%
                    </div>

                    %{--                    <p class="updated-at" data-bind="updatedAtMessage">Last updated at 15:28</p>--}%
                </div>
            </li>   <li data-row="2" data-col="3" data-sizex="1" data-sizey="1" class="gs-w widget-comments">
                <div data-id="outboard-goals" data-title="الأهداف" class="widget widget-outboard outboard-goals" style="direction: rtl; text-align: right;"><h1
                        class="title" data-bind="title">

                    ${Planner.get(377).summary}
                </h1>

                    <div class="content">
                        ${Planner.get(377).description}



                        %{--                        <ul style="direction: rtl; text-align: right;">--}%
                        %{--                            <g:each in="${new File('/nbr/goals.txt').text.split('\n')}" var='l'>--}%
                        %{--                                <li>--}%
                        %{--                                    <span class="name">--}%
                        %{--                                        ${l}--}%
                        %{--                                    </span>--}%
                        %{----}%
                        %{--                                </li>--}%
                        %{--                            </g:each>--}%
                        %{----}%
                    </div>

                    %{--                    <p class="updated-at" data-bind="updatedAtMessage">Last updated at 15:28</p>--}%
                </div>
            </li>
          <li data-row="2" data-col="1" data-sizex="1" data-sizey="1" class="gs-w">
                <div data-id="outboard-goals" data-title="الأهداف" class="widget widget-outboard outboard-goals" style="direction: rtl; text-align: right;"><h1
                        class="title" data-bind="title">Countups</h1>

                    <div class="content">

                        %{--Refactor--}%
%{--                        <b>Countdowns</b>--}%
                        <ul>
                            <g:each in="${Journal.findAllByTypeAndBookmarked(JournalType.get(52), true, [sort: 'startDate', order: 'desc'])}" var="j">
                                <li>
                                    <b>${j.summary}</b>
                                    <pkm:prettyDuration date1="${j.endDate ?: j.startDate}"/>
                                </li>

                            </g:each>
                        </ul>

%{--                        <b>Countups</b>--}%
%{--                        <ul>--}%
%{--                            <g:each in="${Journal.findAllByType(JournalType.get(52), [sort: 'endDate', order: 'desc'])}" var="j">--}%
%{--                                <li>--}%
%{--                                    ${j.description} - ${(j.startDate..new Date()).size()} days have passed--}%
%{--                                </li>--}%

%{--                            </g:each>--}%
%{--                        </ul>--}%
                    </div>

                    <p class="updated-at" data-bind="updatedAtMessage"></p>
                </div>
            </li>

        </ul>
        <center><div style="font-size: 12px; display: none;">Try this: curl -d
        '{ "auth_token": "YOUR_AUTH_TOKEN", "text": "Hey, Look what I can do!"
        }' \http://192.168.0.10:3030/widgets/welcome</div></center>
    </div>

</div>


<div id="saving-instructions">
    <p>Paste the following at the top of <i>sample.erb</i></p>
    <textarea id="gridster-code"></textarea>
</div>
<a href="#saving-instructions" id="save-gridster">Save this layout</a>


<div id="lean_overlay"></div></body></html>
