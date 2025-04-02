<%-- 
    Document   : invoice-view-customer
    Created on : Mar 16, 2025, 12:58:16 AM
    Author     : Gigabyte
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">


    <!-- invoice-view24:07-->
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">
        <title>Preclinic - Medical & Hospital - Bootstrap 4 Admin Template</title>
        <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <!--[if lt IE 9]>
                    <script src="assets/js/html5shiv.min.js"></script>
                    <script src="assets/js/respond.min.js"></script>
            <![endif]-->
    </head>

    <body>
        <div class="main-wrapper">
            <jsp:include page="editseting.jsp"></jsp:include>
            <jsp:include page="sidebarCustomer.jsp"></jsp:include>
                <div class="page-wrapper">
                    <div class="content">
                        <div class="row">
                            <div class="col-sm-5 col-4">
                                <h4 class="page-title">Invoice</h4>
                            </div>
                            <div class="col-sm-7 col-8 text-right m-b-30">
                                <div class="btn-group btn-group-sm">
                                    <form action="xuatPDF" method="get">
                                        <input name="invoiceID" value="${invoice.invoiceID}" hidden=""/>
                                      <button class="btn btn-white"><i class="fa fa-print fa-lg"></i> PDF</button>
                                    </form>
                              
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="card">
                                <div class="card-body">
                                    <div class="row custom-invoice">
                                        <div class="col-6 col-sm-6 m-b-20">
                                            <img src="assets/img/logo-dark.png" class="inv-logo" alt="">
                                            <ul class="list-unstyled">
                                                <li>PreClinic</li>
                                                <li>FPT Univercity,</li>
                                                <li>Hòa Lạc, Hà Nội, Việt Nam</li>
                                            </ul>
                                        </div>
                                        <div class="col-6 col-sm-6 m-b-20">
                                            <div class="invoice-details">
                                                <h3 class="text-uppercase">Invoice #${invoice.invoiceID}</h3>
                                                <ul class="list-unstyled">
                                                    <li>Created Date: <span>${createdDate}</span></li>

                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-6 col-lg-6 m-b-20">
                                            <h5>Invoice to:</h5>
                                            <ul class="list-unstyled">
                                                <li>
                                                    <h5><strong>${invoice.examinationID.customerId.fullName}</strong></h5>
                                                </li>
                                                <li><span>Việt Nam</span></li>
                                                <li>${invoice.examinationID.customerId.address}</li>

                                                <li>${invoice.examinationID.customerId.phone}</li>
                                                <li><a href="#">${invoice.examinationID.customerId.email}</a></li>
                                            </ul>

                                        </div>
                                        <div class="col-sm-6 col-lg-6 m-b-20">
                                            <div class="invoices-view">
                                                <span class="text-muted">Payment Details:</span>
                                                <ul class="list-unstyled invoice-payment-details">
                                                    <li>
                                                        <h5>Total Due: <span class="text-right">${invoice.totalAmount}</span></h5>
                                                    </li>
                                                    <li>Country: <span>Việt Nam</span></li>
                                                    <li>City: <span>Hà Nội</span></li>
                                                    <li>Address: <span>Hòa Lạc, FPT University</span></li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="table-responsive">
                                        <table class="table table-striped table-hover">
                                            <thead>
                                                <tr>
                                                    <th>#</th>
                                                    <th>Service</th>
                                                    <th>DESCRIPTION</th>
                                                    <th>TOTAL</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:set var="i" value="1"/>
                                                <c:forEach var="service" items="${invoice.examinationID.list}">
                                                    <tr>
                                                        <td>${i}</td>
                                                        <c:set var="i" value="${i + 1}"/>
                                                        <td>${service.packageName}</td>
                                                        <td>${service.description}</td>
                                                        <td>${service.price}</td>
                                                    </tr>
                                                </c:forEach>

                                            </tbody>
                                        </table>
                                    </div>
                                    <div>
                                        <div class="row invoice-payment">
                                            <div class="col-sm-7">
                                            </div>
                                            <div class="col-sm-5">
                                                <div class="m-b-20">
                                                    <h6>Total due</h6>
                                                    <div class="table-responsive no-border">
                                                        <table class="table mb-0">
                                                            <tbody>
                                                                <tr>
                                                                    <th>Subtotal:</th>
                                                                    <td class="text-right">${subtotal}</td>
                                                                </tr>
                                                                <tr>
                                                                    <th>Discount: <span class="text-regular">(${invoice.discountID.percentage}%)</span></th>
                                                                    <td class="text-right">${discount}</td>
                                                                </tr>
                                                                <tr>
                                                                    <th>Total:</th>
                                                                    <td class="text-right text-primary">
                                                                        <h5>${invoice.totalAmount}</h5>
                                                                    </td>
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                    <c:if test="${success != null}">
                        <h4 style="color: green">${success}</h4>
                    </c:if>
                    <c:if test="${success == null && invoice.paymentStatus == 'Paid'}">
                        <h4 style="color: green">${mess}</h4>
                    </c:if>
                    <c:if test="${invoice.paymentStatus == 'Pending'}">
                        <div class="text-right">
                            <form action="vnpay" method="post" id="frmCreateOrder">
                                <input type="hidden" name="invoiceID" value="${invoice.invoiceID}" />
                                <input type="hidden" name="amount" value="${invoice.totalAmount*25530}" />
                                <button type="submit" class="btn btn-primary btn-lg">
                                    <i class="fa fa-credit-card"></i> Thanh toán 
                                </button>
                            </form>
                        </div>
                    </c:if>
                </div>

            </div>
        </div>
        <div class="sidebar-overlay" data-reff=""></div>
        <script src="assets/js/jquery-3.2.1.min.js"></script>
        <script src="assets/js/popper.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/jquery.slimscroll.js"></script>
        <script src="assets/js/app.js"></script>
        <script type="text/javascript">
            $("#frmCreateOrder").submit(function () {
                var postData = $("#frmCreateOrder").serialize();
                var submitUrl = $("#frmCreateOrder").attr("action");
                $.ajax({
                    type: "POST",
                    url: submitUrl,
                    data: postData,
                    dataType: 'JSON',
                    success: function (x) {
                        if (x.code === '00') {
                            if (window.vnpay) {
                                vnpay.open({width: 768, height: 600, url: x.data});
                            } else {
                                location.href = x.data;
                            }
                            return false;
                        } else {
                            alert(x.Message);
                        }
                    }
                });
                return false;
            });
        </script>  
    </body>


    <!-- invoice-view24:07-->
</html>
