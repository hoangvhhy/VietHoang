<%-- 
    Document   : home
    Created on : Feb 13, 2025, 2:57:49 AM
    Author     : Hoang
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><!DOCTYPE html>
<html lang="zxx">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="description" content="Orbitor,business,company,agency,modern,bootstrap4,tech,software">
        <meta name="author" content="themefisher.com">

        <title>Novena- Health & Care Medical template</title>

        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="/images/favicon.ico" />

        <!-- bootstrap.min css -->
        <link rel="stylesheet" href="assets2/plugins/bootstrap/css/bootstrap.min.css">
        <!-- Icon Font Css -->
        <link rel="stylesheet" href="assets2/plugins/icofont/icofont.min.css">
        <!-- Slick Slider  CSS -->
        <link rel="stylesheet" href="assets2/plugins/slick-carousel/slick/slick.css">
        <link rel="stylesheet" href="assets2/plugins/slick-carousel/slick/slick-theme.css">

        <!-- Main Stylesheet -->
        <link rel="stylesheet" href="assets2/css/style.css">
        <style>
            /* Định vị icon chat */
            .floating-icons {
                position: fixed;
                right: 10px; /* Căn bên phải màn hình */
                top: 50%; /* Căn giữa theo chiều dọc */
                transform: translateY(-50%); /* Giúp căn giữa chính xác */
                display: flex;
                flex-direction: column; /* Xếp icon theo chiều dọc */
                gap: 10px; /* Khoảng cách giữa các icon */
                z-index: 1000; /* Đảm bảo hiển thị trên cùng */
            }

            .icon img {
                width: 50px; /* Điều chỉnh kích thước icon */
                height: 50px;
                border-radius: 50%;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                transition: transform 0.2s ease-in-out;
            }

            .icon img:hover {
                transform: scale(1.1); /* Hiệu ứng phóng to khi di chuột */
            }


            @keyframes fadeIn {
                from {
                    opacity: 0;
                    transform: translateY(20px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            /* Header chat */
            .chat-header {
                background: #007bff;
                color: white;
                padding: 12px;
                text-align: center;
                font-weight: bold;
                border-top-left-radius: 12px;
                border-top-right-radius: 12px;
                position: relative;
                font-size: 16px;
            }

            /* Nút đóng */
            .close-chat {
                position: absolute;
                right: 15px;
                top: 10px;
                cursor: pointer;
                font-size: 20px;
                font-weight: bold;
                transition: color 0.3s;
            }

            .close-chat:hover {
                color: #ffcccc;
            }

            /* Hộp tin nhắn */
            .chat-box {
                height: 250px;
                overflow-y: auto;
                padding: 15px;
                background: #f9f9f9;
                border-bottom: 1px solid #ddd;
            }

            /* Input tin nhắn */
            .chat-input {
                display: flex;
                border-top: 1px solid #ddd;
                background: white;
                padding: 10px;
                border-bottom-left-radius: 12px;
                border-bottom-right-radius: 12px;
            }

            .chat-input input {
                flex: 1;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 8px;
                outline: none;
                font-size: 14px;
            }

            .chat-input button {
                background: #007bff;
                color: white;
                border: none;
                padding: 10px 15px;
                margin-left: 8px;
                border-radius: 8px;
                cursor: pointer;
                transition: background 0.3s;
            }

            .chat-input button:hover {
                background: #0056b3;
            }

            /* Tin nhắn */
            .chat-box div {
                background: #e1f5fe;
                padding: 8px 12px;
                border-radius: 8px;
                margin-bottom: 8px;
                max-width: 80%;
                word-wrap: break-word;
            }

            /* Ẩn phần tử */
            .hidden {
                display: none;

            }

            .client-info p {
                word-wrap: break-word; /* Xuống dòng khi gặp từ quá dài */
                white-space: normal; /* Giữ nguyên các dòng */
                overflow-wrap: break-word;
            }

            .section testimonial-2 gray-bg{
                padding-top: 30px;
            }
        </style>
    </head>

    <body id="top">
        <jsp:include page="headerHome.jsp"></jsp:include>

            <!-- Slider Start -->
            <section class="banner">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-6 col-md-12 col-xl-7">
                            <div class="block">
                                <div class="divider mb-3"></div>
                                <span class="text-uppercase text-sm letter-spacing " style="color: black;">Total Health care solution</span>
                                <h1 class="mb-3 mt-3">Your most trusted health partner</h1>
                                <div class="btn-container ">
                                    <a href="appointment" target="_blank"
                                       class="btn btn-main-2 btn-icon btn-round-full">Make appoinment <i
                                            class="icofont-simple-right ml-2  "></i></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <section class="features">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="feature-block d-lg-flex">
                                <div class="feature-item mb-5 mb-lg-0">
                                    <div class="feature-icon mb-4">
                                        <i class="icofont-surgeon-alt"></i>
                                    </div>
                                    <span>24 Hours Service</span>
                                    <h4 class="mb-3">Online Appoinment</h4>
                                    <p class="mb-4">Get ALl time support for emergency.We have introduced the principle of
                                        family medicine.</p>
                                    <a href="appointment" class="btn btn-main btn-round-full">Make a appoinment</a>
                                </div>

                                <div class="feature-item mb-5 mb-lg-0">
                                    <div class="feature-icon mb-4">
                                        <i class="icofont-ui-clock"></i>
                                    </div>
                                    <span>Timing schedule</span>
                                    <h4 class="mb-3">Working Hours</h4>
                                    <ul class="w-hours list-unstyled">
                                        <li class="d-flex justify-content-between">Sun - Wed : <span>8:00 - 17:00</span></li>
                                        <li class="d-flex justify-content-between">Thu - Fri : <span>9:00 - 17:00</span></li>
                                        <li class="d-flex justify-content-between">Sat - sun : <span>10:00 - 17:00</span></li>
                                    </ul>
                                </div>

                                <div class="feature-item mb-5 mb-lg-0">
                                    <div class="feature-icon mb-4">
                                        <i class="icofont-support"></i>
                                    </div>
                                    <span>Emegency Cases</span>
                                    <h4 class="mb-3">1-800-700-6200</h4>
                                    <p>Get ALl time support for emergency.We have introduced the principle of family
                                        medicine.Get Conneted with us for any urgency .</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>


            <section class="section about">
                <div class="container">
                    <div class="row align-items-center">
                        <div class="col-lg-4 col-sm-6">
                            <div class="about-img">
                                <img src="assets2/images/about/img-1.jpg" alt="" class="img-fluid">
                                <img src="assets2/images/about/img-2.jpg" alt="" class="img-fluid mt-4">
                            </div>
                        </div>
                        <div class="col-lg-4 col-sm-6">
                            <div class="about-img mt-4 mt-lg-0">
                                <img src="assets2/images/about/img-3.jpg" alt="" class="img-fluid">
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="about-content pl-4 mt-4 mt-lg-0">
                                <h2 class="title-color">Personal care <br>& healthy living</h2>
                                <p class="mt-4 mb-5">We provide best leading medicle service Nulla perferendis veniam deleniti
                                    ipsum officia dolores repellat laudantium obcaecati neque.</p>

                                <a href="service.jsp" class="btn btn-main-2 btn-round-full btn-icon">Services<i
                                        class="icofont-simple-right ml-3"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <section class="cta-section ">
                <div class="container">
                    <div class="cta position-relative">
                        <div class="row">
                            <div class="col-lg-3 col-md-6 col-sm-6">
                                <div class="counter-stat">
                                    <i class="icofont-doctor"></i>
                                    <span class="h3">58</span>k
                                    <p>Happy People</p>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-6 col-sm-6">
                                <div class="counter-stat">
                                    <i class="icofont-flag"></i>
                                    <span class="h3">700</span>+
                                    <p>Surgery Comepleted</p>
                                </div>
                            </div>

                            <div class="col-lg-3 col-md-6 col-sm-6">
                                <div class="counter-stat">
                                    <i class="icofont-badge"></i>
                                    <span class="h3">40</span>+
                                    <p>Expert Doctors</p>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-6 col-sm-6">
                                <div class="counter-stat">
                                    <i class="icofont-globe"></i>
                                    <span class="h3">20</span>
                                    <p>Worldwide Branch</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <section class="section testimonial-2 gray-bg">
                <div class="container">
                    <div class="row justify-content-center">
                        <div class="col-lg-7">
                            <div class="section-title text-center">
                                <h2>We served over 5000+ Patients</h2>
                                <div class="divider mx-auto my-4"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="container">
                    <div class="row align-items-center">
                        <div class="col-lg-12 testimonial-wrap-2">
                        <c:forEach var="feedback" items="${listFeedback}">
                            <div class="testimonial-block style-2  gray-bg">
                                <i class="icofont-quote-right"></i>

                                <div class="testimonial-thumb">
                                    <img src="pictureprofile?customerID=${feedback.invoice.examinationID.customerId.customerID}" alt="" class="img-fluid">
                                </div>

                                <div class="client-info ">
                                    <h4>${feedback.invoice.examinationID.customerId.fullName}</h4>
                                    <span>${feedback.date}</span>
                                    <p>${feedback.comment}</p>
                                    <strong>Rating: ${feedback.rating}★</strong>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </section>
        <section class="section clients">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-7">
                        <div class="section-title text-center">
                            <h2>Partners who support us</h2>
                            <div class="divider mx-auto my-4"></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="container">
                <div class="row clients-logo">
                    <div class="col-lg-2">
                        <div class="client-thumb">
                            <img src="assets2/images/about/1.png" alt="" class="img-fluid">
                        </div>
                    </div>
                    <div class="col-lg-2">
                        <div class="client-thumb">
                            <img src="assets2/images/about/2.png" alt="" class="img-fluid">
                        </div>
                    </div>
                    <div class="col-lg-2">
                        <div class="client-thumb">
                            <img src="assets2/images/about/3.png" alt="" class="img-fluid">
                        </div>
                    </div>
                    <div class="col-lg-2">
                        <div class="client-thumb">
                            <img src="assets2/images/about/4.png" alt="" class="img-fluid">
                        </div>
                    </div>
                    <div class="col-lg-2">
                        <div class="client-thumb">
                            <img src="assets2/images/about/5.png" alt="" class="img-fluid">
                        </div>
                    </div>
                    <div class="col-lg-2">
                        <div class="client-thumb">
                            <img src="assets2/images/about/6.png" alt="" class="img-fluid">
                        </div>
                    </div>
                    <div class="col-lg-2">
                        <div class="client-thumb">
                            <img src="assets2/images/about/3.png" alt="" class="img-fluid">
                        </div>
                    </div>
                    <div class="col-lg-2">
                        <div class="client-thumb">
                            <img src="assets2/images/about/4.png" alt="" class="img-fluid">
                        </div>
                    </div>
                    <div class="col-lg-2">
                        <div class="client-thumb">
                            <img src="assets2/images/about/5.png" alt="" class="img-fluid">
                        </div>
                    </div>
                    <div class="col-lg-2">
                        <div class="client-thumb">
                            <img src="assets2/images/about/6.png" alt="" class="img-fluid">
                        </div>
                    </div>
                </div>
            </div>
        </section>


        <div class="floating-icons">
            <a href="https://zalo.me/0886840889" class="icon">
                <img src="https://help.zalo.me/wp-content/uploads/2023/08/cropped-logoZalo.png" alt="Zalo">
            </a>
            <a href="https://www.messenger.com/t/61573434115221/?messaging_source=source%3Apages%3Amessage_shortlink&source_id=1441792&recurring_notification=0" class="icon">
                <img src="https://th.bing.com/th/id/R.5e81e292523d1f3a099da156223d8cc7?rik=Nj1WZ7uxItlDzA&pid=ImgRaw&r=0" alt="Messenger">
            </a>
        </div>
        <!-- footer Start -->
        <jsp:include page="footer.jsp"></jsp:include>


        <!-- Main jQuery -->
        <script src="assets2/plugins/jquery/jquery.js"></script>
        <!-- Bootstrap 4.3.2 -->
        <script src="assets2/plugins/bootstrap/js/popper.js"></script>
        <script src="assets2/plugins/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets2/plugins/counterup/jquery.easing.js"></script>
        <!-- Slick Slider -->
        <script src="assets2/plugins/slick-carousel/slick/slick.min.js"></script>
        <!-- Counterup -->
        <script src="assets2/plugins/counterup/jquery.waypoints.min.js"></script>

        <script src="assets2/plugins/shuffle/shuffle.min.js"></script>
        <script src="assets2/plugins/counterup/jquery.counterup.min.js"></script>
        <!-- Google Map -->
        <script src="assets2/plugins/google-map/map.js"></script>
        <script
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAkeLMlsiwzp6b3Gnaxd86lvakimwGA6UA&callback=initMap"></script>

        <script src="assets2/js/script.js"></script>
        <script src="assets2/js/contact.js"></script>

    </body>

</html>
