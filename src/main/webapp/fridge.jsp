<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.model.Ingredient" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html>
<head>
    <title>냉장고 관리</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <style>
        .warning {
            background-color: yellow  !important;
        }
        .expired {
            background-color: red !important;
            color: white;
        }
    </style>
</head>

<body>
    <h1>${username}님의 냉장고 관리</h1>
    
    <!-- 검색바 -->
    <form action="FridgeController" method="GET">
        <input type="text" name="search" placeholder="재료 검색">
        <button type="submit">검색</button>
    </form>
    <form action="LogoutController" method="POST" style="display: inline;">
        <button type="submit">로그아웃</button>
    </form>
    

    <!-- 재료 리스트 -->
    <table border="1">
        <thead>
            <tr>
                <th>재료명</th>
                <th>수량</th>
                <th>유통기한</th>
                <th>삭제</th>
                <th>사용</th>
            </tr>
        </thead>
        <tbody>
            <% 
            List<Ingredient> ingredientList = (List<Ingredient>) request.getAttribute("ingredientList");
            if (ingredientList != null && !ingredientList.isEmpty()) {
                LocalDate today = LocalDate.now();
                for (Ingredient ingredient : ingredientList) {
                    LocalDate expirationDate = ingredient.getExpirationDate().toLocalDate();
                    long daysRemaining = expirationDate.toEpochDay() - today.toEpochDay();

                    // CSS 클래스 설정
                    String cssClass = "";
                    if (daysRemaining < 0) {
                        cssClass = "expired";
                    } else if (daysRemaining <= 3) {
                        cssClass = "warning";
                    }
            %>
                        <tr class="<%= cssClass %>">
                            <td><%= ingredient.getName() %></td>
                            <td><%= ingredient.getQuantity() %></td>
                            <td><%= ingredient.getExpirationDate() %></td>
                            <td>
                                <form action="DeleteIngredientController" method="POST">
                                    <input type="hidden" name="id" value="<%= ingredient.getId() %>">
                                    <button type="submit">삭제</button>
                                </form>
                            </td>
                            <td>
                                <form action="UseIngredientController" method="POST">
                                    <input type="hidden" name="id" value="<%= ingredient.getId() %>">
                                    <input type="number" name="quantity" min="1" placeholder="사용할 수량">
                                    <button type="submit">사용</button>
                                </form>
                            </td>
                        </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="5">재료가 없습니다.</td>
                </tr>
            <%
                }
            %>
        </tbody>
    </table>

    <form action="addIngredient.jsp" method="GET">
        <button type="submit">재료 추가</button>
    </form>
    <form action="board.jsp" method="GET" style="display: inline;">
    <button type="submit">게시판</button>
</form>
<form action="RecipeController" method="GET" style="display: inline;">
    <button type="submit">레시피 추천</button>
</body>
</html>