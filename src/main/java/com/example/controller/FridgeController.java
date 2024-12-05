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
        // 세션에서 사용자 ID 가져오기
        HttpSession session = request.getSession(false); // 기존 세션 가져오기 (세션이 없으면 null 반환)
        if (session == null || session.getAttribute("userId") == null) {
            // 세션이 없거나 로그인 정보가 없으면 로그인 페이지로 리다이렉트
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId"); // 세션에서 userId 가져오기
        String username = (String) session.getAttribute("username"); // 사용자 이름 가져오기
        String search = request.getParameter("search"); // 검색어 가져오기
        
        request.setAttribute("username", username);

        ArrayList<Ingredient> ingredientList = new ArrayList<>();

        // DB에서 재료 정보 가져오기
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
            throw new ServletException("DB 작업 중 오류 발생", e);
        }

        // JSP에 데이터 전달
        request.setAttribute("ingredientList", ingredientList);
        request.setAttribute("username", username); // 사용자 이름도 전달

        // JSP로 포워딩
        RequestDispatcher dispatcher = request.getRequestDispatcher("fridge.jsp");
        dispatcher.forward(request, response);
    }
}