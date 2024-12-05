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

@WebServlet("/UseIngredientController")
public class UseIngredientController extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/fridge_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "0000";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id"); // ��� ID
        String quantityParam = request.getParameter("quantity"); // ����� ����

        if (idParam == null || quantityParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "�ʼ� �Ķ���Ͱ� �����ϴ�.");
            return;
        }

        int id = Integer.parseInt(idParam);
        int useQuantity = Integer.parseInt(quantityParam);

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE ingredients SET quantity = quantity - ? WHERE id = ? AND quantity >= ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, useQuantity);
                stmt.setInt(2, id);
                stmt.setInt(3, useQuantity);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    response.sendRedirect("FridgeController"); // ���� �� ����� ���� �������� �����̷�Ʈ
                } else {
                    response.getWriter().println("����� ������ �����ϰų� �߸��� ��û�Դϴ�.");
                }
            }
        } catch (SQLException e) {
            throw new ServletException("DB �۾� �� ���� �߻�", e);
        }
    }
}
