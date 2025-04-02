<%-- 
    Document   : sidebarCustomer
    Created on : Mar 15, 2025, 1:11:03 AM
    Author     : Gigabyte
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="sidebar" id="sidebar">
            <div class="sidebar-inner slimscroll">
                <div class="sidebar-menu">
                    <ul>
                        <li>
                            <a href="index_1.jsp"><i class="fa fa-home back-icon"></i> <span>Back to Home</span></a>
                        </li>
                        <li class="menu-title">Information</li>
<!--                        <li>
                            <a href="localization.html"><i class="fa fa-clock-o"></i> <span>Appointment</span></a>
                        </li>-->
                        <li>
                            <a href="invoiceCustomer"><i class="fa fa-pencil-square-o"></i> <span>Invoice</span></a>
                        </li>
                        <li>
                            <a href="customer-medical-records"><i class="fa fa-money"></i> <span>Medical Record</span></a>
                        </li>
<!--                        <li>
                            <a href="leave-type.html"><i class="fa fa-cogs"></i> <span>Profile</span></a>
                        </li>-->
<!--                        <li>
                            <a href="change-password.html"><i class="fa fa-lock"></i> <span>Change Password</span></a>
                        </li>-->
                    </ul>
                </div>
            </div>
        </div>
    </body>
</html>
