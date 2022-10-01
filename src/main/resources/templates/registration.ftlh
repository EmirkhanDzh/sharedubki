<#--<!DOCTYPE html>-->
<#--<html>-->
<#--<head>-->
<#--    <title>Share, DUBKI</title>-->
<#--</head>-->
<#--<body>-->
<#--<h1>Share, Dubki!</h1>-->
<#--<hr>-->
<#--<h3>Goods from all Dubki students</h3>-->
<#--<hr>-->
<#--<h5>Registration</h5>-->
<#--<form action="/registration" method="post">-->
<#--    Имя пользователя: <input type="text" name="name"><br><br>-->
<#--    Эл. почта: <input type="email" name="email"><br><br>-->
<#--    Номер телефона: <input type="text" name="phoneNumber"><br><br>-->
<#--    Пароль: <input type="password" name="password"><br><br>-->
<#--    <input type="hidden" name="_csrf" value="${_csrf.token}">-->
<#--    <input type="submit" value="Зарегистрироваться"/>-->
<#--</form>-->

<#--<#if errorMessage??>-->
<#--    <h2 style="color: red">${errorMessage}</h2>-->
<#--</#if>-->

<#--</body>-->
<#--</html>-->


<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1 style="text-align: center">Registration</h1>
    <hr>
    <form action="/registration" method="post">
        <div class="form-group">
            <label for="name">User name</label>
            <input type="text" class="form-control" required id="name" name="name">
        </div>
        <div class="form-group">
            <label for="email">User email</label>
            <input type="email" class="form-control" id="email" required name="email" pattern="[a-z0-9._%+-]+@[e][d][u]+\.+[h][s][e]+\.[r][u]$" placeholder="email@edu.hse.ru">
        </div>
        <div class="form-group">
            <label for="phoneNumber">Phone number</label>
            <input type="text" class="form-control" id="phoneNumber" required name="phoneNumber" pattern="[+][7][9][0-9]{9}" placeholder="+79123456789">
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" id="password" required name="password">
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" style="width: 100%" class="btn btn-dark">Sign up</button>
    </form>
    <#if errorMessage??>
        <h2 style="color: red">${errorMessage}</h2>
    </#if>
</@t.template>