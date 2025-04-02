<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <link rel="stylesheet" href="assets2/plugins/bootstrap/css/bootstrap.min.css">
        <!-- Icon Font Css -->
        <link rel="stylesheet" href="assets2/plugins/icofont/icofont.min.css">
        <!-- Slick Slider  CSS -->
        <link rel="stylesheet" href="assets2/plugins/slick-carousel/slick/slick.css">
        <link rel="stylesheet" href="assets2/plugins/slick-carousel/slick/slick-theme.css">

        <!-- Main Stylesheet -->
        <link rel="stylesheet" href="assets2/css/style.css">

        <style>
            .client-info p {
                word-wrap: break-word; /* Xuống dòng khi gặp từ quá dài */
                white-space: normal; /* Giữ nguyên các dòng */
                overflow-wrap: break-word;
            }
        </style>
    </head>

    <body id="top">

        <header>

            <jsp:include page="headerHome.jsp"></jsp:include>


                <section class="page-title bg-1">
                    <div class="overlay"></div>
                    <div class="container">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="block text-center">
                                    <span class="text-white">About Us</span>
                                    <h1 class="text-capitalize mb-5 text-lg">About Us</h1>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <section class="section about-page">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-4">
                                <h2 class="title-color">Personal care for your healthy living</h2>
                            </div>
                            <div class="col-lg-8">
                                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Incidunt, quod laborum alias. Vitae
                                    dolorum, officia sit! Saepe ullam facere at, consequatur incidunt, quae esse, quis ut
                                    reprehenderit dignissimos, libero delectus.</p>
                                <img src="assets2/images/about/sign.png" alt="" class="img-fluid">
                            </div>
                        </div>
                    </div>
                </section>

                <section class="fetaure-page ">
                    <div class="container">
                        <div class="row">
                        <c:forEach var="service" items="${listService}">
                            <div class="col-lg-3 col-md-6">
                                <div class="about-block-item mb-5 mb-lg-0">
                                    <img style="width: 250px; height: 190px;" src="${empty service.packageID ? 'images/default-image.jpg' : 'getimage?packageID='}${service.packageID}" alt="" class="img-fluid w-100">
                                    <h4 class="mt-3">${service.packageName}</h4>
                                    <p>${service.description}</p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </section>
            <section class="section awards">
                <div class="container">
                    <div class="row align-items-center">
                        <div class="col-lg-4">
                            <h2 class="title-color">Our Doctors achievements </h2>
                            <div class="divider mt-4 mb-5 mb-lg-0"></div>
                        </div>
                        <div class="col-lg-8">
                            <div class="row">
                                <div class="col-lg-4 col-md-6 col-sm-6">
                                    <div class="award-img">
                                        <img src="assets2/images/about/3.png" alt="" class="img-fluid">
                                    </div>
                                </div>
                                <div class="col-lg-4 col-md-6 col-sm-6">
                                    <div class="award-img">
                                        <img src="assets2/images/about/4.png" alt="" class="img-fluid">
                                    </div>
                                </div>
                                <div class="col-lg-4 col-md-6 col-sm-6">
                                    <div class="award-img">
                                        <img src="assets2/images/about/1.png" alt="" class="img-fluid">
                                    </div>
                                </div>
                                <div class="col-lg-4 col-md-6 col-sm-6">
                                    <div class="award-img">
                                        <img src="assets2/images/about/2.png" alt="" class="img-fluid">
                                    </div>
                                </div>
                                <div class="col-lg-4 col-md-6 col-sm-6">
                                    <div class="award-img">
                                        <img src="assets2/images/about/5.png" alt="" class="img-fluid">
                                    </div>
                                </div>
                                <div class="col-lg-4 col-md-6 col-sm-6">
                                    <div class="award-img">
                                        <img src="assets2/images/about/6.png" alt="" class="img-fluid">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <section class="section team">
                <div class="container">
                    <div class="row justify-content-center">
                        <div class="col-lg-6">
                            <div class="section-title text-center">
                                <h2 class="mb-4">Meet Our Specialist</h2>
                                <div class="divider mx-auto my-4"></div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <c:forEach var="doctor" items="${listProfessional}">
                            <div class="col-lg-3 col-md-6 col-sm-6">
                                <div class="team-block mb-5 mb-lg-0">
                                    <img style="width: 350px; height: 255px;" src="assets/img/${doctor.getPicture()}" alt="" class="img-fluid w-100">

                                    <div class="content">
                                        <h4 class="mt-4 mb-0"><a href="DetailDoctorServlet?id=${doctor.getStaffID()}">${doctor.getName()}</a></h4>
                                        <p>${doctor.qualification}</p>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </section>

            <section class="section testimonial">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-6 offset-lg-6">
                            <div class="section-title">
                                <h2 class="mb-4">What they say about us</h2>
                                <div class="divider  my-4"></div>
                            </div>
                        </div>
                    </div>
                    <div class="row align-items-center">
                        <div class="col-lg-6 testimonial-wrap offset-lg-6">
                            <c:forEach var="feedback" items="${listFeedback}">
                                <div class="testimonial-block">
                                    <div class="client-info ">
                                        <h4>${feedback.invoice.examinationID.customerId.fullName}</h4>
                                        <span>${feedback.date}</span>
                                    </div>
                                    <p>
                                        ${feedback.comment}
                                    </p>
                                    <strong>Rate: ${feedback.rating}★</strong>
                                    <i class="icofont-quote-right"></i>
                                </div>
                            </c:forEach>
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
            <script src=assets2/plugins/google-map/map.js"></script>
            <script
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAkeLMlsiwzp6b3Gnaxd86lvakimwGA6UA&callback=initMap"></script>

            <script src="assets2/js/script.js"></script>
            <script src="assets2/js/contact.js"></script>

    </body>

</html>