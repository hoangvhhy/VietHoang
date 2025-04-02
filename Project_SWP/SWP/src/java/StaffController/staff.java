/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package StaffController;

import util.ValidFunction;
import dao.RoleDAO;
import dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Permission;
import model.Role;
import model.Staff;

/**
 *
 * @author Gigabyte
 */
@WebServlet(name = "staff", urlPatterns = {"/staff"})
public class staff extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StaffDAO staffDAO = new StaffDAO();
        List<Staff> listStaff = staffDAO.getAllStaff();
        List<Staff> list = new ArrayList<>();
        ValidFunction valid = new ValidFunction();
        RoleDAO roleDAO = new RoleDAO();
        List<Role> listRole = roleDAO.getAllRole();
        request.setAttribute("listRole", listRole);
        String staffID_raw = request.getParameter("staffID");
        String name = request.getParameter("name");
        String roleID = request.getParameter("roleID");

        if (staffID_raw != null && !staffID_raw.isEmpty()) {
            try {
                int staffID = Integer.parseInt(staffID_raw);
                Staff s = new Staff();
                s = staffDAO.getStaffByID(staffID);
                request.setAttribute("staffID", staffID);
                List<Staff> l = new ArrayList<>();
                 l.add(s);
                 listStaff = l;
            } catch (Exception e) {
                response.sendRedirect("staff");
                return;
            }
        }
        if (name != null && !name.isEmpty() && roleID != null && !roleID.isEmpty() && !roleID.equalsIgnoreCase("Select role")) {
            request.setAttribute("name", name);
            request.setAttribute("roleID", roleID);
            listStaff = staffDAO.getStaffByNameandRole(Integer.parseInt(roleID), valid.normalizeName(name));
        } else {
            if (name != null && !name.isEmpty()) {
                request.setAttribute("name", name);
                listStaff = staffDAO.getStaffByName(valid.normalizeName(name));
            }
            if (roleID != null && !roleID.isEmpty() && !roleID.equalsIgnoreCase("Select role")) {
                request.setAttribute("roleID", roleID);
                listStaff = staffDAO.getStaffByRole(Integer.parseInt(roleID));
            }
        }

        int page = 1;
        int recordsPerPage = 5;

        int totalRecords = listStaff.size();
        int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
                // Ngăn truy cập trang không hợp lệ
                if (page < 1) {
                    page = 1;
                }
                if (page > totalPages) {
                    page = totalPages;
                }
            } catch (NumberFormatException e) {
                page = 1; // Nếu nhập sai định dạng, về trang đầu
            }
        }
        int start = (page - 1) * recordsPerPage;
        List<Staff> staffs = new ArrayList<>();
        if ((staffID_raw == null || staffID_raw.isEmpty()) &&  (name == null || name.isEmpty()) && (roleID == null || roleID.isEmpty() || roleID.equalsIgnoreCase("Select role"))){
            staffs = staffDAO.getStaffs(start, recordsPerPage);
        }
        if (staffID_raw != null && !staffID_raw.isEmpty()) {
            try {
                int staffID = Integer.parseInt(staffID_raw);
                Staff s = new Staff();
                s = staffDAO.getStaffByID(staffID);
                staffs.add(s);
            } catch (Exception e) {
                response.sendRedirect("staff");
            }
        }
        if (name != null && !name.isEmpty() && roleID != null && !roleID.isEmpty() && !roleID.equalsIgnoreCase("Select role")) {
            staffs = staffDAO.getStaffByNameandRolePaging(Integer.parseInt(roleID), valid.normalizeName(name), start, recordsPerPage);
        } else {
            if (name != null && !name.isEmpty()) {
                staffs = staffDAO.getStaffByNamePaging(valid.normalizeName(name), start, recordsPerPage);
            }
            if (roleID != null && !roleID.isEmpty() && !roleID.equalsIgnoreCase("Select role")) {
                staffs = staffDAO.getStaffByRolePaging(Integer.parseInt(roleID), start, recordsPerPage);
            }
        }
        for (Staff s : staffs) {
            String dateBirth;
            if (s.getDateOfBirth() != null) {
                dateBirth = valid.formatDate(s.getDateOfBirth());
            } else {
                dateBirth = s.getDateOfBirth();
            }
            Staff r = new Staff(s.getStaffID(), s.getFullName(), s.getEmail(), s.getPassword(), s.getPhone(), s.getGender(), dateBirth, s.getAddress(), valid.formatDate(s.getHireDate()), s.getRoleID(), s.getStatus(), s.getProfilePicture());
            list.add(r);
        }
        HttpSession session = request.getSession();
        if (session.getAttribute("staffAccount") != null) {
            Staff s = (Staff) session.getAttribute("staffAccount");
            Role role = roleDAO.getRoleByID(s.getRoleID());
            List<Permission> listPermission = role.getPermission();
            request.setAttribute("listPermission", listPermission);
        }
        request.setAttribute("show", list.size());
        request.setAttribute("size", listStaff.size());
        request.setAttribute("listStaff", list);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("staff.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
