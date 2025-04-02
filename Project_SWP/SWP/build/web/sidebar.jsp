<%-- 
    Document   : sidebar
    Created on : Feb 19, 2025, 1:27:33 AM
    Author     : Hoang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <div class="sidebar" id="sidebar">
        <div class="sidebar-inner slimscroll">
            <div id="sidebar-menu" class="sidebar-menu">
                <ul>
                    <c:set var="viewStaff" value="false"/>
                    <c:set var="viewRevenue" value="false"/>
                    <c:set var="viewUsage" value="false"/>
                    <c:set var="viewPatient" value="false"/>
                    <c:set var="viewAppointment" value="false"/>
                    <c:set var="viewQA" value="false"/>
                    <c:set var="viewComment" value="false"/>
                    <c:set var="viewSchedule" value="false"/>
                    <c:set var="viewService" value="false"/>
                    <c:set var="viewInvoice" value="false"/>
                    <c:set var="viewFeedback" value="false"/>
                    <c:set var="viewBlog" value="false"/>
                    <c:set var="viewRole" value="false"/>
                    <c:forEach var="permission" items="${sessionScope.role.permission}">
                        <c:if test="${permission.permissionID == 24}">
                            <c:set var="viewStaff" value="true"/>
                        </c:if>
                        <c:if test="${permission.permissionID == 21}">
                            <c:set var="viewRevenue" value="true"/>
                        </c:if>
                        <c:if test="${permission.permissionID == 22}">
                            <c:set var="viewUsage" value="true"/>
                        </c:if>
                        <c:if test="${permission.permissionID == 28}">
                            <c:set var="viewPatient" value="true"/>
                        </c:if>
                        <c:if test="${permission.permissionID == 1}">
                            <c:set var="viewAppointment" value="true"/>
                        </c:if>
                        <c:if test="${permission.permissionID == 30}">
                            <c:set var="viewQA" value="true"/>
                        </c:if>
                        <c:if test="${permission.permissionID == 15}">
                            <c:set var="viewSchedule" value="true"/>
                        </c:if>
                        <c:if test="${permission.permissionID == 18}">
                            <c:set var="viewService" value="true"/>
                        </c:if>
                        <c:if test="${permission.permissionID == 4}">
                            <c:set var="viewInvoice" value="true"/>
                        </c:if>
                        <c:if test="${permission.permissionID == 29}">
                            <c:set var="viewFeedback" value="true"/>
                        </c:if>
                        <c:if test="${permission.permissionID == 8}">
                            <c:set var="viewBlog" value="true"/>
                        </c:if>
                        <c:if test="${permission.permissionID == 27}">
                            <c:set var="viewRole" value="true"/>
                        </c:if>
                    </c:forEach>
                    <li class="menu-title">Main</li>


                    <c:if test="${viewRevenue}">
                        <li>
                            <a href="dashRevenue"><i class="fa fa-dashboard"></i> <span>Revenue Statistic</span></a>
                        </li>
                    </c:if>

                    <c:if test="${viewUsage}">
                        <li>
                            <a href="dashboard"><i class="fa fa-dashboard"></i> <span>Usage Statistic</span></a>
                        </li>
                    </c:if>
                    <c:if test="${viewStaff}">
                        <li>
                            <a href="staff"><i class="fa fa-user"> </i> <span>Staff</span>  </a>
                        </li>
                    </c:if>
                    <c:if test="${viewStaff}">
                        <li>
                            <a href="manage-doctor.jsp"><i class="fa fa-user-md"></i> <span>Doctors</span></a>
                        </li>
                    </c:if>

                    <c:if test="${viewPatient}">
                        <li>
                            <a href="patient"><i class="fa fa-wheelchair"></i> <span>Patients</span></a>
                        </li>
                    </c:if>
                    <c:choose>
                        <c:when test="${sessionScope.staffAccount.roleID == 4 || sessionScope.staffAccount.roleID == 5}">
                            <li>
                                <a href="viewpersonalschedule?professionalID=${sessionScope.staffAccount.staffID}"><i class="fa fa-calendar"></i> <span>Doctor Schedule</span></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${viewSchedule}">
                                <li>
                                    <a href="loadstaffforschedule"><i class="fa fa-calendar-check-o"></i> <span>Doctor Schedule</span></a>
                                </li>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${sessionScope.staffAccount.roleID == 4 || sessionScope.staffAccount.roleID == 5}">
                            <li>
                                <a href="listDoctorAppointment"><i class="fa fa-calendar"></i> <span>Appointments</span></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${viewAppointment}">
                                <li>
                                    <a href="manage_appointment"><i class="fa fa-calendar"></i> <span>Appointments</span></a>
                                </li>
                            </c:if>
                        </c:otherwise>
                    </c:choose>

                    <c:if test="${viewQA}">
                        <li>
                            <a href="viewQA"><i class="fa fa-commenting-o"></i> <span>QA</span></a>
                        </li>
                    </c:if>



                    <c:if test="${viewService}">
                        <li>
                            <a href="loadmanage"><i class="fa fa-hospital-o"></i> <span>Service</span></a>
                        </li>
                    </c:if>

                    <c:if test="${viewInvoice}">
                        <li class="submenu">
                            <a href="#"><i class="fa fa-money"></i> <span> Invoice </span> <span class="menu-arrow"></span></a>
                            <ul style="display: none;">
                                <li><a href="invoice">Invoices</a></li>
                                <li><a href="discount">Discount</a></li>
                            </ul>
                        </li>
                    </c:if>

                    <c:if test="${viewFeedback}">
                        <li >
                            <a href="feedback"><i class="fa fa-flag-o"></i> <span> Feedback </span> </a>
                        </li>
                    </c:if>

                    <c:if test="${viewBlog}">
                        <li class="submenu">
                            <a href="#"><i class="fa fa-commenting-o"></i> <span> Blog</span> <span class="menu-arrow"></span></a>
                            <ul style="display: none;">
                                <li><a href="homeblogseverlet">Blog</a></li>
                                <li><a href="categoryList">Category</a></li>
                                <li><a href="manageIntroduce">Introduce</a></li>
                            </ul>
                        </li>
                    </c:if>

                    <c:if test="${viewRole}">
                        <li>
                            <a href="rolePermission"><i class="fa fa-key"></i> <span>Roles & Permissions</span></a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
</html>
