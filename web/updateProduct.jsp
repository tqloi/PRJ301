
<%-- 
    Document   : updateProduct
    Created on : Jun 9, 2024, 10:17:44 PM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Update Product!</h1>
        <form class="form-field" action="ProductServlet" method="post">
            
            <input type="hidden" name="method" value="update">
            
            <input type="hidden" name="productId" value="<%= request.getAttribute("productId") %>">

            <label for="productName">Product Name:</label>
            <input type="text" id="productName" name="productName" required><br><br>

            <label for="description">Description:</label><br>
            <textarea id="description" name="description" rows="4" cols="50" required></textarea><br><br>

            <label for="price">Price:</label>
            <input type="number" id="price" name="price" step="0.01" required><br><br>

            <input type="submit" value="Submit">
        </form>
    </body>
</html>
