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
<h5>Доступные книги этого автора:</h5>
<#list products as productDTO>
    <div>
        <p><b>${productDTO.title()} ${productDTO.authorDTO().name!}</b> </p>
    </div>
</#list>
<hr>
<form action="/api/authors/delete/${author.id}" method="post">
    <input type="hidden" name="_method" value="DELETE">
    <input type="submit" value="Удалить"/>
</form>
</body>
</html>