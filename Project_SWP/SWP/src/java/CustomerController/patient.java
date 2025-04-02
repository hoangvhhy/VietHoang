/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CustomerController;

import util.ValidFunction;
import dao.CustomerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Customer;

/**
 *
 * @author Hoang
 */
@WebServlet(name = "patient", urlPatterns = {"/patient"})
public class patient extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ValidFunction valid = new ValidFunction();
        CustomerDAO dao = new CustomerDAO();
        String indexPage = request.getParameter("page");
        String status = request.getParameter("status");
        String patientIdStr = request.getParameter("patientID");
        String patientName = request.getParameter("patientName");
        int page;
        int totalPatient = 0;
        int pageSize = 5;
        List<Customer> listPatient = new ArrayList<>();

        if (status == null || status.isEmpty()) {
            status = "";
        }

        try {
            page = Integer.parseInt(indexPage);
            if (page <= 0) {
                page = 1;
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        try {
            if (status.isEmpty()) {
                if (patientIdStr != null && !patientIdStr.isEmpty() && patientName != null && !patientName.isEmpty()) {
                    // ID và Tên nhưng không lọc theo Status
                    int patientID = Integer.parseInt(patientIdStr);
                    Customer customer = dao.getCustomerByIdAndName(patientID, patientName);
                    if (customer != null) {
                        listPatient.add(customer);
                        totalPatient = 1;
                    } else {
                        request.setAttribute("error", "No patient found with ID: " + patientIdStr + " and name: " + patientName);
                    }
                } else if (patientIdStr != null && !patientIdStr.isEmpty()) {
                    //ID nhưng không lọc theo Status
                    int patientID = Integer.parseInt(valid.normalizeName(patientIdStr));
                    Customer customer = dao.getCustomerByID(patientID);
                    if (customer != null) {
                        listPatient.add(customer);
                        totalPatient = 1;
                    } else {
                        request.setAttribute("error", "Patient not found with ID: " + patientIdStr);
                    }
                } else if (patientName != null && !patientName.isEmpty()) {
                    //Name nhưng không lọc theo Status
                    listPatient = dao.getCustomerByName(valid.normalizeName(patientName), page, pageSize);
                    totalPatient = dao.getCustomerByName(valid.normalizeName(patientName)).size();
                    if (listPatient.isEmpty()) {
                        request.setAttribute("error", "No patients found with name: " + patientName);
                    }
                } else {
                    listPatient = dao.getAllCustomer(page, pageSize);
                    totalPatient = dao.getAllCustomer().size();
                }
            } else {
                if (patientIdStr != null && !patientIdStr.isEmpty() && patientName != null && !patientName.isEmpty()) {
                    // ID và Tên nhưng CÓ lọc theo Status
                    int patientID = Integer.parseInt(patientIdStr);
                    Customer customer = dao.getCustomerByIdAndName(patientID, patientName, status);
                    if (customer != null) {
                        listPatient.add(customer);
                        totalPatient = 1;
                    } else {
                        request.setAttribute("error", "No patient found with ID: " + patientIdStr + " and name: " + patientName + " with status " + status);
                    }
                } else if (patientIdStr != null && !patientIdStr.isEmpty()) {
                    //ID nhưng CÓ lọc theo Status
                    int patientID = Integer.parseInt(valid.normalizeName(patientIdStr));
                    Customer customer = dao.getCustomerByID(patientID, status);
                    if (customer != null) {
                        listPatient.add(customer);
                        totalPatient = 1;
                    } else {
                        request.setAttribute("error", "Patient not found with ID: " + patientIdStr + " with status " + status);
                    }
                } else if (patientName != null && !patientName.isEmpty()) {
                    //Name nhưng CÓ lọc theo Status
                    listPatient = dao.getCustomerByName(valid.normalizeName(patientName), page, pageSize, status);
                    totalPatient = dao.getCustomerByName(valid.normalizeName(patientName)).size();
                    if (listPatient.isEmpty()) {
                        request.setAttribute("error", "No patients found with name: " + patientName + " with status " + status);
                    }
                } else {
                    if (status.equals("Active")) {
                        listPatient = dao.getAllCustomerActive(page, pageSize);
                        totalPatient = dao.getAllCustomerActive().size();
                    } else {
                        listPatient = dao.getAllCustomerInactive(page, pageSize);
                        totalPatient = dao.getAllCustomerInactive().size();
                    }
                }
            }
            
            for (Customer customer : listPatient) {
                customer.setFullName(valid.normalizeName(customer.getFullName()));
            }

            int endPage = (int) Math.ceil((double) totalPatient / pageSize);

            request.setAttribute("listPatient", listPatient);
            request.setAttribute("totalPatient", totalPatient);
            request.setAttribute("currentEntries", listPatient.size());
            request.setAttribute("endPage", endPage);
            request.setAttribute("page", page);
            request.setAttribute("patientID", patientIdStr);
            request.setAttribute("patientName", patientName);
            request.setAttribute("status", status);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid Patient ID format.");
        }

        request.getRequestDispatcher("patient.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
