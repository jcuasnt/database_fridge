package com.example.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddIngredientController")
public class AddIngredientController extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/fridge_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "0000";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String quantityStr = request.getParameter("quantity"); // String으로 먼저 받기
        String expirationDate = request.getParameter("expiration_date");
        int userId = 1; // 현재 로그인한 사용자 ID (임시 값)

        // 입력 값 검증
        if (name == null || name.isEmpty() || quantityStr == null || quantityStr.isEmpty() || expirationDate == null || expirationDate.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "모든 필드를 입력해주세요.");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr); // 검증 후 변환
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "INSERT INTO ingredients (user_id, name, quantity, expiration_date) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, userId);
                    stmt.setString(2, name);
                    stmt.setInt(3, quantity);
                    stmt.setString(4, expirationDate);
                    stmt.executeUpdate();
                }
                response.sendRedirect("FridgeController");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "수량은 숫자여야 합니다.");
        } catch (SQLException e) {
            throw new ServletException("DB 작업 중 오류 발생", e);
        }
    }

}
