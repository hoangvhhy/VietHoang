<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Hospital Service Management</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
            body {
                background-color: #e6f2ff;
                font-family: Arial, sans-serif;
            }
            .container {
                background: white;
                padding: 30px;
                border-radius: 12px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            }
            h2 {
                color: #007bff;
            }
            .btn-primary {
                background-color: #007bff;
                border-color: #0056b3;
            }
            .table-primary {
                background-color: #cce5ff;
            }
            .error {
                color: red;
                font-size: 0.9rem;
            }
        </style>
    </head>
    <body>
        <div class="container mt-5">
            <h2 class="text-primary text-center">Hospital Service Management</h2>
            <form id="serviceForm" action="updateservice" method="post" enctype="multipart/form-data" class="mt-4">
                <!-- Package ID -->
                <div class="mb-3">
                    <label class="form-label">Package ID</label>
                    <input type="text" class="form-control" name="packageID" value="${service.packageID}" readonly>
                </div>
                <!-- Package Name -->
                <div class="mb-3">
                    <label class="form-label">Package Name</label>
                    <input type="text" class="form-control" name="packageName" id="packageName" value="${service.packageName}" required>
                    <div class="error" id="nameError"></div>
                </div>
                <!-- Description -->
                <div class="mb-3">
                    <label class="form-label">Description</label>
                    <textarea class="form-control" name="description" id="description" rows="3" required>${service.description}</textarea>
                    <div class="error" id="descriptionError"></div>
                </div>
                <!-- Service Image -->
                <div class="mb-3">
                    <label class="form-label">Service Image (chỉ jpg)</label>
                    <input type="file" class="form-control" name="service_image" id="serviceImage">
                    <div class="error" id="imageError"></div>
                    <div class="mt-2">
                        <img src="getimage?packageID=${service.packageID}" alt="Service Image" class="img-fluid">
                    </div>
                    <!-- Giữ ID ảnh cũ nếu không upload ảnh mới -->
                    <input type="hidden" name="old_image_id" value="${service.packageID}">
                </div>
                <!-- Type (đổi sang select như trang add service) -->
                <div class="mb-3">
                    <label class="form-label">Type</label>
                    <select class="form-select" name="type" id="type" required>
                        <option value="">Chọn loại dịch vụ</option>
                        <option value="VIP" <c:if test="${service.type == 'VIP'}">selected</c:if>>VIP</option>
                        <option value="Basic" <c:if test="${service.type == 'Basic'}">selected</c:if>>Basic</option>
                    </select>
                    <div class="error" id="typeError"></div>
                </div>
                <!-- Price -->
                <div class="mb-3">
                    <label class="form-label">Price ($)</label>
                    <input type="number" class="form-control" name="price" step="0.01" min="0.01" value="${service.price}" required>
                </div>
                <!-- Duration -->
                <div class="mb-3">
                    <label class="form-label">Duration (minutes)</label>
                    <input type="number" class="form-control" name="duration" step="1" min="1" value="${service.duration}" required>
                </div>
                <!-- Nút Update & Cancel -->
                <div class="d-flex justify-content-between">
                    <a href="loadmanage" class="btn btn-danger">Cancel</a>
                    <button type="submit" class="btn btn-primary">Update</button>
                </div>
            </form>
        </div>

        <script>
            // Validate form trước khi submit: tự động trim khoảng trắng cho Package Name và Description,
            // kiểm tra các trường không được để trống và validate file upload nếu có.
            document.getElementById("serviceForm").addEventListener("submit", function (e) {
                let valid = true;

                // Reset thông báo lỗi
                document.getElementById("nameError").innerText = "";
                document.getElementById("descriptionError").innerText = "";
                document.getElementById("typeError").innerText = "";
                document.getElementById("imageError").innerText = "";

                // Lấy và trim giá trị Package Name, Description
                let packageInput = document.getElementById("packageName");
                packageInput.value = packageInput.value.trim();
                let descriptionInput = document.getElementById("description");
                descriptionInput.value = descriptionInput.value.trim();

                // Validate Package Name
                if (packageInput.value === "") {
                    document.getElementById("nameError").innerText = "Package Name không được để trống.";
                    valid = false;
                }

                // Validate Description
                if (descriptionInput.value === "") {
                    document.getElementById("descriptionError").innerText = "Description không được để trống hoặc chuỗi rỗng.";
                    valid = false;
                }

                // Validate Type
                let type = document.getElementById("type").value;
                if (type === "") {
                    document.getElementById("typeError").innerText = "Vui lòng chọn loại dịch vụ.";
                    valid = false;
                }

                // Validate file upload: Nếu có file được chọn thì chỉ chấp nhận định dạng .jpg
                let fileInput = document.getElementById("serviceImage");
                if (fileInput.value) {
                    let allowedExtension = /(\.jpg)$/i;
                    if (!allowedExtension.exec(fileInput.value)) {
                        document.getElementById("imageError").innerText = "Chỉ chấp nhận file có định dạng .jpg";
                        valid = false;
                    }
                }
                
                if (!valid) {
                    e.preventDefault();
                }
            });
        </script>
    </body>
</html>
