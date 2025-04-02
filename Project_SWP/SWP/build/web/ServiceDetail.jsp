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
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
            }
            .div_board {
                display: flex;
                justify-content: flex-start;
                gap: 10px;
                background-color: #ffffff;
                padding: 10px 15px;
                border-radius: 8px;
                flex-wrap: wrap;
            }
            .item {
                padding: 8px 15px;
                border-radius: 5px;
                cursor: pointer;
                background-color: #ffffff;
                color: #007bff;
                font-size: 14px;
                font-weight: bold;
                text-transform: uppercase;
                transition: all 0.3s ease;
                border: 1px solid #0056b3;
            }
            .item:hover, .item.active {
                background-color: #ffcc00;
                color: #000;
                border: 1px solid #d4a100;
            }
            #service-detail {
                background: #ffffff;
                padding: 25px;
                border-radius: 10px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
                margin-top: 20px;
            }
            .service-img {
                width: 100%;
                max-width: 500px;
                height: auto;
                border-radius: 10px;
                box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.2);
            }
            .info {
                font-size: 16px;
                margin: 10px 0;
            }
            .info span {
                font-weight: bold;
                color: #333;
            }
            .btn-custom {
                padding: 10px 15px;
                font-size: 16px;
                border-radius: 5px;
            }
            .section{
                padding: 0px 0;
            }
        </style>
    </head>
    <body>
        <jsp:include page="headerHome.jsp"></jsp:include>
            <!-- Banner -->
            <section class="page-title bg-1">
                <div class="overlay"></div>
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="block text-center">
                                <span class="text-white">Our Service</span>
                                <h1 class="text-capitalize mb-5 text-lg">Detail Service</h1>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <!-- Menu Tabs -->
            <div class="container mt-4">
                <div class="col-xs-12">
                    <div class="div_board">
                        <span class="item active" data-target="introduce-section">Giới thiệu</span>
                            <span class="item" data-target="service-detail">Chi Tiết Dịch Vụ</span>
                    </div>
                </div>
            </div>


            <!-- Mô tả dịch vụ (introduce) -->
            <div class="container" id="introduce-section" style="display: block;">
                <div class="blog-content" style="max-width: 800px; margin: 0 auto; text-align: justify; padding: 15px;">
                    <div class="blog-detail-content" style="word-wrap: break-word; overflow-wrap: break-word; overflow: hidden; text-overflow: ellipsis;">
                    ${service.introduce}
                </div>
            </div>
        </div>

        
        

        <!-- Chi Tiết Dịch Vụ -->
        <div class="container" id="service-detail" style="display: none;">
            <h2 class="text-center mb-4">Chi Tiết Dịch Vụ</h2>
            <div class="text-center mb-3">
                <img src="getimage?packageID=${service.packageID}" alt="Service Image" class="service-img">
            </div>
            <div class="info"><span>Tên Gói:</span> ${service.packageName}</div>
            <div class="info"><span>Loại Dịch Vụ:</span> ${service.type}</div>
            <div class="info"><span>Giá:</span> $${service.price}</div>
            <div class="info"><span>Thời Gian:</span> ${service.duration} phút</div>
            <div class="text-center mt-3">
                <a href="appointment" class="btn btn-primary btn-custom">Đặt Dịch Vụ</a>
                <a href="loadservice" class="btn btn-secondary btn-custom">Quay Lại Danh Sách</a>
            </div>
        </div>


        <jsp:include page="footer.jsp"></jsp:include>

        <!-- Scripts -->
        <script src="plugins/jquery/jquery.js"></script>
        <script src="plugins/bootstrap/js/bootstrap.min.js"></script>
        <script src="plugins/slick-carousel/slick/slick.min.js"></script>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const items = document.querySelectorAll(".item");
                const sections = document.querySelectorAll(".container[id]");

                items.forEach(item => {
                    item.addEventListener("click", function () {
                        items.forEach(i => i.classList.remove("active"));
                        this.classList.add("active");

                        // Ẩn tất cả các section trước khi hiển thị
                        sections.forEach(sec => sec.style.display = "none");

                        // Hiển thị section tương ứng với tab được click
                        const target = this.getAttribute("data-target");
                        document.getElementById(target).style.display = "block";
                    });
                });
            });

        </script>

    </body>
</html>