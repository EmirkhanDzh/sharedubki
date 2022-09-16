<#--<!DOCTYPE html>-->
<#--<html>-->
<#--<head>-->
<#--    <title>Share, DUBKI!</title>-->
<#--</head>-->
<#--<body>-->
<#--<h1>Share, Dubki!</h1>-->
<#--<h3>Goods from all Dubki students</h3>-->
<#--<hr>-->
<#--<h5>Authorization</h5>-->
<#--<form action="/login" method="post">-->
<#--    Эл. почта: <input type="email" name="username"><br><br>-->
<#--    Пароль: <input type="password" name="password"><br><br>-->
<#--    <input type="hidden" name="_csrf" value="${_csrf.token}">-->
<#--    <input type="submit" value="Вход"/>-->
<#--</form>-->
<#--<hr>-->
<#--<a href="/registration">Registration</a>-->
<#--</body>-->
<#--</html>-->

<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1 style="text-align: center">Authorization</h1>
    <hr>
    <form action="/login " method="post">
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control <#if RequestParameters.error??>is-invalid</#if>" id="email" name="username">
            <div id="email" class="invalid-feedback">
                Incorrect email
            </div>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control <#if RequestParameters.error??>is-invalid</#if>" id="password" name="password">
            <div id="password" class="invalid-feedback">
                Incorrect password
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" style="width: 100%" class="btn btn-dark">Sign in</button>
    </form>
    <hr>
    <h2>Don't have an account? <a href="/registration">Sign up!</a></h2>
</@t.template>