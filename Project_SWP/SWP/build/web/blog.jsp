<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="model.News" %> <!-- Thay your.package bằng tên gói thực tế -->
<!DOCTYPE html>
<html lang="en">

    <!-- blog23:34-->
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">
        <title>Preclinic - Medical & Hospital - Bootstrap 4 Admin Template</title>
        <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <link rel="stylesheet" type="text/css" href="assets/css/blog.css">



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
                            <div class="col-sm-8 col-4">
                                <h4 class="page-title">Blog</h4>
                            </div>
                            <div class="col-sm-4 col-8 text-right m-b-30">
                                <a class="btn btn-primary btn-rounded float-right" href="addblog">
                                    <i class="fa fa-plus"></i> Add Blog
                                </a>
                            </div>
                        </div>



                        <form action="homeblogseverlet" method="get" class="d-flex mb-3">
                            <select name="category" class="form-select me-2">
                                <option value="" <c:if test="${selectedCategory == -1}">selected</c:if>>All Categories</option>
                            <c:forEach var="category" items="${categoryList}">
                                <c:if test="${category.status == 1}">
                                    <option value="${category.category_id}" <c:if test="${selectedCategory == category.category_id}">selected</c:if>>
                                        ${category.name}
                                    </option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <button type="submit" class="btn btn-primary">Filter</button>
                    </form>





                    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

                    <script>
                        $(document).ready(function () {
                            $("#searchKeyword").keyup(function () {
                                let keyword = $(this).val().trim();
                                if (keyword.length > 0) {
                                    $.ajax({
                                        url: "SearchByAjax",
                                        method: "GET",
                                        data: {keyword: keyword},
                                        success: function (response) {
                                            $("#initialResults").hide(); // Ẩn danh sách ban đầu
                                            $("#searchResults").show().html(response); // Hiện và cập nhật kết quả AJAX
                                        },
                                        error: function () {
                                            $("#initialResults").hide();
                                            $("#searchResults").show().html("<div class=\"col-12\"><p style=\"color: red;\">⚠️ Error occurred while searching!</p></div>");
                                        }
                                    });
                                } else {
                                    $("#searchResults").hide(); // Ẩn kết quả AJAX
                                    $("#initialResults").show(); // Hiện lại danh sách ban đầu
                                }
                            });
                        });
                    </script>


                    <!-- Search Bar -->
                    <form action="Search_block" method="get" class="d-flex mx-auto my-2 my-lg-0">
                        <input type="text" name="keyword" id="searchKeyword" placeholder="Search blogs..." class="form-control" value="${keyword}">
                        <button type="submit" class="btn btn-primary">Search</button>
                    </form>

                    <!-- Khu vực hiển thị kết quả AJAX -->
                    <div id="searchResults" class="row" style="display: none;"></div>

                    <!-- Danh sách ban đầu -->
                    <div id="initialResults" class="row">
                        <c:if test="${empty blogs}">
                            <div class="col-12">
                                <p style="color: red;">⚠️ No blogs available!</p>
                            </div>
                        </c:if>

                        <c:forEach var="blog" items="${blogs}">
                            <div class="col-12">
                                <div class="blog-list-item">
                                    <a href="blogdetail?postId=${blog.post_id}">
                                        <img src="${blog.image}" alt="${blog.title}">
                                    </a>
                                    <div class="blog-content">
                                        <h3 class="blog-title">
                                            <a href="blogdetail?postId=${blog.post_id}">${blog.title}</a>
                                        </h3>
                                        <p>${blog.content}</p>
                                        <a href="blogdetail?postId=${blog.post_id}" class="read-more">
                                            <i class="fa fa-long-arrow-right"></i> Read More
                                        </a>
                                        <a href="editblog?postId=${blog.post_id}" class="btn btn-warning btn-sm">
                                            Update
                                        </a>
                                        <form action="deleteblog" method="post" style="display: inline;" 
                                              onsubmit="return confirm('Bạn có chắc chắn muốn xóa bài viết này?');">
                                            <input type="hidden" name="postId" value="${blog.post_id}">
                                            <button type="submit" class="btn btn-danger btn-sm">🗑 Xóa</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <!-- Pagination -->
                    <div class="pagination">
                        <c:if test="${totalPages > 1}">
                            <ul class="pagination justify-content-center">
                                <!-- Previous Link -->
                                <c:if test="${currentPage > 1}">
                                    <li class="page-item">
                                        <a class="page-link" href="homeblogseverlet?page=${currentPage - 1}">Previous</a>
                                    </li>
                                </c:if>

                                <!-- First Page -->
                                <li class="page-item ${currentPage == 1 ? 'active' : ''}">
                                    <a class="page-link" href="homeblogseverlet?page=1">1</a>
                                </li>

                                <!-- Ellipsis and Middle Pages -->
                                <c:if test="${currentPage > 3 && totalPages > 5}">
                                    <li class="page-item disabled">
                                        <span class="page-link">...</span>
                                    </li>
                                </c:if>

                                <!-- Pages Around Current Page -->
                                <c:forEach begin="${currentPage - 1}" end="${currentPage + 1}" var="i">
                                    <c:if test="${i > 1 && i < totalPages}">
                                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                                            <a class="page-link" href="homeblogseverlet?page=${i}">${i}</a>
                                        </li>
                                    </c:if>
                                </c:forEach>

                                <!-- Ellipsis Before Last Page -->
                                <c:if test="${currentPage < totalPages - 2 && totalPages > 5}">
                                    <li class="page-item disabled">
                                        <span class="page-link">...</span>
                                    </li>
                                </c:if>

                                <!-- Last Page (if more than 1 page) -->
                                <c:if test="${totalPages > 1}">
                                    <li class="page-item ${currentPage == totalPages ? 'active' : ''}">
                                        <a class="page-link" href="homeblogseverlet?page=${totalPages}">${totalPages}</a>
                                    </li>
                                </c:if>

                                <!-- Next Link -->
                                <c:if test="${currentPage < totalPages}">
                                    <li class="page-item">
                                        <a class="page-link" href="homeblogseverlet?page=${currentPage + 1}">Next</a>
                                    </li>
                                </c:if>
                            </ul>
                        </c:if>
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


    <!-- blog23:51-->
</html>