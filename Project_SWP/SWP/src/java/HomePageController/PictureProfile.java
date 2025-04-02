/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package HomePageController;

import CustomerController.*;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;  // Import PreparedStatement
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import context.DBContext; // Import DBContext để lấy kết nối
import java.io.PrintWriter;
import java.sql.Blob;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "PictureProfile", urlPatterns = {"/pictureprofile"})
public class PictureProfile extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int customerID = Integer.parseInt(request.getParameter("customerID"));

        try {
            DBContext dbContext = new DBContext();
            Connection conn = (Connection) dbContext.connection; // Lấy kết nối từ DBContext

            String sql = "SELECT profilePicture FROM Customer WHERE customerID = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, customerID);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                Blob blob = rs.getBlob("profilePicture");
                if (blob != null) {
                    response.setContentType("image/jpeg");
                    OutputStream out = response.getOutputStream();
                    out.write(blob.getBytes(1, (int) blob.length()));
                    out.close();
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
            conn.close();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }

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
