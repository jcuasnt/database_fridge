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

@WebServlet("/ViewPostController")
public class ViewPostController extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/fridge_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "0000";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int postId = Integer.parseInt(request.getParameter("id"));

        BoardPost post = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM board WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, postId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        post = new BoardPost(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getTimestamp("created_at")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServletException("DB 작업 중 오류 발생", e);
        }

        if (post != null) {
            request.setAttribute("post", post);
            RequestDispatcher dispatcher = request.getRequestDispatcher("viewPost.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("BoardController");
        }
    }
}
