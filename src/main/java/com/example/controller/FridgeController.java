package com.example.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.model.Ingredient;


@WebServlet("/FridgeController")
public class FridgeController extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/fridge_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "0000";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ���ǿ��� ����� ID ��������
        HttpSession session = request.getSession(false); // ���� ���� �������� (������ ������ null ��ȯ)
        if (session == null || session.getAttribute("userId") == null) {
            // ������ ���ų� �α��� ������ ������ �α��� �������� �����̷�Ʈ
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId"); // ���ǿ��� userId ��������
        String username = (String) session.getAttribute("username"); // ����� �̸� ��������
        String search = request.getParameter("search"); // �˻��� ��������
        
        request.setAttribute("username", username);

        ArrayList<Ingredient> ingredientList = new ArrayList<>();

        // DB���� ��� ���� ��������
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM ingredients WHERE user_id = ?";
            if (search != null && !search.isEmpty()) {
                sql += " AND name LIKE ?";
            }
            sql += " ORDER BY expiration_date ASC";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                if (search != null && !search.isEmpty()) {
                    stmt.setString(2, "%" + search + "%");
                }
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Ingredient ingredient = new Ingredient(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("name"),
                            rs.getInt("quantity"),
                            rs.getDate("expiration_date")
                        );
                        ingredientList.add(ingredient);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServletException("DB �۾� �� ���� �߻�", e);
        }

        // JSP�� ������ ����
        request.setAttribute("ingredientList", ingredientList);
        request.setAttribute("username", username); // ����� �̸��� ����

        // JSP�� ������
        RequestDispatcher dispatcher = request.getRequestDispatcher("fridge.jsp");
        dispatcher.forward(request, response);
    }
}