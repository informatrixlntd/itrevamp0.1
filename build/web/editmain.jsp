<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.informatrix.cred.credentials"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Responsive Form</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            /* Custom width for the form */
            .custom-width {
                width: 50%; /* Adjust as needed, e.g., 40%, 60%, or a fixed value like 400px */
            }
            @media (max-width: 768px) {
                .custom-width {
                    width: 90%; /* For smaller screens, increase the width to make it more readable */
                }
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
        <div class="container mt-5 d-flex justify-content-center">
            <!-- Card to hold the form with custom width -->
            <div class="card shadow custom-width">
                <div class="card-body">
                    <!-- Form starts here -->
                    <form class="needs-validation" action="formdata" method="POST" novalidate>
                        
<!--                        <div data-mdb-input-init class="form-outline mb-4" >
                            <div data-mdb-input-init class="form-outline">
                                <input type="number"  name="id" id="form6Example2" readonly class="form-control" />
                                <label class="form-label" for="form6Example2">ID</label>
                            </div>
                        </div>-->
                        
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


                        <div class="form-outline mb-4">
                            <div class="form-outline">
                                <input type="email" id="form6Example3" class="form-control" value="<%=email%>" name="email" required />
                                <label class="form-label" style="color: black" for="form6Example3">Email</label>
                                <small id="emailError" class="text-danger"></small>
                            </div>
                        </div>


                        <!-- Company Name -->
                        <div class="mb-3">
                            <label for="companyName" class="form-label">Company Name</label>
                            <input type="text" class="form-control" id="companyName" name="companyName" placeholder="Enter Company Name" required>
                            <div class="invalid-feedback">Please enter the company name.</div>
                        </div>

                        <!-- Customer Address -->
                        <div class="mb-3">
                            <label for="customerAddress" class="form-label">Customer Address</label>
                            <textarea class="form-control" id="customerAddress" name="customerAddress" rows="3" placeholder="Enter Customer Address" required></textarea>
                            <div class="invalid-feedback">Please enter the customer address.</div>
                        </div>

                        <!-- Customer GST No -->
                        <div class="mb-3">
                            <label for="gstNo" class="form-label">Customer GST No</label>
                            <input type="text" class="form-control" id="gstNo" name="gstNo" placeholder="GST No" pattern="[A-Za-z0-9]{15}" required>
                            <div class="invalid-feedback">Please enter a valid GST number (15 alphanumeric characters).</div>
                        </div>

                        <!-- Mobile No -->
                        <div class="mb-3">
                            <label for="mobileNo" class="form-label">Mobile No</label>
                            <input type="tel" class="form-control" id="mobileNo" value="+91<%=phone%>" name="mobileNo" placeholder="Enter Mobile No" pattern="\+91[0-9]{10}" required>
                            <div class="invalid-feedback">Please enter a valid mobile number (+91XXXXXXXXXX).</div>
                        </div>

                        <!-- Due Date -->
                        <div class="mb-3">
                            <label for="dueDate" class="form-label">Due Date</label>
                            <input type="date" class="form-control" id="dueDate" name="dueDate" required>
                            <div class="invalid-feedback">Please select a due date.</div>
                        </div>

                        <!-- Current Date -->
                        <div class="mb-3">
                            <label for="currentDate" class="form-label">Current Date</label>
                            <input type="date" class="form-control" id="currentDate" name="currentDate" value="" required>
                            <div class="invalid-feedback">Please select the current date.</div>
                        </div>

                        <!-- Description -->
                        <div class="mb-3">
                            <label for="description" class="form-label">Description</label>
                            <textarea class="form-control" id="description" name="description" rows="3" placeholder="Enter Description" required><%= message%></textarea>
                            <div class="invalid-feedback">Please provide a description.</div>
                        </div>

                        <!-- Total Amount -->
                        <div class="mb-3">
                            <label for="total" class="form-label">Total Amount</label>
                            <input type="number" class="form-control" name="total" id="total" placeholder="Enter Total Amount" required>
                            <div class="invalid-feedback">Please enter the total amount.</div>
                        </div>

                        <!-- Centered Submit Button -->
                        <div class="d-flex justify-content-center">
                            <button type="submit" class="btn btn-primary w-50">Submit</button>
                        </div>
                    </form>
                    <!-- Form ends here -->
                </div>
            </div>
        </div>

        <!-- Bootstrap JS and its dependencies -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        <!-- JavaScript for form validation -->
        <script>
            // Disable form submissions if there are invalid fields
            (function () {
                'use strict'
                var forms = document.querySelectorAll('.needs-validation')
                Array.prototype.slice.call(forms).forEach(function (form) {
                    form.addEventListener('submit', function (event) {
                        if (!form.checkValidity()) {
                            event.preventDefault()
                            event.stopPropagation()
                        }
                        form.classList.add('was-validated')
                    }, false)
                })
            })()
        </script>

    </body>
</html>
