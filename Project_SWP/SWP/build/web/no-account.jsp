<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thông báo đăng ký</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                background-color: #f9f9f9;
            }
            .container {
                text-align: center;
                border: 1px solid #ccc;
                border-radius: 8px;
                padding: 20px;
                background-color: white;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                width: 350px;
            }
            button {
                margin-top: 20px;
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                background-color: #007bff;
                color: white;
                font-size: 16px;
                cursor: pointer;
            }
            button:hover {
                background-color: #0056b3;
            }
            .back-btn {
                background-color: #6c757d; /* Màu xám */
                margin-left: 10px;
            }
            .back-btn:hover {
                background-color: #5a6268;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Chưa có tài khoản</h2>
            <p>Xin chào <b>${requestScope.username}</b>, email của bạn <b>${requestScope.email}</b> chưa được đăng ký trong hệ thống.</p>
            <p>Vui lòng đăng ký để sử dụng dịch vụ.</p>    
            <form action="register.jsp" method="POST" style="display: inline;">
                <input type="hidden" name="email" value="${requestScope.email}">
                <input type="hidden" name="username" value="${requestScope.username}">
                <input type="hidden" name="profilePicture" value="${requestScope.profilePicture}">
                <button type="submit">Đăng ký ngay</button>
            </form>


            <button class="back-btn" onclick="window.location.href = 'index_1.jsp'">Quay lại trang chủ</button>
        </div>
    </body>
</html>