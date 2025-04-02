package WorkingScheduleController;

import dao.RoleDAO;
import dao.WorkingScheduleDAO;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ProfessionalLeave;
import model.WorkingSchedule;
import java.sql.Date;
import java.sql.Time;
import model.Role;
import model.Staff;

@WebServlet(name = "ViewPersonalSchedule", urlPatterns = {"/viewpersonalschedule"})
public class ViewPersonalSchedule extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WorkingScheduleDAO workingDAO = new WorkingScheduleDAO();
        HttpSession session = request.getSession();
        Staff s = (Staff) session.getAttribute("staffAccount");
        RoleDAO rDAO = new RoleDAO();
        Role r = rDAO.getRoleByID(s.getRoleID());
        request.setAttribute("listPermission", r.getPermission());
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

            request.getRequestDispatcher("viewPersonalSchedule.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid professional ID format.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = null;
        WorkingScheduleDAO dao = new WorkingScheduleDAO();
        try {
            int professionalID = Integer.parseInt(request.getParameter("professionalID"));
            String leaveDateStr = request.getParameter("leaveDate");
            String reason = request.getParameter("reason");
            String note = request.getParameter("note");
            LocalDate leaveDate = LocalDate.parse(leaveDateStr);

            if (leaveDateStr == null || leaveDateStr.isEmpty()) {
                message = "Leave date is required.";
            } else if (reason == null || reason.isEmpty()) {
                message = "Reason is required.";
            } else if ("Personal reason".equals(reason) && (note == null || note.trim().isEmpty())) {
                message = "Note is required when the reason is personal.";
            } else if (leaveDate.isBefore(LocalDate.now())) {
                message = "Leave date cannot be in the past.";
            } else if (leaveDate.equals(LocalDate.now())) {
                message = "Leave date cannot be currunt date.";
            } else if (!(dao.isWorkingOnDate(professionalID, Date.valueOf(leaveDate)))) {
                message = "No schedule on this day.";
            } else if (dao.isLeaveOnDate(professionalID, Date.valueOf(leaveDate))) {
                message = "Had a day off that day.";
            } else {
                if (dao.isWorkingOnDate(professionalID, Date.valueOf(leaveDate))) {
                    String combinedReason = "(" + reason + ") " + (note != null ? note.trim() : "");
                    dao.addProfessionalLeave(professionalID, Date.valueOf(leaveDate), combinedReason);
                    request.setAttribute("successMessage", "Leave request submitted successfully.");
                }
            }
        } catch (NumberFormatException e) {
            message = "Invalid professional ID.";
        } catch (Exception e) {
            message = "Error processing request: " + e.getMessage();
        }
        if (message != null) {
            request.setAttribute("errorMessage", message);
        }
        doGet(request, response);
    }
}
