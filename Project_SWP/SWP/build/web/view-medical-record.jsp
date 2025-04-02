<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">
        <title>Medical Record Details</title>
        <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
    </head>
    <body>
        <div class="main-wrapper">
            <div class="header">
                <div class="header-left">
                    <a href="customer-dashboard.html" class="logo">
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
            </div>

            <div class="page-wrapper-profile">
                <div class="content container-fluid">
                    <div class="row">
                        <div class="col-lg-8 offset-lg-2">
                            <h4 class="page-title">Medical Record - Appointment #${exam.examinationID}</h4>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-8 offset-lg-2">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Appointment Details</h5>
                                    <p><strong>Doctor:</strong> ${exam.consultantId.fullName}</p>
                                    <p><strong>Services:</strong> 
                                        <c:forEach var="service" items="${exam.list}" varStatus="loop">
                                            ${service.packageName}<c:if test="${!loop.last}">, </c:if>
                                        </c:forEach>
                                    </p>
                                    <p><strong>Date:</strong> ${exam.examinationDate}</p>
                                    <p><strong>Status:</strong> <span class="badge badge-info">${exam.status}</span></p>
                                    <p><strong>Notes:</strong> ${exam.note}</p>
                                </div>
                            </div>
                            <c:if test="${medicalRecord != null}">
                                <div class="card">
                                    <div class="card-body">
                                        <h5 class="card-title">Medical Record</h5>
                                        <p><strong>Diagnosis:</strong> ${medicalRecord.diagnosis}</p>
                                        <p><strong>Treatment Plan:</strong> ${medicalRecord.treatmentPlan}</p>
                                        <p><strong>Medications Prescribed:</strong> ${medicalRecord.medicationsPrescribed}</p>
                                        <p><strong>Doctor's Advice/Notes:</strong> ${medicalRecord.notes}</p>
                                        <p><strong>Created At:</strong> ${medicalRecord.createdAt}</p>
                                    </div>
                                </div>
                            </c:if>
                            <div class="text-center">
                                <a href="customer-medical-records" class="btn btn-primary">Back to Records</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Thêm jQuery tr??c Bootstrap -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>


    </body>
</html>