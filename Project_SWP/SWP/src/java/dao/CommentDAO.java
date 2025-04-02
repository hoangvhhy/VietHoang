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
import model.Comment;
import model.Customer;
import model.Staff;

/**
 *
 * @author Hoang
 */
public class CommentDAO extends DBContext {

    public Comment getCommentById(int commentId) {
        CustomerDAO customerDAO = new CustomerDAO();
        StaffDAO staffDAO = new StaffDAO();

        try {
            String sql = "SELECT * FROM Comments WHERE comment_id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, commentId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                Comment comment = new Comment();
                System.out.println("Debug: Đã tìm thấy comment trong DB!");

                // Lấy thông tin customer
                int customerId = rs.getInt("customerID");
                Customer customer = customerDAO.getCustomerByID(customerId);
                System.out.println("Debug: customerID = " + customerId);

                // Lấy thông tin staff (nếu không phải null)
                Integer staffId = (Integer) rs.getObject("staff_id"); // Cho phép nhận giá trị NULL
                Staff staff = null;
                if (staffId != null) {
                    staff = staffDAO.getStaffByID(staffId);
                }
                System.out.println("Debug: staffID = " + (staffId == null ? "NULL" : staffId));

                // Gán dữ liệu vào comment
                comment.setStaff_id(staff);
                comment.setCustomerID(customer);
                comment.setComment_id(rs.getInt("comment_id"));
                comment.setPost_id(rs.getInt("post_id"));
                comment.setContent(rs.getString("content"));
                comment.setParent_comment_id(rs.getInt("parent_comment_id"));

                return comment;
            } else {
                System.out.println("Debug: Không tìm thấy comment trong DB!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Comment> getCommentsByPostId(int postId) {
        List<Comment> comments = new ArrayList<>();
        CustomerDAO cusDAO = new CustomerDAO();
        StaffDAO staffDAO = new StaffDAO();
        String sql = "SELECT * FROM Comments WHERE post_id = ? and status = 1 ORDER BY created_at desc";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int commentID = rs.getInt("comment_id");
                int postID = rs.getInt("post_id");
                int customerID = rs.getInt("customerID");
                Customer customer = cusDAO.getCustomerByID(customerID);
                int staffID = rs.getInt("staff_id");
                Staff staff = staffDAO.getStaffByID(staffID);
                String content = rs.getString("content");
                int status = rs.getInt("status");
                String createdAt = rs.getString("created_at");
                int parentCommentID = rs.getInt("parent_comment_id");
                Comment comment = new Comment(commentID, postID, customer, staff, content, status, createdAt, parentCommentID);
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    public boolean addComment(Comment comment) {
        String sql = "INSERT INTO Comments (post_id, customerID, staff_id, content, status, parent_comment_id) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, comment.getPost_id());

            if (comment.getCustomerID() != null) {
                ps.setInt(2, comment.getCustomerID().getCustomerID());
                ps.setNull(3, java.sql.Types.INTEGER);
            } else if (comment.getStaff_id() != null) {
                ps.setNull(2, java.sql.Types.INTEGER);
                ps.setInt(3, comment.getStaff_id().getStaffID());
            } else {
                return false;
            }

            ps.setString(4, comment.getContent());
            ps.setInt(5, comment.getStatus());

            if (comment.getParent_comment_id() != 0) {
                ps.setInt(6, comment.getParent_comment_id());
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateComment(Comment comment) {
        String sql = "UPDATE Comments SET content = ? WHERE comment_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, comment.getContent());
            ps.setInt(2, comment.getComment_id());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCommentRecur(int commentId) {
        // Update status of the comment to 0 (deleted)
        String sql = "UPDATE Comments SET status = 0 WHERE comment_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, commentId);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                List<Integer> childCommentIds = getChildCommentIds(commentId);
                for (int childCommentId : childCommentIds) {
                    deleteCommentRecur(childCommentId);
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<Integer> getChildCommentIds(int parentCommentId) {
        List<Integer> childCommentIds = new ArrayList<>();
        String sql = "SELECT comment_id FROM Comments WHERE parent_comment_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, parentCommentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                childCommentIds.add(rs.getInt("comment_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return childCommentIds;
    }

    public static void main(String[] args) {
        CommentDAO commentDAO = new CommentDAO();
        Comment comment = commentDAO.getCommentById(1);
//        if (comment != null) {
//            System.out.println("Original Content: " + comment.getContent());
//
//            comment.setContent("Updated content from main method");
//            boolean isUpdated = commentDAO.updateComment(comment);
//            if (isUpdated) {
//                System.out.println("Comment updated successfully!");
//            } else {
//                System.out.println("Failed to update comment.");
//            }
//
//            Comment updatedComment = commentDAO.getCommentById(comment.getComment_id());
//            if (updatedComment != null) {
//                System.out.println("Updated Content: " + updatedComment.getContent());
//            }
//        } else {
//            System.out.println("Comment not found.");
//        }

        System.out.println(comment.getContent());
    }
}
