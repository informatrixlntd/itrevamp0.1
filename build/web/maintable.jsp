<%@page import="java.util.List"%>
<%@page import="com.informatrix.cred.credentials"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

        <title>Admin Dashboard</title>
    </head>
    <body>

        <%
            // Check if the search term is submitted
            String searchTerm = request.getParameter("searchTerm");

            // JDBC code for database connection and search
            credentials credentials = new credentials();
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(credentials.getJdbcUrl(), credentials.getUname(), credentials.getPassword());

                String sql;
                PreparedStatement pst;

                if (searchTerm != null && !searchTerm.isEmpty()) {
                    sql = "SELECT * FROM clients WHERE email LIKE ?";
                    pst = con.prepareStatement(sql);
                    pst.setString(1, "%" + searchTerm + "%");
                } else {
                    sql = "SELECT * FROM clients";
                    pst = con.prepareStatement(sql);
                }

                ResultSet rs = pst.executeQuery();
        %>

        <!-- Navbar Start -->
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">Admin Panel</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="admin.jsp">Home</a>
                        </li>
                    </ul>

                    <!-- Search Form -->
                    <form class="d-flex">
                        <input type="search" class="form-control rounded me-2" placeholder="Search by email" aria-label="Search" name="searchTerm" />
                        <button class="btn btn-outline-primary" type="submit">Search</button>
                    </form>
                </div>
            </div>
        </nav>
        <!-- Navbar END -->

        <!-- Add lead form -->
        <section class="d-flex justify-content-center m-3">
            <a href="form.html" class="btn btn-outline-secondary">Add Customer Details</a>
        </section>
        <!-- Add lead form End-->

        <!-- Table Section -->
        <section class="container">
            <div class="table-responsive">
                <table class="table table-bordered table-striped table-hover">
                    <thead class="table-primary">
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">First Name</th>
                            <th scope="col">Last Name</th>
                            <th scope="col">Email ID</th>
                            <th scope="col">Company Name</th>
                            <th scope="col">Customer Address</th>
                            <th scope="col">GST Number</th>
                            <th scope="col">Phone No</th>
                            <th scope="col">Due Date</th>
                            <th scope="col">Created Date</th>
                            <th scope="col">Description</th>
                            <th scope="col">Amount</th>
                            <th scope="col">Edit</th>
                            <th scope="col">Send Invoice</th>
                            <th scope="col">Send Email</th>
                            <th scope="col">Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% while (rs.next()) {%>
                        <tr>
                            <td><%=rs.getInt(1)%></td>
                            <td><%=rs.getString(2)%></td>
                            <td><%=rs.getString(3)%></td>
                            <td><%=rs.getString(4)%></td>
                            <td><%=rs.getString(5)%></td>
                            <td><%=rs.getString(6)%></td>
                            <td><%=rs.getString(7)%></td>
                            <td><%=rs.getString(8)%></td>
                            <td><%=rs.getString(9)%></td>
                            <td><%=rs.getString(10)%></td>
                            <td><%=rs.getString(11)%></td>
                            <td><%=rs.getString(12)%></td>
                            <td><a href="mainedit.jsp?id=<%=rs.getInt(1)%>" class="btn btn-sm btn-outline-success">Edit</a></td>
                            <td><a href="viewform.jsp?id=<%=rs.getInt(1)%>" class="btn btn-sm btn-outline-warning">Send </a></td>
                            <td><a href="emailformm.jsp?id=<%=rs.getInt(1)%>" class="btn btn-sm btn-outline-primary">Send </a></td>
                            <td><a href="deletee?id=<%=rs.getInt(1)%>" class="btn btn-sm btn-outline-danger">Delete</a></td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </section>

        <%
                // Close JDBC connections and resources
                rs.close();
                pst.close();
                con.close();
            } catch (Exception e) {
                out.println("Exception: " + e);
            }
        %>

        <!-- Bootstrap JavaScript Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

    </body>
</html>
