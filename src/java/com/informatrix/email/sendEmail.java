/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.informatrix.email;

import com.informatrix.cred.credentials;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

/**
 *
 * @author prohit.bagde
 */
public class sendEmail extends HttpServlet {

    
    //sendemail
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form parameters
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String message = request.getParameter("message");

        // Instantiate credentials object
        credentials ob1 = new credentials();

        Connection con = null;
        try {
            // Load JDBC driver (optional depending on your setup)
            Class.forName("com.mysql.cj.jdbc.Driver");

            //Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(ob1.getJdbcUrl(), ob1.getUname(), ob1.getPassword());

//             // Check if the email is already processed
//            String checkSql = "SELECT processed FROM enquiries WHERE email = ?";
//            PreparedStatement checkStmt = con.prepareStatement(checkSql);
//            checkStmt.setString(1, email);
//            ResultSet rs = checkStmt.executeQuery();
//
//            if (rs.next() && rs.getBoolean("processed")) {
//                System.out.println("Email already sent to: " + email);
//                response.getWriter().println("Email already sent.");
//                return;
//            }
            // Prepare SQL query to insert form data into the database
            String sql = "INSERT INTO enquiries (firstname, lastname, email, phone, message) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            // Set parameters for the query
            pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            pstmt.setString(3, email);
            pstmt.setString(4, phone);
            pstmt.setString(5, message);

            // Execute the query
            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Data inserted into the database successfully!");
            }

            //sendEmailWhenUserIsCreateAndEnroll(firstname, lastname, email, phone, message, ob1);
//            // Mark the enquiry as processed
//            String updateSql = "UPDATE enquiries SET processed = true WHERE email = ?";
//            PreparedStatement updateStmt = con.prepareStatement(updateSql);
//            updateStmt.setString(1, email);
//            updateStmt.executeUpdate();
            // Redirect to success page
            response.sendRedirect("index.html");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Failed to store data in the database. Reason: " + e);
        } finally {
            // Close database connection
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

//        String to = email; // Your email address
//        String subject = "Informatrix IT - New Enquiry " + firstname + " " + lastname;
//        System.out.println("subject = " + subject);
//        String body = "Message From InformatrixIt \n" + "Name: " + firstname + " " + lastname + "\nEmail: " + email + "\nPhone: " + phone + "\n\nMessage:\n" + message;
//
//        // Set up email server properties
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.office365.com"); // Your SMTP server smtp.gmail.com
//        props.put("mail.smtp.socketFactory.port", "465");
//        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtp.port", "587"); // SMTP port
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("https.protocols", "TLSv1.2");
//        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        props.put("mail.pop3s.ssl.protocols", "TLSv1.2");
//        props.put("mail.debug", "true");
//
//        // Create a session with an Authenticator
//        Session session = Session.getInstance(props, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(ob1.fromEmail, ob1.Emailpassword); // Your email credentials  "prohit.bagde@informatrixit.com", "PB@180898"
//            }
//        });
//
//        try {
//            Message mimeMessage = new MimeMessage(session);
//            mimeMessage.setFrom(new InternetAddress(ob1.fromEmail));
//            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//            mimeMessage.setSubject(subject);
//            mimeMessage.setText(body);
//
//           Transport.send(mimeMessage);
//           
//           
//            
//            System.out.println("ob1 = " + ob1);
//            response.sendRedirect("index.html");
//
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            response.getWriter().println("Failed to send email. Reason: " + e);
//        }
    }

    public static void sendEmailWhenUserIsCreateAndEnroll(String firstname, String lastname, String email, String phone, String message, credentials cr) {

        String to = email; // Your email address
        //System.out.println("cr.fromEmail, cr.Emailpassword = " + cr.fromEmail + cr.Emailpassword);
        String subject = "Thank You for Your Inquiry – We’re Here to Assist with Your IT Infra Needs";
        String body = "<html>"
                + "<body>"
                + "<p>Dear " + firstname + ",</p>"
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
                + "<p>First Name: " + firstname + "<br>"
                + "Last Name: " + lastname + "<br>"
                + "Contact: " + phone + "<br>"
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

//        System.out.println("firstname = " + firstname);
//        System.out.println("lastname = " + lastname);
//        System.out.println("phone = " + phone);
//        System.out.println("email = " + email);

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
        props.put("mail.debug", "true");

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
