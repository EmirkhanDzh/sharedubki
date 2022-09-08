<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<h1>Share, Dubki!</h1>
<h4>Goods from all Dubki students</h4>

<#list adverts as elem>

    <div>
        <p><b>${elem.title}</b> ${elem.price} : <a href="/advert/${elem.id}">More...</a> </p>
    </div>

</#list>
<hr>
<h3>Create new advert</h3>
<form action="/advert/create" method="post">
    Advert name: <input type="text" name="title"/><br><br>
    Advert description: <input type="text" name="description"><br><br>
    Good's price: <input type="number" name="price"/><br><br>
    Corpus: <input type="number" name="corpus"/><br><br>
</form>

<div> <h1> чекаем че происходит </h1> </div>

</body>
</html>