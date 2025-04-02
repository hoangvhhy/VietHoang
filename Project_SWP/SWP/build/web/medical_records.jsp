<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">
        <title>Medical Records - Customer</title>
        <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/select2.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/bootstrap-datetimepicker.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">

    </head>
    <body>
        <div class="main-wrapper">
            <jsp:include page="editseting.jsp"></jsp:include>
            <jsp:include page="sidebarCustomer.jsp"></jsp:include>

            <div class="page-wrapper">
                <div class="content container-fluid">
                    <div class="row align-items-center mb-4">
                        <div class="col-md-6">
                            <h4 class="page-title">Your Appointments</h4>
                        </div>
                    </div>

                    <!-- Hi?n th? th�ng b�o -->
                    <c:if test="${not empty message}">
                        <div class="alert alert-success">${message}</div>
                        <c:remove var="message" scope="session"/>
                    </c:if>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                        <c:remove var="error" scope="session"/>
                    </c:if>

                    <!-- Form t�m ki?m -->
                    <!-- Form t�m ki?m -->
                    <form method="get" action="customer-medical-records">
                        <div class="row">
                            <div class="col-md-4">
                                <label>Date:</label>
                                <input type="text" class="form-control floating datetimepicker" name="date" value="${param.date}">
                            </div>
                            <div class="col-md-4">
                                <br>
                                <button type="submit" class="btn btn-primary">Search</button>
                                <a href="customer-medical-records" class="btn btn-secondary">Clear</a>
                            </div>
                        </div>
                    </form>

                    <!-- Danh s�ch cu?c h?n -->
                    <div class="card mt-3">
                        <div class="card-body">
                            <h5 class="card-title">Appointment List</h5>
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Doctor</th>
                                            <th>Service</th>
                                            <th>Date</th>
                                            <th>Status</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:choose>
                                            <c:when test="${empty appointments}">
                                                <tr>
                                                    <td colspan="6" class="text-center">No appointments found.</td>
                                                </tr>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach var="exam" items="${appointments}">
                                                    <tr>
                                                        <td>${exam.examinationID}</td>
                                                        <td>${exam.consultantId.fullName}</td>
                                                        <td>
                                                            <c:forEach var="service" items="${exam.list}" varStatus="loop">
                                                                ${service.packageName}<c:if test="${!loop.last}">, </c:if>
                                                            </c:forEach>
                                                        </td>
                                                        <td>${exam.examinationDate}</td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${exam.status == 'Pending'}">
                                                                    <span class="badge bg-warning text-white">${exam.status}</span>
                                                                </c:when>
                                                                <c:when test="${exam.status == 'In process'}">
                                                                    <span class="badge bg-primary text-white">${exam.status}</span>
                                                                </c:when>
                                                                <c:when test="${exam.status == 'Confirmed'}">
                                                                    <span class="badge bg-success text-white">${exam.status}</span>
                                                                </c:when>
                                                                <c:when test="${exam.status == 'Completed'}">
                                                                    <span class="badge bg-info text-white">${exam.status}</span>
                                                                </c:when>
                                                                <c:when test="${exam.status == 'Rejected'}">
                                                                    <span class="badge bg-danger text-white">${exam.status}</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="badge bg-secondary text-white">${exam.status}</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${exam.status == 'Pending' || exam.status == 'In process'|| exam.status == 'Confirmed'}">
                                                                    <a href="view-medical-record?examId=${exam.examinationID}" class="btn btn-sm btn-info">View Details</a>
                                                                </c:when>
                                                                <c:when test="${exam.status == 'Completed'}">
                                                                    <a href="view-medical-record?examId=${exam.examinationID}" class="btn btn-sm btn-success">View Medical Record</a>
                                                                </c:when>
                                                            </c:choose>

                                                            <c:if test="${exam.status == 'Pending'}">
                                                                <a href="cancel-appointment?examId=${exam.examinationID}" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to cancel this appointment?');">Cancel</a>
                                                            </c:if>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <!-- Ph�n trang -->
                    <div class="pagination">
                        <ul class="pagination">
                            <!-- N�t Previous -->
                            <c:if test="${currentPage > 1}">
                                <li class="page-item">
                                    <a class="page-link" href="customer-medical-records?page=${currentPage - 1}&date=${param.date}&doctorId=${param.doctorId}">Previous</a>
                                </li>
                            </c:if>

                            <!-- Hi?n th? danh s�ch c�c trang -->
                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="customer-medical-records?page=${i}&date=${param.date}&doctorId=${param.doctorId}">${i}</a>
                                </li>
                            </c:forEach>

                            <!-- N�t Next -->
                            <c:if test="${currentPage < totalPages}">
                                <li class="page-item">
                                    <a class="page-link" href="customer-medical-records?page=${currentPage + 1}&date=${param.date}&doctorId=${param.doctorId}">Next</a>
                                </li>
                            </c:if>
                        </ul>
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
        <script src="assets/js/moment.min.js"></script>
        <script src="assets/js/bootstrap-datetimepicker.min.js"></script>
        <script src="assets/js/app.js"></script>
        <script>
                                                                    $(document).ready(function () {
                                                                        $('.select2').select2();
                                                                    });
        </script>
    </body>
</html>
