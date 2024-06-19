<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.*" %>
<!DOCTYPE html>
<%
    HttpSession currentSession = request.getSession(false);
    if (currentSession != null) {
        currentSession.invalidate();
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
          <style>
            /* Style for form container */
            .form-field {
                width: 50%;
                margin: 0 auto;
                padding: 20px;
                border: 1px solid #ccc;
                border-radius: 5px;
            }

            /* Style for labels */
            .form-field label {
                display: block;
                margin-bottom: 10px;
                font-weight: bold;
            }

            /* Style for input fields */
            .form-field input[type="text"],
            .form-field input[type="password"] {
                width: calc(100% - 20px);
                padding: 10px;
                margin-bottom: 20px;
                border: 1px solid #ccc;
                border-radius: 5px;
                box-sizing: border-box;
            }

            /* Style for remember me checkbox */
            .form-field #rememberMe {
                margin-right: 5px;
            }

            /* Style for submit button */
            .form-field button[type="submit"] {
                padding: 10px 20px;
                background-color: #4CAF50;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }

            /* Hover effect for submit button */
            .form-field button[type="submit"]:hover {
                background-color: #45a049;
            }

            /* Style for error message */
            .form-field p {
                margin: 10px 0 0 0;
                color: red;
            }
        </style>
    </head>
    <body>
        <%
                Cookie[] cookies = request.getCookies();
                String username = "";
                String password = "";
                if (cookies != null) {
                    for (Cookie c : cookies) {
                        if (c.getName().equals("username")) {
                            username = c.getValue();
                        }
                        if (c.getName().equals("password")) {
                            password = c.getValue();
                        }
                    }
                }
        %>
        <h1>LoginPage!</h1>
        <form class="form-field" action="LoginServlet" method="post">
            <div>
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required 
                       value="<%= username %>">
            </div>
            <div>
                <label for="password">Password :</label>
                <input type="password" id="password" name="password" required 
                       value="<%= password %>">
            </div>
            <div style="margin: 0px 0px 0px 70px">
                <input type="checkbox" id="rememberMe" name="rememberMe">
                <label for="rememberMe">Remember Me</label>
            </div>
            <div style="margin: 0px 0px 0px 70px">
                <button type="submit">Login</button>
            </div>
            <p style="color:red; margin: 0px 0px 0px 70px">${param.error}</p>
        </form>
    </body>
</html>
