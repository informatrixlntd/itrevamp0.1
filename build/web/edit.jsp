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

        <title>VievData!</title>
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
                    message = rs.getString("message"); //

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
                <!-- Form Header (optional) -->
                <h3 class="text-center mb-4" style="color: black;">Contact Us</h3>

                <!-- Form itself -->
                <div class="form">
                    <form method="post" action="updateservlet" onsubmit="return validateForm()">
                        <div data-mdb-input-init class="form-outline mb-4" >
                            <div data-mdb-input-init class="form-outline">
                                <input type="number" value="<%=id%>" name="id" id="form6Example2" readonly class="form-control" />
                                <label class="form-label" for="form6Example2">ID</label>
                            </div>
                        </div>
                        <div class="row mb-4">
                            <div class="col-md-6 col-12">
                                <div class="form-outline">
                                    <input type="text" id="form6Example1" class="form-control" value="<%=firstname%>" name="firstname" required />
                                    <label class="form-label" style="color: black" for="form6Example1">First Name</label>
                                    <small id="firstNameError" class="text-danger"></small>
                                </div>
                            </div>
                            <div class="col-md-6 col-12">
                                <div class="form-outline">
                                    <input type="text" id="form6Example2" class="form-control" value="<%=lastname%>" name="lastname" required />
                                    <label class="form-label" style="color: black" for="form6Example2">Last Name</label>
                                    <small id="lastNameError" class="text-danger"></small>
                                </div>
                            </div>
                        </div>

                        <div class="row mb-4">
                            <div class="col-md-6 col-12">
                                <div class="form-outline">
                                    <input type="email" id="form6Example3" class="form-control" value="<%=email%>" name="email" required />
                                    <label class="form-label" style="color: black" for="form6Example3">Email</label>
                                    <small id="emailError" class="text-danger"></small>
                                </div>
                            </div>
                            <div class="col-md-6 col-12">
                                <div class="form-outline">
                                    <input type="tel" id="form6Example6" class="form-control" value="<%=phone%>" name="phone" pattern="[0-9]{10}" required />
                                    <label style="color: black" class="form-label" for="form6Example6">Phone</label>
                                    <small id="phoneError" class="text-danger"></small>
                                </div>
                            </div>
                        </div>

                        <div class="form-outline mb-4">
                            <label class="form-label" style="color: black" for="form6Example7">Message</label>
                            <textarea class="form-control" id="form6Example7" rows="4" name="message" required><%= message%></textarea>
                            <small id="messageError" class="text-danger"></small>
                        </div>

                        <!-- Add reCAPTCHA here if needed -->

                        <button type="submit" class="btn btn-primary btn-block mb-4">SUBMIT</button>
                    </form>
                </div>
            </div>
        </div>

        <!-- JavaScript for validation -->
        <script>
            function validateForm() {
                let valid = true;

                // First Name validation
                const firstName = document.getElementById('form6Example1').value;
                const firstNameError = document.getElementById('firstNameError');
                if (firstName === "" || firstName.length < 2) {
                    firstNameError.innerHTML = "First name must be at least 2 characters.";
                    valid = false;
                } else {
                    firstNameError.innerHTML = "";
                }

                // Last Name validation
                const lastName = document.getElementById('form6Example2').value;
                const lastNameError = document.getElementById('lastNameError');
                if (lastName === "" ) {
                    lastNameError.innerHTML = "Last name must be at least 2 characters.";
                    valid = false;
                } else {
                    lastNameError.innerHTML = "";
                }

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

                // Phone number validation
                const phone = document.getElementById('form6Example6').value;
                const phoneError = document.getElementById('phoneError');
                const phonePattern = /^[0-9]{10}$/;
                if (!phone.match(phonePattern)) {
                    phoneError.innerHTML = "Phone number must be 10 digits.";
                    valid = false;
                } else {
                    phoneError.innerHTML = "";
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