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

@WebServlet("/DeleteIngredientController")
public class DeleteIngredientController extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/fridge_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "0000";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "DELETE FROM ingredients WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
            response.sendRedirect("FridgeController");
        } catch (SQLException e) {
            throw new ServletException("DB 작업 중 오류 발생", e);
        }
    }
}
