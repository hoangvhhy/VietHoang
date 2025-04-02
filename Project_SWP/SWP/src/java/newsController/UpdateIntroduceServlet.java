/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package newsController;

import dao.ServiceDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/updateIntroduce")
public class UpdateIntroduceServlet extends HttpServlet {
    private ServiceDAO serviceDAO;

    @Override
    public void init() throws ServletException {
        serviceDAO = new ServiceDAO();
    }

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {
        // Debug: Kiểm tra request nhận được gì
        System.out.println("Received request to update introduce.");
        
        String packageIDParam = request.getParameter("packageID");
        String introduce = request.getParameter("introduce");
        
        System.out.println("packageID (String): " + packageIDParam);
        System.out.println("introduce: " + introduce);

        if (packageIDParam == null || introduce == null) {
            System.out.println("❌ Lỗi: Dữ liệu nhận được NULL.");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu dữ liệu.");
            return;
        }

        int packageID = Integer.parseInt(packageIDParam);

        // Cập nhật introduce trong DB
        boolean success = serviceDAO.updateIntroduce(packageID, introduce);

        if (success) {
            System.out.println("✅ Cập nhật thành công.");
            response.sendRedirect("manageIntroduce");
        } else {
            System.out.println("❌ Lỗi: Không thể cập nhật introduce.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không thể cập nhật introduce.");
        }
    } catch (NumberFormatException e) {
        System.out.println("❌ Lỗi: packageID không hợp lệ.");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "packageID không hợp lệ.");
    } catch (Exception e) {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi không xác định.");
    }
}

}