/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.informatrix.db;

import com.informatrix.cred.credentials;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author prohit.bagde
 */
public class formdata extends HttpServlet {
     

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs /formdata
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        credentials ob = new credentials();

        // Get form data from the request
        String firstname = request.getParameter("firstname").trim();
        String lastname = request.getParameter("lastname").trim();
        String email = request.getParameter("email").trim();
        String companyName = request.getParameter("companyName").trim();
        String customerAddress = request.getParameter("customerAddress").trim();
        String gstNo = request.getParameter("gstNo").trim();
        String mobileNo = request.getParameter("mobileNo").trim();
        String dueDateStr = request.getParameter("dueDate").trim();
        String createdDateStr = request.getParameter("currentDate").trim();
        String description = request.getParameter("description").trim();
        String total = request.getParameter("total").trim();

        Connection con = null;
        PreparedStatement ps = null;

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            con = DriverManager.getConnection(ob.getJdbcUrl(), ob.getUname(), ob.getPassword());

            // Prepare SQL INSERT statement, now including firstname, lastname, email
            String query = "INSERT INTO clients (firstname, lastname, email, company_name, customer_address, gst_no, mobile_no, due_date, created_date, description, total_amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(query);

            // Parse and convert dueDate and createdDate
            Date dueDate = Date.valueOf(dueDateStr);  // Assumes the format is yyyy-MM-dd
            Date createdDate = Date.valueOf(createdDateStr);  // Assumes the format is yyyy-MM-dd

            // Set values for the query
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, email);
            ps.setString(4, companyName);
            ps.setString(5, customerAddress);
            ps.setString(6, gstNo);
            ps.setString(7, mobileNo);
            ps.setDate(8, dueDate);
            ps.setDate(9, createdDate);
            ps.setString(10, description);
            ps.setBigDecimal(11, new BigDecimal(total));

            // Execute the query
            int result = ps.executeUpdate();

            if (result > 0) {
                out.println("<h3>Data saved successfully!</h3>");
                response.sendRedirect("admin.jsp");
            } else {
                out.println("<h3>Error saving data!</h3>");
            }

        } catch (SQLException | ClassNotFoundException e) {
           e.printStackTrace();
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        } finally {
            // Close resources
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Exception: "+e);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
