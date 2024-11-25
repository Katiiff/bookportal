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
        .btn-danger {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1 class="text-center mb-4">BOOKPORTAL</h1>
    <hr>
    <h4 class="mb-4">Подробное описание</h4>
    <hr>
    <div class="card">
        <div class="card-body">
            <h5 class="card-title">Название: ${product.title()}</h5>
            <h6 class="card-subtitle mb-2 text-muted">Автор: ${product.authorDTO().getFullName()}</h6>
            <p class="card-text">Описание: ${product.description()}</p>
            <p class="card-text">Жанр: ${product.genre()}</p>
        </div>
    </div>
    <hr>
    <form action="/product/delete/${product.id()}" method="post">
        <button type="submit" class="btn btn-danger">Удалить</button>
    </form>
</div>
</body>
</html>