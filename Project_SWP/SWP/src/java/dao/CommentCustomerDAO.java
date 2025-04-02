/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Win11
 */
import context.DBContext;
import static context.DBContext.connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Comments;
import java.sql.*;

public class CommentCustomerDAO extends DBContext {

    public static void main(String[] args) {
        CommentCustomerDAO dao = new CommentCustomerDAO();
        // Ví dụ kiểm tra thêm bình luận mới với staffID và customerID là null
        // System.out.println(dao.addComment("chuquockhanhchung@gmail.com", null, "Toi muon hoi ve phoi", null, "Phoi", null, null));
        // Ví dụ update bình luận
        System.out.println(dao.updateComment(33, "Cam on bac si"));
//        List<Comments> list = dao.getRootCommentsWithoutReplies();
//        for (Comments comment : list) {
//            System.out.println(comment);
//        }
    }

    public Integer getCustomerIDByCommentID(int commentID) {
        String sql = "SELECT customerID FROM Comment WHERE commentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, commentID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("customerID"); // Lấy giá trị customerID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy
    }

    // Lấy các comment theo replyToCommentID
        public List<Comments> getCommentsByReplyToCommentID(int replyToCommentID, Integer staffID) {
        List<Comments> comments = new ArrayList<>();

        // Tạo câu lệnh SQL với điều kiện tùy chọn cho staffID
        StringBuilder sqlBuilder = new StringBuilder(
                "SELECT commentID, senderEmail, receiverEmail, commentText, commentDate, replyToCommentID, topic, staffID, customerID "
                + "FROM MedicalSystem.dbo.Comment WHERE replyToCommentID = ?");

        // Nếu staffID không phải là null, thêm điều kiện lọc theo staffID
        if (staffID != null) {
            sqlBuilder.append(" AND staffID = ?");
        }

        sqlBuilder.append(" ORDER BY commentDate DESC");

        try (PreparedStatement stmt = connection.prepareStatement(sqlBuilder.toString())) {
            // Thiết lập tham số cho replyToCommentID
            stmt.setInt(1, replyToCommentID);

            // Nếu staffID có giá trị, thiết lập tham số cho staffID
            if (staffID != null) {
                stmt.setInt(2, staffID);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Comments comment = mapComment(rs);
                    comments.add(comment);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comments;
    }

    // Lấy các comment gốc mà không có phản hồi nào (điều kiện cụ thể trong truy vấn tùy thuộc vào nghiệp vụ)
    public List<Comments> getRootCommentsWithoutReplies() {
        List<Comments> comments = new ArrayList<>();
        String sql = "SELECT c.commentID, c.senderEmail, c.receiverEmail, c.commentText, c.commentDate, c.replyToCommentID, c.topic, c.staffID, c.customerID\n"
                + "                FROM Comment c\n"
                + "                LEFT JOIN Comment r ON c.commentID = r.replyToCommentID\n"
                + "                WHERE c.replyToCommentID IS NULL AND r.replyToCommentID IS NULL \n"
                + "                ORDER BY commentDate DESC;";

        try (
                PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                comments.add(mapComment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    // Lấy các comment gốc (không có replyToCommentID)
    public List<Comments> getRootComments() {
        List<Comments> comments = new ArrayList<>();
        String sql = "SELECT commentID, senderEmail, receiverEmail, commentText, commentDate, replyToCommentID, topic, staffID, customerID "
                + "FROM Comment WHERE replyToCommentID IS NULL ORDER BY commentDate DESC";

        try (
                PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                comments.add(mapComment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    // Lấy tất cả bình luận
    public List<Comments> getAllComments() {
        List<Comments> comments = new ArrayList<>();
        String sql = "SELECT commentID, senderEmail, receiverEmail, commentText, commentDate, replyToCommentID, topic, staffID, customerID "
                + "FROM Comment";

        try (
                PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                comments.add(mapComment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    // Hàm ánh xạ dữ liệu từ ResultSet sang object Comments
    private Comments mapComment(ResultSet rs) throws SQLException {
        Integer replyId = (rs.getObject("replyToCommentID") != null) ? rs.getInt("replyToCommentID") : null;
        Integer staffId = (rs.getObject("staffID") != null) ? rs.getInt("staffID") : null;
        Integer customerId = (rs.getObject("customerID") != null) ? rs.getInt("customerID") : null;

        // Giả sử có constructor của Comments có tham số (int commentID, String senderEmail, String receiverEmail, String commentText, Timestamp commentDate, Integer replyToCommentID, String topic, Integer staffID, Integer customerID)
        return new Comments(
                rs.getInt("commentID"),
                rs.getString("senderEmail"),
                rs.getString("receiverEmail"),
                rs.getString("commentText"),
                rs.getTimestamp("commentDate"),
                rs.getString("topic"),
                replyId,
                staffId,
                customerId
        );
    }

    // Thêm bình luận mới với các trường mở rộng (staffID và customerID có thể null)
    public boolean addComment(String senderEmail, String receiverEmail, String commentText, Integer replyToCommentID, String topic, Integer staffID, Integer customerID) {
        String sql = "INSERT INTO Comment (senderEmail, receiverEmail, commentText, commentDate, replyToCommentID, topic, staffID, customerID) "
                + "VALUES (?, ?, ?, GETDATE(), ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, senderEmail);
            stmt.setString(2, receiverEmail);
            stmt.setString(3, commentText);

            if (replyToCommentID != null) {
                stmt.setInt(4, replyToCommentID);
            } else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }

            stmt.setString(5, topic);

            if (staffID != null) {
                stmt.setInt(6, staffID);
            } else {
                stmt.setNull(6, java.sql.Types.INTEGER);
            }

            if (customerID != null) {
                stmt.setInt(7, customerID);
            } else {
                stmt.setNull(7, java.sql.Types.INTEGER);
            }

            return stmt.executeUpdate() > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật bình luận: chỉ cập nhật commentText và topic (các trường khác không thay đổi)
    public boolean updateComment(int commentID, String newCommentText) {
        String sql = "UPDATE Comment SET commentText = ? WHERE commentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newCommentText);
            stmt.setInt(2, commentID);

            return stmt.executeUpdate() > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa bình luận
    public boolean deleteComment(int commentID) {
        String sql = "DELETE FROM Comment WHERE commentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, commentID);
            return stmt.executeUpdate() > 0; // Trả về true nếu xóa thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
