package newsController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

@WebServlet(name = "UploadCKEditor", urlPatterns = {"/uploadckedittor"})

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  
    maxFileSize = 1024 * 1024 * 10,      
    maxRequestSize = 1024 * 1024 * 50    
)
public class UpLoadCkeditter extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().println("Use POST to upload files.");
    }
    
    
   private static final String IMAGE_DIR = "D:\\Github\\Health-care-servicee\\SWP\\web\\images";

     protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        File uploadDir = new File(IMAGE_DIR);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        try {
            Part filePart = request.getPart("upload");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            if (!fileName.toLowerCase().matches(".*\\.(jpg|jpeg|png|gif)$")) {
                out.println("{ \"uploaded\": 0, \"error\": { \"message\": \"Only JPG, PNG, GIF allowed.\" } }");
                return;
            }

            String filePath = IMAGE_DIR + File.separator + fileName;
            filePart.write(filePath);

            String fileUrl = request.getContextPath() + "/images/" + fileName;
            out.println("{ \"uploaded\": 1, \"fileName\": \"" + fileName + "\", \"url\": \"" + fileUrl + "\" }");
        } catch (Exception e) {
            out.println("{ \"uploaded\": 0, \"error\": { \"message\": \"Upload failed!\" } }");
        }
    }
}



