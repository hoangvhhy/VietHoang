<!DOCTYPE html>
<html lang="zxx">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="description" content="Orbitor,business,company,agency,modern,bootstrap4,tech,software">
        <meta name="author" content="themefisher.com">

        <title>Novena- Health & Care Medical template</title>

        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="/images/favicon.ico" />

        <!-- bootstrap.min css -->
        <link rel="stylesheet" href="plugins/bootstrap/css/bootstrap.min.css">
        <!-- Icon Font Css -->
        <link rel="stylesheet" href="plugins/icofont/icofont.min.css">
        <!-- Slick Slider  CSS -->
        <link rel="stylesheet" href="plugins/slick-carousel/slick/slick.css">
        <link rel="stylesheet" href="plugins/slick-carousel/slick/slick-theme.css">

        <!-- Main Stylesheet -->
        <link rel="stylesheet" href="css/style.css">
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">

        <link rel="stylesheet" type="text/css" href="assets/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/select2.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/bootstrap-datetimepicker.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <!--[if lt IE 9]>
                    <script src="assets/js/html5shiv.min.js"></script>
                    <script src="assets/js/respond.min.js"></script>
            <![endif]-->
    </head>

    <style>
        .doctor-image-wrapper {
            display: flex;
            justify-content: center;
            align-items: center;
            margin:100px 0 0 50px;
        }

        #doctorImage {
            width: 250px;
            height: 300px;
            object-fit: cover;
            border: 4px solid #ffffff;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .emergency {
            margin: 20px 0 0 80px;
            font-size: 16px;
            font-weight: 600;
            color: #007bff;
        }
    </style>

</head>

<body id="top">
    <jsp:include page="headerHome.jsp"></jsp:include>
        <section class="page-title bg-1">
            <div class="overlay"></div>
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="block text-center">
                            <span class="text-white">Appointment</span>
                            <h1 class="text-capitalize mb-5 text-lg">Make appointment</h1>

                        </div>
                    </div>
                </div>
            </div>
        </section>

        <section class="appoinment section">
            <div class="container">
                <div class="row">
                    <div class="col-lg-4">
                        <div class="mt-3">
                            <div class="appoinment-content">
                                <div class="doctor-image-wrapper">
                                    <img id="doctorImage" src="images/about/img-3.jpg" alt="Doctor Image" class="img-fluid">
                                </div>
                            </div>
                            <div class="emergency">
                                <p id="doctorSpecialization" class="mb-2">Specialization</p>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-8">
                        <div class="appoinment-wrap mt-5 mt-lg-0 pl-lg-5">
                            <h2 class="mb-2 title-color">Book an appointment</h2>
                            <form id="appointmentForm" class="appointment" method="post" action="appointment">
                                <div class="row">
                                    <div class="col-lg-6">
                                        <div class="form-group">
                                            <div style="border: 1px solid #ced4da;">
                                                <div class="dropdown" style="margin-bottom: 8px;">
                                                    <button class="btn btn-outline-secondary dropdown-toggle form-control" type="button" onclick="this.nextElementSibling.style.display = this.nextElementSibling.style.display === 'none' ? 'block' : 'none';">
                                                        Select Services
                                                    </button>
                                                    <div class="dropdown-menu p-2" style="max-height: 150px; overflow-y: auto; width: 100%;">
                                                    <c:forEach var="service" items="${services}">
                                                        <div class="form-check">
                                                            <input class="form-check-input" type="checkbox" name="serviceIds[]" value="${service.packageID}" id="service_${service.packageID}" <c:if test="${fn:contains(selectedServiceIds, service.packageID)}">checked</c:if>>
                                                            <label class="form-check-label" for="service_${service.packageID}">
                                                                ${service.packageName}
                                                            </label>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <select class="form-control" name="doctorId" id="doctorSelect" required>
                                            <option value="">Select Doctor</option>
                                            <c:if test="${not empty doctors}">
                                                <c:forEach var="doctor" items="${doctors}">
                                                    <option value="${doctor.staffID}" 
                                                            data-image="assets/img/${doctor.picture}" 
                                                            data-specialization="${doctor.specialization}" 
                                                            <c:if test="${doctor.staffID == selectedDoctorId}">selected</c:if>>${doctor.fullName}</option>
                                                </c:forEach>
                                            </c:if>
                                        </select>
                                    </div>
                                </div>

                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <input type="text" name="date" placeholder="Date" class="form-control datetimepicker" value="${date}" required>
                                    </div>
                                </div>

                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <select name="time" class="form-control" required>
                                            <option value="">Select Time</option>
                                            <option value="08:00" <c:if test="${time == '08:00'}">selected</c:if>>08:00</option>
                                            <option value="09:00" <c:if test="${time == '09:00'}">selected</c:if>>09:00</option>
                                            <option value="10:00" <c:if test="${time == '10:00'}">selected</c:if>>10:00</option>
                                            <option value="11:00" <c:if test="${time == '11:00'}">selected</c:if>>11:00</option>
                                            <option value="12:00" <c:if test="${time == '12:00'}">selected</c:if>>12:00</option>
                                            <option value="13:00" <c:if test="${time == '13:00'}">selected</c:if>>13:00</option>
                                            <option value="14:00" <c:if test="${time == '14:00'}">selected</c:if>>14:00</option>
                                            <option value="15:00" <c:if test="${time == '15:00'}">selected</c:if>>15:00</option>
                                            <option value="16:00" <c:if test="${time == '16:00'}">selected</c:if>>16:00</option>
                                            <option value="17:00" <c:if test="${time == '17:00'}">selected</c:if>>17:00</option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="col-lg-6">
                                        <div class="form-group">
                                            <input name="name" type="text" class="form-control" placeholder="Full Name" value="${customerAccount.fullName}" required>
                                    </div>
                                </div>

                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <input name="phone" type="tel" class="form-control" placeholder="Phone Number" value="${customerAccount.phone}" required>
                                    </div>
                                </div>

                            </div>

                            <div class="form-group-2 mb-4">
                                <textarea name="message" class="form-control" rows="6" placeholder="Your Message">${message}</textarea>
                            </div>

                            <c:if test="${not empty error}">
                                <div class="alert alert-danger">${error}</div>
                            </c:if>

                            <button type="submit" class="btn btn-main btn-round-full">Make Appointment</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <script>
        document.getElementById("doctorSelect").addEventListener("change", function () {
            var selectedOption = this.options[this.selectedIndex];
            var doctorImage = selectedOption.getAttribute("data-image");
            var doctorSpecialization = selectedOption.getAttribute("data-specialization");

            document.getElementById("doctorImage").src = doctorImage;
            document.getElementById("doctorSpecialization").textContent = doctorSpecialization;
        });
    </script>

    <!-- footer Start -->
    <jsp:include page="footer.jsp"></jsp:include>

        <!-- 
        Essential Scripts
        =====================================-->

        <div class="sidebar-overlay" data-reff=""></div>
        <script src="assets/js/jquery-3.2.1.min.js"></script>
        <script src="assets/js/popper.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/jquery.slimscroll.js"></script>
        <script src="assets/js/select2.min.js"></script>
        <script src="assets/js/moment.min.js"></script>

        <script src="assets/js/bootstrap-datetimepicker.min.js"></script>
        <script src="assets/js/app.js"></script>

        <script>
    $(document).ready(function () {
        // Initialize datetimepicker
        $('.datetimepicker').datetimepicker({
            format: 'DD/MM/YYYY',
            minDate: new Date(), // Do not allow selecting dates before today
            useCurrent: false
        });

        // Validate form before submitting
        $('#appointmentForm').on('submit', function (e) {
            var selectedDate = $('.datetimepicker').val();
            var selectedTime = $('select[name="time"]').val();
            var selectedDoctorId = $('#doctorSelect').val();

            if (!selectedDate) {
                e.preventDefault();
                alert('Please select a date');
                return false;
            }

            // Parse selected date
            var parts = selectedDate.split('/');
            var selected = new Date(parts[2], parts[1] - 1, parts[0]);
            var today = new Date();
            today.setHours(0, 0, 0, 0); // Reset time for accurate comparison

            if (selected < today) {
                e.preventDefault();
                alert('Please select a date from today onwards');
                $('.datetimepicker').val('');
                return false;
            }

            if (!selectedTime) {
                e.preventDefault();
                alert('Please select a time');
                return false;
            }

            if (!selectedDoctorId) {
                e.preventDefault();
                alert('Please select a doctor');
                return false;
            }

            // Check if the patient has already booked the same doctor at the same time
            var bookedAppointments = ${bookedAppointmentsJson}; // This should be passed from the server
            if (bookedAppointments) {
                var isConflict = bookedAppointments.some(function (appointment) {
                    return appointment.doctorId === selectedDoctorId &&
                           appointment.date === selectedDate &&
                           appointment.time === selectedTime;
                });

                if (isConflict) {
                    e.preventDefault();
                    alert('You have already booked this doctor at the selected time. Please choose a different time or doctor.');
                    return false;
                }
            }
        });
    });
</script>


</body>
</html>