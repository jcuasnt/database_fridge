<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.BoardPost" %>

<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<!DOCTYPE html>
<html>
<head>
    <title>게시판</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <h1>게시판</h1>
    
    <!-- 새 글 작성 -->
    <form action="CreatePostController" method="POST">
        <input type="text" name="title" placeholder="제목" required>
        <textarea name="content" placeholder="내용" required></textarea>
        <button type="submit">글 작성</button>
    </form>

    <!-- 게시물 리스트 -->
    <table border="1">
        <thead>
            <tr>
                <th>제목</th>
                <th>작성자</th>
                <th>작성일</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<BoardPost> posts = (List<BoardPost>) request.getAttribute("posts");
                if (posts != null && !posts.isEmpty()) {
                    for (BoardPost post : posts) {
                    	
                    	LocalDateTime originalDate = post.getCreatedAt().toLocalDateTime();
                        LocalDateTime adjustedDate = originalDate.minusDays(7); // 7일 전으로 조정
                        String formattedDate = adjustedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            %>
                        <tr>
                            <td><a href="ViewPostController?id=<%= post.getId() %>"><%= post.getTitle() %></a></td>
                            <td><%= post.getUserId() %></td>
                            <td><%= formattedDate %></td>
                        </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="3">게시물이 없습니다.</td>
                </tr>
            <%
                }
            %>
        </tbody>
    </table>
    <!-- 냉장고로 돌아가기 버튼 -->
    <form action="FridgeController" method="GET" style="margin-top: 20px;">
        <button type="submit">냉장고로 돌아가기</button>
    </form>
</body>
</html>
