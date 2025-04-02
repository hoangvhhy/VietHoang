<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Service Management</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
            body {
                background-color: #f8f9fa;
                font-family: Arial, sans-serif;
            }
            .container {
                background: white;
                padding: 30px;
                border-radius: 12px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            }
            h2 {
                color: #007bff;
                text-shadow: 1px 1px 2px rgba(0, 123, 255, 0.5);
            }
            .service-image {
                width: 80px;
                height: 50px;
                object-fit: cover;
            }
            .add-btn {
                margin-top: 10px;
            }
            .form-btns {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            /* New styles to fix sidebar layout issue */
            .main-content {
                margin-left: 250px; /* Adjust this value based on your sidebar width */
                transition: margin-left 0.3s;
                padding: 15px;
            }
            @media (max-width: 768px) {
                .main-content {
                    margin-left: 0;
                }
            }
        </style>
    </head>
    <body>
        <div class="main-wrapper">
            <jsp:include page="headerStaff.jsp"></jsp:include>
            <jsp:include page="sidebar.jsp"></jsp:include>
                <div class="page-wrapper">

                    <div class="container mt-5">
                        <h2 class="text-center">Service Management</h2>

                        <!-- Search Form (Tìm kiếm) -->
                        <form action="loadmanage" method="get" class="row g-2 mb-4" onsubmit="return validateSearch()">
                            <div class="col-md-4 ms-auto d-flex">
                                <input type="text" class="form-control me-2" id="search" name="searchKeyword" value="${param.search}" placeholder="Enter service name">
                            <button type="submit" name="action" value="search" class="btn btn-primary">Search</button>
                        </div>
                    </form>

                    <!-- Filter Form (Lọc) và Nút Add -->
                    <div class="form-btns mb-4">
                        <!-- Filter Form -->
                        <form action="loadmanage" method="get" class="row g-2 flex-grow-1">
                            <div class="col-md-3 d-flex align-items-end">
                                <div>
                                    <label for="sortBy" class="form-label">Sort By</label>
                                    <select name="sortBy" id="sortBy" class="form-select">
                                        <option value="">Chọn tiêu chí</option>
                                        <option value="price_asc" ${param.sortBy == 'price_asc' ? 'selected' : ''}>Giá tăng dần</option>
                                        <option value="price_desc" ${param.sortBy == 'price_desc' ? 'selected' : ''}>Giá giảm dần</option>
                                        <option value="vip" ${param.sortBy == 'vip' ? 'selected' : ''}>Loại VIP</option>
                                        <option value="basic" ${param.sortBy == 'basic' ? 'selected' : ''}>Loại cơ bản</option>
                                        <option value="duration_asc" ${param.sortBy == 'duration_asc' ? 'selected' : ''}>Thời gian tăng dần</option>
                                        <option value="duration_desc" ${param.sortBy == 'duration_desc' ? 'selected' : ''}>Thời gian giảm dần</option>
                                        <option value="status_on" ${param.sortBy == 'status_on' ? 'selected' : ''}>Status On</option>
                                        <option value="status_off" ${param.sortBy == 'status_off' ? 'selected' : ''}>Status Off</option>
                                    </select>
                                </div>
                                <button type="submit" name="action" value="filter" class="btn btn-primary ms-2">Lọc</button>
                            </div>
                        </form>

                        <!-- Nút Add New Service -->
                        <div class="add-btn ms-3">
                            <a href="add_Service.jsp" class="btn btn-success">Add New Service</a>
                        </div>
                    </div>

                    <!-- Bảng hiển thị danh sách dịch vụ -->
                    <table class="table table-bordered table-hover">
                        <!-- Table content unchanged -->
                        <thead class="table-primary">
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Description</th>
                                <th>Image</th>
                                <th>Type</th>
                                <th>Price ($)</th>
                                <th>Duration (min)</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="service" items="${serviceList}">
                                <tr>
                                    <td>${service.packageID}</td>
                                    <td><c:out value="${service.packageName}"></c:out></td>
                                    <td><c:out value="${service.description}"></c:out></td>
                                        <td>
                                            <img src="getimage?packageID=${service.packageID}" alt="" class="img-fluid service-image">
                                    </td>
                                    <td>${service.type}</td>
                                    <td>${service.price}</td>
                                    <td>${service.duration}</td>
                                    <td>
                                        <a href="loadupdate?packageID=${service.packageID}" class="btn btn-warning btn-sm">Update</a>
                                        <c:choose>
                                            <c:when test="${service.status == 'on'}">
                                                <a href="editstatus?packageID=${service.packageID}&status=${service.status}" class="btn btn-success btn-sm" onclick="return confirm('Are you sure?')">On</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="editstatus?packageID=${service.packageID}&status=${service.status}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?')">Off</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- Pagination - unchanged -->
                    <div class="row">
                        <div class="col-12">
                            <nav aria-label="Page navigation">
                                <ul class="pagination justify-content-center">
                                    <!-- Nút Previous -->
                                    <c:if test="${pageNumber > 1}">
                                        <li class="page-item">
                                            <a class="page-link" href="loadmanage?pageNumber=${pageNumber - 1}&searchKeyword=${param.searchKeyword}&sortBy=${param.sortBy}&action=${param.action}" aria-label="Previous">
                                                <span aria-hidden="true">&laquo;</span>
                                            </a>
                                        </li>
                                    </c:if>

                                    <!-- Xác định startPage và endPage -->
                                    <c:set var="startPage" value="${pageNumber - 2}" />
                                    <c:if test="${startPage < 1}">
                                        <c:set var="startPage" value="1" />
                                    </c:if>
                                    <c:set var="endPage" value="${pageNumber + 2}" />
                                    <c:if test="${endPage > totalPages}">
                                        <c:set var="endPage" value="${totalPages}" />
                                    </c:if>

                                    <!-- Nếu startPage lớn hơn 1, hiển thị nút trang 1 và dấu "..." -->
                                    <c:if test="${startPage > 1}">
                                        <li class="page-item">
                                            <a class="page-link" href="loadmanage?pageNumber=1&searchKeyword=${param.searchKeyword}&sortBy=${param.sortBy}&action=${param.action}">1</a>
                                        </li>
                                        <c:if test="${startPage > 2}">
                                            <li class="page-item disabled"><span class="page-link">...</span></li>
                                            </c:if>
                                        </c:if>

                                    <!-- Hiển thị các trang từ startPage đến endPage -->
                                    <c:forEach var="i" begin="${startPage}" end="${endPage}">
                                        <li class="page-item ${i == pageNumber ? 'active' : ''}">
                                            <a class="page-link" href="loadmanage?pageNumber=${i}&searchKeyword=${param.searchKeyword}&sortBy=${param.sortBy}&action=${param.action}">${i}</a>
                                        </li>
                                    </c:forEach>

                                    <!-- Nếu endPage nhỏ hơn totalPages, hiển thị dấu "..." và nút trang cuối -->
                                    <c:if test="${endPage < totalPages}">
                                        <c:if test="${endPage < totalPages - 1}">
                                            <li class="page-item disabled"><span class="page-link">...</span></li>
                                            </c:if>
                                        <li class="page-item">
                                            <a class="page-link" href="loadmanage?pageNumber=${totalPages}&searchKeyword=${param.searchKeyword}&sortBy=${param.sortBy}&action=${param.action}">${totalPages}</a>
                                        </li>
                                    </c:if>

                                    <!-- Nút Next -->
                                    <c:if test="${pageNumber < totalPages}">
                                        <li class="page-item">
                                            <a class="page-link" href="loadmanage?pageNumber=${pageNumber + 1}&searchKeyword=${param.searchKeyword}&sortBy=${param.sortBy}&action=${param.action}" aria-label="Next">
                                                <span aria-hidden="true">&raquo;</span>
                                            </a>
                                        </li>
                                    </c:if>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </body>
    <script src="assets/js/jquery-3.2.1.min.js"></script>
    <script src="assets/js/popper.min.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
    <script src="assets/js/jquery.slimscroll.js"></script>
    <script src="assets/js/Chart.bundle.js"></script>
    <script src="assets/js/app.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
                                            function validateSearch() {
                                                let searchInput = document.getElementById("search");
                                                searchInput.value = searchInput.value.trim(); // Xóa khoảng trắng

                                                if (searchInput.value === "") {
                                                    alert("Vui lòng nhập từ khóa tìm kiếm.");
                                                    return false;
                                                }
                                                return true;
                                            }
    </script>
</html>