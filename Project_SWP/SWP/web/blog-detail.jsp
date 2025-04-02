<!DOCTYPE html>
<html lang="en">


    <!-- blog-details23:51-->
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">
        <title>Preclinic - Medical & Hospital - Bootstrap 4 Admin Template</title>
        <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <!--[if lt IE 9]>
                    <script src="assets/js/html5shiv.min.js"></script>
                    <script src="assets/js/respond.min.js"></script>
            <![endif]-->
    </head>

    <body>
        <div class="main-wrapper">
             <jsp:include page="headerStaff.jsp"></jsp:include>
            <jsp:include page="sidebar.jsp"></jsp:include>
            
            
            <div class="page-wrapper">
                <div class="content">
                    <div class="row">
                        <div class="col-sm-12">
                            <h4 class="page-title">Blog View</h4>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-8">
                            <div class="blog-view">
                                <article class="blog blog-single-post">
                                    <h3 class="blog-title">${blogdetail.title}</h3>
                                    <div class="blog-info clearfix">
                                        <div class="post-left">
                                            <ul>
                                                <li><i class="fa fa-calendar"></i> <span>December 6, 2017</span></li>
                                                <li><i class="fa fa-user-o"></i> <span>By Andrew Dawis</span></li>
                                            </ul>
                                        </div>
                                        <div class="post-right"><i class="fa fa-comment-o"></i>1 Comment</div>
                                    </div>
                                    <div class="blog-image text-center">
                                        <img alt="${blogdetail.title}" src="${blogdetail.image}" class="img-fluid" style="max-width: 100%; height: auto;">
                                    </div>
                                    <div class="blog-content" style="max-width: 800px;max-height: 500px; margin: 0 auto; text-align: justify; padding: 15px;">
                                        <div class="blog-detail-content">
                                            ${blogdetail.detail}
                                        </div>
                                    </div>

                                    <style>
                                        .blog-detail-content img {
                                            max-width: 100% !important; /* ??m b?o ?nh không tràn ra ngoài */
                                            height: auto !important; /* Gi? nguyên t? l? ?nh */
                                            display: block; /* ??m b?o ?nh không b? ?nh h??ng b?i inline spacing */
                                            margin: 10px auto; /* C?n gi?a ?nh */
                                        }
                                    </style>

                                </article>
                            </div>
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
        <script src="assets/js/app.js"></script>
    </body>


    <!-- blog-details23:56-->
</html>