<!DOCTYPE html>
<html>
<head>
    <title>BOOKPORTAL</title>
</head>
<body>
<h1>BOOKPORTAL</h1><hr>
<h4>Доступные книги:</h4>
<#list products as productDTO>
    <div>
        <p><b>${productDTO.title()} ${productDTO.authorDTO().name!}</b> | <a href="/product/${productDTO.id()}">Далее к описанию..</a></p>
    </div>
</#list>

<h4>Доступные авторы:</h4>
<#list authors as authorDTO>
    <div>
        <p><b>${authorDTO.getFullName()} </b> | <a href="/author/${authorDTO.id()}">Далее к книгам..</a></p>
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
<hr>
<h3>Добавить автора</h3>
<form action="/api/authors/create" method="post">
    Имя автора: <input type="text" name="name"><br><br>
    Фамилия автора: <input type="text" name="surname"><br><br>
    <input type="submit" value="Добавить автора"/>
</form>
</body>
</html>
