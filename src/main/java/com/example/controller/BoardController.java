package com.example.controller;

import com.example.model.BoardPost;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/BoardController")
public class BoardController extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/fridge_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "0000";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BoardPost> posts = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM board ORDER BY created_at DESC";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        BoardPost post = new BoardPost(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getTimestamp("created_at")
                        );
                        posts.add(post);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServletException("DB 작업 중 오류 발생", e);
        }

        request.setAttribute("posts", posts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("board.jsp");
        dispatcher.forward(request, response);
    }
}
