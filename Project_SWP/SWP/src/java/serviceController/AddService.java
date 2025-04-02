package serviceController;

import util.ValidFunction;
import dao.ServiceDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/addService")
@MultipartConfig(maxFileSize = 10 * 1024 * 1024) // Giới hạn file 10MB
public class AddService extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServiceDAO serviceDAO = new ServiceDAO();
        ValidFunction val = new ValidFunction();

        try {
            // Lấy thông tin từ form
            String packageName = request.getParameter("packageName");
            packageName = val.normalizeName(packageName);
            String description = request.getParameter("description");
            description = val.normalizeName(description);
            String type = request.getParameter("type");
            String status = request.getParameter("status");

            // Kiểm tra dữ liệu đầu vào
            double price;
            int duration;
            try {
                price = Double.parseDouble(request.getParameter("price"));
                duration = Integer.parseInt(request.getParameter("duration"));
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Giá hoặc thời gian không hợp lệ!");
                request.getRequestDispatcher("errorPage.jsp").forward(request, response);
                return;
            }

            // Kiểm tra tên dịch vụ có tồn tại chưa
            boolean isServiceExist = serviceDAO.isServiceNameExist(packageName);
            if (isServiceExist) {
                // Nếu tên dịch vụ đã tồn tại, trả về thông báo lỗi
                request.setAttribute("error", "Tên dịch vụ đã tồn tại! Vui lòng chọn tên khác.");
                request.getRequestDispatcher("add_Service.jsp").forward(request, response);
                return;
            }

            //  Xử lý file ảnh (nếu có)
            Part filePart = request.getPart("service_image");
            InputStream fileContent = null;
            if (filePart != null && filePart.getSize() > 0) {
                fileContent = filePart.getInputStream();
            }

            //Gọi DAO để thêm dịch vụ
            serviceDAO.addService(packageName, description, fileContent, type, price, duration, status);

            // Đóng InputStream (nếu có)
            if (fileContent != null) {
                fileContent.close();
            }

            response.sendRedirect("loadmanage");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi thêm dịch vụ!");
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
        }
    }
}
