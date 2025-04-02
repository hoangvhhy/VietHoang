<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Tạo lịch làm việc</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="assets/css/css_create_WorkingSchedule.css">
    </head>
    <body>
        <div class="container mt-4">
            <jsp:include page="headerStaff.jsp"></jsp:include>
            <jsp:include page="sidebar.jsp"></jsp:include>
                <a href="loadstaffforschedule" class="btn btn-secondary mb-3">Quay lại</a>
                <h2 class="text-center text-primary">Tạo Lịch Làm Việc Cho Bác Sĩ</h2>
                <div class="doctor-info text-center mt-3 mb-4">
                    <span>${ID} - ${fullName}</span>
            </div>
            <form id="scheduleForm" action="saveSchedule" method="POST">
                <input type="hidden" name="ID" value="${ID}">
                <div class="mb-3">
                    <label class="form-label text-primary">Chọn lịch làm việc:</label>
                    <c:set var="days" value="Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday"/>
                    <c:set var="dayValues" value="2,3,4,5,6,7,8"/>
                    <c:forEach var="day" items="${fn:split(days, ',')}" varStatus="status">
                        <div class="border p-3 mb-3 rounded">
                            <h5 class="text-primary">${day}</h5>
                            <div class="row">
                                <c:set var="dayValue" value="${fn:split(dayValues, ',')[status.index]}" />
                                <div class="col-md-4">
                                    <input type="checkbox" class="shift-checkbox" data-day="${dayValue}" data-shift="MORNING">
                                    <label class="text-dark">Sáng (07:00 - 12:00)</label>
                                    <div class="shift-time d-none">
                                        <label>Bắt đầu:</label>
                                        <input type="time" name="shift-${dayValue}-MORNING-start" class="form-control morning-start">
                                        <label>Kết thúc:</label>
                                        <input type="time" name="shift-${dayValue}-MORNING-end" class="form-control morning-end">
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <input type="checkbox" class="shift-checkbox" data-day="${dayValue}" data-shift="AFTERNOON">
                                    <label class="text-dark">Chiều (13:00 - 18:00)</label>
                                    <div class="shift-time d-none">
                                        <label>Bắt đầu:</label>
                                        <input type="time" name="shift-${dayValue}-AFTERNOON-start" class="form-control afternoon-start">
                                        <label>Kết thúc:</label>
                                        <input type="time" name="shift-${dayValue}-AFTERNOON-end" class="form-control afternoon-end">
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <input type="checkbox" class="shift-checkbox" data-day="${dayValue}" data-shift="EVENING">
                                    <label class="text-dark">Tối (18:00 - 23:00)</label>
                                    <div class="shift-time d-none">
                                        <label>Bắt đầu:</label>
                                        <input type="time" name="shift-${dayValue}-EVENING-start" class="form-control evening-start">
                                        <label>Kết thúc:</label>
                                        <input type="time" name="shift-${dayValue}-EVENING-end" class="form-control evening-end">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <button type="submit" class="btn btn-primary w-100 mt-3">Lưu lịch làm việc</button>
            </form>
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                document.querySelectorAll(".shift-checkbox").forEach(checkbox => {
                    checkbox.addEventListener("change", function () {
                        let shiftContainer = this.parentElement.querySelector(".shift-time");
                        if (this.checked) {
                            shiftContainer.classList.remove("d-none");
                        } else {
                            shiftContainer.classList.add("d-none");
                        }
                    });
                });

                document.getElementById("scheduleForm").addEventListener("submit", function (event) {
                    let isValid = true;
                    let errorMsg = "";
                    document.querySelectorAll(".shift-time:not(.d-none)").forEach(shift => {
                        let startInput = shift.querySelector("input[name*='start']");
                        let endInput = shift.querySelector("input[name*='end']");
                        let shiftType = startInput.name.includes("MORNING") ? "MORNING" : startInput.name.includes("AFTERNOON") ? "AFTERNOON" : "EVENING";
                        let minTime = shiftType === "MORNING" ? "07:00" : shiftType === "AFTERNOON" ? "13:00" : "18:00";
                        let maxTime = shiftType === "MORNING" ? "12:00" : shiftType === "AFTERNOON" ? "18:00" : "23:00";

                        if (!startInput.value || !endInput.value) {
                            isValid = false;
                            errorMsg += "Vui lòng nhập giờ làm đầy đủ.\n";
                        } else if (startInput.value < minTime || startInput.value > maxTime || endInput.value < minTime || endInput.value > maxTime) {
                            isValid = false;
                            errorMsg += "Giờ làm cho ca " + shiftType + " chỉ từ " + minTime + " đến " + maxTime + ".\n";
                        } else if (startInput.value >= endInput.value) {
                            isValid = false;
                            errorMsg += "Giờ bắt đầu phải sớm hơn giờ kết thúc.\n";
                        }
                    });

                    if (!isValid) {
                        event.preventDefault();
                        alert(errorMsg);
                    }
                });
            });
        </script>
        <script src="assets/js/jquery-3.2.1.min.js"></script>
        <script src="assets/js/popper.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/jquery.dataTables.min.js"></script>
        <script src="assets/js/dataTables.bootstrap4.min.js"></script>
        <script src="assets/js/jquery.slimscroll.js"></script>
        <script src="assets/js/select2.min.js"></script>
        <script src="assets/js/moment.min.js"></script>
        <script src="assets/js/bootstrap-datetimepicker.min.js"></script>
        <script src="assets/js/app.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
