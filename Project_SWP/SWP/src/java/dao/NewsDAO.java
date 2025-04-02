/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DBContext;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;
import model.News;

/**
 *
 * @author Hoang
 */
public class NewsDAO extends DBContext {

    public News getNewsByID(int newsID) {
        String sql = "SELECT * FROM Posts WHERE post_id = ? AND status = 1";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, newsID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String createdAt = (rs.getTimestamp("created_at") != null) ? rs.getTimestamp("created_at").toString() : null;
                String updatedAt = (rs.getTimestamp("updated_at") != null) ? rs.getTimestamp("updated_at").toString() : null;

                return new News(
                        rs.getInt("post_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("created_by"),
                        rs.getInt("category_id"),
                        rs.getInt("status"),
                        rs.getString("image"),
                        rs.getString("detail"),
                        createdAt,
                        updatedAt
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Category> getAllCategoryNews() {
        List<Category> cate = new ArrayList<>();
        String sql = "select * from Categories where status = 1";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Category c = new Category(
                        rs.getInt("category_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("status"));
                cate.add(c);
            }
        } catch (SQLException e) {
        }
        return cate;
    }

    public List<News> getNewsByCategory(String category_id) {
        List<News> news = new ArrayList<>();
        String sql = "select * from Posts where category_id = ? and status = 1";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, category_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String createdAt = (rs.getTimestamp("created_at") != null) ? rs.getTimestamp("created_at").toString() : null;
                String updatedAt = (rs.getTimestamp("updated_at") != null) ? rs.getTimestamp("updated_at").toString() : null;

                News n = new News(rs.getInt("post_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("created_by"),
                        rs.getInt("category_id"),
                        rs.getInt("status"),
                        rs.getString("image"),
                        rs.getString("detail"),
                        createdAt,
                        updatedAt);
//                        rs.getString("created_at"),
//                        rs.getString("updated_at"));
                news.add(n);
            }
        } catch (SQLException e) {
        }
        return news;
    }

    //dem
    public int getTotalNews() {
        String sql = "SELECT COUNT(*) FROM Posts WHERE status = 1";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalNewsByCategory(String category_id) {
        String sql = "SELECT COUNT(*) FROM Posts WHERE status = 1 AND category_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, category_id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalNewsBySearch(String title) {
        int totalNews = 0;
        String sql = "SELECT COUNT(*) FROM Posts WHERE status = 1 AND title LIKE ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, "%" + title + "%");
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                totalNews = rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return totalNews;
    }

    //phan trang
    public List<News> getAllNewsNewest(int index, int pageSize) {
        List<News> list = new ArrayList<>();
        String sql = "select * from Posts where status = 1 order by created_at desc offset ? rows  fetch  next ? rows only";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            int offset = (index - 1) * pageSize;
            pre.setInt(1, offset);
            pre.setInt(2, pageSize);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                String createdAt = (rs.getTimestamp("created_at") != null) ? rs.getTimestamp("created_at").toString() : null;
                String updatedAt = (rs.getTimestamp("updated_at") != null) ? rs.getTimestamp("updated_at").toString() : null;

                News n = new News(rs.getInt("post_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("created_by"),
                        rs.getInt("category_id"),
                        rs.getInt("status"),
                        rs.getString("image"),
                        rs.getString("detail"),
                        createdAt,
                        updatedAt);
//                        rs.getString("created_at"),
//                        rs.getString("updated_at"));
                list.add(n);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public List<News> getAllNewsOldest(int index, int pageSize) {
        List<News> list = new ArrayList<>();
        String sql = "select * from Posts where status = 1 order by created_at offset ? rows  fetch  next ? rows only";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            int offset = (index - 1) * pageSize;
            pre.setInt(1, offset);
            pre.setInt(2, pageSize);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                String createdAt = (rs.getTimestamp("created_at") != null) ? rs.getTimestamp("created_at").toString() : null;
                String updatedAt = (rs.getTimestamp("updated_at") != null) ? rs.getTimestamp("updated_at").toString() : null;

                News n = new News(rs.getInt("post_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("created_by"),
                        rs.getInt("category_id"),
                        rs.getInt("status"),
                        rs.getString("image"),
                        rs.getString("detail"),
                        createdAt,
                        updatedAt);
//                        rs.getString("created_at"),
//                        rs.getString("updated_at"));
                list.add(n);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public List<News> getNewsByCategory(String categoryID, int index, int pageSize) {
        List<News> list = new ArrayList<>();
        String sql = "select * from Posts where status = 1 and category_id = ? order by created_at desc offset ? rows fetch next ? rows only";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            int offset = (index - 1) * pageSize;
            pre.setString(1, categoryID);
            pre.setInt(2, offset);
            pre.setInt(3, pageSize);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                String createdAt = (rs.getTimestamp("created_at") != null) ? rs.getTimestamp("created_at").toString() : null;
                String updatedAt = (rs.getTimestamp("updated_at") != null) ? rs.getTimestamp("updated_at").toString() : null;

                News n = new News(rs.getInt("post_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("created_by"),
                        rs.getInt("category_id"),
                        rs.getInt("status"),
                        rs.getString("image"),
                        rs.getString("detail"),
                        createdAt,
                        updatedAt);
//                        rs.getString("created_at"),
//                        rs.getString("updated_at"));
                list.add(n);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public List<News> searchNewsByTitle(String title, int index, int pageSize) {
        List<News> list = new ArrayList<>();
        String sql = "select * from Posts where status = 1 and title LIKE ? order by created_at desc offset ? rows fetch next ? rows only";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            int offset = (index - 1) * pageSize;
            pre.setString(1, "%" + title + "%");
            pre.setInt(2, offset);
            pre.setInt(3, pageSize);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                String createdAt = (rs.getTimestamp("created_at") != null) ? rs.getTimestamp("created_at").toString() : null;
                String updatedAt = (rs.getTimestamp("updated_at") != null) ? rs.getTimestamp("updated_at").toString() : null;

                News n = new News(rs.getInt("post_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("created_by"),
                        rs.getInt("category_id"),
                        rs.getInt("status"),
                        rs.getString("image"),
                        rs.getString("detail"),
                        createdAt,
                        updatedAt);
                list.add(n);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static final Logger LOGGER = Logger.getLogger(NewsDAO.class.getName());

    public void addBlogPost(String title, String content, int createdBy, int categoryId, int status, String detail, InputStream imageStream) {
        String sql = "INSERT INTO Posts (title, content, created_by, category_id, status, detail, image, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE())";

        try (PreparedStatement st = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            st.setString(1, title);
            st.setString(2, content);
            st.setInt(3, createdBy);
            st.setInt(4, categoryId);
            st.setInt(5, status);
            st.setString(6, detail);

            if (imageStream != null) {
                st.setBinaryStream(7, imageStream);
            } else {
                st.setNull(7, java.sql.Types.BLOB);
            }

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int postId = generatedKeys.getInt(1);
                        LOGGER.info("Blog post added successfully with ID: " + postId);
                    }
                }
            } else {
                LOGGER.warning("No rows inserted, check the input data.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding blog post", e);
        }
    }

    public boolean updateBlogPost(int postId, String title, String content, int status, InputStream imageStream, String detail, int categoryId) {
        StringBuilder sql = new StringBuilder("UPDATE Posts SET title = ?, content = ?, status = ?, detail = ?, category_id = ?, updated_at = GETDATE()");

        if (imageStream != null) {
            sql.append(", image = ?");
        }

        sql.append(" WHERE post_id = ?");

        try (PreparedStatement st = connection.prepareStatement(sql.toString())) {
            st.setString(1, title);
            st.setString(2, content);
            st.setInt(3, status);
            st.setString(4, detail);
            st.setInt(5, categoryId);

            int paramIndex = 6;
            if (imageStream != null) {
                st.setBinaryStream(paramIndex++, imageStream);
            }
            st.setInt(paramIndex, postId);

            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating blog post with ID: " + postId, e);
            return false;
        }
    }

    public List<News> getAllBlogs() {
        List<News> blogs = new ArrayList<>();
        String sql = "SELECT post_id, title, content, image, detail FROM Posts";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                News blog = new News();
                blog.setPost_id(rs.getInt("post_id"));
                blog.setTitle(rs.getString("title"));
                blog.setContent(rs.getString("content"));

                Blob blob = rs.getBlob("image");
                if (blob != null) {
                    blog.setImage("LoadBlogImage?postId=" + blog.getPost_id());
                } else {
                    blog.setImage("default.jpg"); // Ảnh mặc định nếu không có ảnh
                }

                blog.setDetail(rs.getString("detail"));
                blogs.add(blog);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Error fetching blogs: " + e.getMessage());
        }
        return blogs;
    }

    public News getBlogById(int id) {
        String sql = "SELECT post_id, title, content, image, detail, category_id, status FROM Posts WHERE post_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    News blog = new News();
                    blog.setPost_id(rs.getInt("post_id"));
                    blog.setTitle(rs.getString("title"));
                    blog.setContent(rs.getString("content"));

                    Blob blob = rs.getBlob("image");
                    blog.setImage(blob != null ? "LoadBlogImage?postId=" + blog.getPost_id() : "default.jpg");

                    blog.setDetail(rs.getString("detail"));
                    blog.setCategory_id(rs.getInt("category_id")); // Thêm category_id
                    blog.setStatus(rs.getInt("status"));           // Thêm status
                    return blog;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching blog by ID", e);
        }
        return null;
    }

    public void deleteBlogPost(int postId) throws SQLException {
        
       

        // Step 2: Delete all comments related to the post
        String deleteCommentsSql = "DELETE FROM dbo.Comments WHERE post_id = ?";
        try (PreparedStatement deleteCommentsStmt = connection.prepareStatement(deleteCommentsSql)) {
            deleteCommentsStmt.setInt(1, postId);
            deleteCommentsStmt.executeUpdate();
        }

        // Step 3: Delete the post itself
        String deletePostSql = "DELETE FROM Posts WHERE post_id = ?";
        try (PreparedStatement deletePostStmt = connection.prepareStatement(deletePostSql)) {
            deletePostStmt.setInt(1, postId);
            int rowsAffected = deletePostStmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No blog post was deleted. Please check the postId.");
            }
            LOGGER.info("Blog post deleted successfully.");
        }
    }

    public List<News> getBlogsByPage(int start, int total) {
        List<News> blogs = new ArrayList<>();
        String sql = "SELECT post_id, title, content, image, detail FROM Posts ORDER BY post_id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, start);
            st.setInt(2, total);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    News blog = new News();
                    blog.setPost_id(rs.getInt("post_id"));
                    blog.setTitle(rs.getString("title"));
                    blog.setContent(rs.getString("content"));

                    Blob blob = rs.getBlob("image");
                    blog.setImage(blob != null ? "LoadBlogImage?postId=" + blog.getPost_id() : "default.jpg");

                    blog.setDetail(rs.getString("detail"));
                    blogs.add(blog);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching paginated blogs", e);
        }
        return blogs;
    }

    public int getTotalBlogCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM Posts";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching total blog count", e);
        }
        return count;
    }

    public List<News> searchBlogs(String keyword) {
        List<News> blogs = new ArrayList<>();
        String sql = "SELECT post_id, title, content, image, detail FROM Posts WHERE title LIKE ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, "%" + keyword + "%");

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    News blog = new News();
                    blog.setPost_id(rs.getInt("post_id"));
                    blog.setTitle(rs.getString("title"));
                    blog.setContent(rs.getString("content"));

                    Blob blob = rs.getBlob("image");
                    blog.setImage(blob != null ? "LoadBlogImage?postId=" + blog.getPost_id() : "default.jpg");

                    blog.setDetail(rs.getString("detail"));
                    blogs.add(blog);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error searching blogs", e);
        }
        return blogs;
    }

    public Optional<Blob> getBlogImageById(int postId) {
        String sql = "SELECT image FROM Posts WHERE post_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, postId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return Optional.ofNullable(rs.getBlob("image"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching blog image", e);
        }
        return Optional.empty();
    }

    public List<News> getBlogsByCategoryAndPage(int categoryId, int offset, int limit) {
        List<News> list = new ArrayList<>();
        String sql = "SELECT * FROM Posts WHERE category_id = ? ORDER BY post_id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement st = new DBContext().connection.prepareStatement(sql)) {
            st.setInt(1, categoryId);
            st.setInt(2, offset);
            st.setInt(3, limit);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    // Lấy dữ liệu từ database
                    int postId = rs.getInt("post_id");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    int createdBy = rs.getInt("created_by");
                    int status = rs.getInt("status");
                    int catId = rs.getInt("category_id");
                    String detail = rs.getString("detail");
                    String createdAt = rs.getString("created_at");
                    String updatedAt = rs.getString("updated_at");

                    // Xử lý ảnh (Blob)
                    Blob blob = rs.getBlob("image");
                    String imageUrl = (blob != null) ? "LoadBlogImage?postId=" + postId : "default.jpg";

                    // Tạo đối tượng News
                    News blog = new News(postId, title, content, createdBy, catId, status, imageUrl, detail, createdAt, updatedAt);

                    // Thêm vào danh sách
                    list.add(blog);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching blogs by category", e);
        }
        return list;
    }

    public int getTotalBlogCountByCategory(int categoryId) {
        String sql = "SELECT COUNT(*) FROM Posts WHERE category_id = ?";

        try (PreparedStatement st = new DBContext().connection.prepareStatement(sql)) {
            st.setInt(1, categoryId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching blog count by category", e);
        }
        return 0;

        
        
        
    }

    public static void main(String[] args) {
        // Kết nối đến database (Thay đổi theo thông tin của bạn)

        // Tạo đối tượng DAO
        NewsDAO dao = new NewsDAO();

        // Dữ liệu giả lập
        int postId = 1; // ID của bài viết cần cập nhật
        String title = "Cập nhật bài viết mới";
        String content = "Nội dung bài viết đã được cập nhật.";
        int status = 6; // 1 = Active, 2 = Inactive
        String detail = "Chi tiết cập nhật mới nhất.";
        int categoryId = 3; // ID thể loại mới

        // Giả lập ảnh (Chuyển chuỗi thành InputStream)
        String fakeImageData = "Fake Image Data";
        InputStream imageStream = new ByteArrayInputStream(fakeImageData.getBytes());

        // Gọi phương thức update
        boolean success = dao.updateBlogPost(postId, title, content, status, imageStream, detail, categoryId);

        if (success) {
            System.out.println("🎉 Cập nhật bài viết thành công!");
        } else {
            System.out.println("❌ Cập nhật thất bại!");
        }

    }
}
