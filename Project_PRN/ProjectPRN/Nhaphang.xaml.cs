using System;
using System.Globalization;
using System.Linq;
using System.Text.RegularExpressions;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using ProjectPRN.Models;

namespace ProjectPRN
{
    /// <summary>
    /// Interaction logic for Nhaphang.xaml
    /// </summary>
    public partial class Nhaphang : Window
    {
        private FootballStoreContext _context = FootballStoreContext.Ins;

        public Nhaphang()
        {
            InitializeComponent();
            try
            {
                LoadProducts();
                LoadImportList();
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Lỗi khi load dữ liệu: {ex.Message}", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void LoadProducts()
        {
            var products = _context.Products
                .Select(p => new { p.ProductId, p.ProductName })
                .ToList();

            cbxProduct.ItemsSource = products;
            cbxProduct.DisplayMemberPath = "ProductName";
            cbxProduct.SelectedValuePath = "ProductId";
        }

        private void LoadImportList()
        {
            var imports = _context.ProductImports
                .Select(i => new
                {
                    i.Product.ProductName,
                    i.Quantity,
                    i.ImportPrice,
                    i.ImportDate
                }).ToList();

            dgvImportList.ItemsSource = imports;
        }

        private void btnAdd_Click(object sender, RoutedEventArgs e)
        {
            if (cbxProduct.SelectedValue == null ||
                string.IsNullOrWhiteSpace(txtQuantity.Text) ||
                string.IsNullOrWhiteSpace(txtImportPrice.Text))
            {
                MessageBox.Show("Vui lòng nhập đầy đủ thông tin!", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }

            int productId = (int)cbxProduct.SelectedValue;
            int quantity;
            decimal importPrice;

            // Kiểm tra số lượng nhập
            if (!int.TryParse(txtQuantity.Text.Trim(), out quantity) || quantity <= 0)
            {
                MessageBox.Show("Số lượng phải là số nguyên dương!", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }

            // Loại bỏ dấu `.` trước khi chuyển đổi giá nhập
            string cleanPrice = txtImportPrice.Text.Replace(".", "").Trim();
            if (!decimal.TryParse(cleanPrice, out importPrice) || importPrice <= 0)
            {
                MessageBox.Show("Giá nhập phải là số dương!", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }

            DateTime importDate = dpImportDate.SelectedDate ?? DateTime.Now;

            // Thêm vào danh sách nhập hàng
            var newImport = new ProductImport
            {
                ProductId = productId,
                Quantity = quantity,
                ImportPrice = importPrice,
                ImportDate = importDate
            };

            _context.ProductImports.Add(newImport);

            // Cập nhật số lượng tồn kho của sản phẩm
            var product = _context.Products.FirstOrDefault(p => p.ProductId == productId);
            if (product != null)
            {
                product.StockQuantity += quantity;
            }

            _context.SaveChanges(); // Lưu thay đổi một lần duy nhất

            MessageBox.Show("Thêm đơn nhập hàng thành công!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
            LoadImportList(); // Cập nhật danh sách nhập hàng
        }

        private void btnBack_Click(object sender, RoutedEventArgs e)
        {
            WindowProduct windowProduct = new WindowProduct();
            windowProduct.Show();
            this.Close();
        }

        // Xử lý chỉ cho phép nhập số vào txtImportPrice
        private void txtImportPrice_PreviewTextInput(object sender, TextCompositionEventArgs e)
        {
            e.Handled = !Regex.IsMatch(e.Text, @"^\d+$"); // Chỉ cho nhập số
        }

        // Tự động định dạng giá tiền khi nhập
        private void txtImportPrice_TextChanged(object sender, TextChangedEventArgs e)
        {
            TextBox textBox = sender as TextBox;
            if (textBox == null) return;

            string number = Regex.Replace(textBox.Text, @"\D", ""); // Loại bỏ ký tự không phải số

            if (string.IsNullOrEmpty(number))
            {
                textBox.Text = "";
                return;
            }

            textBox.Text = int.Parse(number).ToString("N0", new CultureInfo("vi-VN")); // Định dạng số
            textBox.CaretIndex = textBox.Text.Length; // Đưa con trỏ về cuối
        }

        private void btnback_Click_1(object sender, RoutedEventArgs e)
        {

        }
    }
}
