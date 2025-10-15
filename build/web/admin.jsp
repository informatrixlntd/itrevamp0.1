<%-- 
    Document   : admin
    Created on : 10 Sep, 2024, 1:50:31 PM
    Author     : prohit.bagde
--%>

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

        <title>Admin!</title>
    </head>
    <body>

        <%
            List idList = null;
            String seslog = (String) session.getAttribute("seslog");
            if (seslog == null) {
                response.sendRedirect("adminlogin.jsp");
            }

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
                    sql = "SELECT * FROM enquiries WHERE email LIKE ?";
                    pst = con.prepareStatement(sql);
                    pst.setString(1, "%" + searchTerm + "%");
                } else {
                    sql = "SELECT * FROM enquiries";
                    pst = con.prepareStatement(sql);
                }

                ResultSet rs = pst.executeQuery();

        %>


        <!-- Navbar Start -->
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">Navbar</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="#">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Link</a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Dropdown
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="#">Action</a></li>
                                <li><a class="dropdown-item" href="maintable.jsp">Main Table</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="#">Something else here</a></li>
                            </ul>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
                        </li>
                    </ul>
                    <button class="btn btn-primary" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling">yo</button> &nbsp;&nbsp; &nbsp;
                    <form class="d-flex">



                        <input
                            type="search"
                            class="form-control rounded"
                            placeholder="Search by email"
                            aria-label="Search"
                            aria-describedby="search-addon"
                            name="searchTerm"
                            value=""
                            />
                        <span class="input-group-text border-0"  id="search-addon">

                        </span>
                    </form>

                </div>
            </div>
        </nav>
        <!-- Navbar END -->

        <!-- slider -->

        <div class="offcanvas offcanvas-start" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1" id="offcanvasScrolling" aria-labelledby="offcanvasScrollingLabel">
            <div class="offcanvas-header">
                <h5 class="offcanvas-title" id="offcanvasScrollingLabel">Colored with scrolling</h5>
                <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
            </div>
            <div class="offcanvas-body">
                <p>Try scrolling the rest of the page to see this option in action.</p>
            </div>
        </div>
        <br>

        <!-- slider END -->

        <!-- Add lead form -->
        <section class="d-flex justify-content-center m-1 p-1">
            <!--            <button type="button" class="btn btn-outline-secondary" data-bs-toggle="modal" data-bs-target="#exampleModal">Add custumber Details</button> -->

            <a href="form.html" class="btn btn-outline-secondary">Add custumber Details</a>

        </section>
        <!-- Add lead form End-->

        <!-- Table --> 
        <section class="d-flex justify-content-center m-5 p-5">

            <table class="table table-bordered border-primary" >
                <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">First Name</th>
                        <th scope="col">Last Name</th>
                        <th scope="col">Email ID</th>
                        <th scope="col">Phone No</th>
                        <!--                        <th scope="col">Message</th>-->
                        <th scope="col">Edit</th>
                        <th scope="col">Add in invoice table</th>
                        <th scope="col">send email</th>
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
<!--                        <td><%=rs.getString(6)%></td>-->

                        <td><a href="edit.jsp?id=<%=rs.getInt(1)%>"
                               class="btn btn-sm btn-outline-success">Edit</a></td>

                        <td><a href="editmain.jsp?id=<%=rs.getInt(1)%>"
                               class="btn btn-sm btn-outline-success">Add in invoice table</a></td>
                        <td><a href="emailform.jsp?id=<%=rs.getInt(1)%>"
                               class="btn btn-sm btn-outline-success">send Email</a></td>
                        <td><a href="delete?id=<%=rs.getInt(1)%>"
                               class="btn btn-sm btn-outline-success">Delete</a></td>
                    </tr>

                    <% } %>
                </tbody>
            </table>
        </section>


        <%                // Close JDBC connections and resources
                rs.close();
                pst.close();
                con.close();
            } catch (Exception e) {
                out.println("Exception: " + e);
            }
        %>


        <!-- Table -->

        <!-- Optional JavaScript; choose one of the two! -->

        <!-- Option 1: Bootstrap Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

        <!-- Option 2: Separate Popper and Bootstrap JS -->
        <!--
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
        -->
    </body>
</html>