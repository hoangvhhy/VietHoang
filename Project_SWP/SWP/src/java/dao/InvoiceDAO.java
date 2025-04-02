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
import model.Discount;
import model.Invoice;
import model.MedicalExamination;

/**
 *
 * @author Gigabyte
 */
public class InvoiceDAO extends DBContext {

    public int getInvoiceByCustomerID(int customerID) {
        String query = "SELECT TOP 1 i.invoiceID\n"
                + "        FROM Invoice i\n"
                + "        JOIN MedicalExamination me ON i.examinationID = me.examinationID\n"
                + "        WHERE me.customerId = ?\n"
                + "        ORDER BY i.createdAt DESC";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("invoiceID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Trả về -1 nếu không tìm thấy invoiceID
    }

    public List<Invoice> getAllInvoice() {
        List<Invoice> invoiceList = new ArrayList<>();
        CustomerDAO customerDAO = new CustomerDAO();
        MedicalExaminationDAO medDAO = new MedicalExaminationDAO();
        DiscountDAO disDAO = new DiscountDAO();
        String sql = "SELECT * FROM invoice ";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int invoiceID = rs.getInt("invoiceID");
                int examinationID = rs.getInt("examinationID");
                MedicalExamination med = medDAO.getMedicalExaminationByID(examinationID);
                double totalAmount = rs.getDouble("totalAmount");
                String paymentStatus = rs.getString("paymentStatus");
                String paymentDate = rs.getString("paymentDate");
                String paymentMethod = rs.getString("paymentMethod");
                String createdAt = rs.getString("createdAt");
                int discountID = rs.getInt("discountID");
                Discount d = disDAO.getDiscountByID(discountID);
                Invoice inv = new Invoice(invoiceID, med, totalAmount, paymentStatus, paymentDate, paymentMethod, createdAt, d);
                invoiceList.add(inv);
            }

        } catch (SQLException e) {
        }
        return invoiceList; // Trả về danh sách khách hàng
    }

    public List<Invoice> getTop3Invoice() {
        List<Invoice> invoiceList = new ArrayList<>();
        CustomerDAO customerDAO = new CustomerDAO();
        MedicalExaminationDAO medDAO = new MedicalExaminationDAO();
        DiscountDAO disDAO = new DiscountDAO();
        String sql = "SELECT TOP 3 *\n"
                + "    FROM [MedicalSystem].[dbo].[Invoice]\n"
                + "    ORDER BY createdAt DESC";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int invoiceID = rs.getInt("invoiceID");
                int examinationID = rs.getInt("examinationID");
                MedicalExamination med = medDAO.getMedicalExaminationByID(examinationID);
                double totalAmount = rs.getDouble("totalAmount");
                String paymentStatus = rs.getString("paymentStatus");
                String paymentDate = rs.getString("paymentDate");
                String paymentMethod = rs.getString("paymentMethod");
                String createdAt = rs.getString("createdAt");
                int discountID = rs.getInt("discountID");
                Discount d = disDAO.getDiscountByID(discountID);
                Invoice inv = new Invoice(invoiceID, med, totalAmount, paymentStatus, paymentDate, paymentMethod, createdAt, d);
                invoiceList.add(inv);
            }

        } catch (SQLException e) {
        }
        return invoiceList; // Trả về danh sách khách hàng
    }

    public double OrderbyDate(String month, String year) {
        double s = 0;
        String sql = "with t as (select month([paymentDate]) as  [month],  year([paymentDate]) as [year],  SUM(totalAmount) AS revenue from Invoice group by month([paymentDate]), year([paymentDate]) ) select revenue from t where [month] = ? and [year] = ?";
        //thuc thi cau truy van;
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, month);
            pre.setString(2, year);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                s = rs.getDouble("revenue");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return s;

    }

    public Invoice getInvoiceByID(int invoiceID) {
        String sql = "select * from Invoice where invoiceID = ?";
        MedicalExaminationDAO medDAO = new MedicalExaminationDAO();
        DiscountDAO disDAO = new DiscountDAO();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, invoiceID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return new Invoice(
                        rs.getInt("invoiceID"),
                        medDAO.getMedicalExaminationByID(rs.getInt("examinationID")),
                        rs.getDouble("totalAmount"),
                        rs.getString("paymentStatus"),
                        rs.getString("paymentDate"),
                        rs.getString("paymentMethod"),
                        rs.getString("createdAt"),
                        disDAO.getDiscountByID(rs.getInt("discountID")));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void createInvoice(int examinationID, double totalAmount, String discountID) {
        String sql = "insert Invoice(examinationID, totalAmount, paymentStatus,createdAt, discountID)\n"
                + "values (?,?,N'Pending',CURRENT_TIMESTAMP,?)  update MedicalExamination set status = N'Payment' where examinationID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, examinationID);
            st.setDouble(2, totalAmount);
            st.setString(3, discountID);
            st.setInt(4, examinationID);
            st.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public List<Invoice> getInvoices(int start, int total) {
        List<Invoice> invoiceList = new ArrayList<>();
        CustomerDAO customerDAO = new CustomerDAO();
        MedicalExaminationDAO medDAO = new MedicalExaminationDAO();
        DiscountDAO disDAO = new DiscountDAO();
        String sql = "SELECT * FROM invoice ORDER BY invoiceID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, start);
            st.setInt(2, total);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int invoiceID = rs.getInt("invoiceID");
                int examinationID = rs.getInt("examinationID");
                MedicalExamination med = medDAO.getMedicalExaminationByID(examinationID);
                double totalAmount = rs.getDouble("totalAmount");
                String paymentStatus = rs.getString("paymentStatus");
                String paymentDate = rs.getString("paymentDate");
                String paymentMethod = rs.getString("paymentMethod");
                String createdAt = rs.getString("createdAt");
                int discountID = rs.getInt("discountID");
                Discount d = disDAO.getDiscountByID(discountID);
                Invoice inv = new Invoice(invoiceID, med, totalAmount, paymentStatus, paymentDate, paymentMethod, createdAt, d);
                invoiceList.add(inv);
            }

        } catch (SQLException e) {
        }
        return invoiceList; // Trả về danh sách khách hàng
    }

    public List<Invoice> getInvoiceByBetweenDate(String from, String to) {
        List<Invoice> invoiceList = new ArrayList<>();
        CustomerDAO customerDAO = new CustomerDAO();
        MedicalExaminationDAO medDAO = new MedicalExaminationDAO();
        DiscountDAO disDAO = new DiscountDAO();
        String sql = "SELECT * FROM [dbo].[Invoice] WHERE createdAt BETWEEN CONVERT(DATETIME, ?, 103) AND CONVERT(DATETIME, ?, 103)";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, from);
            st.setString(2, to);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int invoiceID = rs.getInt("invoiceID");
                int examinationID = rs.getInt("examinationID");
                MedicalExamination med = medDAO.getMedicalExaminationByID(examinationID);
                double totalAmount = rs.getDouble("totalAmount");
                String paymentStatus = rs.getString("paymentStatus");
                String paymentDate = rs.getString("paymentDate");
                String paymentMethod = rs.getString("paymentMethod");
                String createdAt = rs.getString("createdAt");
                int discountID = rs.getInt("discountID");
                Discount d = disDAO.getDiscountByID(discountID);
                Invoice inv = new Invoice(invoiceID, med, totalAmount, paymentStatus, paymentDate, paymentMethod, createdAt, d);
                invoiceList.add(inv);
            }

        } catch (SQLException e) {
        }
        return invoiceList; // Trả về danh sách khách hàng
    }

    public List<Invoice> getInvoiceByBetweenDatePaging(String from, String to, int start, int total) {
        List<Invoice> invoiceList = new ArrayList<>();
        CustomerDAO customerDAO = new CustomerDAO();
        MedicalExaminationDAO medDAO = new MedicalExaminationDAO();
        DiscountDAO disDAO = new DiscountDAO();
        String sql = "SELECT * FROM [dbo].[Invoice] WHERE createdAt BETWEEN CONVERT(DATETIME, ?, 103) AND CONVERT(DATETIME, ?, 103) ORDER BY invoiceID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, from);
            st.setString(2, to);
            st.setInt(3, start);
            st.setInt(4, total);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int invoiceID = rs.getInt("invoiceID");
                int examinationID = rs.getInt("examinationID");
                MedicalExamination med = medDAO.getMedicalExaminationByID(examinationID);
                double totalAmount = rs.getDouble("totalAmount");
                String paymentStatus = rs.getString("paymentStatus");
                String paymentDate = rs.getString("paymentDate");
                String paymentMethod = rs.getString("paymentMethod");
                String createdAt = rs.getString("createdAt");
                int discountID = rs.getInt("discountID");
                Discount d = disDAO.getDiscountByID(discountID);
                Invoice inv = new Invoice(invoiceID, med, totalAmount, paymentStatus, paymentDate, paymentMethod, createdAt, d);
                invoiceList.add(inv);
            }

        } catch (SQLException e) {
        }
        return invoiceList; // Trả về danh sách khách hàng
    }

    public List<Invoice> getInvoiceByFromDate(String from) {
        List<Invoice> invoiceList = new ArrayList<>();
        CustomerDAO customerDAO = new CustomerDAO();
        MedicalExaminationDAO medDAO = new MedicalExaminationDAO();
        DiscountDAO disDAO = new DiscountDAO();
        String sql = "SELECT * FROM [dbo].[Invoice] WHERE createdAt >= CONVERT(DATETIME, ?, 103)";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, from);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int invoiceID = rs.getInt("invoiceID");
                int examinationID = rs.getInt("examinationID");
                MedicalExamination med = medDAO.getMedicalExaminationByID(examinationID);
                double totalAmount = rs.getDouble("totalAmount");
                String paymentStatus = rs.getString("paymentStatus");
                String paymentDate = rs.getString("paymentDate");
                String paymentMethod = rs.getString("paymentMethod");
                String createdAt = rs.getString("createdAt");
                int discountID = rs.getInt("discountID");
                Discount d = disDAO.getDiscountByID(discountID);
                Invoice inv = new Invoice(invoiceID, med, totalAmount, paymentStatus, paymentDate, paymentMethod, createdAt, d);
                invoiceList.add(inv);
            }

        } catch (SQLException e) {
        }
        return invoiceList; // Trả về danh sách khách hàng
    }

    public List<Invoice> getInvoiceByFromDatePaging(String from, int start, int total) {
        List<Invoice> invoiceList = new ArrayList<>();
        CustomerDAO customerDAO = new CustomerDAO();
        MedicalExaminationDAO medDAO = new MedicalExaminationDAO();
        DiscountDAO disDAO = new DiscountDAO();
        String sql = "SELECT * FROM [dbo].[Invoice] WHERE createdAt >= CONVERT(DATETIME, ?, 103) ORDER BY invoiceID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, from);
            st.setInt(2, start);
            st.setInt(3, total);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int invoiceID = rs.getInt("invoiceID");
                int examinationID = rs.getInt("examinationID");
                MedicalExamination med = medDAO.getMedicalExaminationByID(examinationID);
                double totalAmount = rs.getDouble("totalAmount");
                String paymentStatus = rs.getString("paymentStatus");
                String paymentDate = rs.getString("paymentDate");
                String paymentMethod = rs.getString("paymentMethod");
                String createdAt = rs.getString("createdAt");
                int discountID = rs.getInt("discountID");
                Discount d = disDAO.getDiscountByID(discountID);
                Invoice inv = new Invoice(invoiceID, med, totalAmount, paymentStatus, paymentDate, paymentMethod, createdAt, d);
                invoiceList.add(inv);
            }

        } catch (SQLException e) {
        }
        return invoiceList; // Trả về danh sách khách hàng
    }

    public List<Invoice> getInvoiceByToDate(String to) {
        List<Invoice> invoiceList = new ArrayList<>();
        CustomerDAO customerDAO = new CustomerDAO();
        MedicalExaminationDAO medDAO = new MedicalExaminationDAO();
        DiscountDAO disDAO = new DiscountDAO();
        String sql = "SELECT * FROM [dbo].[Invoice] WHERE createdAt <= CONVERT(DATETIME, ?, 103)";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, to);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int invoiceID = rs.getInt("invoiceID");
                int examinationID = rs.getInt("examinationID");
                MedicalExamination med = medDAO.getMedicalExaminationByID(examinationID);
                double totalAmount = rs.getDouble("totalAmount");
                String paymentStatus = rs.getString("paymentStatus");
                String paymentDate = rs.getString("paymentDate");
                String paymentMethod = rs.getString("paymentMethod");
                String createdAt = rs.getString("createdAt");
                int discountID = rs.getInt("discountID");
                Discount d = disDAO.getDiscountByID(discountID);
                Invoice inv = new Invoice(invoiceID, med, totalAmount, paymentStatus, paymentDate, paymentMethod, createdAt, d);
                invoiceList.add(inv);
            }

        } catch (SQLException e) {
        }
        return invoiceList; // Trả về danh sách khách hàng
    }

    public List<Invoice> getInvoiceByToDatePaging(String to, int start, int total) {
        List<Invoice> invoiceList = new ArrayList<>();
        CustomerDAO customerDAO = new CustomerDAO();
        MedicalExaminationDAO medDAO = new MedicalExaminationDAO();
        DiscountDAO disDAO = new DiscountDAO();
        String sql = "SELECT * FROM [dbo].[Invoice] WHERE createdAt <= CONVERT(DATETIME, ?, 103) ORDER BY invoiceID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, to);
            st.setInt(2, start);
            st.setInt(3, total);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int invoiceID = rs.getInt("invoiceID");
                int examinationID = rs.getInt("examinationID");
                MedicalExamination med = medDAO.getMedicalExaminationByID(examinationID);
                double totalAmount = rs.getDouble("totalAmount");
                String paymentStatus = rs.getString("paymentStatus");
                String paymentDate = rs.getString("paymentDate");
                String paymentMethod = rs.getString("paymentMethod");
                String createdAt = rs.getString("createdAt");
                int discountID = rs.getInt("discountID");
                Discount d = disDAO.getDiscountByID(discountID);
                Invoice inv = new Invoice(invoiceID, med, totalAmount, paymentStatus, paymentDate, paymentMethod, createdAt, d);
                invoiceList.add(inv);
            }

        } catch (SQLException e) {
        }
        return invoiceList; // Trả về danh sách khách hàng
    }

    public List<Invoice> getInvoiceByStatus(String status) {
        List<Invoice> invoiceList = new ArrayList<>();
        CustomerDAO customerDAO = new CustomerDAO();
        MedicalExaminationDAO medDAO = new MedicalExaminationDAO();
        DiscountDAO disDAO = new DiscountDAO();
        String sql = "SELECT * FROM [dbo].[Invoice] WHERE paymentStatus = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, status);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int invoiceID = rs.getInt("invoiceID");
                int examinationID = rs.getInt("examinationID");
                MedicalExamination med = medDAO.getMedicalExaminationByID(examinationID);
                double totalAmount = rs.getDouble("totalAmount");
                String paymentStatus = rs.getString("paymentStatus");
                String paymentDate = rs.getString("paymentDate");
                String paymentMethod = rs.getString("paymentMethod");
                String createdAt = rs.getString("createdAt");
                int discountID = rs.getInt("discountID");
                Discount d = disDAO.getDiscountByID(discountID);
                Invoice inv = new Invoice(invoiceID, med, totalAmount, paymentStatus, paymentDate, paymentMethod, createdAt, d);
                invoiceList.add(inv);
            }

        } catch (SQLException e) {
        }
        return invoiceList; // Trả về danh sách khách hàng
    }

    public List<Invoice> getInvoiceByStatusPaging(String status, int start, int total) {
        List<Invoice> invoiceList = new ArrayList<>();
        CustomerDAO customerDAO = new CustomerDAO();
        MedicalExaminationDAO medDAO = new MedicalExaminationDAO();
        DiscountDAO disDAO = new DiscountDAO();
        String sql = "SELECT * FROM [dbo].[Invoice] WHERE paymentStatus = ? ORDER BY invoiceID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, status);
            st.setInt(2, start);
            st.setInt(3, total);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int invoiceID = rs.getInt("invoiceID");
                int examinationID = rs.getInt("examinationID");
                MedicalExamination med = medDAO.getMedicalExaminationByID(examinationID);
                double totalAmount = rs.getDouble("totalAmount");
                String paymentStatus = rs.getString("paymentStatus");
                String paymentDate = rs.getString("paymentDate");
                String paymentMethod = rs.getString("paymentMethod");
                String createdAt = rs.getString("createdAt");
                int discountID = rs.getInt("discountID");
                Discount d = disDAO.getDiscountByID(discountID);
                Invoice inv = new Invoice(invoiceID, med, totalAmount, paymentStatus, paymentDate, paymentMethod, createdAt, d);
                invoiceList.add(inv);
            }

        } catch (SQLException e) {
        }
        return invoiceList; // Trả về danh sách khách hàng
    }

    public List<Invoice> getInvoiceByStatusandDate(String from, String to, String status) {
        List<Invoice> invoiceList = new ArrayList<>();
        CustomerDAO customerDAO = new CustomerDAO();
        MedicalExaminationDAO medDAO = new MedicalExaminationDAO();
        DiscountDAO disDAO = new DiscountDAO();
        String sql = "SELECT * FROM [dbo].[Invoice] WHERE createdAt BETWEEN CONVERT(DATETIME, ?, 103) AND CONVERT(DATETIME, ?, 103) and paymentStatus = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, from);
            st.setString(2, to);
            st.setString(3, status);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int invoiceID = rs.getInt("invoiceID");
                int examinationID = rs.getInt("examinationID");
                MedicalExamination med = medDAO.getMedicalExaminationByID(examinationID);
                double totalAmount = rs.getDouble("totalAmount");
                String paymentStatus = rs.getString("paymentStatus");
                String paymentDate = rs.getString("paymentDate");
                String paymentMethod = rs.getString("paymentMethod");
                String createdAt = rs.getString("createdAt");
                int discountID = rs.getInt("discountID");
                Discount d = disDAO.getDiscountByID(discountID);
                Invoice inv = new Invoice(invoiceID, med, totalAmount, paymentStatus, paymentDate, paymentMethod, createdAt, d);
                invoiceList.add(inv);
            }

        } catch (SQLException e) {
        }
        return invoiceList; // Trả về danh sách khách hàng
    }

    public List<Invoice> getInvoiceByStatusandDatePaging(String from, String to, String status, int start, int total) {
        List<Invoice> invoiceList = new ArrayList<>();
        CustomerDAO customerDAO = new CustomerDAO();
        MedicalExaminationDAO medDAO = new MedicalExaminationDAO();
        DiscountDAO disDAO = new DiscountDAO();
        String sql = "SELECT * FROM [dbo].[Invoice] WHERE createdAt BETWEEN CONVERT(DATETIME, ?, 103) AND CONVERT(DATETIME, ?, 103) and paymentStatus = ? ORDER BY invoiceID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, from);
            st.setString(2, to);
            st.setString(3, status);
            st.setInt(4, start);
            st.setInt(5, total);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int invoiceID = rs.getInt("invoiceID");
                int examinationID = rs.getInt("examinationID");
                MedicalExamination med = medDAO.getMedicalExaminationByID(examinationID);
                double totalAmount = rs.getDouble("totalAmount");
                String paymentStatus = rs.getString("paymentStatus");
                String paymentDate = rs.getString("paymentDate");
                String paymentMethod = rs.getString("paymentMethod");
                String createdAt = rs.getString("createdAt");
                int discountID = rs.getInt("discountID");
                Discount d = disDAO.getDiscountByID(discountID);
                Invoice inv = new Invoice(invoiceID, med, totalAmount, paymentStatus, paymentDate, paymentMethod, createdAt, d);
                invoiceList.add(inv);
            }

        } catch (SQLException e) {
        }
        return invoiceList; // Trả về danh sách khách hàng
    }

    public List<Invoice> getInvoiceByStatusandFromDate(String from, String status) {
        List<Invoice> invoiceList = new ArrayList<>();
        CustomerDAO customerDAO = new CustomerDAO();
        MedicalExaminationDAO medDAO = new MedicalExaminationDAO();
        DiscountDAO disDAO = new DiscountDAO();
        String sql = "SELECT * FROM [dbo].[Invoice] WHERE createdAt >= CONVERT(DATETIME, ?, 103) and paymentStatus = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, from);
            st.setString(2, status);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int invoiceID = rs.getInt("invoiceID");
                int examinationID = rs.getInt("examinationID");
                MedicalExamination med = medDAO.getMedicalExaminationByID(examinationID);
                double totalAmount = rs.getDouble("totalAmount");
                String paymentStatus = rs.getString("paymentStatus");
                String paymentDate = rs.getString("paymentDate");
                String paymentMethod = rs.getString("paymentMethod");
                String createdAt = rs.getString("createdAt");
                int discountID = rs.getInt("discountID");
                Discount d = disDAO.getDiscountByID(discountID);
                Invoice inv = new Invoice(invoiceID, med, totalAmount, paymentStatus, paymentDate, paymentMethod, createdAt, d);
                invoiceList.add(inv);
            }

        } catch (SQLException e) {
        }
        return invoiceList; // Trả về danh sách khách hàng
    }

    public List<Invoice> getInvoiceByStatusandFromDatePaging(String from, String status, int start, int total) {
        List<Invoice> invoiceList = new ArrayList<>();
        CustomerDAO customerDAO = new CustomerDAO();
        MedicalExaminationDAO medDAO = new MedicalExaminationDAO();
        DiscountDAO disDAO = new DiscountDAO();
        String sql = "SELECT * FROM [dbo].[Invoice] WHERE createdAt >= CONVERT(DATETIME, ?, 103) and paymentStatus = ? ORDER BY invoiceID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, from);
            st.setString(2, status);
            st.setInt(3, start);
            st.setInt(4, total);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int invoiceID = rs.getInt("invoiceID");
                int examinationID = rs.getInt("examinationID");
                MedicalExamination med = medDAO.getMedicalExaminationByID(examinationID);
                double totalAmount = rs.getDouble("totalAmount");
                String paymentStatus = rs.getString("paymentStatus");
                String paymentDate = rs.getString("paymentDate");
                String paymentMethod = rs.getString("paymentMethod");
                String createdAt = rs.getString("createdAt");
                int discountID = rs.getInt("discountID");
                Discount d = disDAO.getDiscountByID(discountID);
                Invoice inv = new Invoice(invoiceID, med, totalAmount, paymentStatus, paymentDate, paymentMethod, createdAt, d);
                invoiceList.add(inv);
            }

        } catch (SQLException e) {
        }
        return invoiceList; // Trả về danh sách khách hàng
    }

    public List<Invoice> getInvoiceByStatusandToDate(String to, String status) {
        List<Invoice> invoiceList = new ArrayList<>();
        CustomerDAO customerDAO = new CustomerDAO();
        MedicalExaminationDAO medDAO = new MedicalExaminationDAO();
        DiscountDAO disDAO = new DiscountDAO();
        String sql = "SELECT * FROM [dbo].[Invoice] WHERE createdAt <= CONVERT(DATETIME, ?, 103) and paymentStatus = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, to);
            st.setString(2, status);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int invoiceID = rs.getInt("invoiceID");
                int examinationID = rs.getInt("examinationID");
                MedicalExamination med = medDAO.getMedicalExaminationByID(examinationID);
                double totalAmount = rs.getDouble("totalAmount");
                String paymentStatus = rs.getString("paymentStatus");
                String paymentDate = rs.getString("paymentDate");
                String paymentMethod = rs.getString("paymentMethod");
                String createdAt = rs.getString("createdAt");
                int discountID = rs.getInt("discountID");
                Discount d = disDAO.getDiscountByID(discountID);
                Invoice inv = new Invoice(invoiceID, med, totalAmount, paymentStatus, paymentDate, paymentMethod, createdAt, d);
                invoiceList.add(inv);
            }

        } catch (SQLException e) {
        }
        return invoiceList; // Trả về danh sách khách hàng
    }

    public List<Invoice> getInvoiceByStatusandToDatePaging(String to, String status, int start, int total) {
        List<Invoice> invoiceList = new ArrayList<>();
        CustomerDAO customerDAO = new CustomerDAO();
        MedicalExaminationDAO medDAO = new MedicalExaminationDAO();
        DiscountDAO disDAO = new DiscountDAO();
        String sql = "SELECT * FROM [dbo].[Invoice] WHERE createdAt <= CONVERT(DATETIME, ?, 103) and paymentStatus = ? ORDER BY invoiceID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, to);
            st.setString(2, status);
            st.setInt(3, start);
            st.setInt(4, total);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int invoiceID = rs.getInt("invoiceID");
                int examinationID = rs.getInt("examinationID");
                MedicalExamination med = medDAO.getMedicalExaminationByID(examinationID);
                double totalAmount = rs.getDouble("totalAmount");
                String paymentStatus = rs.getString("paymentStatus");
                String paymentDate = rs.getString("paymentDate");
                String paymentMethod = rs.getString("paymentMethod");
                String createdAt = rs.getString("createdAt");
                int discountID = rs.getInt("discountID");
                Discount d = disDAO.getDiscountByID(discountID);
                Invoice inv = new Invoice(invoiceID, med, totalAmount, paymentStatus, paymentDate, paymentMethod, createdAt, d);
                invoiceList.add(inv);
            }

        } catch (SQLException e) {
        }
        return invoiceList; // Trả về danh sách khách hàng
    }

    public void updateInvoiceOnline(int invoiceID) {
        InvoiceDAO invDAO = new InvoiceDAO();
        Invoice invoice = invDAO.getInvoiceByID(invoiceID);
        int medicalID = invoice.getExaminationID().getExaminationID();
        String sql = "update invoice set paymentDate = SYSDATETIME(),paymentMethod=N'Credit Card', paymentStatus = N'Paid' where invoiceID = ? update MedicalExamination set status = N'Completed' where examinationID = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, invoiceID);
            pre.setInt(2, medicalID);
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateInvoiceDicount(int invoiceID, String discountID) {
        String sql = "update invoice set discountID = ?  where invoiceID = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, discountID);
            pre.setInt(2, invoiceID);
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateInvoiceOffline(int invoiceID, String discountID) {
        InvoiceDAO invDAO = new InvoiceDAO();
        Invoice invoice = invDAO.getInvoiceByID(invoiceID);
        int medicalID = invoice.getExaminationID().getExaminationID();
        String sql = "update invoice set discountID = ?, paymentDate = SYSDATETIME(),paymentMethod=N'Cash', paymentStatus = N'Paid'  where invoiceID = ? update MedicalExamination set status = N'Completed' where examinationID = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, discountID);
            pre.setInt(2, invoiceID);
            pre.setInt(3, medicalID);
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deleteInvoice(int invoiceID) {
        String sql = "delete Feedback where invoiceID = ?"
                + "  delete invoice where invoiceID = ? ";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, invoiceID);
            pre.setInt(2, invoiceID);
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        // Tạo đối tượng InvoiceDAO
        InvoiceDAO invoiceDAO = new InvoiceDAO();

        // CustomerID cần kiểm tra
        int customerID = 1; // Thay bằng một customerID thực tế trong database của bạn

        // Gọi phương thức getInvoiceByCustomerID
        int invoiceID = invoiceDAO.getInvoiceByCustomerID(customerID);

        // Kiểm tra kết quả
        if (invoiceID != -1) {
            System.out.println("Invoice ID tìm thấy cho Customer ID " + customerID + ": " + invoiceID);
        } else {
            System.out.println("Không tìm thấy Invoice ID cho Customer ID " + customerID);
        }
    }
}
