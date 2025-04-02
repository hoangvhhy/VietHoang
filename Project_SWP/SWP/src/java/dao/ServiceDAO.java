package dao;

import context.DBContext;
import model.Service;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO extends DBContext {

    public ServiceDAO() {
        try {
            DBContext db = new DBContext();
            this.connection = db.connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ Lấy tất cả dịch vụ
    public List<Service> getAllService() {
        List<Service> serviceList = new ArrayList<>();
        String sql = "SELECT * FROM ServicePackage";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                serviceList.add(mapResultSetToService(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceList;
    }

    public void insertMedicalService(int examinationID, int packageID) {
        String sql = "INSERT INTO MedicalService (examinationID, packageID) VALUES (?, ?)";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, examinationID);
            st.setInt(2, packageID);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<Service> getServiceExaminationID(int examinationID) {
        List<Service> serviceList = new ArrayList<>();
        String sql = " select ServicePackage.packageID, packageName,description, service_image, type, price, duration, status, createdAt from ServicePackage join MedicalService on ServicePackage.packageID = MedicalService.packageID where examinationID = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, examinationID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                serviceList.add(new Service(
                        rs.getInt("packageID"),
                        rs.getString("packageName"),
                        rs.getString("description"),
                        rs.getBytes("service_image"),
                        rs.getString("type"),
                        rs.getDouble("price"),
                        rs.getInt("duration"),
                        rs.getString("status"),
                        rs.getString("createdAt")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceList;
    }

    public List<Service> getAll_ON_Service() {
        List<Service> serviceList = new ArrayList<>();
        String sql = "SELECT * FROM ServicePackage WHERE status = 'on'";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                serviceList.add(mapResultSetToService(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceList;
    }

    // ✅ Lấy ảnh từ `packageID`
    public byte[] getImageById(int packageID) {
        String sql = "SELECT service_image FROM ServicePackage WHERE packageID = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, packageID);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getBytes("service_image");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Service getServiceById(int packageID) {
        String sql = "SELECT * FROM ServicePackage WHERE packageID = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, packageID);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToService(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ✅ Thêm dịch vụ mới
    // ✅ Cập nhật dịch vụ
    public void updateService(int packageID, String packageName, String description, InputStream imageStream,
            String type, double price, int duration) {
        // Xây dựng câu lệnh SQL dựa trên việc có ảnh hay không
        String sql;
        if (imageStream != null) {
            sql = "UPDATE ServicePackage SET packageName = ?, description = ?, type = ?, price = ?, duration = ?, service_image = ? WHERE packageID = ?";
        } else {
            sql = "UPDATE ServicePackage SET packageName = ?, description = ?, type = ?, price = ?, duration = ? WHERE packageID = ?";
        }

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, packageName);
            st.setString(2, description);
            st.setString(3, type);
            st.setDouble(4, price);
            st.setInt(5, duration);

            if (imageStream != null) {
                st.setBinaryStream(6, imageStream);
                st.setInt(7, packageID);
            } else {
                st.setInt(6, packageID);
            }

            // In ra câu lệnh SQL để debug
            System.out.println("Executing SQL: " + sql);
            System.out.println("packageName: " + packageName);
            System.out.println("description: " + description);
            System.out.println("type: " + type);
            System.out.println("price: " + price);
            System.out.println("duration: " + duration);
            System.out.println("packageID: " + packageID);

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cập nhật thành công!");
            } else {
                System.err.println("Không có dòng nào được cập nhật. Kiểm tra packageID.");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi cập nhật dịch vụ: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateStatus(int packageID, String status) {
        String sql = "UPDATE ServicePackage SET status = ? WHERE packageID = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, status);
            st.setInt(2, packageID);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Chuyển đổi `ResultSet` thành `Service` để tránh lặp code
    private Service mapResultSetToService(ResultSet rs) throws SQLException {
        Service service = new Service(
                rs.getInt("packageID"),
                rs.getString("packageName"),
                rs.getString("description"),
                rs.getBytes("service_image"),
                rs.getString("type"),
                rs.getDouble("price"),
                rs.getInt("duration"),
                rs.getString("status"),
                rs.getString("createdAt")
        );
        service.setIntroduce(rs.getString("introduce")); // Gán introduce bằng setter
        return service;
    }

    public void addService(String packageName, String description, InputStream imageStream,
            String type, double price, int duration, String status) {
        String sql = "INSERT INTO ServicePackage (packageName, description, service_image, type, price, duration, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, packageName);
            st.setString(2, description);

            // Nếu có ảnh, lưu ảnh; nếu không, truyền NULL
            if (imageStream != null) {
                st.setBinaryStream(3, imageStream);
            } else {
                st.setNull(3, Types.BLOB);
            }

            st.setString(4, type);
            st.setDouble(5, price);
            st.setInt(6, duration);
            st.setString(7, status);

            st.executeUpdate();

        } catch (Exception e) {
            System.err.println("❌ Lỗi SQL khi thêm dịch vụ: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Service> getAllOnServicesWithFilter(String sortBy) {
        List<Service> serviceList = new ArrayList<>();
        String sql = "";

        // Xử lý lọc theo các tiêu chí
        switch (sortBy) {
            case "price_asc":
                sql = "SELECT * FROM ServicePackage WHERE status = 'on' ORDER BY price ASC";
                break;
            case "price_desc":
                sql = "SELECT * FROM ServicePackage WHERE status = 'on' ORDER BY price DESC";
                break;
            case "vip":
                sql = "SELECT * FROM ServicePackage WHERE status = 'on' AND type = 'VIP'";
                break;
            case "basic":
                sql = "SELECT * FROM ServicePackage WHERE status = 'on' AND type = 'Basic'";
                break;
            case "duration_asc":
                sql = "SELECT * FROM ServicePackage WHERE status = 'on' ORDER BY duration ASC";
                break;
            case "duration_desc":
                sql = "SELECT * FROM ServicePackage WHERE status = 'on' ORDER BY duration DESC";
                break;
            case "reload":
                sql = "SELECT * FROM ServicePackage WHERE status = 'on'";
                break;
            default:
                sql = "SELECT * FROM ServicePackage WHERE status = 'on'"; // Nếu không có filter, lấy tất cả
        }

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                serviceList.add(mapResultSetToService(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return serviceList;
    }

    public List<Service> getAllServicesWithFilter(String sortBy) {
        List<Service> serviceList = new ArrayList<>();
        String sql = "";

        // Xử lý lọc theo các tiêu chí
        switch (sortBy) {
            case "price_asc":
                sql = "SELECT * FROM ServicePackage ORDER BY price ASC";
                break;
            case "price_desc":
                sql = "SELECT * FROM ServicePackage ORDER BY price DESC";
                break;
            case "vip":
                sql = "SELECT * FROM ServicePackage WHERE type = 'VIP'";
                break;
            case "basic":
                sql = "SELECT * FROM ServicePackage WHERE type = 'Basic'";
                break;
            case "duration_asc":
                sql = "SELECT * FROM ServicePackage ORDER BY duration ASC";
                break;
            case "duration_desc":
                sql = "SELECT * FROM ServicePackage ORDER BY duration DESC";
                break;
            case "status_on":
                sql = "SELECT * FROM ServicePackage WHERE status = 'on'";
                break;
            case "status_off":
                sql = "SELECT * FROM ServicePackage WHERE status = 'off'";
                break;
            default:
                sql = "SELECT * FROM ServicePackage"; // Nếu không có filter, lấy tất cả
        }

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                serviceList.add(mapResultSetToService(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return serviceList;
    }

    public List<Service> getAllOnServicesWithSearch(String keyword) {
        List<Service> serviceList = new ArrayList<>();
        String sql = "SELECT * FROM ServicePackage WHERE status = 'on' AND packageName LIKE ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, "%" + keyword + "%");
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    serviceList.add(mapResultSetToService(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return serviceList;
    }

    public List<Service> getAllServicesWithSearch(String keyword) {
        List<Service> serviceList = new ArrayList<>();
        String sql = "SELECT * FROM ServicePackage WHERE packageName LIKE ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, "%" + keyword + "%");
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    serviceList.add(mapResultSetToService(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return serviceList;
    }

    public boolean isServiceNameExist(String packageName) {
        String sql = "SELECT COUNT(*) FROM ServicePackage WHERE packageName = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, packageName);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Cập nhật introduce của gói dịch vụ
    public boolean updateIntroduce(int packageID, String introduce) {
        String sql = "UPDATE ServicePackage SET introduce = ? WHERE packageID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, introduce);
            ps.setInt(2, packageID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

// ✅ Xóa introduce của gói dịch vụ
    public boolean deleteIntroduce(int packageID) {
        String sql = "UPDATE ServicePackage SET introduce = NULL WHERE packageID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, packageID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

   public static void main(String[] args) {
        ServiceDAO serviceDAO = new ServiceDAO();

        // Giá trị giả lập để kiểm thử
        int testPackageID = 1;  // Đảm bảo packageID này tồn tại trong database
        String testIntroduce = "This is a test introduce update.";

        // Gọi phương thức updateIntroduce để kiểm tra
        boolean result = serviceDAO.updateIntroduce(testPackageID, testIntroduce);

        // Kiểm tra kết quả
        if (result) {
            System.out.println("✅ Cập nhật introduce thành công!");
        } else {
            System.out.println("❌ Không thể cập nhật introduce. Kiểm tra lại dữ liệu!");
        }
    }
}