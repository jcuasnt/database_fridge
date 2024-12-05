package com.example.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/LogoutController")
public class LogoutController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // 기존 세션 가져오기
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        response.sendRedirect("index.jsp"); // 로그아웃 후 index.jsp로 리다이렉트
    }
}
