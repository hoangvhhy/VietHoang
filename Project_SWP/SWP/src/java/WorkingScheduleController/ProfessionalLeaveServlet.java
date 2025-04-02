/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package WorkingScheduleController;

import dao.WorkingScheduleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import model.ProfessionalLeave;
import model.WorkingSchedule;

/**
 *
 * @author laptop 368
 */
@WebServlet(name = "ProfessionalLeaveServlet", urlPatterns = {"/professionalleave"})
public class ProfessionalLeaveServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProfessionalLeaveServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProfessionalLeaveServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        WorkingScheduleDAO workingDAO = new WorkingScheduleDAO();

        try {

            int professionalID = Integer.parseInt(request.getParameter("professionalID"));
            String selectedDateStr = request.getParameter("selectedDate");

            LocalDate selectedDate = (selectedDateStr != null && !selectedDateStr.isEmpty())
                    ? LocalDate.parse(selectedDateStr)
                    : LocalDate.now();

            LocalDate firstMondayOfWeek = selectedDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

            List<WorkingSchedule> professionalList = workingDAO.getOnSchedulesByProfessionalID(professionalID);
            List<ProfessionalLeave> leaveList = workingDAO.getProfessionalLeavesByProfessionalID(professionalID);

            List<Date> leaveDates = new ArrayList<>();
            for (ProfessionalLeave leave : leaveList) {
                if ("Approved".equals(leave.getStatus())) {
                    leaveDates.add((Date) leave.getLeaveDate());
                }
            }

            List<WorkingSchedule> updatedSchedule = new ArrayList<>();
            for (WorkingSchedule schedule : professionalList) {
                LocalDate scheduleDate = firstMondayOfWeek.plusDays(schedule.getDayOfWeek() - 2);
                if (leaveDates.contains(Date.valueOf(scheduleDate))) {
                    schedule.setStartTime(Time.valueOf("00:00:00"));
                    schedule.setEndTime(Time.valueOf("00:00:00"));
                    for (ProfessionalLeave leave : leaveList) {
                        if (leave.getLeaveDate().equals(Date.valueOf(scheduleDate))) {
                            schedule.setStatus(leave.getReason());
                            break;
                        }
                    }
                }
                updatedSchedule.add(schedule);
            }

            request.setAttribute("professionalID", professionalID);
            request.setAttribute("leaveList", leaveList);
            request.setAttribute("professionalList", updatedSchedule);
            request.setAttribute("firstMondayOfWeek", firstMondayOfWeek);
            request.setAttribute("selectedDate", selectedDate);

            request.getRequestDispatcher("professionalleave.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid professional ID format.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        WorkingScheduleDAO workingDAO = new WorkingScheduleDAO();
        int professionalID = Integer.parseInt(request.getParameter("professionalID"));
        request.setAttribute("professionalID", professionalID);
        int leaveID = Integer.parseInt(request.getParameter("leaveID"));
        String oldStatus = request.getParameter("oldStatus");
        String status = request.getParameter("status");
        String leaveDateStr = request.getParameter("leaveDate");
        LocalDate leaveDate = LocalDate.parse(leaveDateStr);

        if (leaveDate.isBefore(LocalDate.now()) || leaveDate.isEqual(LocalDate.now())) {
            if (oldStatus.equalsIgnoreCase("Pending")) {
                workingDAO.updateLeaveStatus(leaveID, "Missed time!");
                String errorMessage = "Missed time to edit status.";
                request.setAttribute("errorMessage", errorMessage);
                doGet(request, response);
            } else {
                String errorMessage = "Can not edit this status";
                request.setAttribute("errorMessage", errorMessage);
                doGet(request, response);
            }
        } else {
            if (oldStatus.equalsIgnoreCase("Pending")) {
                if (status.equalsIgnoreCase("Pending") || status.equalsIgnoreCase("Missed time!")) {
                    String errorMessage = "Invalid status";
                    request.setAttribute("errorMessage", errorMessage);
                    doGet(request, response);
                } else {
                    workingDAO.updateLeaveStatus(leaveID, status);
                    String successMessage = "Edit this status done";
                    request.setAttribute("successMessage", successMessage);
                    doGet(request, response);
                }
            }

            if (oldStatus.equalsIgnoreCase("Approved") || oldStatus.equalsIgnoreCase("Rejected")) {
                if (status.equalsIgnoreCase("Approved") || status.equalsIgnoreCase("Rejected")) {
                    workingDAO.updateLeaveStatus(leaveID, status);
                    String successMessage = "Edit this status done";
                    request.setAttribute("successMessage", successMessage);
                    doGet(request, response);
                } else {
                    String errorMessage = "Invalid status";
                    request.setAttribute("errorMessage", errorMessage);
                    doGet(request, response);
                }
            }
            doGet(request, response);
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
