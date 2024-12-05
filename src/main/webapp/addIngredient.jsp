<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>재료 추가</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <h1>재료 추가</h1>
    <form action="${pageContext.request.contextPath}/AddIngredientController" method="POST">
    <label for="name">재료 이름:</label>
    <input type="text" id="name" name="name" required><br>
    
    <label for="quantity">수량:</label>
    <input type="number" id="quantity" name="quantity" required><br>
    
    <label for="expiration_date">유통기한:</label>
    <input type="date" id="expiration_date" name="expiration_date" required><br>
    
    <button type="submit">추가</button>
</form>

</body>
</html>
