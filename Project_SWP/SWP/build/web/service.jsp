<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="zxx">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="description" content="Health & Care Medical template">
        <meta name="author" content="themefisher.com">
        <title>Novena - Health & Care</title>

        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="/images/favicon.ico" />

        <!-- CSS -->
        <link rel="stylesheet" href="plugins/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="plugins/icofont/icofont.min.css">
        <link rel="stylesheet" href="plugins/slick-carousel/slick/slick.css">
        <link rel="stylesheet" href="plugins/slick-carousel/slick/slick-theme.css">
        <link rel="stylesheet" href="css/style.css">

        <!-- Custom CSS -->
        <style>
            .image-container {
                width: 100%;
                height: 250px;
                overflow: hidden;
                display: flex;
                align-items: center;
                justify-content: center;
                background-color: #f8f9fa;
                border-radius: 10px;
            }
            .image-container img {
                width: 100%;
                height: 100%;
                object-fit: cover;
            }
            .badge {
                padding: 8px 16px;
                font-size: 14px;
                border-radius: 12px;
            }
            .pagination {
                justify-content: center;
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
                                <span class="text-white">Our Service</span>
                                <h1 class="text-capitalize mb-5 text-lg">What we do</h1>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <!-- Phần lọc và tìm kiếm -->
            <section class="container mt-4">
                <form action="loadservice" method="get" class="row align-items-center" onsubmit="return validateForm(event);">
                    <!-- Nhóm lọc -->
                    <div class="col-md-6">
                        <div class="row g-2 align-items-center">
                            <div class="col-auto">
                                <label for="sortBy" class="fw-bold">Lọc dịch vụ:</label>
                            </div>
                            <div class="col-auto">
                                <select name="sortBy" id="sortBy" class="form-select">
                                    <option value="reload">Chọn tiêu chí</option>
                                    <option value="price_asc" ${param.sortBy == 'price_asc' ? 'selected' : ''}>Giá tăng dần</option>
                                <option value="price_desc" ${param.sortBy == 'price_desc' ? 'selected' : ''}>Giá giảm dần</option>
                                <option value="vip" ${param.sortBy == 'vip' ? 'selected' : ''}>Loại VIP</option>
                                <option value="basic" ${param.sortBy == 'basic' ? 'selected' : ''}>Loại cơ bản</option>
                                <option value="duration_asc" ${param.sortBy == 'duration_asc' ? 'selected' : ''}>Thời gian tăng dần</option>
                                <option value="duration_desc" ${param.sortBy == 'duration_desc' ? 'selected' : ''}>Thời gian giảm dần</option>
                            </select>
                        </div>
                        <div class="col-auto">
                            <button type="submit" name="action" value="filter" class="btn btn-primary">Lọc</button>
                        </div>
                    </div>
                </div>
                <!-- Nhóm tìm kiếm -->
                <div class="col-md-6">
                    <div class="row g-2 align-items-center justify-content-end">
                        <div class="col-auto">
                            <input type="text" name="searchKeyword" id="searchKeyword" class="form-control" placeholder="Tìm kiếm theo tên dịch vụ" value="${param.searchKeyword}">
                        </div>
                        <div class="col-auto">
                            <button type="submit" name="action" value="search" class="btn btn-primary">Search</button>
                        </div>
                    </div>
                </div>
            </form>
        </section>

        <!-- Danh sách dịch vụ -->
        <section class="section service-2">
            <div class="container">
                <div class="row">
                    <c:forEach var="service" items="${serviceList}">
                        <div class="col-lg-4 col-md-6 col-sm-6">
                            <div class="service-block mb-5">
                                <div class="image-container">
                                    <img src="${empty service.packageID ? 'images/default-image.jpg' : 'getimage?packageID='}${service.packageID}" alt="Service Image">
                                </div>
                                <div class="content">
                                    <h4 class="mt-4 mb-2 title-color">
                                        <a href="loadservicedetail?packageID=${service.packageID}" class="text-decoration-none text-primary">
                                            ${service.packageName}
                                        </a>
                                    </h4>
                                    <p class="mb-4">Giá: $${service.price}</p>
                                    <p class="mb-4">Thời gian: ${service.duration} phút</p>
                                    <p class="mb-4">Loại dịch vụ: 
                                        <span class="badge ${service.type == 'VIP' ? 'bg-danger text-white' : 'bg-success text-white'}">
                                            ${service.type}
                                        </span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <!-- Pagination -->
                <div class="row">
                    <div class="col-12">
                        <nav aria-label="Page navigation">
                            <ul class="pagination justify-content-center">
                                <!-- Nút Previous -->
                                <c:if test="${pageNumber > 1}">
                                    <li class="page-item">
                                        <a class="page-link" href="loadservice?pageNumber=${pageNumber - 1}&searchKeyword=${param.searchKeyword}&sortBy=${param.sortBy}&action=${param.action}" aria-label="Previous">
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
                                        <a class="page-link" href="loadservice?pageNumber=1&searchKeyword=${param.searchKeyword}&sortBy=${param.sortBy}&action=${param.action}">1</a>
                                    </li>
                                    <c:if test="${startPage > 2}">
                                        <li class="page-item disabled"><span class="page-link">...</span></li>
                                        </c:if>
                                    </c:if>

                                <!-- Hiển thị các trang từ startPage đến endPage -->
                                <c:forEach var="i" begin="${startPage}" end="${endPage}">
                                    <li class="page-item ${i == pageNumber ? 'active' : ''}">
                                        <a class="page-link" href="loadservice?pageNumber=${i}&searchKeyword=${param.searchKeyword}&sortBy=${param.sortBy}&action=${param.action}">${i}</a>
                                    </li>
                                </c:forEach>

                                <!-- Nếu endPage nhỏ hơn totalPages, hiển thị dấu "..." và nút trang cuối -->
                                <c:if test="${endPage < totalPages}">
                                    <c:if test="${endPage < totalPages - 1}">
                                        <li class="page-item disabled"><span class="page-link">...</span></li>
                                        </c:if>
                                    <li class="page-item">
                                        <a class="page-link" href="loadservice?pageNumber=${totalPages}&searchKeyword=${param.searchKeyword}&sortBy=${param.sortBy}&action=${param.action}">${totalPages}</a>
                                    </li>
                                </c:if>

                                <!-- Nút Next -->
                                <c:if test="${pageNumber < totalPages}">
                                    <li class="page-item">
                                        <a class="page-link" href="loadservice?pageNumber=${pageNumber + 1}&searchKeyword=${param.searchKeyword}&sortBy=${param.sortBy}&action=${param.action}" aria-label="Next">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </li>
                                </c:if>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </section>

        <jsp:include page="footer.jsp"></jsp:include>

        <!-- Scripts -->
        <script src="plugins/jquery/jquery.js"></script>
        <script src="plugins/bootstrap/js/popper.js"></script>
        <script src="plugins/bootstrap/js/bootstrap.min.js"></script>
        <script src="plugins/slick-carousel/slick/slick.min.js"></script>
        <script src="js/script.js"></script>

        <!-- JavaScript validate form -->
        <script>
                    function validateForm(event) {
                        if (event.submitter && event.submitter.value === 'search') {
                            var searchInputField = document.getElementById("searchKeyword");
                            var searchInput = searchInputField.value.trim();
                            searchInputField.value = searchInput;
                            if (searchInput === "") {
                                alert("Vui lòng nhập từ khóa tìm kiếm.");
                                return false;
                            }
                        }
                        return true;
                    }
        </script>

    </body>
</html>
