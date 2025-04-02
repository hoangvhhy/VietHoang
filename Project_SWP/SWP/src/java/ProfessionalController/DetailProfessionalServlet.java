/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package ProfessionalController;

import dao.ProfessionalDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Professional;

/**
 *
 * @author Win11
 */
@WebServlet("/DetailDoctorServlet")
public class DetailProfessionalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy ID của bác sĩ từ request
        String id = request.getParameter("id");
        if (id != null) {
            int doctorId = Integer.parseInt(id);

            // Giả sử bạn có một lớp DAO để lấy thông tin bác sĩ
            ProfessionalDAO doctorDAO = new ProfessionalDAO();
            Professional  professional = doctorDAO.getProfessionalbyID(doctorId);

            // Đặt dữ liệu vào request
            request.setAttribute("professional", professional);
            
            // Chuyển hướng đến trang JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("doctor-single.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("error.jsp");
        }
    }
}

