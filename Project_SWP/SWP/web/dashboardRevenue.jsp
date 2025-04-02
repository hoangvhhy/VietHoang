<%-- 
    Document   : dashboardRevenue
    Created on : Mar 17, 2025, 12:54:49 AM
    Author     : Gigabyte
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">
        <title>Preclinic - Medical & Hospital - Bootstrap 4 Admin Template</title>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
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
                                    <span class="dash-widget-bg1"><i class="fa fa-file-invoice-dollar"></i></span>
                                    <div class="dash-widget-info text-right">
                                        <h3>${sumInvoice}</h3>
                                    <span class="widget-title1">Sum Invoice <i class="fa fa-check"></i></span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 col-sm-6 col-lg-6 col-xl-3">
                            <div class="dash-widget">
                                <span class="dash-widget-bg2"><i class="fa fa-money-bill"></i></span>
                                <div class="dash-widget-info text-right">
                                    <h3>${sumAmount}$</h3>
                                    <span class="widget-title2">Sum Revenue <i class="fa fa-check"></i></span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 col-sm-6 col-lg-6 col-xl-3">
                            <div class="dash-widget">
                                <span class="dash-widget-bg3"><i class="fa fa-check-circle"></i></span>
                                <div class="dash-widget-info text-right">
                                    <h3>${numberPaid}</h3>
                                    <span class="widget-title3">Paid <i class="fa fa-check"></i></span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 col-sm-6 col-lg-6 col-xl-3">
                            <div class="dash-widget">
                                <span class="dash-widget-bg4"><i class="fa fa-hourglass-half"></i></span>
                                <div class="dash-widget-info text-right">
                                    <h3>${numberPending}</h3>
                                    <span class="widget-title4">Pending <i class="fa fa-check"></i></span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Biểu đồ -->
                    <div class="row">
                        <!-- Biểu đồ Doanh thu theo tháng -->
                        <div class="col-12 col-md-6">
                            <div class="card">
                                <div class="card-body">
                                    <div class="d-flex align-items-center justify-content-between mb-3">
                                        <h4 class="mb-0">Revenue statistic</h4>
                                        <div>
                                            <label for="yearSelect" class="me-2">Select year:</label>
                                            <select id="yearSelect" class="form-control d-inline-block w-auto">
                                                <c:forEach var="year" begin="2020" end="2025">
                                                    <option value="${year}" ${year == 2025 ? 'selected' : ''}>${year}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <canvas id="revenueChart"></canvas>
                                </div>
                            </div>
                        </div>

                        <!-- Biểu đồ Tình trạng hóa đơn -->
                        <div class="col-12 col-md-6">
                            <div class="card">
                                <div class="card-body">
                                    <div class="d-flex align-items-center justify-content-between mb-3">
                                        <h4 class="mb-0">Invoice Status</h4>
                                        <ul class="list-inline mb-0">
                                            <li class="list-inline-item"><i class="fa fa-circle text-success"></i> Paid</li>
                                            <li class="list-inline-item"><i class="fa fa-circle text-danger"></i> Pending</li>
                                        </ul>
                                    </div>
                                    <canvas id="invoiceStatusChart"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-body">
                                    <h4 class="mb-3">Recent Invoice</h4>
                                    <table class="table table-striped">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Customer</th>
                                                <th>Created Date</th>
                                                <th>Total Amount</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>
                                        <tbody >
                                            <c:forEach var="invoice" items="${listInvoice}">
                                                <tr>
                                                    <td>${invoice.invoiceID}</td>
                                                    <td>${invoice.examinationID.customerId.fullName}</td>
                                                    <td>${invoice.createdAt}</td>
                                                    <td>${invoice.totalAmount}</td>
                                                    <td>${invoice.paymentStatus}</td>
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


            <script src="assets/js/jquery-3.2.1.min.js"></script>
            <script src="assets/js/popper.min.js"></script>
            <script src="assets/js/bootstrap.min.js"></script>
            <script src="assets/js/jquery.slimscroll.js"></script>
            <script src="assets/js/Chart.bundle.js"></script>
            <script src="assets/js/chart.js"></script>
            <script src="assets/js/app.js"></script>
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    // Lấy thẻ select
                    const yearSelect = document.getElementById("yearSelect");

                    // Gọi dữ liệu mặc định khi trang load (ví dụ mặc định là 2025)
                    loadRevenueData(yearSelect.value || 2025);

                    // Lắng nghe sự kiện khi người dùng thay đổi năm
                    yearSelect.addEventListener("change", function () {
                        const selectedYear = yearSelect.value;
                        loadRevenueData(selectedYear); // Gọi API với năm mới
                    });
                });
                document.addEventListener("DOMContentLoaded", function () {
                    var ctx = document.getElementById("revenueChart").getContext("2d");
                    revenueChart = new Chart(ctx, {
                        type: "line",
                        data: {
                            labels: [], // Ban đầu để trống, sẽ cập nhật sau
                            datasets: [{
                                    label: "Amount",
                                    data: [],
                                    borderColor: "blue",
                                    fill: false
                                }]
                        }
                    });

                });

                function loadRevenueData(year) {
                    fetch("/SWP/dashboardRevenue?year=" + year)
                            .then(response => response.json())
                            .then(data => {
                                console.log("Dữ liệu nhận được:", data);

                                if (Array.isArray(data.revenue)) {
                                    revenueChart.data.labels = data.month;
                                    revenueChart.data.datasets[0].data = data.revenue.map(value => value || 0); // Convert null thành 0
                                    revenueChart.update();
                                } else {
                                    console.error("Dữ liệu revenue không hợp lệ:", data.revenue);
                                }
                            })
                            .catch(error => console.error("Lỗi lấy dữ liệu từ API:", error));
                }
            </script>
            <script>
                document.addEventListener("DOMContentLoaded", function () {


                    var ctx2 = document.getElementById("invoiceStatusChart").getContext("2d");
                    var invoiceStatusChart = new Chart(ctx2, {
                        type: "pie",
                        data: {
                            labels: ["Paid", "Pending"],
                            datasets: [{
                                    data: [${requestScope.numberPaid}, ${requestScope.numberPending}],
                                    backgroundColor: ["green", "red"]
                                }]
                        }
                    });
                });
            </script>


        </div>
    </body>
</html>

