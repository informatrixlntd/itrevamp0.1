/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.informatrix.db;

import com.informatrix.cred.credentials;
import java.io.IOException;
import java.io.PrintWriter;
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
public class updateservlet extends HttpServlet {

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
        // Retrieve parameters from the form
        String id = request.getParameter("id");
        String firstname = request.getParameter("firstname").trim();
        String lastname = request.getParameter("lastname").trim();
        String email = request.getParameter("email").trim();
        String phone = request.getParameter("phone").trim();
        String message = request.getParameter("message").trim();
        
        try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(cr.getJdbcUrl(), cr.getUname(), cr.getPassword());
		String sql = "update enquiries set firstname='" + firstname + "',lastname='" + lastname + "',email='"
		+ email + "',phone='" + phone + "',message='" + message + "' where id='" + id + "'";
		 PreparedStatement pst = con.prepareStatement(sql);
		int i = pst.executeUpdate();
		if (i > 0) {
                    
                    //add email here
                    
			//session.setAttribute("sesupdt", "Update Successfully!");
			response.sendRedirect("admin.jsp");
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
