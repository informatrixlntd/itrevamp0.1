/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.informatrix.email;

import com.informatrix.cred.credentials;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author prohit.bagde
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 100)   // 100MB

public class EmailSendDirect extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get form parameters
        String email = request.getParameter("email");
        System.out.println("email = " + email);
        String subject = request.getParameter("subject");
        String messageContent = request.getParameter("message");
        credentials ob1 = new credentials();

        // Email credentials
        final String username = ob1.fromEmail; // your Gmail
        final String password = ob1.Emailpassword; // your Gmail password

        // Setup email properties
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

        // Create session with authentication
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a new email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);

            // Create message body part for text
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(messageContent); // Add the email body text

            // Create a multipart message to hold both the message and the attachment
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart); // Add text part

            // Handle file attachment (if any)
            Part filePart = request.getPart("attachment"); // Get the file part
            System.out.println("filePart = " + filePart);

            if (filePart != null && filePart.getSize() > 0) {

                // Debugging: Print file details
                System.out.println("Received file: " + filePart.getSubmittedFileName());
                System.out.println("File size: " + filePart.getSize() + " bytes");
                System.out.println("Content type: " + filePart.getContentType());

                MimeBodyPart attachmentBodyPart = new MimeBodyPart(); // Create a new attachment part
                String fileName = filePart.getSubmittedFileName(); // Get the uploaded file's name
                String contentType = filePart.getContentType(); // Get the content type (MIME type)

                // Attach file as a ByteArrayDataSource with its content type
                InputStream fileContent = filePart.getInputStream();
                DataSource source = new ByteArrayDataSource(fileContent, contentType);

                attachmentBodyPart.setDataHandler(new DataHandler(source));
                attachmentBodyPart.setFileName(fileName); // Set the file name

                multipart.addBodyPart(attachmentBodyPart); // Add attachment part to multipart
            } else {
                System.out.println("No attachment found or file size is 0.");
            }

            // Set the complete message parts (text + attachment)
            message.setContent(multipart);

            // Send the email
            Transport.send(message);

            // Redirect to a success page or send a success message back
            response.sendRedirect("admin.jsp"); // Change this to your success page

        } catch (Exception e) {
            throw new RuntimeException(e);
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
