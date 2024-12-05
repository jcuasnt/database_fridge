package com.example.controller;

import com.example.dao.UserDAO;
import com.example.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/UserController")
public class UserController extends HttpServlet {
    private Connection conn;

    @Override
    public void init() throws ServletException {
        try {
        	
        	// JDBC ����̹� �ε�
            Class.forName("com.mysql.cj.jdbc.Driver");
        	
            String dbURL = "jdbc:mysql://localhost:3306/fridge_management";;
            String dbUser = "root";
            String dbPassword = "0000";

            conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
        } catch (ClassNotFoundException e) {
            throw new ServletException("JDBC ����̹��� �ε��� �� �����ϴ�.", e);
        } catch (SQLException e) {
            throw new ServletException("DB ���ῡ �����߽��ϴ�.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        UserDAO userDAO = new UserDAO(conn);

        if ("register".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");

            User user = new User(username, password, email);
            if (userDAO.registerUser(user)) {
                response.sendRedirect("login.jsp");
            } else {
                response.getWriter().println("ȸ������ ����!");
            }
        } else if ("login".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            User user = userDAO.loginUser(username, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getUserId()); // ����� ID ����
                session.setAttribute("username", user.getUsername()); // ����� �̸� ����
                response.sendRedirect("fridge.jsp");
            } else {
                response.getWriter().println("�α��� ����!");
            }
        }
    }

    @Override
    public void destroy() {
        try {
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
