<%-- 
    Document   : blog-single
    Created on : Feb 9, 2025, 11:15:20 PM
    Author     : jaxbo
--%>

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
            .child-comments {
                margin-left: 50px;
                border-left: 2px solid #ddd;
                padding-left: 15px;
            }
            .comment-area-box {
                background: #f9f9f9;
                padding: 10px;
                border-radius: 5px;
                margin-top: 10px;
            }
            .comment-content p {
                word-wrap: break-word;  /* Ngắt từ khi quá dài */
                overflow-wrap: break-word;  /* Hỗ trợ xuống dòng trên mọi trình duyệt */
                max-width: 100%; /* Giới hạn nội dung không tràn ra ngoài */
            }
            html{
                scroll-behavior: smooth;
            }
            .comment-container {
                /*width:  1000px;*/
                max-height: 600px; /* Chiều cao tối đa */
                overflow-y: auto; /* Hiển thị thanh cuộn khi nội dung vượt quá */
                padding-right: 10px; /* Để tránh bị che mất nội dung do thanh cuộn */
                border: 1px solid #ddd; /* Viền để phân tách danh sách */
                border-radius: 5px; /* Bo tròn góc */
                padding: 10px;
            }

        </style>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                var commentContainer = document.querySelector(".comment-container");
                if (commentContainer) {
                    commentContainer.scrollTop = commentContainer.scrollTop;
                }
            });
        </script>

    </head>

    <body id="top">
        <jsp:include page="headerHome.jsp"></jsp:include>
            <section class="page-title bg-1">
                <div class="overlay"></div>
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="block text-center">
                                <span class="text-white">News details</span>
                                <h1 class="text-capitalize mb-5 text-lg">Blog Single</h1>
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
                                <div class="col-lg-12 mb-5">
                                    <div class="single-blog-item">
                                        <img style="width:730px; height: 485px;" src="LoadBlogImage?postId=${newsDetail.post_id}" alt="" class="img-fluid">
                                    <div class="blog-item-content mt-5">
                                        <div class="blog-item-meta mb-3">
                                            <!--<span class="text-color-2 text-capitalize mr-3"><i class="icofont-book-mark mr-2"></i> Equipment</span>-->
                                            <span class="text-muted text-capitalize mr-3"><i class="icofont-comment mr-2"></i>${comments.size()} Comments</span>
                                            <span class="text-black text-capitalize mr-3"><i class="icofont-calendar mr-2"></i>${newsDetail.created_at}</span>
                                            <span class="text-black text-capitalize mr-3"><i class="mr-1"></i>Updated last: ${newsDetail.updated_at}</span>
                                        </div>

                                        <h2 style="color:#009efb " class="mb-4 text-md">${newsDetail.title}</h2>


                                        <p class="lead mb-4">${newsDetail.detail}</p>

                                        <p>${newsDetail.content}</p>

                                    </div>
                                </div>
                            </div>

                            <div class="col-lg-12">
                                <div class="comment-area mt-4 mb-5">
                                    <h4 class="mb-4">${comments.size()} Comments on ${newsDetail.title}</h4>
                                    <div class="comment-container">
                                        <ul class="comment-tree list-unstyled">
                                            <c:forEach var="comment" items="${comments}">
                                                <c:if test="${comment.parent_comment_id == 0}">
                                                    <li class="mb-4">
                                                        <div class="comment-area-box">
                                                            <div class="comment-thumb float-left">
                                                                <c:if test="${comment.customerID != null}">
                                                                    <img style="width: 50px; height: 50px; border-radius: 50%;" 
                                                                         alt="" 
                                                                         src="pictureprofile?customerID=${comment.customerID.customerID}" 
                                                                         class="img-fluid">
                                                                </c:if>
                                                                <c:if test="${comment.staff_id != null}">
                                                                    <img style="width: 50px; height: 50px; border-radius: 50%;" 
                                                                         alt="" 
                                                                         src="" 
                                                                         class="img-fluid">
                                                                </c:if>
                                                            </div>

                                                            <div class="comment-info">
                                                                <h5 class="mb-1 d-flex justify-content-between align-items-center">
                                                                    <c:if test="${comment.customerID != null}">
                                                                        ${comment.customerID.fullName}
                                                                    </c:if>
                                                                    <c:if test="${comment.staff_id != null}">
                                                                        <span style="color: #009efb;">[Staff] ${comment.staff_id.fullName}</span>
                                                                    </c:if>
                                                                    <c:if test="${sessionScope.customerAccount != null 
                                                                                  && sessionScope.customerAccount.customerID == comment.customerID.customerID 
                                                                                  || sessionScope.staffAccount != null 
                                                                                  && sessionScope.staffAccount.staffID == comment.staff_id.staffID}">                                                               
                                                                          <span>
                                                                              <a href="detailNews?newsID=${newsDetail.post_id}&editMode=true&comment_id=${comment.comment_id}#comment-form" class="mr-2"><i
                                                                                      class="icofont-edit text-muted"></i>Edit</a>
                                                                              <a href="deleteComment?comment_id=${comment.comment_id}" 
                                                                                 onclick="return confirm('Are you sure to delete this comment?');">
                                                                                  <i class="icofont-trash text-muted"></i>Delete
                                                                              </a>
                                                                          </span>
                                                                    </c:if>
                                                                </h5>
                                                                <span>${comment.create_at}</span>
                                                            </div>

                                                            <div class="comment-meta mt-2">
                                                                <c:if test="${sessionScope.customerAccount == null && sessionScope.staffAccount == null}">
                                                                    <a href="login.jsp" onclick="return confirm('Bạn cần đăng nhập để trả lời bình luận!');">
                                                                        <i class="icofont-reply mr-2 text-muted"></i>Reply
                                                                    </a>
                                                                </c:if>
                                                                <c:if test="${sessionScope.customerAccount != null || sessionScope.staffAccount != null}">
                                                                    <a href="?newsID=${newsDetail.post_id}&parent_comment_id=${comment.comment_id}#comment-form">
                                                                        <i class="icofont-reply mr-2 text-muted"></i>Reply
                                                                    </a>
                                                                </c:if>
                                                            </div>
                                                            <div class="comment-content mt-3">
                                                                <p>${comment.content}</p>
                                                            </div>
                                                        </div>
                                                        <ul class="child-comments list-unstyled ml-5">
                                                            <jsp:include page="comment-reply.jsp">
                                                                <jsp:param name="parentId" value="${comment.comment_id}"/>
                                                            </jsp:include>
                                                        </ul>
                                                    </li>
                                                </c:if>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                            </div>


                            <div class="col-lg-12">                                    
                                <c:choose>
                                    <c:when test="${param.editMode == 'true'}">
                                        <c:set var="editMode" value="true" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="editMode" value="false" />
                                    </c:otherwise>
                                </c:choose>
                                <c:if test="${sessionScope.customerAccount != null || sessionScope.staffAccount != null}">
                                    <form action="<c:out value='${editMode ? "editComment" : "comment"}'/>" method="post" class="comment-form my-5" id="comment-form">
                                        <h4 class="mb-4">
                                            <c:choose>
                                                <c:when test="${parent_comment_name != null}">
                                                    Reply to <b>${parent_comment_name}</b>
                                                    <a href="detailNews?newsID=${newsDetail.post_id}" class="text-danger ml-2">Cancel Reply</a>
                                                </c:when>
                                                <c:when test="${editMode}">
                                                    Edit Comment
                                                    <a href="detailNews?newsID=${newsDetail.post_id}" class="text-danger ml-2">Cancel Edit</a>
                                                </c:when>
                                                <c:otherwise>
                                                    Write a comment
                                                </c:otherwise>
                                            </c:choose>
                                        </h4>

                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <input class="form-control" type="text" name="name" 
                                                           value="${sessionScope.customerAccount != null ? sessionScope.customerAccount.fullName : sessionScope.staffAccount.fullName}" 
                                                           id="name" placeholder="Name:" required readonly>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <input class="form-control" type="email" name="mail" 
                                                           value="${sessionScope.customerAccount != null ? sessionScope.customerAccount.email : sessionScope.staffAccount.email}" 
                                                           id="mail" placeholder="Email:" required readonly>
                                                </div>
                                            </div>
                                        </div>
                                        <textarea class="form-control mb-4" name="content" id="comment" cols="30" rows="5" placeholder="Write your comment here..." required>${not empty param.content ? param.content : ""}</textarea>
                                        <input type="hidden" name="newsID" value="${newsDetail.post_id}">
                                        <c:if test="${sessionScope.customerAccount != null}">
                                            <input type="hidden" name="customerId" value="${sessionScope.customerAccount.customerID}">
                                        </c:if>
                                        <c:if test="${sessionScope.staffAccount != null}">
                                            <input type="hidden" name="staffId" value="${sessionScope.staffAccount.staffID}">
                                        </c:if>
                                        <input type="hidden" name="parent_comment_id" value="${editMode ? comment.parent_comment_id : (requestScope.parent_comment_id != null ? requestScope.parent_comment_id : 0)}">
                                        <input type="hidden" name="comment_id" value="${param.comment_id != null ? param.comment_id : (editMode ? comment.comment_id : 0)}">
                                        <input class="btn btn-main-2 btn-round-full" type="submit" name="submit-comment" id="submit_comment" value="${editMode ? "Update Comment" : "Submit"}">
                                    </form>
                                </c:if>
                                <c:if test="${sessionScope.customerAccount == null && sessionScope.staffAccount == null}">
                                    <p>Please <a href="login.jsp">login</a> to write a comment.</p>
                                </c:if>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-4">
                        <div class="sidebar-wrap pl-lg-4 mt-5 mt-lg-0">
                            <div class="sidebar-widget search  mb-3 ">
                                <h5>Search Here</h5>
                                <form action="allNews" method="get" class="search-form" style="display: flex; gap: 5px;">
                                    <input type="text" name="search" class="form-control" placeholder="Search" value="${search}" style="flex: 1;">
                                    <button type="submit" class="btn btn-primary"><i class="ti-search"></i>Search</button>
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
                                    <li class="align-items-center">
                                        <a href="allNews">All</a>
                                        <!--<span>(14)</span>-->
                                    </li>
                                    <c:forEach var="cate" items="${listCate}">
                                        <li class="align-items-center">
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
            </div>
        </section>

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
        <script src="assets2/js/script.js"></script>
        <script src="assets2/js/contact.js"></script>
    </body>
</html>
