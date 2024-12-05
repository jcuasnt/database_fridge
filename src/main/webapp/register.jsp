<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="form-container">
        <h2>회원가입</h2>
        <form action="${pageContext.request.contextPath}/UserController" method="POST">

    <input type="hidden" name="action" value="register">
    <label for="username">아이디:</label>
    <input type="text" id="username" name="username" required><br>
    <label for="password">비밀번호:</label>
    <input type="password" id="password" name="password" required><br>
    <label for="email">이메일:</label>
    <input type="email" id="email" name="email"><br>
    <button type="submit">회원가입</button>
</form>
        <a href="index.jsp">메인으로 돌아가기</a>
    </div>
</body>
</html>
