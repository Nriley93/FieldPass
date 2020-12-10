
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Field Pass System</title>
    </head>
    <body>
        <h1>Field Pass System</h1> 
        <p>Please Enter your id and password:</p>
        <form action="Logon" method="post">
            <table>
                <tr>
                    <td>Employee ID:</td>
                    <td><input type="text" name="empid" id="empid"
                         value="${empty employee.empID ? cookie.empid.value : employee.empID }"/>
                    </td>           
                    </td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type="password" name="password" id="password">
                    </td>
                </tr>
            </table>
            <br>        
            <input type="submit" value="Logon">
        </form>
        <br>
        ${msg}
        Cookies: <br>
        <% 
           Cookie[] cookies = request.getCookies();
           if (cookies != null) {
               for (Cookie c: cookies) {
        %>
        <%= c.getName() %> = <%= c.getValue() %> <br>
         <%
               }
           }    
        %>
    </body>
</html>
