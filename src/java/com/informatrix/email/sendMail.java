/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.informatrix.email;

import com.informatrix.cred.credentials;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author prohit.bagde
 */
// sendmail
public class sendMail extends HttpServlet {

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

        // Collect form data
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String message = request.getParameter("message");

        // Validate input
        if (isInvalidInput(firstname, lastname, email, phone, message)) {
            response.getWriter().println("Invalid input. Please fill all required fields.");
            return;
        }

        // Instantiate credentials
        credentials credentials = new credentials();

        // Process database insertion and send email
        try (Connection con = createDatabaseConnection(credentials)) {
            if (insertEnquiry(con, firstname, lastname, email, phone, message)) {
                sendThankYouEmail(firstname, lastname, email, phone, message, credentials);
                response.sendRedirect("index.html");
                return;
            } else {
                response.getWriter().println("Failed to store data. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("An error occurred: " + e.getMessage());
        }

    }

    // Email validation regex pattern
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

//    private boolean isInvalidInput(String firstname, String lastname, String email, String phone, String message) {
//        return firstname == null || firstname.isEmpty()
//                || lastname == null || lastname.isEmpty()
//                || email == null || email.isEmpty()
//                || phone == null || phone.isEmpty()
//                || message == null || message.isEmpty();
//    }
    public static boolean isInvalidInput(String firstname, String lastname, String email, String phone, String message) {
        return isNullOrEmpty(firstname)
                || isNullOrEmpty(lastname)
                || isNullOrEmpty(email) || !isValidEmail(email)
                || isNullOrEmpty(phone)
                || isNullOrEmpty(message);
    }

    private static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private static boolean isValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    private Connection createDatabaseConnection(credentials credentials) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                credentials.getJdbcUrl(),
                credentials.getUname(),
                credentials.getPassword()
        );
    }

    private boolean insertEnquiry(Connection con, String firstname, String lastname, String email, String phone, String message)
            throws SQLException {
        String sql = "INSERT INTO enquiries (firstname, lastname, email, phone, message) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            pstmt.setString(3, email);
            pstmt.setString(4, phone);
            pstmt.setString(5, message);
            return pstmt.executeUpdate() > 0;
        }
    }

    private void sendThankYouEmail(String firstname, String lastname, String email, String phone, String message, credentials credentials) {
        String subject = "Thank You for Your Inquiry – We’re Here to Assist with Your IT Infra Needs";//Thank You for Your Inquiry – Informatrix IT Solutions
        String body = generateEmailBody(firstname, lastname, phone, email, message);

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(credentials.fromEmail, credentials.Emailpassword);
            }
        });

        try {
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(credentials.fromEmail));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(body, "text/html");
            Transport.send(mimeMessage);
            System.out.println("Email sent successfully to: " + email);
            return;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String generateEmailBody(String firstname, String lastname, String phone, String email, String message) {
        return "<html>"
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
