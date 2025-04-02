package serviceController;

import dao.ServiceDAO;
import java.io.IOException;
import java.io.OutputStream;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet để lấy ảnh từ database và hiển thị trên web
 */
@WebServlet(name="Get_Image", urlPatterns={"/getimage"})
public class Get_Image extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            // Lấy packageID từ request
            int packageID = Integer.parseInt(request.getParameter("packageID"));

            // Gọi DAO để lấy ảnh
            ServiceDAO dao = new ServiceDAO();
            byte[] imageData = dao.getImageById(packageID);

            // Kiểm tra nếu không có ảnh
            if (imageData == null || imageData.length == 0) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy ảnh.");
                return;
            }
            // Đặt MIME type phù hợp (ở đây là ảnh PNG, có thể thay đổi)
            response.setContentType("image/jpeg");

            // Ghi dữ liệu ảnh ra luồng phản hồi
            OutputStream out = response.getOutputStream();
            out.write(imageData);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải ảnh.");
        }
    }
}
