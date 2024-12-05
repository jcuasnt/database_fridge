package com.example.controller;

import com.example.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/RecipeController")
public class RecipeController extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/fridge_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "0000";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        List<Recipe> matchedRecipes = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // ����� ����� ��� ������� ������ ��Ī
        	String recipeSql =
        		    "SELECT r.id, r.name, r.instructions " +
        		    "FROM recipes r " +
        		    "JOIN recipe_ingredients ri ON r.id = ri.recipe_id " +
        		    "WHERE ri.ingredient_name IN (SELECT name FROM ingredients WHERE user_id = ?) " +
        		    "GROUP BY r.id " +
        		    "HAVING COUNT(DISTINCT ri.ingredient_name) = " +
        		    "(SELECT COUNT(*) FROM recipe_ingredients WHERE recipe_id = r.id)";

            try (PreparedStatement stmt = conn.prepareStatement(recipeSql)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Recipe recipe = new Recipe(
                            rs.getInt("id"),
                            rs.getString("name"),
                            null, // ��� ����Ʈ�� �ʿ� �����Ƿ� null
                            rs.getString("instructions")
                        );
                        matchedRecipes.add(recipe);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServletException("DB �۾� �� ���� �߻�", e);
        }

        request.setAttribute("matchedRecipes", matchedRecipes);
        request.getRequestDispatcher("recipe.jsp").forward(request, response);
    }
}