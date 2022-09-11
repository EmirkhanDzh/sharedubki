<!DOCTYPE html>
<html>
<head>
    <title>Share, DUBKI!</title>
</head>
<body>
<h1>Share, Dubki!</h1>
<h3>Goods from all Dubki students</h3>
<hr>
<h5>Authorization</h5>
<form action="/login" method="post">
    Эл. почта: <input type="email" name="username"><br><br>
    Пароль: <input type="password" name="password"><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Вход"/>
</form>
<hr>
<a href="/registration">Registration</a>
</body>
</html>