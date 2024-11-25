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
    <h4 class="mb-4">Произведения автора:</h4>
    <hr>
    <div class="card">
        <div class="card-body">
            <h5 class="card-title">Имя: ${author.getFullName()}</h5>
            <h6 class="card-subtitle mb-2 text-muted">Доступные книги этого автора:</h6>
            <#list products as productDTO>
                <div class="card mb-2">
                    <div class="card-body">
                        <p class="card-text"><b>${productDTO.title()}</b> </p>
                    </div>
                </div>
            </#list>
        </div>
    </div>
    <hr>
    <form action="/author/delete/${author.id()}" method="post">
        <input type="hidden" name="_method" value="DELETE">
        <button type="submit" class="btn btn-danger">Удалить</button>
    </form>
</div>
</body>
</html>