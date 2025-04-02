package dao;

import context.DBContext;
import model.WorkingSchedule;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.ProfessionalLeave;

public class WorkingScheduleDAO {

    private Connection connection;

    public WorkingScheduleDAO() {
        try {
            DBContext db = new DBContext();
            this.connection = db.connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<WorkingSchedule> getListProfessionalSchedules() {
        List<WorkingSchedule> schedules = new ArrayList<>();
        String sql = "SELECT ws.professionalID, s.fullName, ws.dayOfWeek, ws.shift, ws.startTime, ws.endTime, ws.status\n"
                + "        FROM WorkingSchedule ws\n"
                + "        INNER JOIN Professional p ON ws.professionalID = p.professionalID\n"
                + "        INNER JOIN Staff s ON p.staffID = s.staffID\n"
                + "        ORDER BY ws.professionalID, ws.dayOfWeek, ws.startTime";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                schedules.add(new WorkingSchedule(
                        rs.getInt("professionalID"), // professionalID
                        rs.getString("fullName"), // fullName
                        rs.getInt("dayOfWeek"), // dayOfWeek
                        rs.getString("shift"), // shift
                        rs.getTime("startTime"), // startTime
                        rs.getTime("endTime"), // endTime
                        rs.getString("status") // status
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    public List<WorkingSchedule> getAllSchedulesByProfessionalID(int professionalID) {
        List<WorkingSchedule> schedules = new ArrayList<>();
        String sql = "SELECT ws.professionalID, s.fullName, ws.dayOfWeek, ws.shift, ws.startTime, ws.endTime, ws.status\n"
                + "        FROM WorkingSchedule ws\n"
                + "        INNER JOIN Professional p ON ws.professionalID = p.professionalID\n"
                + "        INNER JOIN Staff s ON p.staffID = s.staffID\n"
                + "        WHERE ws.professionalID = ?\n"
                + "        ORDER BY ws.dayOfWeek, ws.startTime";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, professionalID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) { // Duyệt từng dòng trong kết quả
                schedules.add(new WorkingSchedule(
                        rs.getInt("professionalID"),
                        rs.getString("fullName"),
                        rs.getInt("dayOfWeek"),
                        rs.getString("shift"),
                        rs.getTime("startTime"),
                        rs.getTime("endTime"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules; // Trả về danh sách lịch làm việc (có thể rỗng nếu không có lịch)
    }

    public List<WorkingSchedule> getOnSchedulesByProfessionalID(int professionalID) {
        List<WorkingSchedule> schedules = new ArrayList<>();
        String sql = "SELECT ws.professionalID, s.fullName, ws.dayOfWeek, ws.shift, ws.startTime, ws.endTime, ws.status\n"
                + "        FROM WorkingSchedule ws\n"
                + "        INNER JOIN Professional p ON ws.professionalID = p.professionalID\n"
                + "        INNER JOIN Staff s ON p.staffID = s.staffID\n"
                + "        WHERE ws.professionalID = ? AND ws.status = 'On'\n"
                + "        ORDER BY ws.dayOfWeek, ws.startTime";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, professionalID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) { // Duyệt từng dòng trong kết quả
                schedules.add(new WorkingSchedule(
                        rs.getInt("professionalID"),
                        rs.getString("fullName"),
                        rs.getInt("dayOfWeek"),
                        rs.getString("shift"),
                        rs.getTime("startTime"),
                        rs.getTime("endTime"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules; // Trả về danh sách lịch làm việc (có thể rỗng nếu không có lịch)
    }

    public List<WorkingSchedule> getSchedulesByShiftAndDay(String shift, Integer dayOfWeek) {
        List<WorkingSchedule> schedules = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT ws.professionalID, s.fullName, ws.dayOfWeek, ws.shift, ws.startTime, ws.endTime, ws.status\n"
                + "        FROM WorkingSchedule ws\n"
                + "        INNER JOIN Professional p ON ws.professionalID = p.professionalID\n"
                + "        INNER JOIN Staff s ON p.staffID = s.staffID\n"
                + "        WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (shift != null && !shift.isEmpty()) {
            sql.append(" AND ws.shift = ?");
            params.add(shift);
        }
        if (dayOfWeek != null) {
            sql.append(" AND ws.dayOfWeek = ?");
            params.add(dayOfWeek);
        }

        sql.append(" ORDER BY ws.professionalID, ws.startTime");

        try (PreparedStatement st = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                if (params.get(i) instanceof String) {
                    st.setString(i + 1, (String) params.get(i));
                } else if (params.get(i) instanceof Integer) {
                    st.setInt(i + 1, (Integer) params.get(i));
                }
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                schedules.add(new WorkingSchedule(
                        rs.getInt("professionalID"),
                        rs.getString("fullName"),
                        rs.getInt("dayOfWeek"),
                        rs.getString("shift"),
                        rs.getTime("startTime"),
                        rs.getTime("endTime"),
                        rs.getString("status") // status
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    // Thêm lịch làm việc mới
    public void addSchedule(WorkingSchedule schedule) {
        String sql = "INSERT INTO WorkingSchedule (professionalID, dayOfWeek, startTime, endTime, shift) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, schedule.getProfessionalID());
            st.setInt(2, schedule.getDayOfWeek());
            st.setTime(3, schedule.getStartTime());
            st.setTime(4, schedule.getEndTime());
            st.setString(5, schedule.getShift());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateScheduleByKey(WorkingSchedule schedule) {
        String sql = "UPDATE WorkingSchedule "
                + "SET startTime = ?, endTime = ? "
                + "WHERE professionalID = ? AND dayOfWeek = ? AND shift = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setTime(1, schedule.getStartTime());
            st.setTime(2, schedule.getEndTime());
            st.setInt(3, schedule.getProfessionalID());
            st.setInt(4, schedule.getDayOfWeek());
            st.setString(5, schedule.getShift());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateScheduleStatus(int professionalID, int dayOfWeek, String shift, String status) {
        String sql = "UPDATE WorkingSchedule SET status = ? WHERE professionalID = ? AND dayOfWeek = ? AND shift = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, status);
            st.setInt(2, professionalID);
            st.setInt(3, dayOfWeek);
            st.setString(4, shift);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isScheduleExists(int professionalID, int dayOfWeek, String shift) {
        String sql = "SELECT COUNT(*) FROM WorkingSchedule WHERE professionalID = ? AND dayOfWeek = ? AND shift = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, professionalID);
            st.setInt(2, dayOfWeek);
            st.setString(3, shift);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu count > 0, lịch đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Search for schedules by professional name
    public List<WorkingSchedule> searchSchedulesByName(String searchName) {
        List<WorkingSchedule> schedules = new ArrayList<>();
        String sql = "SELECT ws.scheduleID, ws.professionalID, s.fullName, ws.dayOfWeek, ws.shift, ws.startTime, ws.endTime, ws.status\n"
                + "        FROM WorkingSchedule ws\n"
                + "        INNER JOIN Professional p ON ws.professionalID = p.professionalID\n"
                + "        INNER JOIN Staff s ON p.staffID = s.staffID\n"
                + "        WHERE s.fullName LIKE ?\n"
                + "        ORDER BY ws.professionalID, ws.dayOfWeek, ws.startTime";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, "%" + searchName + "%");  // Add wildcards for partial name matching
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                schedules.add(new WorkingSchedule(
                        rs.getInt("professionalID"),
                        rs.getString("fullName"),
                        rs.getInt("dayOfWeek"),
                        rs.getString("shift"),
                        rs.getTime("startTime"),
                        rs.getTime("endTime"),
                        rs.getString("status") // status
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    public List<WorkingSchedule> getSchedulesByDate(String dateStr, String shift) {
        try {
            // Định dạng ngày (YYYY-MM-DD)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateStr, formatter);

            // Lấy thứ trong tuần theo Java
            int dayOfWeek = date.getDayOfWeek().getValue();

            // Điều chỉnh để khớp với database (Chủ Nhật là 1, Thứ Hai là 2, ...)
            int dbDayOfWeek = dayOfWeek + 1;

            // Gọi DAO để lấy lịch làm việc theo thứ trong database và ca làm việc
            return getSchedulesByShiftAndDay(shift, dbDayOfWeek);
        } catch (Exception e) {
            System.err.println("Lỗi khi chuyển đổi ngày: " + e.getMessage());
            return List.of(); // Trả về danh sách rỗng nếu lỗi
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    public static void main(String[] args) {
//        WorkingScheduleDAO dao = new WorkingScheduleDAO();
//        List<WorkingSchedule> list = dao.getAllSchedulesByProfessionalID(2);
//        for (WorkingSchedule ws : list) {
//            System.out.println(ws);
//        }
//
//    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Professional Leave
    public List<ProfessionalLeave> getAllProfessionalLeaves() {
        List<ProfessionalLeave> leaves = new ArrayList<>();
        String sql = "SELECT leaveID, professionalID, leaveDate, reason, status FROM ProfessionalLeave";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                leaves.add(new ProfessionalLeave(
                        rs.getInt("leaveID"),
                        rs.getInt("professionalID"),
                        rs.getDate("leaveDate"),
                        rs.getString("reason"),
                        rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return leaves;
    }

    public List<ProfessionalLeave> getProfessionalLeavesByProfessionalID(int professionalID) {
        List<ProfessionalLeave> leaves = new ArrayList<>();
        String sql = "SELECT leaveID, professionalID, leaveDate, reason, status FROM ProfessionalLeave WHERE professionalID = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, professionalID);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    leaves.add(new ProfessionalLeave(
                            rs.getInt("leaveID"),
                            rs.getInt("professionalID"),
                            rs.getDate("leaveDate"),
                            rs.getString("reason"),
                            rs.getString("status")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaves;
    }

    public void addProfessionalLeave(int professionalID, Date leaveDate, String reason) {
        String sql = "INSERT INTO ProfessionalLeave (professionalID, leaveDate, reason, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, professionalID);
            st.setDate(2, leaveDate);
            st.setString(3, reason);
            st.setString(4, "Pending"); // Default status for new leave requests
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isWorkingOnDate(int professionalID, Date date) {
        boolean hasSchedule = false;

        // Chuyển đổi Date sang LocalDate
        LocalDate localDate = date.toLocalDate();

        // Tính toán dayCode theo quy ước của bạn (2 = Thứ Hai, 8 = Chủ Nhật)
        int dayCode = (localDate.getDayOfWeek().getValue() == 7) ? 8 : localDate.getDayOfWeek().getValue() + 1;

        // Câu lệnh SQL
        String sql = "SELECT COUNT(*) FROM WorkingSchedule WHERE professionalID = ? AND dayOfWeek = ? AND status = 'On'";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, professionalID);
            ps.setInt(2, dayCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    hasSchedule = count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hasSchedule;
    }

    public boolean isLeaveOnDate(int professionalID, Date date) {
        boolean hasSchedule = false;

        // Chuyển đổi từ java.sql.Date sang LocalDate
        LocalDate localDate = date.toLocalDate();

        // Câu lệnh SQL để kiểm tra lịch làm việc
        String sql = "SELECT COUNT(*) FROM ProfessionalLeave WHERE professionalID = ? AND leaveDate = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, professionalID);
            ps.setDate(2, date);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    hasSchedule = rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hasSchedule;
    }

    public void updateLeaveStatus(int leaveID, String status) {
        String sql = "UPDATE ProfessionalLeave SET status = ? WHERE leaveID = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, status);
            st.setInt(2, leaveID);
            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 0) {
                System.err.println("No rows updated for leaveID: " + leaveID);
            }
        } catch (SQLException e) {
            System.err.println("Error updating leave status for leaveID " + leaveID + ": " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        WorkingScheduleDAO dao = new WorkingScheduleDAO();
//        System.out.println(dao.isWorkingOnDate(4,Date.valueOf("2025-03-19") ));
        List<WorkingSchedule> list = dao.getOnSchedulesByProfessionalID(4);
        for (WorkingSchedule ws : list) {
            System.out.println(ws);
        }

    }

}
