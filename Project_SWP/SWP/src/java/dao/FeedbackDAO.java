/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import model.Feedback;
import model.Invoice;
import model.MedicalExamination;
import util.ValidFunction;

/**
 *
 * @author Hoang
 */
public class FeedbackDAO extends DBContext {

    public List<Feedback> getAllFeedbackByCustomer() {
        List<Feedback> list = new ArrayList<>();
        InvoiceDAO inDAO = new InvoiceDAO();
        String sql = "select * from Feedback order by date desc";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Feedback(
                        rs.getInt("feedbackID"),
                        inDAO.getInvoiceByID(rs.getInt("invoiceID")),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getString("date")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Feedback> getAllFeedbackByCustomer(int index, int pageSize) {
        List<Feedback> list = new ArrayList<>();
        InvoiceDAO inDAO = new InvoiceDAO();
        String sql = "select * from Feedback order by date desc offset ? rows  fetch  next ? rows only";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            int offset = (index - 1) * pageSize;
            st.setInt(1, offset);
            st.setInt(2, pageSize);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Feedback(
                        rs.getInt("feedbackID"),
                        inDAO.getInvoiceByID(rs.getInt("invoiceID")),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getString("date")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Feedback> getAllFeedback5StarByCustomer() {
        List<Feedback> list = new ArrayList<>();
        InvoiceDAO inDAO = new InvoiceDAO();
        String sql = "select * from Feedback where rating = 5";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Feedback(
                        rs.getInt("feedbackID"),
                        inDAO.getInvoiceByID(rs.getInt("invoiceID")),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getString("date")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Feedback> getAllFeedback5StarByCustomer(int index, int pageSize) {
        List<Feedback> list = new ArrayList<>();
        InvoiceDAO inDAO = new InvoiceDAO();
        String sql = "select * from Feedback where rating = 5 order by date desc offset ? rows  fetch  next ? rows only";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            int offset = (index - 1) * pageSize;
            st.setInt(1, offset);
            st.setInt(2, pageSize);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Feedback(
                        rs.getInt("feedbackID"),
                        inDAO.getInvoiceByID(rs.getInt("invoiceID")),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getString("date")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Feedback> getAllFeedback4StarByCustomer() {
        List<Feedback> list = new ArrayList<>();
        InvoiceDAO inDAO = new InvoiceDAO();
        String sql = "select * from Feedback where rating = 4";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Feedback(
                        rs.getInt("feedbackID"),
                        inDAO.getInvoiceByID(rs.getInt("invoiceID")),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getString("date")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Feedback> getAllFeedback4StarByCustomer(int index, int pageSize) {
        List<Feedback> list = new ArrayList<>();
        InvoiceDAO inDAO = new InvoiceDAO();
        String sql = "select * from Feedback where rating = 4 order by date desc offset ? rows  fetch  next ? rows only";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            int offset = (index - 1) * pageSize;
            st.setInt(1, offset);
            st.setInt(2, pageSize);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Feedback(
                        rs.getInt("feedbackID"),
                        inDAO.getInvoiceByID(rs.getInt("invoiceID")),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getString("date")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Feedback> getAllFeedback3StarByCustomer() {
        List<Feedback> list = new ArrayList<>();
        InvoiceDAO inDAO = new InvoiceDAO();
        String sql = "select * from Feedback where rating = 3";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Feedback(
                        rs.getInt("feedbackID"),
                        inDAO.getInvoiceByID(rs.getInt("invoiceID")),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getString("date")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Feedback> getAllFeedback3StarByCustomer(int index, int pageSize) {
        List<Feedback> list = new ArrayList<>();
        InvoiceDAO inDAO = new InvoiceDAO();
        String sql = "select * from Feedback where rating = 3 order by date desc offset ? rows  fetch  next ? rows only";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            int offset = (index - 1) * pageSize;
            st.setInt(1, offset);
            st.setInt(2, pageSize);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Feedback(
                        rs.getInt("feedbackID"),
                        inDAO.getInvoiceByID(rs.getInt("invoiceID")),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getString("date")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Feedback> getAllFeedback2StarByCustomer() {
        List<Feedback> list = new ArrayList<>();
        InvoiceDAO inDAO = new InvoiceDAO();
        String sql = "select * from Feedback where rating = 2";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Feedback(
                        rs.getInt("feedbackID"),
                        inDAO.getInvoiceByID(rs.getInt("invoiceID")),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getString("date")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Feedback> getAllFeedback2StarByCustomer(int index, int pageSize) {
        List<Feedback> list = new ArrayList<>();
        InvoiceDAO inDAO = new InvoiceDAO();
        String sql = "select * from Feedback where rating = 2 order by date desc offset ? rows  fetch  next ? rows only";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            int offset = (index - 1) * pageSize;
            st.setInt(1, offset);
            st.setInt(2, pageSize);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Feedback(
                        rs.getInt("feedbackID"),
                        inDAO.getInvoiceByID(rs.getInt("invoiceID")),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getString("date")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Feedback> getAllFeedback1StarByCustomer() {
        List<Feedback> list = new ArrayList<>();
        InvoiceDAO inDAO = new InvoiceDAO();
        String sql = "select * from Feedback where rating = 1";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Feedback(
                        rs.getInt("feedbackID"),
                        inDAO.getInvoiceByID(rs.getInt("invoiceID")),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getString("date")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Feedback> getAllFeedback1StarByCustomer(int index, int pageSize) {
        List<Feedback> list = new ArrayList<>();
        InvoiceDAO inDAO = new InvoiceDAO();
        String sql = "select * from Feedback where rating = 1 order by date desc offset ? rows  fetch  next ? rows only";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            int offset = (index - 1) * pageSize;
            st.setInt(1, offset);
            st.setInt(2, pageSize);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Feedback(
                        rs.getInt("feedbackID"),
                        inDAO.getInvoiceByID(rs.getInt("invoiceID")),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getString("date")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addFeedback(int invoiceID, int rating, String comment) {
        String sql = "INSERT INTO Feedback (invoiceID, rating, comment) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql);) {

            stmt.setInt(1, invoiceID);
            stmt.setInt(2, rating);
            stmt.setString(3, comment);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu xảy ra lỗi
    }

    public static void main(String[] args) {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        ValidFunction valid = new ValidFunction();
        List<Feedback> feedbacks = feedbackDAO.getAllFeedback5StarByCustomer();
        for (Feedback fb : feedbacks) {
            // Định dạng ngày mới
            String formattedDate = valid.formatDateNews(fb.getDate());

            // Cập nhật giá trị vào đối tượng Feedback
            fb.setDate(formattedDate);

            // In thông tin ra màn hình
            System.out.println(fb.getInvoice().getExaminationID().getCustomerId().getFullName() + ", "
                    + fb.getComment() + ", "
                    + fb.getRating() + ", "
                    + formattedDate);
        }

//        int invoiceID = 7;
//        int rating = 5;
//        String comment = "Dịch vụ rất tuyệt vời!";
//
//        // Gọi hàm DAO để thêm feedback
//        boolean isInserted = feedbackDAO.addFeedback(invoiceID, rating, comment);
//
//        // In kết quả kiểm tra
//        if (isInserted) {
//            System.out.println("Feedback đã được chèn thành công vào cơ sở dữ liệu!");
//        } else {
//            System.out.println("Có lỗi xảy ra khi chèn Feedback.");
//        }
    }
}
