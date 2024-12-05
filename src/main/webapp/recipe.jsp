<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Recipe" %>
<!DOCTYPE html>
<html>
<head>
    <title>레시피 추천</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <h1>추천 레시피</h1>
    <a href="FridgeController"><button>냉장고 관리로 돌아가기</button></a>

    <table border="1">
        <thead>
            <tr>
                <th>레시피명</th>
                <th>조리 방법</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Recipe> recipes = (List<Recipe>) request.getAttribute("matchedRecipes");
                if (recipes != null && !recipes.isEmpty()) {
                    for (Recipe recipe : recipes) {
            %>
                        <tr>
                            <td><%= recipe.getName() %></td>
                            <td><%= recipe.getInstructions() %></td>
                        </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="2">추천 가능한 레시피가 없습니다.</td>
                </tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
</html>
