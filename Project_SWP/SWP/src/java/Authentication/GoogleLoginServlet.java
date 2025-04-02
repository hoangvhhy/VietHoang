package Authentication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.client.ClientProtocolException;
import util.Iconstant;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import model.Customer;
import dao.CustomerDAO;

@WebServlet(name = "GoogleLoginServlet", urlPatterns = {"/LoginGoogle"})
public class GoogleLoginServlet extends HttpServlet {

    private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage, String errorPage) throws ServletException, IOException {
        System.err.println(errorMessage); // Log lỗi chi tiết
        request.setAttribute("errorMessage", errorMessage); // Lưu thông báo lỗi vào request attribute
        request.getRequestDispatcher(errorPage).forward(request, response); // Chuyển hướng đến trang lỗi
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        PrintWriter out = response.getWriter();

        if (code == null || code.isEmpty()) {
            handleError(request, response, "Không tìm thấy authorization code", "error.jsp");
            return;
        }

        try {
            String accessToken = getToken(code);
            if (accessToken == null) {
                handleError(request, response, "Lỗi lấy access token", "error.jsp");
                return;
            }
            Customer user = getUserInfo(accessToken);
            if (user == null || user.getEmail() == null) {
                handleError(request, response, "Lỗi lấy thông tin user", "error.jsp");
                out.println(code);
                out.print(3);
                return;
            }
            System.out.println(user);

            CustomerDAO customerDAO = new CustomerDAO();
            Customer existingCustomer = customerDAO.getCustomerByEmail(user.getEmail());
            String contextPath = request.getContextPath();
            if (existingCustomer != null) {
                // Nếu đã có tài khoản, đăng nhập thành công
                request.getSession().setAttribute("customerAccount", existingCustomer);
                response.sendRedirect(contextPath + "/index_1.jsp");
                return;
            }

            // Nếu chưa có tài khoản, chuyển đến trang thông báo
            request.setAttribute("email", user.getEmail());
            request.setAttribute("username", user.getUsername());
            request.setAttribute("profilePicture", user.getProfilePicture());
            request.getRequestDispatcher("register.jsp").forward(request, response);

        } catch (Exception e) {
            handleError(request, response, "Lỗi hệ thống: " + e.getMessage(), "error.jsp");
        }
    }

    public static String getToken(String code) throws ClientProtocolException, IOException {
        String response = Request.Post(Iconstant.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form()
                        .add("client_id", Iconstant.GOOGLE_CLIENT_ID)
                        .add("client_secret", Iconstant.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", Iconstant.GOOGLE_REDIRECT_URI)
                        .add("code", code)
                        .add("grant_type", "authorization_code")
                        .build())
                .execute().returnContent().asString();

        System.out.println("Google Token Response: " + response);

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        return jobj.has("access_token") ? jobj.get("access_token").getAsString() : null;
    }

    public static Customer getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = Iconstant.GOOGLE_LINK_GET_USER_INFO;
        String response = Request.Get(link)
                .addHeader("Authorization", "Bearer " + accessToken)
                .execute()
                .returnContent()
                .asString();
        System.out.println("Google User Info Response: " + response); // Log the full response

        Gson gson = new Gson();
        try {
            Customer customer = gson.fromJson(response, Customer.class);
            // Now check if the required fields exist
//            if (!jsonResponse.has("email") || !jsonResponse.has("name")) {
//                System.err.println("Error: Email or name not found in Google User Info response.");
//                return null; // Or throw an exception
//            }

            // If fields exist, create a new Customer object and populate it
//            Customer customer = new Customer();
//            customer.setEmail(ga.getEmail());
//            customer.setFullName(ga.getName());
//            if (ga.getPicture()!=null) {
//                customer.setProfilePicture(ga.getPicture());
//            }
            return customer;
        } catch (Exception e) {
            System.err.println("Error parsing Google User Info response: " + e.getMessage());
            return null; // Or throw an exception
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
