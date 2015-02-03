<%-- 
    Document   : login
    Created on : Oct 22, 2014, 9:50:56 AM
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>      
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <div style="width: 350px; margin: auto">
            <form class="form-signin" role="form" action="AccountController" method="POST">
                <h2 class="form-signin-heading">Login</h2>
                <input type="text" class="form-control" name="txtEmail" placeholder="Email" required autofocus>
                <input type="password" class="form-control" name="txtPassword" placeholder="Password" required>
                <br/>
                <c:if test="${not empty param.msg}" >
                    <div style="color: red">Wrong username or password!</div>
                </c:if>
                <button class="btn btn-lg btn-primary btn-block" type="submit" value="Login" name="action" >Login</button>
            </form>
            <br/>
            <div><a href="/RMS/register.jsp">Don't have an account? Register here</a></div>
        </div>
    </body>
</html>
