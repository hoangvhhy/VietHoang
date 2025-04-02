<%-- 
    Document   : dashboard
    Created on : Mar 5, 2025, 11:47:26 PM
    Author     : Hoang
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">


    <!-- index22:59-->
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">
        <title>Preclinic - Medical & Hospital - Bootstrap 4 Admin Template</title>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        
    </head>

    <body>
        <div class="main-wrapper">
            <jsp:include page="headerStaff.jsp"></jsp:include>
            <jsp:include page="sidebar.jsp"></jsp:include>
                <div class="page-wrapper">
                    <div class="content">
                        <div class="row">
                            <div class="col-md-6 col-sm-6 col-lg-6 col-xl-3">
                                <div class="dash-widget">
                                    <span class="dash-widget-bg2"><i class="fa fa-user-o"></i></span>
                                    <div class="dash-widget-info text-right">
                                        <h3>${requestScope.todayCount}</h3>
                                    <span class="widget-title2">Visits in day <i class="fa fa-check" aria-hidden="true"></i></span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 col-sm-6 col-lg-6 col-xl-3">
                            <div class="dash-widget">
                                <span class="dash-widget-bg4"><i class="fa fa-heartbeat" aria-hidden="true"></i></span>
                                <div class="dash-widget-info text-right">
                                    <h3>${pending}</h3>
                                    <span class="widget-title4">Pending <i class="fa fa-check" aria-hidden="true"></i></span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-body">
                                    <div class="d-flex align-items-center justify-content-between mb-3">
                                        <h4 class="mb-0">Visit Statistic</h4>
                                        <div>
                                            <select id="filterSelect">
                                                <option value="day">7 ngày qua</option>
                                                <option value="month">6 tháng qua</option>
                                                <option value="year">Theo năm</option>
                                            </select>
                                        </div>
                                    </div>
                                    <canvas id="visitChart"></canvas>
                                </div>
                            </div>
                        </div>



                        <div class="col-12 col-md-6 col-lg-6 col-xl-6">
                            <div class="card">
                                <div class="card-body">
                                    <div class="d-flex align-items-center justify-content-between mb-3">
                                        <h4 class="mb-0">Customer Registration Statistic</h4>
                                        <div>
                                            <label for="customerYearSelect" class="me-2">Select year:</label>
                                            <select id="customerYearSelect" class="form-control d-inline-block w-auto">
                                                <c:forEach var="year" begin="2020" end="2025">
                                                    <option value="${year}" ${year == 2025 ? 'selected' : ''}>${year}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <canvas id="customerChart"></canvas>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 col-md-6 col-lg-6 col-xl-6">
                            <div class="card">
                                <div class="card-body">
                                    <div class="d-flex align-items-center justify-content-between mb-3">
                                        <h4 class="mb-0">Appointment Statistic</h4>
                                        <div>
                                            <label for="appointmentYearSelect" class="me-2">Select year:</label>
                                            <select id="appointmentYearSelect" class="form-control d-inline-block w-auto">
                                                <c:forEach var="year" begin="2020" end="2025">
                                                    <option value="${year}" ${year == 2025 ? 'selected' : ''}>${year}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <canvas id="appointmentChart"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-12 col-md-6 col-lg-8 col-xl-8">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title d-inline-block">Upcoming Appointments</h4> 
                                    <!--<a href="appointments.html" class="btn btn-primary float-right">View all</a>-->
                                </div>
                                <div class="card-body p-0">
                                    <div class="table-responsive" style="max-height: 350px; overflow-y: auto;">
                                        <table class="table mb-0">
                                            <thead class="d-none">
                                                <tr>
                                                    <th>Patient Name</th>
                                                    <th>Doctor Name</th>
                                                    <th>Timing</th>
                                                    <th class="text-right">Status</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="apm" items="${listAppointment}">
                                                    <tr>
                                                        <td style="min-width: 200px;">
                                                            <img width="40" height="40" class="rounded-circle" src="pictureprofile?customerID=${apm.customerId.customerID}" alt=""> 
                                                            <h2>${apm.customerId.fullName}<span>${apm.customerId.address}</span></h2>
                                                        </td>                 
                                                        <td>
                                                            <h5 class="time-title p-0">Appointment With</h5>
                                                            <p>${apm.consultantId.fullName}</p>
                                                        </td>
                                                        <td>
                                                            <h5 class="time-title p-0">Time</h5>
                                                            <p>${apm.examinationDate}</p>
                                                        </td>
                                                        <!--                                                        <td class="text-right">
                                                                                                                    <a href="appointments.html" class="btn btn-outline-primary take-btn">Take up</a>
                                                                                                                </td>-->
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 col-md-6 col-lg-4 col-xl-4">
                            <div class="card member-panel">
                                <div class="card-header bg-white">
                                    <h4 class="card-title mb-0">Doctors</h4>
                                </div>
                                <div class="card-body">
                                    <ul class="contact-list">
                                        <c:forEach var="doc" items="${listDoctor}">
                                            <li>
                                                <div class="contact-cont">
                                                    <div class="float-left user-img m-r-10">
                                                        <a href="profile.html" title="${doc.getName()}">
                                                            <img src="assets/img/user.jpg" alt="" class="w-40 rounded-circle">
                                                            <!--<span class="status online"></span>-->
                                                        </a>
                                                    </div>
                                                    <div class="contact-info">
                                                        <span class="contact-name text-ellipsis">${doc.getName()}</span>
                                                        <span class="contact-date">${doc.qualification}</span>
                                                    </div>
                                                </div>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-12 col-md-6 col-lg-8 col-xl-8">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title d-inline-block">New Patients </h4> 
                                    <!--<a href="patients.html" class="btn btn-primary float-right">View all</a>-->
                                </div>
                                <div class="card-block">
                                    <div class="table-responsive">
                                        <table class="table mb-0 new-patient-table">
                                            <tbody>
                                                <c:forEach var="cus" items="${listCustomer}">
                                                    <tr>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${cus.customerID != null}">                                                
                                                                    <img width="28" height="28" class="rounded-circle" src="pictureprofile?customerID=${cus.customerID}" alt=""> 
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <img width="28" height="28" class="rounded-circle" src="assets/img/user.jpg" alt=""> 
                                                                </c:otherwise>
                                                            </c:choose>                                                                    
                                                            <h2>${cus.fullName}</h2>
                                                        </td>
                                                        <td>${cus.email}</td>
                                                        <td>${cus.phone}</td>
                                                        <td>${cus.gender}</td>
                                                        <!--<td><button class="btn btn-primary btn-primary-four float-right">Fever</button></td>-->
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="notification-box">
                    <div class="msg-sidebar notifications msg-noti">
                        <div class="topnav-dropdown-header">
                            <span>Messages</span>
                        </div>
                        <div class="drop-scroll msg-list-scroll" id="msg_list">
                            <ul class="list-box">
                                <li>
                                    <a href="chat.html">
                                        <div class="list-item">
                                            <div class="list-left">
                                                <span class="avatar">R</span>
                                            </div>
                                            <div class="list-body">
                                                <span class="message-author">Richard Miles </span>
                                                <span class="message-time">12:28 AM</span>
                                                <div class="clearfix"></div>
                                                <span class="message-content">Lorem ipsum dolor sit amet, consectetur adipiscing</span>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <a href="chat.html">
                                        <div class="list-item new-message">
                                            <div class="list-left">
                                                <span class="avatar">J</span>
                                            </div>
                                            <div class="list-body">
                                                <span class="message-author">John Doe</span>
                                                <span class="message-time">1 Aug</span>
                                                <div class="clearfix"></div>
                                                <span class="message-content">Lorem ipsum dolor sit amet, consectetur adipiscing</span>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <a href="chat.html">
                                        <div class="list-item">
                                            <div class="list-left">
                                                <span class="avatar">T</span>
                                            </div>
                                            <div class="list-body">
                                                <span class="message-author"> Tarah Shropshire </span>
                                                <span class="message-time">12:28 AM</span>
                                                <div class="clearfix"></div>
                                                <span class="message-content">Lorem ipsum dolor sit amet, consectetur adipiscing</span>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <a href="chat.html">
                                        <div class="list-item">
                                            <div class="list-left">
                                                <span class="avatar">M</span>
                                            </div>
                                            <div class="list-body">
                                                <span class="message-author">Mike Litorus</span>
                                                <span class="message-time">12:28 AM</span>
                                                <div class="clearfix"></div>
                                                <span class="message-content">Lorem ipsum dolor sit amet, consectetur adipiscing</span>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <a href="chat.html">
                                        <div class="list-item">
                                            <div class="list-left">
                                                <span class="avatar">C</span>
                                            </div>
                                            <div class="list-body">
                                                <span class="message-author"> Catherine Manseau </span>
                                                <span class="message-time">12:28 AM</span>
                                                <div class="clearfix"></div>
                                                <span class="message-content">Lorem ipsum dolor sit amet, consectetur adipiscing</span>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <a href="chat.html">
                                        <div class="list-item">
                                            <div class="list-left">
                                                <span class="avatar">D</span>
                                            </div>
                                            <div class="list-body">
                                                <span class="message-author"> Domenic Houston </span>
                                                <span class="message-time">12:28 AM</span>
                                                <div class="clearfix"></div>
                                                <span class="message-content">Lorem ipsum dolor sit amet, consectetur adipiscing</span>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <a href="chat.html">
                                        <div class="list-item">
                                            <div class="list-left">
                                                <span class="avatar">B</span>
                                            </div>
                                            <div class="list-body">
                                                <span class="message-author"> Buster Wigton </span>
                                                <span class="message-time">12:28 AM</span>
                                                <div class="clearfix"></div>
                                                <span class="message-content">Lorem ipsum dolor sit amet, consectetur adipiscing</span>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <a href="chat.html">
                                        <div class="list-item">
                                            <div class="list-left">
                                                <span class="avatar">R</span>
                                            </div>
                                            <div class="list-body">
                                                <span class="message-author"> Rolland Webber </span>
                                                <span class="message-time">12:28 AM</span>
                                                <div class="clearfix"></div>
                                                <span class="message-content">Lorem ipsum dolor sit amet, consectetur adipiscing</span>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <a href="chat.html">
                                        <div class="list-item">
                                            <div class="list-left">
                                                <span class="avatar">C</span>
                                            </div>
                                            <div class="list-body">
                                                <span class="message-author"> Claire Mapes </span>
                                                <span class="message-time">12:28 AM</span>
                                                <div class="clearfix"></div>
                                                <span class="message-content">Lorem ipsum dolor sit amet, consectetur adipiscing</span>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <a href="chat.html">
                                        <div class="list-item">
                                            <div class="list-left">
                                                <span class="avatar">M</span>
                                            </div>
                                            <div class="list-body">
                                                <span class="message-author">Melita Faucher</span>
                                                <span class="message-time">12:28 AM</span>
                                                <div class="clearfix"></div>
                                                <span class="message-content">Lorem ipsum dolor sit amet, consectetur adipiscing</span>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <a href="chat.html">
                                        <div class="list-item">
                                            <div class="list-left">
                                                <span class="avatar">J</span>
                                            </div>
                                            <div class="list-body">
                                                <span class="message-author">Jeffery Lalor</span>
                                                <span class="message-time">12:28 AM</span>
                                                <div class="clearfix"></div>
                                                <span class="message-content">Lorem ipsum dolor sit amet, consectetur adipiscing</span>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <a href="chat.html">
                                        <div class="list-item">
                                            <div class="list-left">
                                                <span class="avatar">L</span>
                                            </div>
                                            <div class="list-body">
                                                <span class="message-author">Loren Gatlin</span>
                                                <span class="message-time">12:28 AM</span>
                                                <div class="clearfix"></div>
                                                <span class="message-content">Lorem ipsum dolor sit amet, consectetur adipiscing</span>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <a href="chat.html">
                                        <div class="list-item">
                                            <div class="list-left">
                                                <span class="avatar">T</span>
                                            </div>
                                            <div class="list-body">
                                                <span class="message-author">Tarah Shropshire</span>
                                                <span class="message-time">12:28 AM</span>
                                                <div class="clearfix"></div>
                                                <span class="message-content">Lorem ipsum dolor sit amet, consectetur adipiscing</span>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <div class="topnav-dropdown-footer">
                            <a href="chat.html">See all messages</a>
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
        <script src="assets/js/Chart.bundle.js"></script>
        <script src="assets/js/app.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <!-- 2 cái bảng thống kê-->
        <!--<script src="assets/js/chart.js"></script>-->


        <!--Biểu đồ thống kê lượng đăng ký tài khoản của khách hàng  -->
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const yearSelect = document.getElementById("customerYearSelect");
                var ctx = document.getElementById("customerChart").getContext("2d");
                var customerChart = new Chart(ctx, {
                    type: "bar",
                    data: {
                        labels: [],
                        datasets: [{
                                label: "Số khách hàng đăng ký tài khoản",
                                data: [],
                                backgroundColor: 'rgba(0, 158, 251, 0.5)',
                                borderColor: 'rgba(0, 158, 251, 1)',
                                borderWidth: 1}]
                    }
                });
                // Tải dữ liệu mặc định khi trang load
                loadCustomerData(yearSelect.value || 2025);
                // Lắng nghe sự kiện thay đổi năm
                yearSelect.addEventListener("change", function () {
                    loadCustomerData(yearSelect.value);
                });

                function loadCustomerData(year) {
                    fetch("/SWP/dashboardCustomer?year=" + year)
                            .then(response => response.json())
                            .then(data => {
                                console.log("Dữ liệu nhận được:", data);
                                if (Array.isArray(data.customerCount)) {
                                    customerChart.data.labels = data.month.map(m => "" + m);
                                    customerChart.data.datasets[0].data = data.customerCount;
                                    customerChart.update();
                                } else {
                                    console.error("Dữ liệu không hợp lệ:", data);
                                }
                            })
                            .catch(error => console.error("Lỗi API:", error));
                }
            });
        </script>

        <!--Biểu đồ thống kê lịch đặt khám  -->
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const yearSelect = document.getElementById("appointmentYearSelect");
                // Tạo biểu đồ đường (line chart)
                var ctx = document.getElementById("appointmentChart").getContext("2d");
                var appointmentChart = new Chart(ctx, {
                    type: "line",
                    data: {
                        labels: [],
                        datasets: [{
                                label: "Số cuộc hẹn khám bệnh",
                                data: [],
                                borderColor: "red",
                                backgroundColor: "rgba(255, 0, 0, 0.2)", // Màu nền trong suốt
                                fill: true, // Hiển thị vùng dưới đường
                                tension: 0.4 // Làm mềm đường kẻ
                            }]
                    },
                    options: {
                        responsive: true,
                        plugins: {
                            legend: {
                                display: true
                            }
                        },
                        scales: {
                            x: {
                                title: {
                                    display: true,
                                    text: "Tháng"
                                }
                            },
                            y: {
                                beginAtZero: true,
                                title: {
                                    display: true,
                                    text: "Số cuộc hẹn"
                                }
                            }
                        }
                    }
                });

                // Tải dữ liệu mặc định khi trang load
                loadAppointmentData(yearSelect.value || 2025);

                // Lắng nghe sự kiện thay đổi năm
                yearSelect.addEventListener("change", function () {
                    loadAppointmentData(yearSelect.value);
                });

                function loadAppointmentData(year) {
                    fetch("/SWP/dashboardAppointment?year=" + year)
                            .then(response => response.json())
                            .then(data => {
                                console.log("Dữ liệu nhận được:", data);
                                if (Array.isArray(data.appointmentCount)) {
                                    appointmentChart.data.labels = data.month.map(m => "" + m);//truc x
                                    appointmentChart.data.datasets[0].data = data.appointmentCount;//truc y
                                    appointmentChart.update();
                                } else {
                                    console.error("Dữ liệu không hợp lệ:", data);
                                }
                            }).catch(error => console.error("Lỗi API:", error));
                }
            });
        </script>

        <!--Biểu đồ thống kê lượng truy cập  -->
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                loadChart("day"); // Mặc định là 7 ngày gần nhất
                document.getElementById("filterSelect").addEventListener("change", function () {
                    loadChart(this.value);//chon trong dropdown thi loadChart dc goi voi fiterType duoc chon
                });
            });

            function loadChart(filterType) {
                fetch("visit-chart?filter=" + filterType)
                        .then(response => response.json())
                        .then(data => {
                            const labels = data.map(item => item.date);//ngay
                            const counts = data.map(item => item.count);//so lg truy cap 
                            const ctx = document.getElementById("visitChart").getContext("2d");
                            if (window.myChart)
                                window.myChart.destroy(); // Xóa biểu đồ cũ

                            window.myChart = new Chart(ctx, {
                                type: "line", // Thay bằng "line" nếu muốn biểu đồ đường, "bar" nếu cột
                                data: {
                                    labels: labels,
                                    datasets: [{
                                            label: "Lượt truy cập",
                                            data: counts,
                                            borderColor: "blue",
                                            backgroundColor: "rgba(54, 162, 235, 0.2)",
                                            borderWidth: 2
                                        }]
                                },
                                options: {
                                    responsive: true,
                                    scales: {
                                        x: {title: {display: true, text: filterType === "day" ? "Ngày" : (filterType === "month" ? "Tháng" : "Năm")}},
                                        y: {title: {display: true, text: "Số lượt truy cập"}, beginAtZero: true}
                                    }
                                }
                            });
                        })
                        .catch(error => console.error("Lỗi tải dữ liệu:", error));
            }
        </script>
    </body>


    <!-- index22:59-->
</html>
