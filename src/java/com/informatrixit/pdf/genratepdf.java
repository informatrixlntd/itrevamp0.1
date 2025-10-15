/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.informatrixit.pdf;

import com.informatrix.cred.credentials;
import com.itextpdf.text.BaseColor;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

/**
 *
 * @author prohit.bagde
 */
public class genratepdf extends HttpServlet {

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

        String action = request.getParameter("action"); // "Download PDF" or "Send Email"
        if ("Download PDF".equals(action)) {
            generatePDF(request, response);  // Method to handle PDF generation
        } else if ("Send Email".equals(action)) {
            sendEmail(request, response);    // Method to handle sending email
        }

    }

    private void generatePDF(HttpServletRequest request, HttpServletResponse response) throws IOException {
        credentials ob = new credentials();
        String id = request.getParameter("id");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String company_name = request.getParameter("companyName");
        String customer_address = request.getParameter("customerAddress");
        String gst_no = request.getParameter("gstNo");
        String phone = request.getParameter("mobileNo");
        String due_date = request.getParameter("dueDate");
        String created_date = request.getParameter("currentDate");
        String message = request.getParameter("description");
        String total_amount = request.getParameter("total");

        // Create a Random object
        Random random = new Random();
        // Generate a random number from 0001 to 9999
        int randomNumber = random.nextInt(9999) + 1; // Add 1 to include 0001
        // Format the number to have leading zeros if necessary
        String formattedNumber = String.format("%04d", randomNumber);
        // Print the random number
        //System.out.println("Random number: " + formattedNumber);

        // Set the content type to application/pdf
        response.setContentType("application/pdf");

        // Set the content disposition header so the browser knows it's an attachment
        response.setHeader("Content-Disposition", "attachment; filename=\"sample.pdf\"");

        // Create a new document
        Document document = new Document();

        try {

            // Retrieve form data
            String invoiceNumber = formattedNumber;

            // Create a PDF writer instance that writes to the response output stream
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

            // Open the document
            document.open();

            //Statr genrating pdf
//===================================LOGO====================================            
            // Add logo image to the right corner
            Image logo = Image.getInstance("https://informatrixit.com/infrastructure-management/assets/img/logo.png"); // Change "logo.png" to your logo file path
            logo.setAlignment(Image.ALIGN_RIGHT);
            float maxWidth = 150; // Maximum width of the logo
            float maxHeight = 150 * (logo.getHeight() / logo.getWidth()); // Calculate height to maintain aspect ratio
            logo.scaleToFit(maxWidth, maxHeight);
            document.add(logo);

//===========================Heading==========================================            
            // Create a big font for the heading
            Font headingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLUE);
            Font Font = FontFactory.getFont(FontFactory.HELVETICA, 15);
            Font Fontsm = FontFactory.getFont(FontFactory.HELVETICA, 11);
            Font Fontsmb = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
            Font Fontxsm = FontFactory.getFont(FontFactory.HELVETICA, 9);
            // Create a font for the paragraphs
            Font paraFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
//=======================================================================================               
            // Write heading
            Paragraph heading = new Paragraph("INVOICE", headingFont);
            heading.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(heading);
            // Add a line break
            document.add(new Paragraph("\n "));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

//================================DATE==========================================
            // Add current date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String currentDate = dateFormat.format(new java.util.Date());
            Paragraph dateParagraph = new Paragraph(currentDate, Fontsm);
            //dateParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            // document.add(dateParagraph);
//========================================Recept No================================================
            Paragraph receptno = new Paragraph("IMLNTD" + invoiceNumber, Fontsm);
            // receptno.setAlignment(Paragraph.ALIGN_CENTER);
            // document.add(receptno);

//========================================================================================
            Paragraph address = new Paragraph("54,Central Bazar Road, Bajaj Nagar\n" + " Nagpur, Maharashtra 440010", Fontsm);
            address.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(address);

//========================================================================================
            Paragraph gstno = new Paragraph("GST NO: 27AADCI1880Q1Z0", Fontsm);
            Paragraph gstmo = new Paragraph("+91 7387874545", Fontsm);
            gstno.setAlignment(Paragraph.ALIGN_RIGHT);
            gstmo.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(gstno);
            document.add(gstmo);
//========================================================================================
//==================== RECEIPT NO ===============================================  
            Paragraph recept = new Paragraph("INVOICE TO", Fontsmb);
            Paragraph name = new Paragraph(firstname + " " + lastname, Fontsm);
            Paragraph cusaddress = new Paragraph(customer_address, Fontsm);
            Paragraph custmob = new Paragraph( phone, Fontsm);
            Paragraph custgst = new Paragraph("GST NO:" + gst_no, Fontsm);
            receptno.setAlignment(Paragraph.ALIGN_LEFT);
            recept.setAlignment(Paragraph.ALIGN_LEFT);
            dateParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(recept);
            document.add(name);
            document.add(cusaddress);
            document.add(custgst);
            document.add(custmob);
            document.add(new Paragraph("\n"));
//==================================Contain============================================= 
            //=============================Small Table====================================================
            PdfPTable table1 = new PdfPTable(2); // 3 columns

            // Add cells to the table
            PdfPCell cell1 = new PdfPCell(new Paragraph("PAYMENT TERMS", Fontsmb));
            PdfPCell cell2 = new PdfPCell(new Paragraph("DUE DATE", Fontsmb));

            // Customize cell appearance if needed
            cell1.setBorderWidth(1);
            cell2.setBorderWidth(1);

            // Adding cells to the table
            cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);

            // Add cells to the table
            table1.addCell(cell1);
            table1.addCell(cell2);

            // Add table rows
            table1.addCell("Due on Receipt");
            table1.addCell(due_date);//subtotal

            // Adjusting column widths
            float[] columnWidths = {1.5f, 0.5f}; // Adjust the widths as needed
            table1.setWidths(columnWidths);

            // **Add the table to the document
            document.add(table1);

            document.add(new Paragraph("\n\n\n"));

//========================================================================================
            //=============================Table====================================================
            PdfPTable table = new PdfPTable(3); // 3 columns

            // Add cells to the table
            PdfPCell cell4 = new PdfPCell(new Paragraph("SR NO", Fontsmb));
            PdfPCell cell5 = new PdfPCell(new Paragraph("LEARNING PROGRAM NAME", Fontsmb));
            PdfPCell cell6 = new PdfPCell(new Paragraph("AMOUNT(INR)", Fontsmb));

            // Customize cell appearance if needed
            cell4.setBorderWidth(1);
            cell5.setBorderWidth(1);
            cell6.setBorderWidth(1);

            // Adding cells to the table
            cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell6.setBackgroundColor(BaseColor.LIGHT_GRAY);

            // Add cells to the table
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);

            // Add table rows
            table.addCell("1.");
            table.addCell("");//subtotal
            table.addCell("" + "/-");
//=============================ROW2===================================================
            table.addCell("");
            table.addCell("Subtotal");
            table.addCell("" + "/-");
//=============================ROW2===================================================
            table.addCell("");
            table.addCell("S GST - 9%");
            table.addCell("" + "/-");
//=============================ROW2===================================================
            table.addCell("");
            table.addCell("C GST - 9%");
            table.addCell("" + "/-");
//=============================ROW2===================================================
            table.addCell("");
            table.addCell("Total");
            table.addCell("" + "/-");

            // Adjusting column widths
            float[] columnWidths1 = {0.5f, 2.5f, 1f}; // Adjust the widths as needed
            table.setWidths(columnWidths1);

            // **Add the table to the document
            document.add(table);

            document.add(new Paragraph("\n\n"));

//========================================================================================
            //==================== RECEIPT NO ===============================================  
            Paragraph recept1 = new Paragraph("Payment To: ", Fontsmb);
            Paragraph name1 = new Paragraph("Account Name: " + company_name, Fontsm);
            Paragraph cusaddress1 = new Paragraph("Bank: Bank of Baroda ", Fontsm);
            Paragraph custmob1 = new Paragraph("Account Number: 97770200000209 ", Fontsm);
            Paragraph custgst1 = new Paragraph("IFSC Code: BARB0AMBAZA", Fontsm);
            //receptno.setAlignment(Paragraph.ALIGN_LEFT);
            recept1.setAlignment(Paragraph.ALIGN_LEFT);
            //dateParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(recept1);
            document.add(name1);
            document.add(cusaddress1);
            document.add(custgst1);
            document.add(custmob1);
            document.add(new Paragraph("\n"));

//========================================================================================
            Paragraph Term = new Paragraph("Payment Terms: ", Fontsmb);
            Paragraph par = new Paragraph("Invoice amount to be paid as per terms and conditions mentioned on the Service Agreement & SOW.", Fontsm);
            document.add(Term);
            document.add(par);

//========================================================================================
            // Create three paragraphs
            Paragraph para1 = new Paragraph("Date", Fontsmb);
            Paragraph para2 = new Paragraph("INVOICE NO", Fontsmb);
            Paragraph para3 = new Paragraph("INFORMATRIX IT SOLUTION PVT LTD ", Fontsmb);

            // Get the width of the document page
            float pageWidth = document.getPageSize().getWidth();
            float leftMargin = document.leftMargin();
            float rightMargin = document.rightMargin();

            // Calculate the available width after margins
            float availableWidth = pageWidth - leftMargin - rightMargin;

            // Estimate paragraph widths (adjust based on content length)
            float paraWidth = availableWidth / 3;
            // Define the x-positions for each paragraph
            float x1 = leftMargin;
            float x2 = x1 + paraWidth;
            float x3 = x2 + paraWidth;
            // Vertical position (height from the bottom of the page)
            float y = 680;
            float z = 650;

            // Add the paragraphs at the specified positions
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, para1, x1, y, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, dateParagraph, x1, z, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, para2, x2, y, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, receptno, x2, z, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, para3, x3, y, 0);

            // Add a line break
            document.add(new Paragraph("\n \n \n"));

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            // Add a line break
            document.add(new Paragraph("\n"));
            document.close();

        } catch (DocumentException e) {
            throw new IOException(e.getMessage());
        } finally {
            // Close the document
            document.close();
        }
    }

    // Method for sending email
    private void sendEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        credentials ob = new credentials();
        String id = request.getParameter("id");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String company_name = request.getParameter("companyName");
        String customer_address = request.getParameter("customerAddress");
        String gst_no = request.getParameter("gstNo");
        String phone = request.getParameter("mobileNo");
        String due_date = request.getParameter("dueDate");
        String created_date = request.getParameter("currentDate");
        String message = request.getParameter("description");
        String total_amount = request.getParameter("total");

        // Generate a random invoice number
        Random random = new Random();
        int randomNumber = random.nextInt(9999) + 1;
        String invoiceNumber = String.format("%04d", randomNumber);

        // Generate the PDF in memory using ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            // Create a PDF writer instance that writes to the ByteArrayOutputStream
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            // Open the document
            document.open();

            //Statr genrating pdf
//===================================LOGO====================================            
            // Add logo image to the right corner
            Image logo = Image.getInstance("https://informatrixit.com/infrastructure-management/assets/img/logo.png"); // Change "logo.png" to your logo file path
            logo.setAlignment(Image.ALIGN_RIGHT);
            float maxWidth = 150; // Maximum width of the logo
            float maxHeight = 150 * (logo.getHeight() / logo.getWidth()); // Calculate height to maintain aspect ratio
            logo.scaleToFit(maxWidth, maxHeight);
            document.add(logo);

//===========================Heading==========================================            
            // Create a big font for the heading
            Font headingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLUE);
            Font Font = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 15);
            Font Fontsm = FontFactory.getFont(FontFactory.HELVETICA, 18);
            Font Fontsmb = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            Font Fontxsm = FontFactory.getFont(FontFactory.HELVETICA, 9);
            // Create a font for the paragraphs
            Font paraFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
//=======================================================================================               
            // Write heading
            Paragraph heading = new Paragraph("INVOICE", headingFont);
            heading.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(heading);
            // Add a line break
            document.add(new Paragraph("\n "));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

//================================DATE==========================================
            // Add current date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String currentDate = dateFormat.format(new java.util.Date());
            Paragraph dateParagraph = new Paragraph(currentDate, Fontsm);
            //dateParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            // document.add(dateParagraph);
//========================================Recept No================================================
            Paragraph receptno = new Paragraph("IMLNTD" + invoiceNumber, Fontsm);
            // receptno.setAlignment(Paragraph.ALIGN_CENTER);
            // document.add(receptno);

//========================================================================================
            Paragraph address = new Paragraph("54, Central Bazar Road, Bajaj Nagar \n" + " Nagpur, Maharashtra 440010", paraFont);
            address.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(address);

//========================================================================================
            Paragraph gstno = new Paragraph("GST NO: 27AADCI1880Q1Z0", Fontsmb);
            Paragraph gstmo = new Paragraph("+91 7387874545", paraFont);
            gstno.setAlignment(Paragraph.ALIGN_RIGHT);
            gstmo.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(gstno);
            document.add(gstmo);
//========================================================================================
//==================== RECEIPT NO ===============================================  
            Paragraph recept = new Paragraph("INVOICE TO", Fontsm);
            Paragraph name = new Paragraph(firstname + " " + lastname, Fontsmb);
            Paragraph cusaddress = new Paragraph(customer_address, Fontsmb);
            Paragraph custmob = new Paragraph("+91" + phone);
            Paragraph custgst = new Paragraph("GST NO:" + gst_no, Fontsmb);
            receptno.setAlignment(Paragraph.ALIGN_LEFT);
            recept.setAlignment(Paragraph.ALIGN_LEFT);
            dateParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(recept);
            document.add(name);
            document.add(cusaddress);
            document.add(custgst);
            document.add(custmob);
            document.add(new Paragraph("\n"));
//==================================Contain============================================= 
            //=============================Small Table====================================================
            PdfPTable table1 = new PdfPTable(2); // 3 columns

            // Add cells to the table
            PdfPCell cell1 = new PdfPCell(new Paragraph("PAYMENT TERMS", Fontsmb));
            PdfPCell cell2 = new PdfPCell(new Paragraph("DUE DATE", Fontsmb));

            // Customize cell appearance if needed
            cell1.setBorderWidth(1);
            cell2.setBorderWidth(1);

            // Adding cells to the table
            cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);

            // Add cells to the table
            table1.addCell(cell1);
            table1.addCell(cell2);

            // Add table rows
            table1.addCell("Due on Receipt");
            table1.addCell(due_date);//subtotal

            // Adjusting column widths
            float[] columnWidths = {1.5f, 0.5f}; // Adjust the widths as needed
            table1.setWidths(columnWidths);

            // **Add the table to the document
            document.add(table1);

            document.add(new Paragraph("\n\n\n"));

//========================================================================================
            //=============================Table====================================================
            PdfPTable table = new PdfPTable(3); // 3 columns

            // Add cells to the table
            PdfPCell cell4 = new PdfPCell(new Paragraph("SR NO", Fontsmb));
            PdfPCell cell5 = new PdfPCell(new Paragraph("LEARNING PROGRAM NAME", Fontsmb));
            PdfPCell cell6 = new PdfPCell(new Paragraph("AMOUNT(INR)", Fontsmb));

            // Customize cell appearance if needed
            cell4.setBorderWidth(1);
            cell5.setBorderWidth(1);
            cell6.setBorderWidth(1);

            // Adding cells to the table
            cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell6.setBackgroundColor(BaseColor.LIGHT_GRAY);

            // Add cells to the table
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);

            // Add table rows
            table.addCell("1.");
            table.addCell("");//subtotal
            table.addCell("" + "/-");
//=============================ROW2===================================================
            table.addCell("");
            table.addCell("Subtotal");
            table.addCell("" + "/-");
//=============================ROW2===================================================
            table.addCell("");
            table.addCell("S GST - 9%");
            table.addCell("" + "/-");
//=============================ROW2===================================================
            table.addCell("");
            table.addCell("C GST - 9%");
            table.addCell("" + "/-");
//=============================ROW2===================================================
            table.addCell("");
            table.addCell("Total");
            table.addCell("" + "/-");

            // Adjusting column widths
            float[] columnWidths1 = {0.5f, 2.5f, 1f}; // Adjust the widths as needed
            table.setWidths(columnWidths1);

            // **Add the table to the document
            document.add(table);

            document.add(new Paragraph("\n\n\n"));

//========================================================================================
            //==================== RECEIPT NO ===============================================  
            Paragraph recept1 = new Paragraph("Payment To: ", Fontsmb);
            Paragraph name1 = new Paragraph("Account Name: " + company_name, Fontsmb);
            Paragraph cusaddress1 = new Paragraph("Bank: Bank of Baroda ", Fontsmb);
            Paragraph custmob1 = new Paragraph("Account Number: 97770200000209 ");
            Paragraph custgst1 = new Paragraph("IFSC Code: BARB0AMBAZA", Fontsmb);
            //receptno.setAlignment(Paragraph.ALIGN_LEFT);
            recept1.setAlignment(Paragraph.ALIGN_LEFT);
            //dateParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(recept1);
            document.add(name1);
            document.add(cusaddress1);
            document.add(custgst1);
            document.add(custmob1);
            document.add(new Paragraph("\n"));

//========================================================================================
            Paragraph Term = new Paragraph("Payment Terms: ", Fontsmb);
            Paragraph par = new Paragraph("Invoice amount to be paid as per terms and conditions mentioned on the Service Agreement & SOW.", Fontsmb);
            document.add(Term);
            document.add(par);

//========================================================================================
            // Create three paragraphs
            Paragraph parax = new Paragraph("DATE", Font);
            Paragraph para2 = new Paragraph("INVOICE NO", Font);
            Paragraph para3 = new Paragraph("INFORMATRIX IT SOLUTION PVT LTD ", Font);

            // Get the width of the document page
            float pageWidth = document.getPageSize().getWidth();
            float leftMargin = document.leftMargin();
            float rightMargin = document.rightMargin();

            // Calculate the available width after margins
            float availableWidth = pageWidth - leftMargin - rightMargin;

            // Estimate paragraph widths (adjust based on content length)
            float paraWidth = availableWidth / 3;
            // Define the x-positions for each paragraph
            float x1 = leftMargin;
            float x2 = x1 + paraWidth;
            float x3 = x2 + paraWidth;
            // Vertical position (height from the bottom of the page)
            float y = 680;
            float z = 650;

            // Add the paragraphs at the specified positions
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, parax, x1, y, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, dateParagraph, x1, z, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, para2, x2, y, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, receptno, x2, z, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, para3, x3, y, 0);

            // Add a line break
            document.add(new Paragraph("\n \n \n"));

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            // Add a line break
            document.add(new Paragraph("\n"));
            document.close(); // Close the PDF document
        } catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }

        // Now proceed with sending the email
        String subject = "Invoice: " + invoiceNumber;
        String bodyMessage = "Dear " + firstname + ",\n\nPlease find attached your invoice.\n\nRegards,\n" + company_name;
        credentials cr = new credentials();
        String host = "smtp.gmail.com";
        final String user = cr.fromEmail;
        final String password = cr.Emailpassword;

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

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            MimeMessage message1 = new MimeMessage(session);
            message1.setFrom(new InternetAddress(user));
            message1.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message1.setSubject(subject);

            // Create the email body part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(bodyMessage);

            // Create another part for the attachment
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.setDataHandler(new javax.activation.DataHandler(new ByteArrayDataSource(baos.toByteArray(), "application/pdf")));
            attachmentBodyPart.setFileName("Invoice.pdf");

            // Create a multipart message
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            // Set the content
            message1.setContent(multipart);

            // Send the email
            Transport.send(message1);
            
            response.sendRedirect("maintable.jsp");

            System.out.println("Email sent successfully with PDF attachment.");
        } catch (Exception e) {
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
