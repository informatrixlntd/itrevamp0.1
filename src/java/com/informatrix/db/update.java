/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.informatrix.db;

import com.informatrix.cred.credentials;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author prohit.bagde
 */
public class update extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        credentials cr = new credentials();
        String id = request.getParameter("id");
        String firstname = request.getParameter("firstname").trim();
        String lastname = request.getParameter("lastname").trim();
        String email = request.getParameter("email").trim();
        String company_name = request.getParameter("companyName").trim();
        String customer_address = request.getParameter("customerAddress").trim();
        String gst_no = request.getParameter("gstNo").trim();
        String mobile_no = request.getParameter("mobileNo").trim();
        String due_date = request.getParameter("dueDate").trim();
        String created_date = request.getParameter("currentDate").trim();
        String description = request.getParameter("description").trim();
        String total_amount = request.getParameter("total").trim();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(cr.getJdbcUrl(), cr.getUname(), cr.getPassword());
            String sql = "update clients set firstname='" + firstname + "',lastname='" + lastname + "',email='"
                    + email + "',company_name='" + company_name + "',customer_address='" + customer_address + "',gst_no='" + gst_no + "',mobile_no='" + mobile_no + "',due_date='" + due_date + "',created_date='" + created_date + "',description='" + description + "',total_amount='" + total_amount + "' where id='" + id + "'";
            PreparedStatement pst = con.prepareStatement(sql);
            int i = pst.executeUpdate();
            if (i > 0) {

                //add email here
                //session.setAttribute("sesupdt", "Update Successfully!");
                response.sendRedirect("maintable.jsp");
            }
        } catch (Exception e) {
            System.out.println("Exception" + e);
            e.printStackTrace();
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
