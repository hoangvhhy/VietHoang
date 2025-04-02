<%-- 
    Document   : editseting
    Created on : Jan 14, 2025, 11:02:30 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">
        <title>Preclinic - Medical & Hospital - Bootstrap 4 Admin Template</title>
        <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <!--[if lt IE 9]>
                    <script src="assets/js/html5shiv.min.js"></script>
                    <script src="assets/js/respond.min.js"></script>
            <![endif]-->
    </head>
    <c:if test="${sessionScope.customerAccount != null}">
        <div class="header">
            <div class="header-left">
                <a href="home" class="logo">
                    <img src="assets/img/logo.png" width="35" height="35" alt=""> <span>Preclinic</span>
                </a>
            </div>

            <ul class="nav user-menu float-right">
                <li class="nav-item dropdown has-arrow">
                    <a href="#" class="dropdown-toggle nav-link user-link" data-toggle="dropdown">
                        <span class="user-img"><img class="rounded-circle" src="pictureprofile?customerID=${sessionScope.customerAccount.customerID}" width="50" height="35">
                            <span class="status online"></span></span>
                        <span>${sessionScope.customerAccount.fullName}</span>
                    </a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="profile.jsp">My Profile</a>
                        <a class="dropdown-item" href="customer-medical-records">Medical record</a>
                        
                        <a class="dropdown-item" href="logout">Logout</a>
                    </div>
                </li>
            </ul>
            <div class="dropdown mobile-user-menu float-right">
                <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i
                        class="fa fa-ellipsis-v"></i></a>
                <div class="dropdown-menu dropdown-menu-right">
                    <a class="dropdown-item" href="profile.jsp">My Profile</a>
                   
                    <a class="dropdown-item" href="edit-profile.jsp">Settings</a>
                    <a class="dropdown-item" href="logout">Logout</a>
                </div>
            </div>
        </div>
    </c:if>

    <c:if test="${sessionScope.staffAccount != null}">
        <div class="header">
            <div class="header-left">
                <a href="home" class="logo">
                    <img src="assets/img/logo.png" width="35" height="35" alt=""> <span>Preclinic</span>
                </a>
            </div>

            <ul class="nav user-menu float-right">
                <li class="nav-item dropdown has-arrow">
                    <a href="#" class="dropdown-toggle nav-link user-link" data-toggle="dropdown">
                        <span class="user-img"><img class="rounded-circle" src="pictureStaff?staffID=${sessionScope.staffAccount.staffID}" width="40"
                                                    alt="Admin">
                            <span class="status online"></span></span>
                        <span>${sessionScope.staffAccount.fullName}</span>
                    </a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="profile.jsp">My Profile</a>
                        <!--<a class="dropdown-item" href="edit-profile.html">Edit Profile</a>-->
                        <!--<a class="dropdown-item" href="settings.html">Settings</a>-->
                        <a class="dropdown-item" href="logout">Logout</a>
                    </div>
                </li>
            </ul>
            <div class="dropdown mobile-user-menu float-right">
                <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i
                        class="fa fa-ellipsis-v"></i></a>
                <div class="dropdown-menu dropdown-menu-right">
                    <a class="dropdown-item" href="profile.html">My Profile</a>
                    <!--<a class="dropdown-item" href="edit-profile.jsp">Edit Profile</a>-->
                    <!--<a class="dropdown-item" href="settings.html">Settings</a>-->
                    <a class="dropdown-item" href="logout">Logout</a>
                </div>
            </div>
        </div>
    </c:if>
</html>