<%-- 
    Document   : edit-role
    Created on : Feb 16, 2025, 9:21:35 PM
    Author     : Gigabyte
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">


    <!-- edit-role24:13-->
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">
        <title>Preclinic - Medical & Hospital - Bootstrap 4 Admin Template</title>
        <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/select2.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <!--[if lt IE 9]>
                    <script src="assets/js/html5shiv.min.js"></script>
                    <script src="assets/js/respond.min.js"></script>
            <![endif]-->
    </head>

    <body>
        <div class="main-wrapper">
            <jsp:include page="headerStaff.jsp"></jsp:include>
            <jsp:include page="sidebar.jsp"></jsp:include>
            <div class="page-wrapper">
                <div class="content">
                    <div class="row">
                        <div class="col-lg-8 offset-lg-2">
                            <h4 class="page-title">Edit Role</h4>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-8 offset-lg-2">
                            <form action="editRole" method="post">
                                <input type="text" id="hiddenInput" name="roleID" value="${roleID}" style="display: none;">
                                <div class="form-group">
                                    <label>Role Name <span class="text-danger">*</span></label>
                                    <input class="form-control" name="roleName" value="${roleName}" type="text">
                                </div>
                                <div class="form-group">
                                    <label>Description <span class="text-danger"></span></label>
                                    <input class="form-control" name="description" value="${description}" type="text">
                                </div>
                                <c:if test="${error != null}">
                                    <h4><i style="color: red">${error}</i></h4>
                                    </c:if>
                                    <c:if test="${mess != null}">
                                    <h4><i style="color: green">${mess}</i></h4>
                                    </c:if>
                                <div class="m-t-20 text-center">
                                    <button class="btn btn-primary submit-btn">Save</button>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="sidebar-overlay" data-reff=""></div>
        <script src="assets/js/jquery-3.2.1.min.js"></script>
        <script src="assets/js/popper.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/jquery.slimscroll.js"></script>
        <script src="assets/js/select2.min.js"></script>
        <script src="assets/js/app.js"></script>
    </body>


    <!-- edit-role24:13-->
</html>

