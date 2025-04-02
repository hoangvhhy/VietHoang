
<%@ page import="java.time.LocalDate, java.time.temporal.TemporalAdjusters, java.time.DayOfWeek" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">


    <!-- edit-patient24:07-->
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">
        <title>Doctor's Weekly Schedule</title>
        <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/select2.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/bootstrap-datetimepicker.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <link rel="stylesheet" type="text/css" href="assets/css/css_viewPersonalSchedule.css">
        <style>
            .filter-section {
                margin: 20px 0;
                padding: 15px;
                background-color: #f9f9f9;
                border-radius: 5px;
                box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
            }

            .filter-section form {
                display: flex;
                align-items: center;
                white-space: nowrap;
            }

            .filter-section label {
                margin-right: 10px;
                font-weight: 500;
            }

            .filter-section select {
                padding: 8px 12px;
                border: 1px solid #ddd;
                border-radius: 4px;
                background-color: white;
                height: 38px;
                min-width: 150px;
                margin-right: 10px;
            }

            .filter-section button {
                padding: 8px 20px;
                height: 38px;
                width: 100px;
                background-color: #009efb;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-weight: 500;
                transition: background-color 0.2s;
                display: inline-block;
            }

            .filter-section button:hover {
                background-color: #0088d9;
            }

            /* Only apply responsive styles for very small screens */
            @media (max-width: 480px) {
                .filter-section form {
                    flex-direction: column;
                    align-items: flex-start;
                }

                .filter-section select,
                .filter-section button {
                    width: 100%;
                    margin-top: 5px;
                }
            }
        </style>
    </head>

    <body>
        <jsp:include page="headerStaff.jsp"></jsp:include>
        <jsp:include page="sidebar.jsp"></jsp:include>
            <div class="page-wrapper">
                <h1>Doctor's Weekly Schedule</h1>
                <form id="dateForm" method="GET" action="professionalleave">
                    <input type="hidden" name="professionalID" value="${param.professionalID}" />
                <label for="selectedDate">Select Date:</label>
                <input type="date" id="selectedDate" name="selectedDate" value="${selectedDate}">
                <button type="submit">Submit</button>
            </form>

            <table>
                <thead>
                    <tr>
                        <th>Shift</th>
                            <c:forEach var="day" begin="0" end="6">
                                <c:set var="date" value="${firstMondayOfWeek.plusDays(day)}" />
                            <th>${date.getDayOfWeek()} (${date})</th>
                            </c:forEach>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="shifts" value="${'MORNING,AFTERNOON,EVENING'.split(',')}" />
                    <c:forEach var="shift" items="${shifts}">
                        <tr class="shift">
                            <td>${shift}</td>
                            <c:forEach var="day" begin="0" end="6">
                                <td>
                                    <c:forEach var="schedule" items="${professionalList}">
                                        <c:set var="scheduleDate" value="${firstMondayOfWeek.plusDays(day)}" />
                                        <c:if test="${schedule.dayOfWeek == day + 2 && schedule.shift == shift}">
                                            <c:choose>
                                                <c:when test="${schedule.startTime == '00:00:00' && schedule.endTime == '00:00:00'}">
                                                    <span class="on-leave">On Leave: ${schedule.status}</span>
                                                </c:when>
                                                <c:otherwise>
                                                    ${schedule.startTime} - ${schedule.endTime}
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </c:forEach>
                                </td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <h1>Professional Leave List</h1>
            <!-- Add Status Filter -->
            <div class="filter-section">
                <form method="GET" action="professionalleave">
                    <input type="hidden" name="professionalID" value="${param.professionalID}" />
                    <input type="hidden" name="selectedDate" value="${selectedDate}" />
                    <label for="statusFilter">Filter by Status:</label>
                    <select name="statusFilter" id="statusFilter">
                        <option value="All" ${param.statusFilter == 'All' || empty param.statusFilter ? 'selected' : ''}>All</option>
                        <option value="Missed time!" ${param.statusFilter == 'Missed time!' ? 'selected' : ''}>Missed time!</option>
                        <option value="Pending" ${param.statusFilter == 'Pending' ? 'selected' : ''}>Pending</option>
                        <option value="Approved" ${param.statusFilter == 'Approved' ? 'selected' : ''}>Approved</option>
                        <option value="Rejected" ${param.statusFilter == 'Rejected' ? 'selected' : ''}>Rejected</option>
                    </select>
                    <button type="submit">Filter</button>
                </form>
            </div>

            <c:if test="${not empty errorMessage}">
                <div style="color: red; margin-bottom: 10px;">
                    ${errorMessage}
                </div>
            </c:if>

            <!-- Hiển thị thông báo thành công -->
            <c:if test="${not empty successMessage}">
                <div style="color: green; margin-bottom: 10px;">
                    ${successMessage}
                </div>
            </c:if>

            <div id="leave-section">
                <table id="leaveTable">
                    <tr>
                        <th>Leave ID</th>
                        <th>Professional ID</th>
                        <th>Leave Date</th>
                        <th>Reason</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                    <c:choose>
                        <c:when test="${not empty leaveList}">
                            <c:forEach var="leave" items="${leaveList}">
                                <c:if test="${param.statusFilter == 'All' || empty param.statusFilter || leave.status == param.statusFilter}">
                                    <tr>
                                        <td>${leave.leaveID}</td>
                                        <td>${leave.professionalID}</td>
                                        <td>${leave.leaveDate}</td>
                                        <td>${leave.reason}</td>
                                        <td>
                                            <form method="POST" action="professionalleave">
                                                <select name="status">
                                                    <option value="Missed time!" ${leave.status == 'Missed time!' ? 'selected' : ''}>Missed time!</option>
                                                    <option value="Pending" ${leave.status == 'Pending' ? 'selected' : ''}>Pending</option>
                                                    <option value="Approved" ${leave.status == 'Approved' ? 'selected' : ''}>Approved</option>
                                                    <option value="Rejected" ${leave.status == 'Rejected' ? 'selected' : ''}>Rejected</option>
                                                </select>
                                                <input type="hidden" name="oldStatus" value="${leave.status}" />
                                                <input type="hidden" name="leaveID" value="${leave.leaveID}" />
                                                <input type="hidden" name="professionalID" value="${leave.professionalID}" />
                                                <input type="hidden" name="leaveDate" value="${leave.leaveDate}" />
                                        </td>
                                        <td>
                                            <button type="submit">Update</button>
                                        </td>
                                        </form>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="6">No records found</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </table>
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
<script src="assets/js/moment.min.js"></script>
<script src="assets/js/bootstrap-datetimepicker.min.js"></script>
<script src="assets/js/app.js"></script>
</body>


<!-- edit-patient24:07-->
</html>
