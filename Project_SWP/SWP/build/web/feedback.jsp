<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" %>
<!DOCTYPE html>
<html lang="zxx">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="description" content="Orbitor,business,company,agency,modern,bootstrap4,tech,software">
        <meta name="author" content="themefisher.com">

        <title>Novena- Health & Care Medical template</title>

        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="/images/favicon.ico" />

        <!-- bootstrap.min css -->
        <link rel="stylesheet" href="plugins/bootstrap/css/bootstrap.min.css">
        <!-- Icon Font Css -->
        <link rel="stylesheet" href="plugins/icofont/icofont.min.css">
        <!-- Slick Slider  CSS -->
        <link rel="stylesheet" href="plugins/slick-carousel/slick/slick.css">
        <link rel="stylesheet" href="plugins/slick-carousel/slick/slick-theme.css">

        <!-- Main Stylesheet -->
        <link rel="stylesheet" href="css/style.css">
        <style>
            .feed {
                /*font-family: Arial, sans-serif;*/
                text-align: center;
                /*padding: 20px;*/
            }

            /* Nút mở feedback & về home */
            .open-feedback, .back-home {
                background-color: #007bff;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                transition: background 0.3s;
                margin: 10px;
            }
            .open-feedback:hover {
                background-color: #0056b3;
            }
            .back-home {
                background-color: #dc3545;
            }
            .back-home:hover {
                background-color: #c82333;
            }

            /* Hộp thoại feedback */
            .modal {
                display: none;
                position: fixed;
                z-index: 1000;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.4);
                opacity: 0;
                transition: opacity 0.3s ease-in-out;
            }
            .modal.show {
                display: block;
                opacity: 1;
            }
            .modal-content {
                background-color: white;
                margin: 10% auto;
                padding: 20px;
                border: 1px solid #ddd;
                border-radius: 10px;
                width: 50%;
                text-align: center;
                transform: translateY(-20px);
                opacity: 0;
                transition: transform 0.3s ease-in-out, opacity 0.3s ease-in-out;
            }
            .modal.show .modal-content {
                transform: translateY(0);
                opacity: 1;
            }
            .closed {
                color: red;
                /*float: right;*/
                font-size: 28px;
                font-weight: bold;
                /*cursor: pointer;*/
                text-align: right;
            }

            /* Hiển thị sao */
            .stars {
                display: flex;
                flex-direction: row; /* Chọn từ trái qua phải */
                justify-content: center;
                gap: 10px;
            }
            .stars input {
                display: none;
            }
            .stars label {
                font-size: 30px;
                color: #ccc;
                cursor: pointer;
            }
            .stars input:checked ~ label {
                color: #ffcc00;
            }

            /* Thông báo lỗi */
            .error {
                color: red;
                font-size: 14px;
                display: none;
                margin-top: 5px;
            }

            textarea {
                width: 100%;
                height: 100px;
                margin-top: 10px;
                padding: 5px;
            }

            button {
                background-color: #28a745;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                transition: background 0.3s;
            }
            button:hover {
                background-color: #218838;
            }

            .pagination {
                justify-content: center;
                margin-top: 30px;
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
                                <span class="text-white">Feedback Us</span>
                                <h1 class="text-capitalize mb-5 text-lg">Feedback</h1>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- contact form start -->
        <c:if test="${not empty msg}">
            <section class="section pb-0 feed">
                <div class="container">
                    <h2>${msg}</h2>
                    <button class="back-home" onclick="goHome()">Trở về trang chủ</button>
                </div>
            </section>
        </c:if>
        <c:if test="${empty msg && sessionScope.customerAccount != null}">
            <section class="section pb-50 feed">
                <div class="container"> 
                    <h2>Cảm ơn bạn đã sử dụng dịch vụ!</h2>

                    <c:if test="${not empty invoiceID}">
                        <p>Vui lòng để lại phản hồi của bạn để chúng tôi cải thiện chất lượng dịch vụ.</p>
                        <button class="open-feedback" onclick="openFeedback()">Để lại phản hồi</button>

                        <div id="feedbackModal" class="modal">
                            <div class="modal-content">
                                <div class="closed">
                                    <span style="cursor: pointer;" onclick="closeFeedback()">&times;</span>
                                </div>
                                <h2>Phản hồi của bạn</h2>

                                <form id="feedbackForm" action="addFeedback" method="post" onsubmit="return validateFeedback()">
                                    <input type="hidden" name="invoiceId" value="${invoiceID}">
                                    <div class="stars">
                                        <input type="radio" id="star5" name="rating" value="5"><label for="star5">★</label>
                                        <input type="radio" id="star4" name="rating" value="4"><label for="star4">★</label>
                                        <input type="radio" id="star3" name="rating" value="3"><label for="star3">★</label>
                                        <input type="radio" id="star2" name="rating" value="2"><label for="star2">★</label>
                                        <input type="radio" id="star1" name="rating" value="1"><label for="star1">★</label>
                                    </div>

                                    <p class="error" id="ratingError">Chọn số sao tương ứng với mức độ hài lòng nhé!</p>
                                    <textarea name="comment" placeholder="Nhập phản hồi của bạn..."></textarea>
                                    <br>
                                    <button type="submit">Gửi phản hồi</button>
                                </form>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${empty invoiceID}">
                        <!--<p>Bạn không có hóa đơn nào để phản hồi.</p>-->
                    </c:if>

                    <button class="back-home" onclick="goHome()">Trở về trang chủ</button>
                </div>
            </section>
        </c:if>
        <c:if test="${sessionScope.customerAccount == null}">
            <section class="section pb-50 feed">
                <div class="container"> 
                    <h3>Bạn cần <a style="color: red;" href="login.jsp">đăng nhập</a> để để lại phản hồi.</h3>
                </div>
            </section>
        </c:if>

        <hr>
        <section class="feedback-section section">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-8 col-md-10 col-sm-12">
                        <div class="section-title text-center">
                            <h2 class="text-md mb-2">Customer Feedback</h2>
                            <p class="mb-5">See what our customers are saying about us!</p>
                        </div>
                    </div>
                </div>

                <c:forEach var="f" items="${listFeed}">
                    <div class="row justify-content-center">
                        <div class="col-lg-8 col-md-10 col-sm-12 mb-4">
                            <div class="feedback-card p-4 text-center shadow rounded" style="word-wrap: break-word; overflow-wrap: break-word; white-space: normal;">
                                <!--<img src="avatar3.jpg" alt="Mark Wilson" class="rounded-circle mb-3" width="100" height="100">-->
                                <h5 class="mt-3">${f.invoice.examinationID.customerId.fullName}</h5>
                                <p class="feedback-text" style="font-size: 20px; line-height: 1.6; word-break: break-word;">"${f.comment}"</p>
                                <strong>Rate: ${f.rating}★</strong>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="row">
                <div class="col-12">
                    <nav aria-label="Page navigation">
                        <ul class="pagination">
                            <c:if test="${currentPage > 1}">
                                <li class="page-item">
                                    <a class="page-link" href="loadfeedback?page=${currentPage - 1}">Previous</a>
                                </li>
                            </c:if>

                            <c:forEach var="i" begin="1" end="${endPage}">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="loadfeedback?page=${i}">${i}</a>
                                </li>
                            </c:forEach>

                            <c:if test="${currentPage < endPage}">
                                <li class="page-item">
                                    <a class="page-link" href="loadfeedback?page=${currentPage + 1}">Next</a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
                </div>
            </div>
        </section>
        <!-- footer Start -->
        <jsp:include page="footer.jsp"></jsp:include>


        <!-- 
        Essential Scripts
        =====================================-->


        <!-- Main jQuery -->
        <script>
            // Mở hộp thoại feedback
            function openFeedback() {
                let modal = document.getElementById("feedbackModal");

                // Reset feedback mỗi lần mở lại
                resetFeedback();

                modal.style.display = "block";
                setTimeout(() => {
                    modal.classList.add("show");
                }, 10);
            }

            // Đóng hộp thoại feedback
            function closeFeedback() {
                let modal = document.getElementById("feedbackModal");
                modal.classList.remove("show");

                // Đợi 300ms trước khi ẩn modal
                setTimeout(() => {
                    modal.style.display = "none";
                }, 300);
            }

            // Reset form feedback khi mở lại
            function resetFeedback() {
                document.querySelectorAll('input[name="rating"]').forEach((radio) => {
                    radio.checked = false;
                });
                document.querySelector('textarea').value = "";
                document.getElementById("ratingError").style.display = "none";
            }

            // Điều hướng về trang chủ
            function goHome() {
                window.location.href = "home"; // Hoặc "home" nếu dùng servlet
            }

            // Kiểm tra xem khách hàng đã chọn sao chưa
            function validateFeedback() {
                let ratingChecked = document.querySelector('input[name="rating"]:checked');
                let errorText = document.getElementById("ratingError");
                let comment = document.querySelector('textarea[name="comment"]').value.trim();

                // Kiểm tra số sao
                if (!ratingChecked) {
                    errorText.style.display = "block"; // Hiển thị lỗi nếu chưa chọn sao
                    return false; // Ngăn không cho submit form
                }
                errorText.style.display = "none"; // Ẩn lỗi nếu chọn đúng

                // Kiểm tra nhận xét
                if (comment === "") {
                    alert("Vui lòng nhập nhận xét của bạn!");
                    return false; // Ngăn không cho submit form
                }

                return true; // Cho phép submit form nếu hợp lệ
            }
        </script>
        <script src="plugins/jquery/jquery.js"></script>
        <!-- Bootstrap 4.3.2 -->
        <script src="plugins/bootstrap/js/popper.js"></script>
        <script src="plugins/bootstrap/js/bootstrap.min.js"></script>
        <script src="plugins/counterup/jquery.easing.js"></script>
        <!-- Slick Slider -->
        <script src="plugins/slick-carousel/slick/slick.min.js"></script>
        <!-- Counterup -->
        <script src="plugins/counterup/jquery.waypoints.min.js"></script>

        <script src="plugins/shuffle/shuffle.min.js"></script>
        <script src="plugins/counterup/jquery.counterup.min.js"></script>
        <!-- Google Map -->
        <script src="plugins/google-map/map.js"></script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAkeLMlsiwzp6b3Gnaxd86lvakimwGA6UA&callback=initMap"></script>    

        <script src="js/script.js"></script>
        <script src="js/contact.js"></script>

    </body>
</html>
