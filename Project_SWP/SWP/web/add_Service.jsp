<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            h2, h3 {
                color: #007bff;
            }
            .btn-primary {
                background-color: #007bff;
                border-color: #0056b3;
            }
            .btn-secondary {
                background-color: #6c757d;
                border-color: #545b62;
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

            <!-- Hiển thị thông báo lỗi nếu có -->
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <form id="serviceForm" action="addService" method="post" enctype="multipart/form-data" class="mt-4">
                <!-- Package Name: Nếu có lỗi (duplicate name) thì để trống -->
                <div class="mb-3">
                    <label class="form-label">Package Name</label>
                    <input type="text" class="form-control" name="packageName" id="packageName" 
                           value="<c:if test='${empty error}'><c:out value='${param.packageName}'/></c:if>" required>
                           <div class="error" id="nameError"></div>
                    </div>
                    <!-- Description: Giữ lại giá trị đã nhập -->
                    <div class="mb-3">
                        <label class="form-label">Description</label>
                        <textarea class="form-control" name="description" id="description" rows="3" required>
                        <c:out value="${param.description}" />
                    </textarea>
                    <div class="error" id="descriptionError"></div>
                </div>
                <!-- Service Image: không thể giữ lại file đã chọn -->
                <div class="mb-3">
                    <label class="form-label">Service Image (chỉ jpg)</label>
                    <input type="file" class="form-control" name="service_image" id="serviceImage" required>
                    <div class="error" id="imageError"></div>
                </div>
                <!-- Type: Giữ lại lựa chọn của người dùng -->
                <div class="mb-3">
                    <label class="form-label">Type</label>
                    <select class="form-select" name="type" id="type" required>
                        <option value="">Chọn loại dịch vụ</option>
                        <option value="VIP" <c:if test="${param.type == 'VIP'}">selected</c:if>>VIP</option>
                        <option value="Basic" <c:if test="${param.type == 'Basic'}">selected</c:if>>Basic</option>
                        </select>
                        <div class="error" id="typeError"></div>
                    </div>
                    <!-- Price: Giữ lại giá trị đã nhập -->
                    <div class="mb-3">
                        <label class="form-label">Price ($)</label>
                        <input type="number" class="form-control" name="price" step="0.01" min="0.01" value="${param.price}" required>
                </div>
                <!-- Duration: Giữ lại giá trị đã nhập -->
                <div class="mb-3">
                    <label class="form-label">Duration (minutes)</label>
                    <input type="number" class="form-control" name="duration" step="1" min="1" value="${param.duration}" required>
                </div>
                <!-- Status: Giữ nguyên (read only) -->
                <div class="mb-3">
                    <label class="form-label">Status</label>
                    <input type="text" class="form-control" name="status" value="on" readonly required>
                </div>
                <!-- Submit/Cancel -->
                <div class="d-flex justify-content-between">
                    <a href="loadmanage" class="btn btn-secondary w-50 ms-2">Cancel</a>
                    <button type="submit" class="btn btn-primary w-50">Save</button>
                </div>
            </form>
        </div>
        <script>
            // Validate form và tự động xóa khoảng trắng cho Package Name và Description
            document.getElementById("serviceForm").addEventListener("submit", function (e) {
                let valid = true;

                // Reset thông báo lỗi
                document.getElementById("nameError").innerText = "";
                document.getElementById("descriptionError").innerText = "";
                document.getElementById("typeError").innerText = "";
                document.getElementById("imageError").innerText = "";

                // Trim giá trị Package Name và Description
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

                // Validate file upload: chỉ cho phép định dạng .jpg nếu có file được chọn
                let fileInput = document.getElementById("serviceImage");
                if (fileInput.value) {
                    let allowedExtension = /(\.jpg)$/i;
                    if (!allowedExtension.exec(fileInput.value)) {
                        document.getElementById("imageError").innerText = "Chỉ chấp nhận file có định dạng .jpg";
                        valid = false;
                    }
                }

                if (!valid) {
                    e.preventDefault(); // Ngăn form submit nếu có lỗi
                }
            });
        </script>
    </body>
</html>
