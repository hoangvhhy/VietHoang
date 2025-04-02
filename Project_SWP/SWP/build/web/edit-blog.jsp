<!DOCTYPE html>
<html lang="en">


    <!-- edit-blog23:57-->
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">
        <title>Preclinic - Medical & Hospital - Bootstrap 4 Admin Template</title>
        <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/select2.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/tagsinput.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <!--[if lt IE 9]>
                    <script src="assets/js/html5shiv.min.js"></script>
                    <script src="assets/js/respond.min.js"></script>
            <![endif]-->




        <script src="https://cdn.ckeditor.com/ckeditor5/40.0.0/classic/ckeditor.js"></script>


    </head>

    <body>
        <div class="main-wrapper">
            <jsp:include page="headerStaff.jsp"></jsp:include>
            <jsp:include page="sidebar.jsp"></jsp:include>









            <%@ page contentType="text/html;charset=UTF-8" language="java" %>
            <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

            <div class="page-wrapper">
                <div class="content">
                    <div class="row">
                        <div class="col-lg-8 offset-lg-2">


                            <h4 class="page-title">Edit Blog</h4>

                            <form method="post" action="editblog" enctype="multipart/form-data">
                                <input type="hidden" name="postId" value="${blog.post_id}">

                                <div class="form-group">
                                    <label>Blog Name</label>
                                    <input class="form-control" type="text" name="title" required value="${blog.title}">
                                </div>
                                <div class="form-group">
                                    <label>Description</label>
                                    <textarea cols="30" rows="2" class="form-control" name="description" required>${blog.content}</textarea>
                                </div>

                                <div class="form-group">
                                    <label>Category</label>
                                    <c:if test="${empty categoryList}">
                                        <p style="color: red;">No categories found. Please add some categories.</p>
                                    </c:if>
                                    <select class="form-control" name="categoryId" required>
                                        <option value="">Select Category</option>
                                        <c:forEach items="${categoryList}" var="cat">
                                            <c:if test="${cat.status == 1}">
                                                <option value="${cat.category_id}" ${cat.category_id == blog.category_id ? 'selected' : ''}>
                                                    ${cat.name}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>

                                </div>


                                <div class="form-group">
                                    <label>Blog Images</label>
                                    <input class="form-control" type="file" name="image" id="image" accept="image/png, image/jpeg, image/gif, image/jpg">
                                    <c:if test="${not empty blog.image}">
                                        <img src="${blog.image}" width="100">
                                    </c:if>
                                </div>

                                <div class="form-group">
                                    <label>Blog Content</label>
                                    <textarea cols="30" rows="6" class="form-control" name="detail" id="detail" >${blog.detail}</textarea>
                                </div>

                                <div class="form-group">
                                    <label class="display-block">Blog Status</label>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" name="status" value="1" ${blog.status == 1 ? 'checked' : ''}>
                                        <label class="form-check-label">Active</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" name="status" value="2" ${blog.status == 2 ? 'checked' : ''}>
                                        <label class="form-check-label">Inactive</label>
                                    </div>
                                </div>

                                <div class="m-t-20 text-center">
                                    <button class="btn btn-primary submit-btn">Update</button>
                                </div>
                            </form>

                           






 <script>
                                document.addEventListener("DOMContentLoaded", function () {
                                    ClassicEditor
                                            .create(document.querySelector('#detail'), {
                                                ckfinder: {
                                                    uploadUrl: '/SWP/uploadckedittor' // Trỏ đến servlet xử lý upload
                                                }
                                            })
                                            .catch(error => console.error(error));
                                });



                            </script>


                            <script>
                                document.addEventListener("DOMContentLoaded", function () {
                                    document.getElementById("image").addEventListener("change", function () {
                                        const file = this.files[0];
                                        if (file) {
                                            const allowedTypes = ["image/png", "image/jpeg", "image/gif", "image/jpg"];
                                            if (!allowedTypes.includes(file.type) || file.size > 50 * 1024 * 1024) {
                                                alert("Only PNG, JPEG, JPG, GIF files are allowed and must be under 50MB.");
                                                this.value = '';
                                            }
                                        }
                                    });
                                });

                            </script>
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
        <script src="assets/js/select2.min.js"></script>
        <script src="assets/js/tagsinput.js"></script>
        <script src="assets/js/app.js"></script>
    </body>


    <!-- edit-blog23:57-->
</html>