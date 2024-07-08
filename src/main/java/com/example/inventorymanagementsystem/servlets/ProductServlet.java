package com.example.inventorymanagementsystem.servlets;

import com.example.inventorymanagementsystem.DBConnection;
import com.example.inventorymanagementsystem.Product;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<Product> products = new ArrayList<>();

        // Your database connection and query logic
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getCon();
            String query = "SELECT * FROM products";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("Name"));
                product.setDescription(rs.getString("Description"));
                product.setQuantity(rs.getString("Quantity"));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving products", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Convert products list to JSON
        JSONArray jsonArray = new JSONArray();
        for (Product product : products) {
            JSONObject jsonProduct = new JSONObject();
            jsonProduct.put("id", product.getId());
            jsonProduct.put("name", product.getName());
            jsonProduct.put("description", product.getDescription());
            jsonProduct.put("quantity", product.getQuantity());
            jsonArray.put(jsonProduct);
        }

        // Set content type and write JSON response
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(jsonArray);
        out.flush();
    }
    }

