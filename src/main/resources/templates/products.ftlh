<!DOCTYPE html>
<html>
<head>
    <title>BOOKPORTAL</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            margin-top: 50px;
        }
        .card {
            margin-bottom: 20px;
        }
        .btn-primary, .btn-danger {
            margin-top: 20px;
        }
        .title {
            background: linear-gradient(to right, #ff7e5f, #feb47b);
            color: white;
            padding: 20px;
            border-radius: 10px;
            text-align: center;
            font-size: 2.5em;
            font-weight: bold;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
        }
        .btn-custom {
            background: linear-gradient(to right, #ff7e5f, #feb47b);
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
        }
        .btn-custom:hover {
            background: linear-gradient(to right, #feb47b, #ff7e5f);
        }
    </style>
</head>
<body>
<div class="container">
    <div class="title mb-4">BOOKPORTAL</div>
    <hr>
    <h4 class="mb-4">Доступные книги:</h4>
    <#list products as productDTO>
        <div class="card mb-2">
            <div class="card-body">
                <#if productDTO.authorDTO??>
                    <p class="card-text"><b>${productDTO.title()}</b> by ${productDTO.authorDTO().surname()!} | <a href="/product/${productDTO.id()}">Далее к описанию..</a></p>
                <#else>
                    <div class="alert alert-warning" role="alert">
                        Сначала создайте автора!
                    </div>
                </#if>
            </div>
        </div>
    </#list>

    <h4 class="mb-4">Доступные авторы:</h4>
    <#list authors as authorDTO>
        <div class="card mb-2">
            <div class="card-body">
                <p class="card-text"><b>${authorDTO.getFullName()}</b> | <a href="/author/${authorDTO.id()}">Далее к книгам..</a></p>
            </div>
        </div>
    </#list>
    <hr>
    <h3 class="mb-4">Если вы автор, добавьте свою книгу</h3>
    <form action="/product/create" method="post">
        <div class="form-group">
            <label for="title">Название:</label>
            <input type="text" class="form-control" id="title" name="title">
        </div>
        <div class="form-group">
            <label for="authorName">Имя автора:</label>
            <input type="text" class="form-control" id="authorName" name="authorDTO.name">
        </div>
        <div class="form-group">
            <label for="authorSurname">Фамилия автора:</label>
            <input type="text" class="form-control" id="authorSurname" name="authorDTO.surname">
        </div>
        <div class="form-group">
            <label for="genre">Жанр:</label>
            <input type="text" class="form-control" id="genre" name="genre">
        </div>
        <div class="form-group">
            <label for="description">Описание:</label>
            <input type="text" class="form-control" id="description" name="description">
        </div>
        <button type="submit" class="btn btn-custom">Добавить в библиотеку</button>
    </form>
    <hr>
    <h3 class="mb-4">Поиск книг по автору</h3>
    <form action="/search/author" method="get">
        <div class="form-group">
            <label for="authorName">Автор:</label>
            <input type="text" class="form-control" id="authorName" name="authorName">
        </div>
        <button type="submit" class="btn btn-custom">Поиск</button>
    </form>
    <hr>
    <h3 class="mb-4">Добавить автора</h3>
    <form action="/author/create" method="post">
        <div class="form-group">
            <label for="name">Имя автора:</label>
            <input type="text" class="form-control" id="name" name="name">
        </div>
        <div class="form-group">
            <label for="surname">Фамилия автора:</label>
            <input type="text" class="form-control" id="surname" name="surname">
        </div>
        <button type="submit" class="btn btn-custom">Добавить автора</button>
    </form>
</div>
</body>
</html>