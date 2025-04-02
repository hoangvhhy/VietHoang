/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package newsController;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;  // Import PreparedStatement
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import context.DBContext; // Import DBContext để lấy kết nối
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "LoadBlogImage", urlPatterns = {"/LoadBlogImage"})
public class LoadBlogImage2 extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoadBlogImage</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoadBlogImage at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String postIdParam = request.getParameter("postId");

        if (postIdParam == null || postIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing postId parameter.");
            return;
        }

        try {
            int postId = Integer.parseInt(postIdParam);
            DBContext dbContext = new DBContext();
            Connection conn = dbContext.connection;

            String sql = "SELECT image FROM Posts WHERE post_id = ?";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, postId);
                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        Blob blob = rs.getBlob("image");
                        if (blob != null && blob.length() > 0) {
                            response.setContentType("image/jpeg");
                            try (OutputStream out = response.getOutputStream()) {
                                out.write(blob.getBytes(1, (int) blob.length()));
                            }
                        } else {
                            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found.");
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Post not found.");
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoadBlogImage2.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                conn.close();
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid postId parameter.");
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
