<%-- 
    Document   : form-medical
    Created on : Mar 14, 2025, 8:51:14 AM
    Author     : Phan Huu Kien
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Medical Record Form</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            max-width: 700px;
            margin-top: 50px;
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .hospital-info {
            text-align: center;
            margin-bottom: 20px;
            padding: 15px;
            background: #007bff;
            color: white;
            border-radius: 10px;
        }
        .form-section {
            display: flex;
            justify-content: space-between;
            gap: 15px;
        }
        .form-section .form-group {
            flex: 1;
        }
        .form-buttons {
            display: flex;
            justify-content: space-between;
        }
    </style>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            let examDateInput = document.getElementById("examDate");
            let now = new Date();
            let minDateTime = now.toISOString().slice(0, 16);
            examDateInput.min = minDateTime;
        });
    </script>
</head>
<body>
    <div class="container">
        <div class="hospital-info">
            <h2>Bệnh viện Thắng Hoàng</h2>
            <p>Địa chỉ: 123 Đường ABC, Thành phố XYZ</p>
            <p>Điện thoại: 0123-456-789</p>
        </div>
        
        <h2 class="text-center">Medical Record Form</h2>
        <form action="saveMedicalRecord" method="POST">
            <div class="form-section">
                <div class="form-group">
                    <label for="doctorName" class="form-label">Doctor</label>
                    <input type="text" class="form-control" id="doctorName" name="doctorName" value="Dr. John Doe" readonly>
                </div>
                <div class="form-group">
                    <label for="examDate" class="form-label">Examination Date</label>
                    <input type="datetime-local" class="form-control" id="examDate" name="examDate" required>
                </div>
            </div>
            <hr>
            <div class="mb-3">
                <label for="patientName" class="form-label">Patient Name</label>
                <select class="form-control" id="patientName" name="patientName" required>
                    <option value="">Select Patient</option>
                    <!-- Fetch patient names dynamically -->
                </select>
            </div>
            <div class="mb-3">
                <label for="patientAge" class="form-label">Age</label>
                <input type="number" class="form-control" id="patientAge" name="patientAge" required>
            </div>
            <div class="mb-3">
                <label for="patientAddress" class="form-label">Address</label>
                <input type="text" class="form-control" id="patientAddress" name="patientAddress" required>
            </div>
            <div class="mb-3">
                <label for="patientPhone" class="form-label">Phone Number</label>
                <input type="text" class="form-control" id="patientPhone" name="patientPhone" required>
            </div>
            <div class="mb-3">
                <label for="diagnosis" class="form-label">Diagnosis</label>
                <textarea class="form-control" id="diagnosis" name="diagnosis" rows="3" required></textarea>
            </div>
            <div class="mb-3">
                <label for="medications" class="form-label">Prescribed Medications</label>
                <input type="text" class="form-control" id="medications" name="medications" placeholder="E.g., Paracetamol 500mg - 2 times/day">
            </div>
            <div class="mb-3">
                <label for="notes" class="form-label">Additional Notes</label>
                <textarea class="form-control" id="notes" name="notes" rows="3"></textarea>
            </div>
            <div class="form-buttons">
                <button type="button" class="btn btn-secondary" onclick="window.history.back()">Back</button>
                <button type="reset" class="btn btn-danger">Cancel</button>
                <button type="submit" class="btn btn-primary">Save</button>
            </div>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
