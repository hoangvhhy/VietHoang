<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh Sách Lịch Làm Việc</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/bootstrap-datetimepicker.min.css">
        <title>Preclinic - Medical & Hospital - Bootstrap 4 Admin Template</title>
        <link rel="stylesheet" type="text/css" href="assets/css/css_list_doctor.css">
    </head>
    <body>
        <div class="main-wrapper">
            <jsp:include page="headerStaff.jsp"></jsp:include>
            <jsp:include page="sidebar.jsp"></jsp:include>
            <c:set var="view" value="false"/>
            <c:set var="view1" value="false"/>
            <c:forEach var="permission" items="${listPermission}">
                <c:if test="${permission.permissionID == 16}">
                    <c:set var="view" value="true"/>
                </c:if>
                <c:if test="${permission.permissionID == 31}">
                    <c:set var="view1" value="true"/>
                </c:if>
            </c:forEach>
            <div class="container mt-5">
                <h2>Danh Sách Lịch Làm Việc</h2>

                <!-- Search by Name (Top-left corner) -->
                <div class="row mb-3">
                    <div class="col-md-4">
                        <form action="loadstaffforschedule" method="POST" class="d-flex align-items-end gap-2">
                            <input type="hidden" name="searchType" value="name">
                            <div class="flex-grow-1">
                                <label for="doctorName" class="form-label control-label">
                                    <i class="fas fa-search"></i> Tìm kiếm theo tên
                                </label>
                                <input type="text" class="form-control" id="doctorName" name="searchName" placeholder="Nhập tên bác sĩ" value="${param.searchName}">
                            </div>
                            <button type="submit" class="btn search-btn">
                                <i class="fas fa-search"></i>
                            </button>
                            <a href="loadstaffforschedule" class="btn reset-btn">
                                <i class="fas fa-sync-alt"></i>
                            </a>
                        </form>
                    </div>
                </div>

                <!-- Search by Date and Shift + Filter by Day and Shift -->
                <div class="row mb-4 g-3">
                    <!-- Search by Date and Shift -->
                    <div class="col-md-6">
                        <form action="loadstaffforschedule" method="POST" class="row g-2 align-items-end">
                            <input type="hidden" name="searchType" value="date">
                            <div class="col-5">
                                <label for="workDate" class="form-label control-label">Tìm kiếm theo ngày</label>
<!--                                <input type="date" class="form-control" id="workDate" name="workDate" required="" value="${param.workDate}">-->
                                <input type="text" class="form-control datetimepicker" placeholder="DD/MM/YYYY" id="workDate" name="workDate" required="" value="${param.workDate}">
                            </div>
                            <div class="col-5">
                                <label for="shift" class="form-label">Ca trong ngày</label>
                                <select class="form-select" id="shift" name="shiftFilter">
                                    <option value="">Tất cả</option>
                                    <option value="MORNING" ${param.shiftFilter == 'MORNING' ? 'selected' : ''}>MORNING</option>
                                    <option value="AFTERNOON" ${param.shiftFilter == 'AFTERNOON' ? 'selected' : ''}>AFTERNOON</option>
                                    <option value="EVENING" ${param.shiftFilter == 'EVENING' ? 'selected' : ''}>EVENING</option>
                                </select>
                            </div>
                            <div class="col-2">
                                <button type="submit" class="btn search-btn w-100">
                                    <i class="fas fa-search"></i>
                                </button>
                            </div>
                        </form>
                    </div>

                    <!-- Filter by Day and Shift -->
                    <div class="col-md-6">
                        <form action="loadstaffforschedule" method="POST" class="row g-2 align-items-end">
                            <input type="hidden" name="searchType" value="dayandshift">
                            <div class="col-5">
                                <label for="dayName" class="form-label">Ngày trong tuần</label>
                                <select class="form-select" id="dayName" name="dayFilter" required="">
                                    <option value="">Chọn ngày</option>
                                    <option value="2" ${param.dayFilter == '2' ? 'selected' : ''}>Monday</option>
                                    <option value="3" ${param.dayFilter == '3' ? 'selected' : ''}>Tuesday</option>
                                    <option value="4" ${param.dayFilter == '4' ? 'selected' : ''}>Wednesday</option>
                                    <option value="5" ${param.dayFilter == '5' ? 'selected' : ''}>Thursday</option>
                                    <option value="6" ${param.dayFilter == '6' ? 'selected' : ''}>Friday</option>
                                    <option value="7" ${param.dayFilter == '7' ? 'selected' : ''}>Saturday</option>
                                    <option value="8" ${param.dayFilter == '8' ? 'selected' : ''}>Sunday</option>
                                </select>
                            </div>
                            <div class="col-5">
                                <label for="shift" class="form-label">Ca trong ngày</label>
                                <select class="form-select" id="shift" name="shiftFilter">
                                    <option value="">Tất cả</option>
                                    <option value="MORNING" ${param.shiftFilter == 'MORNING' ? 'selected' : ''}>MORNING</option>
                                    <option value="AFTERNOON" ${param.shiftFilter == 'AFTERNOON' ? 'selected' : ''}>AFTERNOON</option>
                                    <option value="EVENING" ${param.shiftFilter == 'EVENING' ? 'selected' : ''}>EVENING</option>
                                </select>
                            </div>
                            <div class="col-2">
                                <button type="submit" class="btn filter-btn w-100">
                                    <i class="fas fa-filter"></i>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

                <c:set var="prevID" value="-1" />
                <c:forEach var="schedule" items="${professionalList}">
                    <c:if test="${schedule.professionalID ne prevID}">
                        <c:if test="${prevID ne -1}">
                            </tbody>
                            </table>
                        </c:if>
                        <div class="doctor-header">
                            <span>Bác sĩ: ${schedule.fullName} (ID: ${schedule.professionalID})</span>
                            <c:if test="${view}">
                                <form action="saveSchedule" method="GET">
                                    <input type="hidden" name="fullName" value="${schedule.fullName}">
                                    <input type="hidden" name="professionalID" value="${schedule.professionalID}">
                                    <button type="submit" class="manage-btn">Manage Lịch</button>
                                </form>
                            </c:if>
                     
                                <form action="viewpersonalschedule" method="GET">
                                    <input type="hidden" name="fullName" value="${schedule.fullName}">
                                    <input type="hidden" name="professionalID" value="${schedule.professionalID}">
                                    <button type="submit" class="manage-btn">View personal schedule</button>
                                </form>
                           
                            <c:if test="${view}">
                                <form action="professionalleave" method="GET">
                                    <input type="hidden" name="fullName" value="${schedule.fullName}">
                                    <input type="hidden" name="professionalID" value="${schedule.professionalID}">
                                    <button type="submit" class="manage-btn">Manage Personal Leave</button>
                                </form>
                            </c:if>

                        </div>
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>Ngày</th>
                                    <th>Ca</th>
                                    <th>Giờ bắt đầu</th>
                                    <th>Giờ kết thúc</th>
                                    <th>Trạng thái</th>
                                </tr>
                            </thead>
                            <tbody>
                            </c:if>
                            <tr>
                                <td>${schedule.dayName}</td>
                                <td>${schedule.shift}</td>
                                <td>${schedule.startTime}</td>
                                <td>${schedule.endTime}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${schedule.status == 'On'}">
                                            <a href="saveSchedule?update=statusUpdate&professionalID=${schedule.professionalID}&dayOfWeek=${schedule.dayOfWeek}&shift=${schedule.shift}&status=On" 
                                               class="btn btn-success btn-sm" 
                                               onclick="return confirm('Bạn có chắc chắn muốn tắt trạng thái này không?')">On</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="saveSchedule?update=statusUpdate&professionalID=${schedule.professionalID}&dayOfWeek=${schedule.dayOfWeek}&shift=${schedule.shift}&status=Off" 
                                               class="btn btn-danger btn-sm" 
                                               onclick="return confirm('Bạn có chắc chắn muốn bật trạng thái này không?')">Off</a>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <c:set var="prevID" value="${schedule.professionalID}" />
                        </c:forEach>
                        <c:if test="${not empty professionalList}">
                        </tbody>
                    </table>
                </c:if>

                <!-- No Results Message -->
                <c:if test="${empty professionalList}">
                    <div class="alert alert-info text-center mt-4">
                        <i class="fas fa-info-circle"></i> Không tìm thấy kết quả phù hợp.
                    </div>
                </c:if>
            </div>

            <!-- Pagination -->
            <div class="d-flex justify-content-center align-items-center mt-4">
                <nav aria-label="Pagination">
                    <ul class="pagination">
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <form action="loadstaffforschedule" method="POST">
                                    <input type="hidden" name="page" value="${currentPage - 1}">
                                    <input type="hidden" name="searchType" value="${param.searchType}">
                                    <input type="hidden" name="searchName" value="${param.searchName}">
                                    <input type="hidden" name="workDate" value="${param.workDate}">
                                    <input type="hidden" name="dayFilter" value="${param.dayFilter}">
                                    <input type="hidden" name="shiftFilter" value="${param.shiftFilter}">
                                    <button type="submit" class="page-link">« Trước</button>
                                </form>
                            </li>
                        </c:if>

                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <form action="loadstaffforschedule" method="POST">
                                    <input type="hidden" name="page" value="${i}">
                                    <input type="hidden" name="searchType" value="${param.searchType}">
                                    <input type="hidden" name="searchName" value="${param.searchName}">
                                    <input type="hidden" name="workDate" value="${param.workDate}">
                                    <input type="hidden" name="dayFilter" value="${param.dayFilter}">
                                    <input type="hidden" name="shiftFilter" value="${param.shiftFilter}">
                                    <button type="submit" class="page-link">${i}</button>
                                </form>
                            </li>
                        </c:forEach>

                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <form action="loadstaffforschedule" method="POST">
                                    <input type="hidden" name="page" value="${currentPage + 1}">
                                    <input type="hidden" name="searchType" value="${param.searchType}">
                                    <input type="hidden" name="searchName" value="${param.searchName}">
                                    <input type="hidden" name="workDate" value="${param.workDate}">
                                    <input type="hidden" name="dayFilter" value="${param.dayFilter}">
                                    <input type="hidden" name="shiftFilter" value="${param.shiftFilter}">
                                    <button type="submit" class="page-link">Sau »</button>
                                </form>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </div>

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
        <script src="assets/js/bootstrap-datetimepicker.min.js"></script>
    </body>
</html>