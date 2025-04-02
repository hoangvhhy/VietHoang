/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package InvoiceController;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import util.ValidFunction;
import dao.InvoiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import model.Invoice;
import model.Service;

/**
 *
 * @author Gigabyte
 */
@WebServlet(name = "xuatPDF", urlPatterns = {"/xuatPDF"})
public class xuatPDF extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=invoice.pdf");
        String invoiceID = request.getParameter("invoiceID");
        InvoiceDAO iDAO = new InvoiceDAO();
        Invoice in = iDAO.getInvoiceByID(Integer.parseInt(invoiceID));
        ValidFunction valid = new ValidFunction();
        try {
            OutputStream out = response.getOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();
            Font normalFont = getVietnameseFont(12, false);
            Font boldFont = getVietnameseFont(12, true);

            // 🏥 Logo và thông tin phòng khám
            // 🏥 Tạo bảng với 2 cột
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100); // Chiếm toàn bộ chiều rộng trang
            headerTable.setWidths(new float[]{3, 2}); // Cột trái lớn hơn cột phải

// 🎨 Cột 1: Logo + Thông tin phòng khám
            PdfPCell clinicCell = new PdfPCell();
            clinicCell.setBorder(PdfPCell.NO_BORDER);
            clinicCell.setPadding(5);

// 🏥 Thêm logo vào cell
            String logoPath = getServletContext().getRealPath("/assets/img/logo-dark.png");
            Image logo = Image.getInstance(logoPath);
            logo.scaleAbsolute(80, 80);
            clinicCell.addElement(logo);

// 🏢 Thêm thông tin phòng khám
            Paragraph clinicInfo = new Paragraph("PreClinic\nFPT University\nHòa Lạc, Hà Nội, Việt Nam", normalFont);
            clinicCell.addElement(clinicInfo);

// 📄 Cột 2: Tiêu đề hóa đơn + Ngày tạo (Căn phải)
            PdfPCell invoiceCell = new PdfPCell();
            invoiceCell.setBorder(PdfPCell.NO_BORDER);
            invoiceCell.setPadding(5);
            invoiceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

            Paragraph invoiceTitle = new Paragraph("INVOICE #" + invoiceID, boldFont);
            invoiceTitle.setAlignment(Element.ALIGN_RIGHT);
            invoiceCell.addElement(invoiceTitle);

// 📅 Ngày tạo hóa đơn
            Paragraph createdDate = new Paragraph(valid.formatDateInvoice(in.getCreatedAt()), normalFont);
            createdDate.setAlignment(Element.ALIGN_RIGHT);
            invoiceCell.addElement(createdDate);

// 📌 Thêm 2 ô vào bảng
            headerTable.addCell(clinicCell);
            headerTable.addCell(invoiceCell);

// 📄 Thêm bảng vào tài liệu PDF
            document.add(headerTable);
            document.add(new Paragraph("\n")); // Thêm khoảng trắng

            //-------------------------------------------------------------
            // 📝 Bảng chứa thông tin khách hàng và thông tin công ty
            PdfPTable customerTable = new PdfPTable(2);
            customerTable.setWidthPercentage(100); // Chiếm toàn bộ chiều rộng
            customerTable.setWidths(new float[]{3, 2}); // Cột trái lớn hơn cột phải

// 📌 Cột trái: Thông tin khách hàng
            PdfPCell customerCell = new PdfPCell();
            customerCell.setBorder(PdfPCell.NO_BORDER);
            customerCell.setPadding(5);

// 🔹 Tiêu đề
            Paragraph customerTitle = new Paragraph("Invoice to:", boldFont);
            customerCell.addElement(customerTitle);

// 👤 Thông tin khách hàng
            Paragraph customerInfo = new Paragraph( in.getExaminationID().getCustomerId().getFullName() + "\nViệt Nam," + in.getExaminationID().getCustomerId().getAddress()
                    + "\n" + in.getExaminationID().getCustomerId().getPhone() + "\n" + in.getExaminationID().getCustomerId().getEmail(), normalFont);
            customerCell.addElement(customerInfo);

// 📌 Cột phải: Thông tin công ty
            PdfPCell companyCell = new PdfPCell();
            companyCell.setBorder(PdfPCell.NO_BORDER);
            companyCell.setPadding(5);
            companyCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

// 🔹 Tiêu đề
            Paragraph companyTitle = new Paragraph("Payment Details:", boldFont);
            companyTitle.setAlignment(Element.ALIGN_RIGHT);
            companyCell.addElement(companyTitle);

// 💰 Tổng tiền
            Paragraph totalDue = new Paragraph("Total Due: " + in.getTotalAmount(), boldFont);
            totalDue.setAlignment(Element.ALIGN_RIGHT);
            companyCell.addElement(totalDue);

// 🌍 Địa chỉ công ty
            Paragraph companyInfo = new Paragraph("Country: Việt Nam\nCity: Hà Nội\nAddress: Hòa Lạc, FPT University", normalFont);
            companyInfo.setAlignment(Element.ALIGN_RIGHT);
            companyCell.addElement(companyInfo);

// Thêm các ô vào bảng
            customerTable.addCell(customerCell);
            customerTable.addCell(companyCell);

// Thêm bảng vào tài liệu PDF
            document.add(customerTable);
            document.add(new Paragraph("\n")); // Thêm khoảng trắng

            //--------------------------------------------------------------------
            // 📊 Bảng dịch vụ
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setWidths(new float[]{1, 4, 2});

            // 🌟 Tiêu đề bảng
            table.addCell(createHeaderCell("#"));
            table.addCell(createHeaderCell("Service"));
            table.addCell(createHeaderCell("Total"));
            int i = 1;
            double total = 0;
            for (Service s : in.getExaminationID().getList()) {
                table.addCell(createCell(i + ""));
                i++;
                total += s.getPrice();
                table.addCell(createCell(s.getPackageName()));
                table.addCell(createCell(s.getPrice() + ""));
            }
            // 📝 Dữ liệu dịch vụ

            document.add(table);
            document.add(new Paragraph("\n"));

            // 💰 Tổng tiền và giảm giá
            PdfPTable totalTable = new PdfPTable(2);
            totalTable.setWidthPercentage(50);
            totalTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

            totalTable.addCell(createHeaderCell("Subtotal:"));
            totalTable.addCell(createCell(total + ""));
            if (in.getDiscountID() != null) {
                totalTable.addCell(createHeaderCell("Discount (" + in.getDiscountID().getPercentage() + "%):"));
                totalTable.addCell(createCell((total - in.getTotalAmount()) + ""));
            } else {
                totalTable.addCell(createHeaderCell("Discount (0%):"));
                totalTable.addCell(createCell((total - in.getTotalAmount()) + ""));
            }

            totalTable.addCell(createHeaderCell("Total:"));
            totalTable.addCell(createBoldCell(in.getTotalAmount() + ""));

            document.add(totalTable);
            document.close();
            out.close();
          

        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public Font getVietnameseFont(float size, boolean bold) {
        try {
            String fontPath = getServletContext().getRealPath("/assets/fonts/arial.ttf");
            BaseFont baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            return new Font(baseFont, size, bold ? Font.BOLD : Font.NORMAL);
        } catch (Exception e) {
            e.printStackTrace();
            return new Font(Font.FontFamily.HELVETICA, size); // Nếu lỗi, dùng font mặc định
        }
    }

    private PdfPCell createHeaderCell(String text) {
        Font boldFont = getVietnameseFont(12, true);
        PdfPCell cell = new PdfPCell(new Phrase(text, boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        return cell;
    }

    // 📌 Hàm tạo ô dữ liệu
    private PdfPCell createCell(String text) {
        Font normalFont = getVietnameseFont(12, false);
        PdfPCell cell = new PdfPCell(new Phrase(text, normalFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        return cell;
    }

    // 📌 Hàm tạo ô dữ liệu đậm
    private PdfPCell createBoldCell(String text) {
        Font boldFont = getVietnameseFont(12, true);
        PdfPCell cell = new PdfPCell(new Phrase(text, boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        return cell;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
