package com.example.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/LogoutController")
public class LogoutController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // ���� ���� ��������
        if (session != null) {
            session.invalidate(); // ���� ��ȿȭ
        }
        response.sendRedirect("index.jsp"); // �α׾ƿ� �� index.jsp�� �����̷�Ʈ
    }
}
