/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package InvoiceController;

import util.ValidFunction;
import dao.DiscountDAO;
import dao.InvoiceDAO;
import dao.MedicalExaminationDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import model.Discount;
import model.MedicalExamination;
import model.Service;

/**
 *
 * @author Gigabyte
 */
@WebServlet(name = "createInvoice", urlPatterns = {"/createInvoice"})
public class createInvoice extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MedicalExaminationDAO medDAO = new MedicalExaminationDAO();
        ValidFunction valid = new ValidFunction();
        InvoiceDAO inDAO = new InvoiceDAO();
        DiscountDAO disDAO = new DiscountDAO();
        List<Discount> listDis = disDAO.getAllDiscountStatus();
        String discount = request.getParameter("discount");
        String medicalExaminationID = request.getParameter("medicalExaminationID");
        if (medicalExaminationID != null && !medicalExaminationID.isEmpty() && !medicalExaminationID.equals("0")) {
            MedicalExamination med = medDAO.getMedicalExaminationByID(Integer.parseInt(medicalExaminationID));
            double total = 0;
            List<Service> listService = med.getList();
            for(Service s : listService){
                total += s.getPrice();
            }
            double giam = 0;
            if(discount.isEmpty() || discount.equals("0")){
                discount = null;
            }
            if(discount!= null && !discount.isEmpty() && !discount.equals("0")){
                Discount dis = disDAO.getDiscountByID(Integer.parseInt(discount));
                giam = dis.getPercentage();
            }
            total = total*(100-giam)/100;
            inDAO.createInvoice(Integer.parseInt(medicalExaminationID), total, discount);
            request.setAttribute("mess", "Create invoice succesfully");
        }
        if((medicalExaminationID != null && medicalExaminationID.isEmpty()) || (medicalExaminationID != null && medicalExaminationID.equals("0"))){
            request.setAttribute("error", "Please choose Medical Examination!!");
        }
        List<MedicalExamination> listMedicalExam = medDAO.getAllMedicalExamination();
        List<MedicalExamination> list = new ArrayList<>();
        for (MedicalExamination x : listMedicalExam) {
            if (x.getStatus().equals("Confirmed")) {
                list.add(x);
            }
        }
        request.setAttribute("listDis", listDis);
        request.setAttribute("listMedicalExam", list);
        request.getRequestDispatcher("create-invoice.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         MedicalExaminationDAO medDAO = new MedicalExaminationDAO();
        List<MedicalExamination> listMedicalExam = medDAO.getAllMedicalExamination();
        ValidFunction valid = new ValidFunction();
        String phone = request.getParameter("phone");
        System.out.println(phone);
        request.setAttribute("phone", phone);
        List<MedicalExamination> list = new ArrayList<>();
        for (MedicalExamination x : listMedicalExam) {
            if (x.getStatus().equals("Confirmed") && x.getCustomerId().getPhone().equals(phone)) {
                list.add(x);
            }
        }
        DiscountDAO disDAO = new DiscountDAO();
        List<Discount> listDis = disDAO.getAllDiscountStatus();
        String discount = request.getParameter("discount");
        String medicalExaminationID = request.getParameter("medicalExaminationID");
        if (medicalExaminationID != null && !medicalExaminationID.isEmpty() && !medicalExaminationID.equals("0")) {
            MedicalExamination med = medDAO.getMedicalExaminationByID(Integer.parseInt(medicalExaminationID));
            Customer cus = med.getCustomerId();
            double total = 0;
            List<Service> listService = med.getList();
            for(Service s : listService){
                total += s.getPrice();
            }
            double giam = 0;
            if(discount!= null && !discount.isEmpty() && !discount.equals("0")){
                Discount dis = disDAO.getDiscountByID(Integer.parseInt(discount));
                giam = dis.getPercentage();
                request.setAttribute("discountID", discount);
            }
            request.setAttribute("discount", giam);
            request.setAttribute("totalGrand", total*(100-giam)/100);
            request.setAttribute("total", total);
            
            request.setAttribute("medicalExaminationID",medicalExaminationID);
            request.setAttribute("patientName", cus.getFullName());
            request.setAttribute("email", cus.getEmail());
            request.setAttribute("phone", cus.getPhone());
            request.setAttribute("address", cus.getAddress());
            request.setAttribute("dateOfBirth", valid.formatDateNews(cus.getDateOfBirth()));
            request.setAttribute("listService", listService);
        }
        request.setAttribute("listDis", listDis);
        request.setAttribute("listMedicalExam", list);
        request.getRequestDispatcher("create-invoice.jsp").forward(request, response);

    }

}
