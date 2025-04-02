<%-- 
    Document   : comment-reply
    Created on : Mar 5, 2025, 1:13:14 AM
    Author     : Hoang
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <c:forEach var="reply" items="${comments}">
        <c:if test="${reply.parent_comment_id == param.parentId}">
            <li class="mb-3">
                <div class="comment-area-box">
                    <c:if test="${reply.status == 0}">
                        <p class="text-muted">This comment has been deleted.</p>
                    </c:if>
                    <c:if test="${reply.status != 0}">
                        <div class="comment-thumb float-left">
                            <c:if test="${reply.customerID != null}">
                                <img style="width: 50px; height: 50px; border-radius: 50%;" 
                                     alt="" 
                                     src="pictureprofile?customerID=${reply.customerID.customerID}" 
                                     class="img-fluid">
                            </c:if>
                            <c:if test="${reply.staff_id != null}">
                                <img style="width: 50px; height: 50px; border-radius: 50%;" 
                                     alt="" 
                                     src="pictureprofile?staffID=${reply.staff_id.staffID}" 
                                     class="img-fluid">
                            </c:if>
                            <c:if test="${reply.customerID == null && reply.staff_id == null}">
                                <img style="width: 50px; height: 50px; border-radius: 50%;" 
                                     alt="Unknown" 
                                     src="assets/default-profile.png" 
                                     class="img-fluid">
                            </c:if>
                        </div>
                        <div class="comment-info">
                            <h5 class="mb-1 d-flex justify-content-between align-items-center">
                                <c:if test="${reply.customerID != null}">
                                    ${reply.customerID.fullName}
                                </c:if>
                                <c:if test="${reply.staff_id != null}">
                                    <span style="color: #009efb;">[Staff] ${reply.staff_id.fullName}</span>
                                </c:if>
                                <c:if test="${sessionScope.customerAccount != null 
                                              && sessionScope.customerAccount.customerID == reply.customerID.customerID 
                                              || sessionScope.staffAccount != null 
                                              && sessionScope.staffAccount.staffID == reply.staff_id.staffID}">
                                      <span>
                                          <a href="detailNews?newsID=${newsDetail.post_id}&editMode=true&comment_id=${reply.comment_id}&content=${reply.content}#comment-form" class="mr-2">
                                              <i class="icofont-edit text-muted"></i>Edit
                                          </a>
                                          <a href="deleteComment?comment_id=${reply.comment_id}" 
                                             onclick="return confirm('Are you sure to delete this comment?');">
                                              <i class="icofont-trash text-muted"></i>Delete
                                          </a>
                                      </span> 
                                </c:if>
                            </h5>
                            <span>${reply.create_at}</span>
                        </div>
                        <div class="comment-meta mt-2">
                            <c:if test="${sessionScope.customerAccount == null && sessionScope.staffAccount == null}">
                                <a href="login.jsp"
                                   onclick="return confirm('Bạn cần đăng nhập để trả lời bình luận!');">
                                    <i class="icofont-reply mr-2 text-muted"></i>Reply
                                </a>
                            </c:if>
                            <c:if test="${sessionScope.customerAccount != null || sessionScope.staffAccount != null}">
                                <a href="?newsID=${newsDetail.post_id}&parent_comment_id=${reply.comment_id}#comment-form">
                                    <i class="icofont-reply mr-2 text-muted"></i>Reply
                                </a>
                            </c:if>
                        </div>
                        <div class="comment-content mt-3">
                            <p>
                                <strong>Reply to 
                                    <c:if test="${reply.customerID != null}">
                                        ${reply.customerID.fullName}
                                    </c:if>
                                    <c:if test="${reply.staff_id != null}">
                                        <span style="color: #009efb;">[Staff] ${reply.staff_id.fullName}</span>
                                    </c:if>
                                    :</strong> 
                                    ${reply.content}
                            </p>
                        </div>
                    </c:if>
                </div>
                <!--<ul class="child-comments list-unstyled ml-5">-->
                <jsp:include page="comment-reply.jsp">
                    <jsp:param name="parentId" value="${reply.comment_id}" />
                    <jsp:param name="depth" value="${param.depth + 1}" />
                </jsp:include>
                <!--</ul>-->
            </li>
        </c:if>
    </c:forEach>







