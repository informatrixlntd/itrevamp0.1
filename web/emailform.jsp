<%-- 
    Document   : index
    Created on : 6 Apr, 2024, 9:59:33 AM
    Author     : Prohit
--%>

<%@page import="com.informatrix.cred.credentials"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

        <title>Email!</title>
        <style>
            /* Custom styles for the form */
            .form-container {
                max-width: 500px;
                margin: auto;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
        </style>

    </head>
    <body>

        <%
            credentials ob = new credentials();
            String id = request.getParameter("id");
            String firstname = "";
            String lastname = "";
            String email = "";
            String phone = "";
            String message = "";

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(ob.getJdbcUrl(), ob.getUname(), ob.getPassword());
                String sql = "select *  from enquiries where id= '" + id + "'";
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    firstname = rs.getString("firstname");
                    lastname = rs.getString("lastname");
                    email = rs.getString("email");
                    phone = rs.getString("phone");
                    message = rs.getString("message");

                }

            } catch (Exception e) {
                out.println("Exception" + e);
                e.printStackTrace();
            }

        %>

        <!-- Container for the form -->
        <!-- Container for the form -->
        <div class="d-flex justify-content-center align-items-center vh-100">
            <div class="testimonial-item shadow-lg p-5" style="border: 2px solid #ddd; border-radius: 10px; background-color: #fff; max-width: 700px; width: 100%;">
                <!-- Form Header -->
                <h3 class="text-center mb-4" style="color: black;">Contact Us</h3>

                <!-- Form -->
                <div class="form">
                    <form method="post" action="emailsenddirect" onsubmit="return validateForm()" enctype="multipart/form-data">
                        <!-- ID (Read-only) -->
                        <div class="form-outline mb-4">
                            <input type="number" value="<%=id%>" name="id" id="form6Example2" readonly class="form-control" />
                            <label class="form-label" for="form6Example2">ID</label>
                        </div>

                        <!-- Email (Read-only) -->
                        <div class="form-outline mb-4">
                            <input type="email" id="form6Example3" class="form-control" value="<%=email%>" readonly name="email" required />
                            <label class="form-label" style="color: black" for="form6Example3">Email</label>
                            <small id="emailError" class="text-danger"></small>
                        </div>
                            
                            <!-- Email (Read-only) -->
                        <div class="form-outline mb-4">
                            <input type="text" id="form6Example3" class="form-control"  name="subject" required />
                            <label class="form-label" style="color: black" for="form6Example3">Subject</label>
                            <small id="emailError" class="text-danger"></small>
                        </div>

                        <!-- Message -->
                        <div class="form-outline mb-4">
                            <textarea class="form-control" id="form6Example7" rows="4" name="message" required><%= message%></textarea>
                            <label class="form-label" style="color: black" for="form6Example7">Message</label>
                            <small id="messageError" class="text-danger"></small>
                        </div>

                        <!-- File upload for attachment -->
                        <div class="form-outline mb-4">
                            <input type="file" name="attachment" class="form-control">
                            <label class="form-label" style="color: black">Attachment (optional)</label>
                        </div>

                        <!-- Submit Button -->
                        <button type="submit" class="btn btn-primary btn-block mb-4">SEND EMAIL</button>
                    </form>
                </div>
            </div>
        </div>

        <!-- JavaScript for validation -->
        <script>
            function validateForm() {
                let valid = true;

                // Email validation
                const email = document.getElementById('form6Example3').value;
                const emailError = document.getElementById('emailError');
                const emailPattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;
                if (!email.match(emailPattern)) {
                    emailError.innerHTML = "Please enter a valid email address.";
                    valid = false;
                } else {
                    emailError.innerHTML = "";
                }

                // Message validation
                const message = document.getElementById('form6Example7').value;
                const messageError = document.getElementById('messageError');
                if (message === "" || message.length < 10) {
                    messageError.innerHTML = "Message must be at least 10 characters long.";
                    valid = false;
                } else {
                    messageError.innerHTML = "";
                }

                return valid;
            }
        </script>


        <!-- Bootstrap CSS for styling -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">


    </body>
</html>