<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ page contentType="text/html" pageEncoding="UTF-8" %>


<!DOCTYPE html>
<html lang="zxx">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">
        <title>Preclinic - Medical & Hospital - Bootstrap 4 Admin Template</title>
        <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">

        <link rel="stylesheet" type="text/css" href="assets/css/select2.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/tagsinput.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <script src="https://cdn.ckeditor.com/ckeditor5/40.0.0/classic/ckeditor.js"></script>
        <!-- Custom CSS -->
        <style>
            .main-wrapper {
                display: flex;
            }

            .container {
                margin-top: 80px; /* Điều chỉnh theo kích thước header */
                padding: 20px;
            }



        </style>
    </head>
    <body>
        <div class="main-wrapper">
            <jsp:include page="headerStaff.jsp"></jsp:include>
            <jsp:include page="sidebar.jsp"></jsp:include>



                <div class="container" style="margin-left: 230px">
     <div class="form-container">
                        <h3>${editMode ? 'Sửa Introduce' : 'Thêm Introduce'}</h3>
                    <form action="updateIntroduce" method="post">
                        <div class="form-group">
                            <label for="packageID">Chọn Gói Dịch Vụ:</label>
                            <select name="packageID" id="packageID" class="form-control" required>
                                <c:forEach var="service" items="${serviceList}">
                                    <option value="${service.packageID}" ${service.packageID == selectedService.packageID ? 'selected' : ''}>
                                        ${service.packageName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="introduce">Introduce:</label>
                            <textarea name="introduce" id="introduce" class="form-control" rows="4">${selectedService.introduce}</textarea>
                        </div>
                        <button type="submit" class="btn btn-primary btn-custom">Lưu</button>
                        <a href="manageIntroduce" class="btn btn-secondary btn-custom">Hủy</a>
                    </form>
                </div>
            </div>

                         <script>

            document.addEventListener("DOMContentLoaded", function () {
                ClassicEditor
                        .create(document.querySelector('#introduce'), {
                            ckfinder: {
                                uploadUrl: '/SWP/uploadckedittor' // Trỏ đến servlet xử lý upload
                            }
                        })
                        .catch(error => console.error(error));
            });


        </script>


        




        <!-- Scripts -->
        <div class="sidebar-overlay" data-reff=""></div>
        <script src="assets/js/jquery-3.2.1.min.js"></script>
        <script src="assets/js/popper.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/jquery.slimscroll.js"></script>
        <script src="assets/js/select2.min.js"></script>
        <script src="assets/js/tagsinput.js"></script>
        <script src="assets/js/app.js"></script>
    </body>
</html>