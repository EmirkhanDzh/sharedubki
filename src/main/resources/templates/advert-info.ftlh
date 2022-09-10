<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<h1>Share, Dubki!</h1>
<h4>Detailed information</h4>

<#list images as img>
    <img src="/images/${img.id}" height="80px"/><br><br>
</#list>

<b>Good title: </b>${advert.title}<br>
<b>Good description: </b>${advert.description}<br>
<b>Price: </b>${advert.price}<br>
<b>Corpus: </b>${advert.corpus}<br>
<b>Author name: </b>${advert.authorName}<br>

<hr>

<form action="/advert/delete/${advert.id}" method="post">
    <input type="submit" value="Delete good"/>
</form>

</body>
</html>