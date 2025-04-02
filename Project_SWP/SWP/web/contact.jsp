<%-- 
    Document   : contact
    Created on : Mar 16, 2025, 7:54:44 AM
    Author     : Win11
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <link rel="stylesheet" href="css/pagination.css">
        <!-- Slick Slider  CSS -->
        <link rel="stylesheet" href="plugins/slick-carousel/slick/slick.css">
        <link rel="stylesheet" href="plugins/slick-carousel/slick/slick-theme.css">

        <!-- Main Stylesheet -->
        <link rel="stylesheet" href="css/style.css">
        <style>
            /* Card style for each comment */
            .comment-card {
                border: 1px solid #ccc;
                border-radius: 8px;
                padding: 15px;
                margin-bottom: 20px;
                background-color: #f9f9f9;
            }

            /* Header style with the sender's email */
            .comment-header {
                font-size: 1.2em;
                margin-bottom: 10px;
            }

            /* Body style for comment text */
            .comment-body {
                font-size: 1em;
                color: #333;
                margin-bottom: 15px;
            }

            /* Style for replies section */
            .replies-section {
                margin-top: 10px;
                padding-left: 20px;
                border-left: 2px solid #ddd;
            }

            /* Style for each reply */
            .reply {
                margin-bottom: 10px;
            }

            /* Strong text for labels (e.g., 'Replies') */
            strong {
                color: #333;
            }
            /* Phong cách phân trang */
            .pagination a, .pagination span {
                display: inline-block;
                margin: 0 5px;
                padding: 8px 12px;
                border: 1px solid #ddd;
                color: #333;
                text-decoration: none;
                cursor: pointer;
            }
            .pagination a.active, .pagination span.current {
                background-color: #007bff;
                color: #fff;
                border-color: #007bff;
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
                                <span class="text-white">Contact Us</span>
                                <h1 class="text-capitalize mb-5 text-lg">Get in Touch</h1>

                                <!-- <ul class="list-inline breadcumb-nav">
                                  <li class="list-inline-item"><a href="index.html" class="text-white">Home</a></li>
                                  <li class="list-inline-item"><span class="text-white">/</span></li>
                                  <li class="list-inline-item"><a href="#" class="text-white-50">Contact Us</a></li>
                                </ul> -->
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- contact form start -->

            <section class="contact-form-wrap section">
                <div class="container">
                    <div class="row justify-content-center">
                        <div class="col-lg-6">
                            <div class="section-title text-center">
                                <h2 class="text-md mb-2">Contact us</h2>
                                <div class="divider mx-auto my-4"></div>
                            </div>
                        </div>
                    </div>
                <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
                <h2>Comments</h2>
                <div id="comments-container">
                    <c:forEach var="comment" items="${comments}">
                        <c:if test="${not empty comment.getReplies()}">
                            <div class="comment-card">
                                <div class="comment-header">
                                    <p><strong>${comment.topic}</strong>
                                        <br>
                                    <p><strong>${comment.senderEmail}</strong> says:</p>
                                </div>
                                <div class="comment-body">
                                    <p>${comment.getCommentText()}</p>
                                </div>
                                <c:if test="${not empty comment.getReplies()}">
                                    <div class="replies-section">
                                        <p><strong>Replies:</strong></p>
                                        <ul>
                                            <c:forEach var="reply" items="${comment.getReplies()}">
                                                <li class="reply">
                                                    <p><strong>${reply.senderEmail}</strong> replied:</p>
                                                    <p>${reply.getCommentText()}</p>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </c:if>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>

                <!-- Container để hiển thị liên kết phân trang -->
                <div id="pagination-container" class="pagination text-center"></div>


                <div class="row">
                    <div class="col-lg-12 col-md-12 col-sm-12">
                        <form id="contact-form" class="contact__form" action="contact" method="POST" onsubmit="return validateForm()">
                            <!-- form message -->
                            <div class="row">
                                <div class="col-12">
                                    <div class="alert alert-success contact__msg" <c:if test="${sessionScope.add == false}">style="display: none"</c:if> role="alert">
                                            Your message was sent successfully.
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-lg-6">
                                        <div class="form-group">
                                            <input name="name" id="name" type="text" class="form-control" placeholder="Your Full Name">
                                            <small id="nameError" class="text-danger"></small>
                                        </div>
                                    </div>

                                    <div class="col-lg-6">
                                        <div class="form-group">
                                            <input name="email" id="email" type="email" class="form-control" placeholder="Your Email Address">
                                            <small id="emailError" class="text-danger"></small>
                                        </div>
                                    </div>

                                    <div class="col-lg-6">
                                        <div class="form-group">
                                            <input name="topic" id="subject" type="text" class="form-control" placeholder="Your Query Topic">
                                            <small id="topicError" class="text-danger"></small>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group-2 mb-4">
                                    <textarea name="message" id="message" class="form-control" rows="8" placeholder="Your Message"></textarea>
                                    <small id="messageError" class="text-danger"></small>
                                </div>

                                <div class="text-center">
                                    <input class="btn btn-main btn-round-full" name="submit" type="submit" value="Send Message">
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </section>

            <!-- footer Start -->
        <jsp:include page="footer.jsp"></jsp:include>


        <!-- 
        Essential Scripts
        =====================================-->


        <!-- Main jQuery -->
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
        <script src="js/pagination.js"></script>
        <script>
                            document.addEventListener("DOMContentLoaded", function () {
                                paginateItems('.comment-card', 5, '#pagination-container');
                            });
        </script>
        <script>
            function validateForm() {
                let isValid = true;

                // Lấy giá trị từ các trường input
                let name = document.getElementById("name").value.trim();
                let email = document.getElementById("email").value.trim();
                let topic = document.getElementById("subject").value.trim();
                let message = document.getElementById("message").value.trim();

                // Xóa thông báo lỗi cũ
                document.getElementById("nameError").innerText = "";
                document.getElementById("emailError").innerText = "";
                document.getElementById("topicError").innerText = "";
                document.getElementById("messageError").innerText = "";

                // Kiểm tra tên (không được để trống, ít nhất 3 ký tự)
                if (name === "" || name.length < 3) {
                    document.getElementById("nameError").innerText = "Please enter at least 3 characters for your name.";
                    isValid = false;
                }

                // Kiểm tra email hợp lệ
                let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                if (email === "" || !emailPattern.test(email)) {
                    document.getElementById("emailError").innerText = "Please enter a valid email address.";
                    isValid = false;
                }

                // Kiểm tra topic (không được để trống)
                if (topic === "") {
                    document.getElementById("topicError").innerText = "Please enter a topic.";
                    isValid = false;
                }

                // Kiểm tra tin nhắn (tối thiểu 10 ký tự)
                if (message === "" || message.length < 10) {
                    document.getElementById("messageError").innerText = "Message must be at least 10 characters long.";
                    isValid = false;
                }

                return isValid;
            }
        </script>
    </body>
</html>