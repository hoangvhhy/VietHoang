<%-- 
    Document   : viewmedical-customer
    Created on : Mar 15, 2025, 9:19:36 PM
    Author     : Win11
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zxx">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="description" content="Medical History">
        <meta name="author" content="Your Company">
        <title>Patient Medical History</title>
        <link rel="stylesheet" href="plugins/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/pagination.css">
        <style>
            .contact-block {
                display: flex;
                flex-direction: column;
                justify-content: space-between; /* Căn nội dung */
                min-height: 300px; /* Đặt chiều cao tối thiểu */
                height: 100%; /* Đảm bảo đồng nhất */
                width: 300px;
                padding: 20px;
                border: 1px solid #ddd;
                border-radius: 8px;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                background-color: #fff;
                transition: transform 0.3s ease-in-out;
            }

            .contact-block:hover {
                transform: scale(1.02);
            }

            .row {
                display: flex;
                flex-wrap: wrap;
                align-items: stretch; /* Đảm bảo chiều cao đồng nhất */
            }

            .col-lg-4, .col-md-6, .col-sm-6 {
                display: flex;
                align-items: stretch; /* Căn chỉnh đều */
            }

        </style>
    </head>
    <body id="top">
        <jsp:include page="headerHome.jsp"></jsp:include>
            <section class="page-title bg-1">
                <div class="overlay"></div>
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="block text-center">
                                <span class="text-white">Medical History</span>
                                <h1 class="text-capitalize mb-5 text-lg">Your Medical History</h1>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            
        <c:if test="${medicalRecords == null || empty medicalRecords}">
            <div class="alert alert-warning text-center">No medical records available</div>
        </c:if>
        <section class="section contact-info pb-0">
            <div class="container">
                <div class="row">
                    <!-- Dynamically generate medical records -->
                    <c:forEach var="record" items="${medicalRecords}">
                        <div class="col-lg-4 col-sm-6 col-md-6 card">
                            <div class="contact-block mb-4 mb-lg-0">
                                <i class="icofont-prescription"></i>
                                <h5>Medical Record</h5>
                                <p><strong>Diagnosis:</strong> ${record.diagnosis}</p>
                                <p><strong>Doctor:</strong> ${record.doctorName}</p>
                                <p><strong>Phone:</strong> ${record.phone}</p>
                                <p><strong>Treatment Plan:</strong> ${record.treatmentPlan}</p>
                                <p><strong>Medications:</strong> ${record.medicationsPrescribed}</p>
                                <p><strong>Date:</strong> ${record.createdAt}</p>
                                <p><strong>Notes:</strong> ${record.notes}</p>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div id="pagination-container" class="pagination text-center"></div>
        </section>

        <jsp:include page="footer.jsp"></jsp:include>

        <script src="plugins/jquery/jquery.js"></script>
        <script src="plugins/bootstrap/js/popper.js"></script>
        <script src="plugins/bootstrap/js/bootstrap.min.js"></script>
        <script src="js/script.js"></script>
        <script src="js/pagination.js"></script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                paginateItems('.card', 2, '#pagination-container');
            });
        </script>
    </body>
</html>

