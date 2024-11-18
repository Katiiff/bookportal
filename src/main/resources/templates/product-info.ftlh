<!DOCTYPE html>
<html>
<head>
    <title>BOOKPORTAL</title>
</head>
<body>
<h1>BOOKPORTAL</h1><hr>
<h4>Подробное описание</h4>
<hr>

<b>Название: </b>${product.title}<br>
<b>Автор: </b>${product.author.getFullName()}<br>
<b>Описание: </b>${product.description}<br>
<b>Жанр: </b>${product.genre}<br>
<hr>
<form action="/product/delete/${product.id}" method="post">

    <input type="submit" value="Удалить"/>
</form>
</body>
</html>
