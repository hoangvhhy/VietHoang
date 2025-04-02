<%-- 
    Document   : profile
    Created on : Jan 14, 2025, 11:40:13 PM
    Author     : jaxbo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">


    <!-- profile22:59-->
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

    <body>
        <div class="header">
            <div class="header-left">
                <a href="home" class="logo">
                    <img src="assets/img/logo.png" width="35" height="35" alt=""> <span>Preclinic</span>
                </a>
            </div>
            <c:choose>
                <c:when test="${sessionScope.customerAccount != null}">
                    <ul class="nav user-menu float-right">
                        <li class="nav-item dropdown has-arrow">
                            <a href="#" class="dropdown-toggle nav-link user-link" data-toggle="dropdown">
                                <span class="user-img"><img class="rounded-circle" src="pictureprofile?customerID=${sessionScope.customerAccount.customerID}" width="50" height="35">
                                    <span class="status online"></span></span>
                                <span>${sessionScope.customerAccount.fullName}</span>
                            </a>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="profile.jsp">My Profile</a>
                                <c:if test="${sessionScope.customerAccount != null}">
                                    <a class="dropdown-item" href="customer-medical-records">Medical record</a>
                                </c:if>
                                <a class="dropdown-item" href="logout">Logout</a>
                            </div>
                        </li>
                    </ul>
                </c:when>
                <c:otherwise>
                    <c:if test="${sessionScope.staffAccount != null}">
                        <ul class="nav user-menu float-right">
                            <li class="nav-item dropdown has-arrow">
                                <a href="#" class="dropdown-toggle nav-link user-link" data-toggle="dropdown">
                                    <span class="user-img"><img class="rounded-circle" src="pictureStaff?staffID=${sessionScope.staffAccount.staffID}" width="50" height="35">
                                        <span class="status online"></span></span>
                                    <span>${sessionScope.staffAccount.fullName}</span>
                                </a>
                                <div class="dropdown-menu">
                                    <a class="dropdown-item" href="profile.jsp">My Profile</a>
                                    <a class="dropdown-item" href="logout">Logout</a>
                                </div>
                            </li>
                        </ul>
                    </c:if>
                </c:otherwise>
            </c:choose>



        </div>

        <style>
            .enlarged-img {
                max-width: 80%;
                max-height: 80vh;
                border-radius: 5px;
                box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.5);
            }
        </style>

        <div class="page-wrapper-profile">
            <div class="content">
                <div class="row">
                    <div class="col-sm-7 col-6">
                        <h4 class="page-title">My Profile</h4>
                    </div>

                    <div class="col-sm-5 col-6 text-right m-b-30">
                        <a href="edit-profile.jsp" class="btn btn-primary btn-rounded"><i class="fa fa-plus"></i> Edit Profile</a>
                    </div>
                </div>
            </div>
            <c:if test="${sessionScope.customerAccount != null}">
                <div class="card-box profile-header">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="profile-view">
                                <div class="profile-img-wrap">
                                    <div class="profile-img">
                                        <a href="#" data-toggle="modal" data-target="#imageModal">
                                            <img class="avatar" src="pictureprofile?customerID=${sessionScope.customerAccount.customerID}" alt="Profile Picture">
                                        </a>
                                    </div>
                                </div>

                                <!-- Modal Bootstrap -->
                                <div class="modal fade" id="imageModal" tabindex="-1" role="dialog" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title">Profile Picture</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body text-center">
                                                <img id="modalImage" class="img-fluid enlarged-img" src="pictureprofile?customerID=${sessionScope.customerAccount.customerID}" alt="Profile Picture">
                                            </div>
                                        </div>
                                    </div>
                                </div>


                                <div class="profile-basic">
                                    <div class="row">
                                        <div class="col-md-5">
                                            <div class="profile-info-left">
                                                <h3 class="user-name m-t-0 mb-0">${sessionScope.customerAccount.fullName}</h3>
                                                <!--<small class="text-muted">Gynecologist</small>-->
                                                <div class="staff-id">Customer ID : ${sessionScope.customerAccount.customerID}</div>
                                                <div class="staff-msg"><a href="change-password.jsp" class="btn btn-primary">Change password</a></div>
                                            </div>
                                        </div>
                                        <div class="col-md-7">
                                            <ul class="personal-info">
                                                <li>
                                                    <span class="title">Phone:</span>
                                                    <span class="text"><a href="#">${sessionScope.customerAccount.phone}</a></span>
                                                </li>
                                                <li>
                                                    <span class="title">Email:</span>
                                                    <span class="text"><a href="#">${sessionScope.customerAccount.email}</a></span
                                                </li>

                                                <li>
                                                    <span class="title">Birthday:</span>
                                                    <span class="text">${sessionScope.customerAccount.dateOfBirth}</span>
                                                </li>


                                                <li>
                                                    <span class="title">Address:</span>
                                                    <span class="text">${sessionScope.customerAccount.address}</span>
                                                </li>
                                                <li>
                                                    <span class="title">Gender:</span>
                                                    <span class="text">${sessionScope.customerAccount.gender}</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>                        
                        </div>
                    </div>
                </div>
            </c:if>   
            <c:if test="${sessionScope.staffAccount != null}">
                <div class="card-box profile-header">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="profile-view">
                                <div class="profile-img-wrap">
                                    <div class="profile-img">
                                        <a href="#" data-toggle="modal" data-target="#imageModal">
                                            <img class="avatar" src="pictureStaff?staffID=${sessionScope.staffAccount.staffID}" alt="Profile Picture">
                                        </a>
                                    </div>
                                </div>

                                <!-- Modal Bootstrap -->
                                <div class="modal fade" id="imageModal" tabindex="-1" role="dialog" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title">Profile Picture</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body text-center">
                                                <img id="modalImage" class="img-fluid enlarged-img" src="pictureStaff?staffID=${sessionScope.staffAccount.staffID}" alt="Profile Picture">
                                            </div>
                                        </div>
                                    </div>
                                </div>


                                <div class="profile-basic">
                                    <div class="row">
                                        <div class="col-md-5">
                                            <div class="profile-info-left">
                                                <h3 class="user-name m-t-0 mb-0">${sessionScope.staffAccount.fullName}</h3>
                                                <!--<small class="text-muted">Gynecologist</small>-->
                                                <div class="staff-id">Staff ID : ${sessionScope.staffAccount.staffID}</div>
                                                <div class="staff-msg"><a href="change-password.jsp" class="btn btn-primary">Change password</a></div>
                                            </div>
                                        </div>
                                        <div class="col-md-7">
                                            <ul class="personal-info">
                                                <li>
                                                    <span class="title">Phone:</span>
                                                    <span class="text"><a href="#">${sessionScope.staffAccount.phone}</a></span>
                                                </li>
                                                <li>
                                                    <span class="title">Email:</span>
                                                    <span class="text"><a href="#">${sessionScope.staffAccount.email}</a></span
                                                </li>

                                                <li>
                                                    <span class="title">Birthday:</span>
                                                    <span class="text">${sessionScope.staffAccount.dateOfBirth}</span>
                                                </li>


                                                <li>
                                                    <span class="title">Address:</span>
                                                    <span class="text">${sessionScope.staffAccount.address}</span>
                                                </li>
                                                <li>
                                                    <span class="title">Gender:</span>
                                                    <span class="text">${sessionScope.staffAccount.gender}</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>                        
                        </div>
                    </div>
                </div>
            </c:if> 
        </div>

        <div class="sidebar-overlay" data-reff=""></div>
        <script src="assets/js/jquery-3.2.1.min.js"></script>
        <script src="assets/js/popper.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/jquery.slimscroll.js"></script>
        <script src="assets/js/app.js"></script>
    </body>


    <!-- profile23:03-->
</html>
