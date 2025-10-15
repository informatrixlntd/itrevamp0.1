/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.informatrix.email;

import java.io.IOException;
import javax.mail.Transport;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletException;

/**
 *
 * @author prohit.bagde
 */
public class awsserviceemail extends HttpServlet {

    private final String user = "info@informatrixit.com"; // your email
    private final String pass = "IBGAcc@2025"; // use App Password, not normal password

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullname = request.getParameter("fullname");
        //System.out.println("fullname = " + fullname);
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        String cloud = request.getParameter("AWS");
        String message = request.getParameter("message");

        String subject = "New Contact Request from " + fullname;
        String content = "Name: " + fullname + "\n"
                + "mobile: " + mobile + "\n"
                + "Email: " + email + "\n"
                + "Cloud Provider: " + cloud + "\n"
                + "Message: \n" + message;

        // Set properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("https.protocols", "TLSv1.2");

        // Auth
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });
        //session.setDebug(true);

        try {
            // Compose message
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(user));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user)); // send to yourself
            mimeMessage.setSubject(subject);
            mimeMessage.setText(content);

            // Send message
            Transport.send(mimeMessage);

            // Response to user
            //response.setContentType("text/html");
            //response.getWriter().println("<h3>Email sent successfully!</h3>");
            response.sendRedirect("aws_billingsupport.html");

        } catch (Exception e) {
            throw new ServletException("Failed to send email", e);
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
