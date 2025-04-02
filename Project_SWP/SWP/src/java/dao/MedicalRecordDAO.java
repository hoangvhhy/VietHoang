/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DBContext;
import jakarta.mail.internet.ParseException;
import model.MedicalRecord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Win11
 */
public class MedicalRecordDAO extends DBContext {

    public MedicalRecordDAO() {

    }

    public static void main(String[] args) {
        MedicalRecordDAO dao = new MedicalRecordDAO();

        System.out.println(dao.getMedicalRecordsByCustomerId(1));
    }

    public MedicalRecord getMedicalRecordByExamID(int examID) {
        String sql = "SELECT * FROM MedicalRecord WHERE examinationID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, examID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new MedicalRecord(
                        rs.getInt("examinationID"),
                        rs.getInt("recordID"),
                        rs.getString("diagnosis"),
                        rs.getString("treatmentPlan"),
                        rs.getString("medicationsPrescribed"),
                        rs.getString("notes")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<MedicalRecord> getMedicalRecordsByCustomerId(int customerId) {
        List<MedicalRecord> records = new ArrayList<>();

        String sql = "SELECT \n"
                + "    me.examinationID,\n"
                + "    me.examinationDate,\n"
                + "    me.customerID,\n"
                + "    me.status AS examinationStatus,\n"
                + "    me.consultantID,\n"
                + "    me.notes AS examinationNotes,\n"
                + "    me.createdAt AS examinationCreatedAt,\n"
                + "    mr.recordID,\n"
                + "    mr.diagnosis,\n"
                + "    mr.treatmentPlan,\n"
                + "    mr.medicationsPrescribed,\n"
                + "    mr.createdAt AS recordCreatedAt,\n"
                + "    mr.notes AS recordNotes,\n"
                + "	st.fullName,\n"
                + "	st.phone\n"
                + "	\n"
                + "FROM \n"
                + "    [MedicalSystem].[dbo].[MedicalExamination] me\n"
                + "JOIN \n"
                + "    [MedicalSystem].[dbo].[MedicalRecord] mr\n"
                + "    ON me.examinationID = mr.examinationID\n"
                + "JOIN [dbo].[Staff] st on  st.staffID= me.consultantID\n"
                + "\n"
                + "WHERE \n"
                + "    me.customerID = ?  -- Thay @customerID bằng giá trị thực tế của customerID\n"
                + "ORDER BY \n"
                + "    me.examinationDate DESC;  -- Sắp xếp theo ngày khám (từ mới đến cũ)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, customerId);  // Set the customerID parameter

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MedicalRecord record = new MedicalRecord();
                    record.setExaminationID(rs.getInt("examinationID"));
                    record.setRecordID(rs.getInt("recordID"));
                    record.setDiagnosis(rs.getString("diagnosis"));
                    record.setTreatmentPlan(rs.getString("treatmentPlan"));
                    record.setMedicationsPrescribed(rs.getString("medicationsPrescribed"));
                    record.setCreatedAt((rs.getString("recordCreatedAt")));  // Format date here
                    record.setNotes(rs.getString("recordNotes"));
                    record.setDoctorName(rs.getString("fullName"));
                    record.setPhone(rs.getString("phone"));
                    records.add(record);  // Add the MedicalRecord to the list
                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return records;
    }

    public boolean saveOrUpdateRecord(int examID, String diagnosis, String treatmentPlan, String medications) {
        if (getMedicalRecordByExamID(examID) != null) {
            // Update existing record
            String sql = "UPDATE MedicalRecord SET diagnosis=?, treatmentPlan=?, medicationsPrescribed=? WHERE examinationID=?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, diagnosis);
                ps.setString(2, treatmentPlan);
                ps.setString(3, medications);
                ps.setInt(4, examID);
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Insert new record
            String sql = "INSERT INTO MedicalRecord (examinationID, diagnosis, treatmentPlan, medicationsPrescribed) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, examID);
                ps.setString(2, diagnosis);
                ps.setString(3, treatmentPlan);
                ps.setString(4, medications);
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
