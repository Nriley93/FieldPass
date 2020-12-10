<%-- 
    Document   : Home
    Created on : Nov 2, 2020, 7:15:35 PM
    Author     : n.riley
--%>

<%@page import="java.util.Date"%>
<%@page import="business.EventDB"%>
<%@page import="java.util.List"%>
<%@page import="business.Event"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<script src="ajax.js" type="text/javascript"></script>
<script src="history.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
function eventAction(event){
    if(event === "event") {
        document.getElementById('total').style.display = 'block';
        document.card.actiontype.value=event;
        if(ajax && event === 'event') {
                ajax.open('get','AddPass?actiontype=event');
                ajax.send();
        }
    }
}
    </script>
    <style>
        #total {
            display: none;
        }
    </style>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Field Pass Home Page</title>
    </head>
    <c:if test="${!employee.authenticated}">
        <script type="text/javascript">
            window.location = "/FieldPass";
        </script>    
    </c:if>
        
    <body>
        <!--Greeting message-->
        <h1>Welcome ${employee.fname}</h1>
        <!--Admin Status-->
        <p>Administrator Status - ${employee.admin}</p>
        
        <!--new pass & update form-->
<!--        <form method="post">-->
        <form method="post">
        <!--List of events-->
            Event:
            <select class="event" name="event" id="event" onchange="eventAction('event')">
                <c:forEach var="e" items="${events}"> 
                     <option value="" disabled selected hidden>
                         --- select and event ---
                     </option>
                     <option value="${e.evtID}">${e.evtDT} (${e.evtDesc})</option> 
                </c:forEach>
            </select><br>
            
            <table>
            <!--Guest totals for selected event-->
            <%--<c:if test="${events.evtDT != null}">--%>
                <tr>
                    <td id="results" name="results"></td>
                    <td style="" class="total" name="total" id="total">Current Passes For Event: ${event.evtID}
                    </td>
                    <!--<td>Large Group Area:<br></td>-->
                </tr>
                <input type="hidden" name="actiontype" id="actiontype" value="">
            <%--</c:if>--%>
                
            <!--Pass Labels-->
                <tr>
                    <th>Field Escort</th>
                    <th>No. In Group</th>
                    <th>Group Name</th>
                    <th>Departments:</th>
                </tr><br>
              
            <!--Pass Input-->
            <!--Escort-->
                <c:if test="${employee.admin == 'Y'}">
                    <!--if admin: allow escort to be changed-->
                    <td><input type="text" name="name" id="name" required="" 
                               value="${employee.fname}, ${employee.lname}">
                </td>
                </c:if>
                <c:if test="${employee.admin == 'N'}">
                    <!--if not admin: escort will be current user-->
                    <td><input type="text" name="name" id="name" required="" 
                           value="${employee.fname}, ${employee.lname}" 
                            readonly="true">
                    </td>
                </c:if>
                
            <!--Pass Group number-->
                <td>
                    <input type="text" name="groupNum" id="groupNum" 
                           required="" value="${pass.groupNum}" 
                           placeholder="Size of group (max 8)">
                </td>

            <!--Pass Group Name-->
                <td>
                    <input type="text" name="groupNm" id="groupNm" required=""
                           value="${pass.groupNm}" placeholder="Name">
                </td>
                
            <!--List of Departments-->
                <td>
                    <select name="department" id="department">
                    <c:forEach var="d" items="${departments}"> 
                        <option value="${pass.deptID}" hidden="">
                            ${!empty pass.deptID  ? dept.deptNm : "-- select Dept --"}    
                        </option>
                        <option value="${d.deptID}">${d.deptNm}</option> 
                    </c:forEach>
                    </select><br>
                </td>
                
            <!--Submit buttons related to forms-->
                <tr>
                    <td></td>
                    <td></td>
                    <td><input type="submit" formaction="UpdatePass" value="Update/Delete Pass"></td>
                    <td><input type="submit" formaction="AddPass" value="New Pass"></td>
                </tr>
            </table>
        </form>
                     
        Reservations on FIle for Requestor<br>
        <form action="ExistingPass" name="pass" id="pass" method="post">
            <select name="passid" id="passid" onchange="this.form.submit()">
            <!--<select name="passid" id="passid" onchange="existingAction(this.value)">-->
                <c:forEach var="p" items="${passes}"> 
                    <option disabled selected hidden>
                        ${!empty pass  ? pass.groupNm : "-- select prior entry to edit/delete --"}
                        
                    </option>
                    <c:if test="${employee.empID == p.empID}">
                        <option value="${p.passID}" >
                        ${p.passDt} (# = ${p.groupNum}) - ${p.groupNm} / ${p.escort}
                        </option>
                    </c:if>
                </c:forEach>
            </select>
        </form>
        <br>
        ${msg}
        <br>
        <a href="Logon.jsp" >Log Out</a>
    </body>
</html>
