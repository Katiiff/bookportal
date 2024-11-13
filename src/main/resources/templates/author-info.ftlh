<!DOCTYPE html>
<html>
<head>
<title>BOOKPORTAL</title>
</head>
<body>
<h1>BOOKPORTAL</h1><hr>
<h4>Произведения автора:</h4>
<hr>

<b>Имя: </b>${author.getFullName()}<br>
<#list products as product>
    <div>
        <p><b>Книги: ${product.title} ${product.author.name!}</b> </p>
    </div>
</#list>
<hr>
<form action="/author/delete/${author.id}" method="post">

<input type="submit" value="Удалить"/>
</form>
</body>
</html>