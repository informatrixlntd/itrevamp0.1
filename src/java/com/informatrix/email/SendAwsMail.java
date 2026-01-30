/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.informatrix.email;

import com.informatrix.cred.credentials;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author prohit.bagde
 */
public class SendAwsMail extends HttpServlet {

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

        String fullname = request.getParameter("fullname");
        String companyname = request.getParameter("companyname");
        String number = request.getParameter("number");
        String email = request.getParameter("email");
        String message = request.getParameter("message");
        credentials cr = new credentials();
        
        String subject = "Thank You for Your Inquiry – We’re Here to Assist with Your IT Infra Needs";
        String body = "<html>"
                + "<body>"
                + "<p>Dear " + fullname + ",</p>"
                + "<p>Thank you for your inquiry. We've received your request and will be in touch shortly to discuss how we can assist you.</p>"
                + "<p>In the meantime, you can explore more about our services. If you have any immediate questions, feel free to reply to this email.</p>"
                + "<p>To know more, kindly visit:</p>"
                + "<p>Our Services:</p>"
                + "<ul>"
                + "<li><a href='https://informatrixit.com/infrastructure-management/it_infra_project_support.html'>IT Infrastructure Operation Support</a></li>"
                + "<li><a href='https://informatrixit.com/infrastructure-management/it_infra_operation_support.html'>IT Infrastructure Project Support</a></li>"
                + "<li><a href='https://informatrixit.com/infrastructure-management/AWS-blogs.html'>Case Studies</a></li>"
                + "<li><a href='https://informatrixit.com/infrastructure-management/AWS-Cloud-blog.html'>Blog</a></li>"
                + "</ul>"
                + "<p>We look forward to connecting with you soon. Your submitted details are as follows:</p>"
                + "<p>Full Name: " + fullname + "<br>"
                + "Company Name: " + companyname + "<br>"
                + "Contact: " + number + "<br>"
                + "Email: " + email + "<br>"
                + "Message: " + message + "</p>"
                + "<p>Best regards,</p>"
                + "<p>Chandan Singh - Account Manager<br>"
                + "Informatrix IT Solutions Pvt Ltd<br>"
                + "Contact: +91 8446185875<br>"
                + "Email: <a href='mailto:chandan.singh@informatrixit.com'>chandan.singh@informatrixit.com</a><br>"
                + "Web: <a href='https://informatrixit.com/infrastructure-management/index.html'>www.informatrixit.com</a></p>"
                + "</body>"
                + "</html>";
        
        // 2. Email setup
        
        final String to = email;
        
        // Set up email server properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.office365.com"); // Your SMTP server smtp.gmail.com
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port", "587"); // SMTP port
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("https.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.pop3s.ssl.protocols", "TLSv1.2");
        //props.put("mail.debug", "true");
        
        // Create a session with an Authenticator
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(cr.fromEmail, cr.Emailpassword); // Your email credentials
            }
        });

        try {
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress("info@informatrixit.com"));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(body, "text/html");

            Transport.send(mimeMessage);
            System.out.println("Email sent successfully!....to "+ to);
            response.sendRedirect("index.html");
            return;
        } catch (MessagingException e) {
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
