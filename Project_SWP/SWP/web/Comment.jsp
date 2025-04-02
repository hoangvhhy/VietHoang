<%-- 
    Document   : Comment
    Created on : Mar 15, 2025, 10:58:59 PM
    Author     : Win11
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Hỏi đáp bác sĩ</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/select2.min.css">
        <link rel="stylesheet" type="text/css" href="css/pagination.css">
        <!-- Thêm CSS cho Datetimepicker -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">

        <style>
            /* Ẩn popup ban đầu */
            #editCommentPopup {
                display: none;
                position: fixed;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.3);
                z-index: 1000;
            }

            /* Nền mờ khi popup hiển thị */
            #popupOverlay {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.5);
                z-index: 999;
            }

        </style>
    </head>
    <body>
        <div class="main-wrapper">
            <!-- Header -->
            <jsp:include page="headerStaff.jsp"></jsp:include>
            <jsp:include page="sidebar.jsp"></jsp:include>

                <!-- Page Content -->
                <div class="page-wrapper">
                    <div class="content">
                        <div class="row">
                            <div class="col-sm-4 col-3">
                                <h4 class="page-title">Comments</h4>
                            </div>
                        </div>

                    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
                    <div class="container">
                        <h2>User Comments</h2>

                        <div class="row">
                            <div class="col-md-12">
                                <c:if test="${list == null || empty list}">
                                    <div class="alert alert-warning text-center">No comments available</div>
                                </c:if>
                                <!-- Bộ lọc theo ngày -->
                                <div class="mb-3">
                                    <label for="filterDate"><strong>Filter by Date:</strong></label>
                                    <input type="date" id="filterDate" class="form-control" onchange="filterComments()">
                                    <button class="btn btn-secondary mt-2" onclick="resetFilter()">Reset</button>
                                </div>

                                <c:forEach var="comment" items="${list}">

                                    <div class="card p-3 mb-3 comment-card" data-date="${comment.getDate()}">
                                        <div class="d-flex align-items-center mb-2">
                                            <strong class="text-primary">&#128172; ${comment.senderEmail}</strong>
                                            <br>
                                            <strong class="text-primary">&#128172; ${comment.topic}</strong>
                                        </div>
                                        <div class="p-3 bg-light rounded">
                                            <p>${comment.getCommentText()}</p>
                                        </div>
                                        <small class="text-muted">Posted on: ${comment.getDate()}</small>
                                        <div class="mt-2">
                                            <button class="btn btn-sm btn-primary" onclick="openReplyModal(${comment.commentId})">Reply</button>
                                            <button class="btn btn-sm btn-danger" onclick="deleteComment(${comment.commentId})">Delete</button>
                                        </div>
                                        <c:if test="${not empty comment.getReplies()}">
                                            <div class="replies-section">
                                                <p><strong>Replies:</strong></p>
                                                <ul>


                                                    <c:forEach var="reply" items="${comment.getReplies()}">
                                                        <li class="reply">
                                                            <p><strong>${reply.senderEmail}</strong> replied:</p>
                                                            <p>${reply.getCommentText()}</p>


                                                            <!--                                                                 Nút mở popup 
                                                            -->                                                                <button class="btn btn-warning btn-sm" 
                                                                                                                                       onclick="openPopup(${reply.commentId}, `${reply.getCommentText()}`)">
                                                                Edit
                                                            </button>

                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </c:if>
                                    </div>

                                </c:forEach>
                                <div id="pagination-container" class="pagination text-center"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Nền mờ -->
        <div id="popupOverlay" onclick="closePopup()"></div>

        <!-- Popup Edit -->
        <div id="editCommentPopup">
            <h5>Edit Comment</h5>
            <form action="editQA" method="POST">
                <input type="hidden" id="editPopupCommentId" name="commentId">
                <textarea id="editPopupCommentText" name="commentText" class="form-control" rows="3"></textarea>
                <br>
                <button type="submit" class="btn btn-primary">Save</button>
                <button type="button" class="btn btn-secondary" onclick="closePopup()">Cancel</button>
            </form>
        </div>
        <!-- Reply Modal -->
        <div class="modal fade" id="replyModal" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Reply to Comment</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" onclick="closePopup2()"></button>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" id="replyToCommentId">
                        <textarea id="replyText" class="form-control" rows="3" placeholder="Enter your reply..."></textarea>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" onclick="closePopup2()">Cancel</button>
                        <button type="button" class="btn btn-primary" onclick="submitReply()">Submit</button>
                    </div>
                </div>
            </div>
        </div>
    </body>

    <script>

        function openReplyModal(commentId) {
            document.getElementById('replyToCommentId').value = commentId;
            new bootstrap.Modal(document.getElementById('replyModal')).show();
        }

        function submitReply() {
            let replyToId = document.getElementById('replyToCommentId').value;
            let text = document.getElementById('replyText').value;

            if (text.trim() === '') {
                alert('Reply cannot be empty!');
                return;
            }
            console.log(replyToId);

            // Mã hóa text trong JavaScript thay vì EL
            let encodedText = encodeURIComponent(text);

            // Đảm bảo rằng commentId và replyText được mã hóa đúng cách
            let bodyData = new URLSearchParams();
            bodyData.append('commentId', replyToId);
            bodyData.append('replyText', encodedText);

            // Gửi request đến ReplyCommentServlet
            fetch('/SWP/replyQA', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: bodyData.toString()  // Convert the data into URL-encoded format
            })
                    .then(response => {
                        console.log('Response status:', response.status);
                        return response.text();  // Using response.text() to check the response body
                    })
                    .then(data => {
                        console.log('Response body:', data);
                        try {
                            const jsonData = JSON.parse(data);
                            if (jsonData.status === 'success') {
                                // Reload the page if the reply was successfully submitted
                                location.reload();  // This will reload the current page
                            }
                        } catch (e) {
                            console.error('Failed to parse JSON:', e);
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
        }




        function deleteComment(commentId) {
            if (confirm('Are you sure you want to delete this comment?')) {
                // Gửi request đến DeleteCommentServlet
                let bodyData = new URLSearchParams();
                bodyData.append('commentId', commentId);
                fetch('/SWP/deleteQA', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: bodyData.toString()  // Send commentId to the server to delete
                })
                        .then(response => {
                            console.log('Response status:', response.status);
                            return response.text();  // Check the response body
                        })
                        .then(data => {
                            console.log('Response body:', data);
                            try {
                                const jsonData = JSON.parse(data);
                                if (jsonData.status === 'success') {
                                    // Reload the page after successful deletion
                                    location.reload();  // This will reload the current page
                                }
                            } catch (e) {
                                console.error('Failed to parse JSON:', e);
                            }
                        })
                        .catch(error => {
                            console.error('Error:', error);
                        });
            }
        }
        function openPopup(commentId, commentText) {
            document.getElementById("editPopupCommentId").value = commentId;
            document.getElementById("editPopupCommentText").value = commentText;

            document.getElementById("editCommentPopup").style.display = "block";
            document.getElementById("popupOverlay").style.display = "block";
        }

        function closePopup() {
            document.getElementById("editCommentPopup").style.display = "none";
            document.getElementById("popupOverlay").style.display = "none";
        }
        function closePopup2() {
            let modalElement = document.getElementById("replyModal");
            let backdrop = document.querySelector(".modal-backdrop");

            if (modalElement) {
                modalElement.classList.remove("show");
                modalElement.style.display = "none";
                document.body.classList.remove("modal-open"); // Xóa class của Bootstrap
            }

            if (backdrop) {
                backdrop.remove(); // Xóa nền mờ
            }
        }


        function filterComments() {
            let filterDate = document.getElementById("filterDate").value; // Ngày từ input (YYYY-MM-DD)
            let comments = document.querySelectorAll(".comment-card"); // Lấy danh sách comment

            comments.forEach(comment => {
                let rawDate = comment.getAttribute("data-date"); // Ngày ban đầu (có thể khác format)
                let commentDate = convertToISODate(rawDate); // Chuyển về YYYY-MM-DD

                if (!filterDate || commentDate > filterDate) {
                    comment.style.display = "block"; // Hiện comment nếu khớp ngày
                } else {
                    comment.style.display = "none"; // Ẩn nếu không khớp
                }
            });
        }

// Chuyển đổi định dạng ngày từ `dd/MM/yyyy` → `YYYY-MM-DD`
        function convertToISODate(dateStr) {
            let parts = dateStr.split("/");
            if (parts.length === 3) {
                return `${parts[2]}-${parts[1]}-${parts[0]}`; // Trả về YYYY-MM-DD
                        }
                        return dateStr; // Nếu không đúng định dạng, giữ nguyên
                    }

// Hàm reset bộ lọc
                    function resetFilter() {
                        document.getElementById("filterDate").value = "";
                        filterComments(); // Hiển thị lại tất cả comments
                    }


    </script>

    <script src="assets/js/jquery-3.2.1.min.js"></script>
    <script src="assets/js/popper.min.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
    <script src="assets/js/jquery.slimscroll.js"></script>
    <script src="assets/js/select2.min.js"></script>
    <!-- Thêm Moment.js và Bootstrap Datetimepicker -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
    <script src="assets/js/app.js"></script>
    <script src="js/pagination.js"></script>
    <script>
                    document.addEventListener("DOMContentLoaded", function () {
                        paginateItems('.card', 2, '#pagination-container');
                    });
    </script>
</html>
