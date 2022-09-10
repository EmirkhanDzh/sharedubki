<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<h1>Share, Dubki!</h1>
<h4>Goods from all Dubki students</h4>
<form action="/" method="get">
    Find advert by its title: <input type="text" name="title"><br>
    <input type="submit" value="search">
</form>


<#list adverts as elem>

    <div>
        <p><b>${elem.title}</b> ${elem.price} руб. : <a href="/advert/${elem.id}">More...</a> </p>
    </div>
    <#else>
    <h3>No goods...</h3>

</#list>

<hr>

<h3>Create new advert</h3>
<form action="/advert/create" method="post" enctype="multipart/form-data">
    Advert title: <input type="text" name="title"/><br><br>
    Advert description: <input type="text" name="description"><br><br>
    Good's price: <input type="number" name="price"/><br><br>
    Corpus: <input type="number" name="corpus"/><br><br>
    Author name: <input type="text" name="authorName"/><br><br>
    First image: <input type = "file" name = "file1"><br><br>
    Second image: <input type = "file" name = "file2"><br><br>
    Third image: <input type = "file" name = "file3"><br><br>
    <input type="submit" value="Create advert"/>
</form>

</body>
</html>