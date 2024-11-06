<!DOCTYPE html>
<html>
<head>
    <title>BOOKPORTAL</title>
</head>
<body>
<h1>BOOKPORTAL</h1><hr>
<h4>Доступные книги:</h4>
<#list products as product>
    <div>
        <p><b>${product.title} ${product.author}</b> | <a href="/product/${product.id}">Далее к описанию..</a></p>
    </div>
</#list>
<hr>
<h3>Если вы автор, добавьте свою книгу</h3>
<form action="/product/create" method="post">
    Название: <input type="text" name="title"><br><br>
    Автор: <input type="text" name="author"><br><br>
    Жанр: <input type="text" name="genre"><br><br>
    Описание: <input type="text" name="description"><br><br>
    <input type="submit" value="Добавить в библиотеку"/>
</form>
<hr>
<h3>Поиск книг по автору</h3>
<form action="/api/search/author" method="get">
    Автор: <input type="text" name="author"><br><br>
    <input type="submit" value="Поиск"/>
</form>
</body>
</html>
