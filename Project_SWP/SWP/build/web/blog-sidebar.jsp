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
            li.active a {
                color: red !important;  /* Màu chữ khi được chọn */
                font-weight: bold;      /* In đậm */
                text-decoration: underline; /* Gạch chân */
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
                                <span class="text-white">Our News</span>
                                <h1 class="text-capitalize mb-5 text-lg">News articles</h1>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <section class="section blog-wrap">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-8">
                            <div class="row">
                            <c:if test="${empty pagingPage}">
                                <p>No news available.</p>
                            </c:if>

                            <c:forEach var="news" items="${pagingPage}">  
                                <div class="col-lg-12 col-md-12 mb-5">
                                    <div class="blog-item">
                                        <div class="blog-thumb">
                                            <img style="width:730px; height: 485px; " src="LoadBlogImage?postId=${news.post_id}" alt="" class="img-fluid ">
                                        </div>
                                        <div class="blog-item-content">
                                            <div class="blog-item-meta mb-3 mt-4">
                                                <!--<span class="text-muted text-capitalize mr-3"><i class="icofont-comment mr-2"></i>5 Comments</span>-->
                                                <span class="text-black text-capitalize mr-3"><i class="icofont-calendar mr-1"></i>${news.created_at}</span>
                                                <span class="text-black text-capitalize mr-3"><i class=" mr-1"></i>Updated last: ${news.updated_at}</span>
                                            </div> 

                                            <h2 class="mt-3 mb-3"><a href="detailNews?newsID=${news.post_id}">${news.title}</a></h2>

                                            <p class="mb-4">${news.content}</p>

                                            <a href="detailNews?newsID=${news.post_id}" target="_blank" class="btn btn-main btn-icon btn-round-full">Read More <i class="icofont-simple-right ml-2  "></i></a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="sidebar-wrap pl-lg-4 mt-5 mt-lg-0">
                            <div class="sidebar-widget search mb-3">
                                <h5>Search Here</h5>
                                <form action="allNews" method="get" class="search-form" style="display: flex; gap: 5px;">
                                    <input type="text" name="search" class="form-control" placeholder="Search" value="${search}" style="flex: 1;">
                                    <button type="submit" class="btn btn-primary"><i class="ti-search"></i>Search</button>
                                </form>
                                <form action="allNews" method="get" class="search-form">
                                    <select name="sort" class="form-control mt-2" onchange="this.form.submit()">
                                        <option value="new" ${empty sort or sort == 'new' ? 'selected' : ''}>Mới nhất</option>
                                        <option value="old" ${sort == 'old' ? 'selected' : ''}>Cũ nhất</option>
                                    </select>
                                </form>
                            </div>



<!--                            <div class="sidebar-widget latest-post mb-3">
                                <h5>Popular Posts</h5>

                                <div class="py-2">
                                    <span class="text-sm text-muted">03 Mar 2018</span>
                                    <h6 class="my-2"><a href="#">Thoughtful living in los Angeles</a></h6>
                                </div>

                                <div class="py-2">
                                    <span class="text-sm text-muted">03 Mar 2018</span>
                                    <h6 class="my-2"><a href="#">Vivamus molestie gravida turpis.</a></h6>
                                </div>

                                <div class="py-2">
                                    <span class="text-sm text-muted">03 Mar 2018</span>
                                    <h6 class="my-2"><a href="#">Fusce lobortis lorem at ipsum semper sagittis</a></h6>
                                </div>
                            </div>-->

                            <div class="sidebar-widget category mb-3">
                                <h5 class="mb-4">Categories</h5>

                                <ul class="list-unstyled">
                                    <li class="align-items-center ${empty param.categoryID ? 'active' : ''}">
                                        <a href="allNews">All</a>
                                    </li>
                                    <c:forEach var="cate" items="${listCate}">
                                        <li class="align-items-center ${param.categoryID == cate.category_id ? 'active' : ''}">
                                            <a href="allNews?categoryID=${cate.category_id}">${cate.name}</a>
                                            <!--<span>(14)</span>-->
                                        </li>


                                    </c:forEach>

                                </ul>
                            </div>


<!--                            <div class="sidebar-widget tags mb-3">
                                <h5 class="mb-4">Tags</h5>

                                <a href="#">Doctors</a>
                                <a href="#">agency</a>
                                <a href="#">company</a>
                                <a href="#">medicine</a>
                                <a href="#">surgery</a>
                                <a href="#">Marketing</a>
                                <a href="#">Social Media</a>
                                <a href="#">Branding</a>
                                <a href="#">Laboratory</a>
                            </div>-->


<!--                            <div class="sidebar-widget schedule-widget mb-3">
                                <h5 class="mb-4">Time Schedule</h5>

                                <ul class="list-unstyled">
                                    <li class="d-flex justify-content-between align-items-center">
                                        <a href="#">Monday - Friday</a>
                                        <span>9:00 - 17:00</span>
                                    </li>
                                    <li class="d-flex justify-content-between align-items-center">
                                        <a href="#">Saturday</a>
                                        <span>9:00 - 16:00</span>
                                    </li>
                                    <li class="d-flex justify-content-between align-items-center">
                                        <a href="#">Sunday</a>
                                        <span>Closed</span>
                                    </li>
                                </ul>

                                <div class="sidebar-contatct-info mt-4">
                                    <p class="mb-0">Need Urgent Help?</p>
                                    <h3>+23-4565-65768</h3>
                                </div>
                            </div>-->

                        </div>
                    </div> 
                </div>

                <div class="row mt-5">
                    <div class="col-lg-8">
                        <nav class="pagination py-2 d-inline-block">
                            <div class="nav-links">
                                <c:set var="range" value="2" /> 
                                <c:set var="startPage" value="${page - range > 1 ? page - range : 1}" />
                                <c:set var="endPage" value="${page + range < endPage ? page + range : endPage}" />
                                <c:if test="${page != 1}">
                                    <a class="page-numbers" href="allNews?page=${page - 1}"><i class="icofont-thin-double-left"></i></a>
                                    </c:if>
                                    <c:forEach begin="${startPage}" end="${endPage}" var="i">
                                    <a class="page-numbers ${page == i ? 'page-numbers current' : ''}" href="allNews?page=${i}&categoryID=${categoryID}&search=${search}&sort=${sort}">${i}</a>
                                </c:forEach>
                                <c:if test="${page != endPage}">
                                    <!--<span class="page-numbers">.....</span>-->
                                    <a class="page-numbers" href="allNews?page=${page + 1}"><i class="icofont-thin-double-right"></i></a>
                                    </c:if>
                                <form action="allNews" method="get" style="display: inline;">
                                    <input type="hidden" name="search" value="${search}" />
                                    <input type="hidden" name="categoryID" value="${categoryID}" />
                                    <input type="hidden" name="sort" value="${sort}" />
                                    <input type="text" name="page" placeholder="Page" style="width: 40px;" />
                                    <input type="submit" value="Go" />
                                </form>
                            </div>
                        </nav>
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
        <script src="assets2/plugins/google-map/map.js"></script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAkeLMlsiwzp6b3Gnaxd86lvakimwGA6UA&callback=initMap"></script>    

        <script src="assets2/js/script.js"></script>
        <script src="assets2/js/contact.js"></script>

    </body>
</html>
