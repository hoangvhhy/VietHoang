using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using Microsoft.EntityFrameworkCore;
using Microsoft.Win32;
using ProjectPRN.Models;

namespace ProjectPRN
{
    /// <summary>
    /// Interaction logic for AdminSell.xaml
    /// </summary>
    public partial class AdminSell : Window
    {
        public AdminSell()
        {
            InitializeComponent();
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            loadProduct();
            loadCategory();
        }
        private void loadProduct()
        {
            var prd = FootballStoreContext.Ins.Products
        .Include(x => x.Category) // Lấy thông tin danh mục
        .Select(p => new
        {
            p.ProductId,
            p.ProductName,
            CategoryName = p.Category.CategoryName,
            p.Brand,
            p.Price,
            p.StockQuantity,
            TotalSold = FootballStoreContext.Ins.OrderDetails
                .Where(od => od.ProductId == p.ProductId
                    && FootballStoreContext.Ins.Orders
                        .Where(o => o.OrderId == od.OrderId
                            && (o.Status == "Shipped" || o.Status == "Processing"))
                        .Any()) // Kiểm tra Order hợp lệ
                .Sum(od => (int?)od.Quantity) ?? 0
        })
        .Where(p => p.TotalSold > 0)
        .OrderByDescending(p => p.TotalSold)
        .ToList();

            dgvDisplay.ItemsSource = prd;
        }
        private void loadCategory()
        {
            var cate = FootballStoreContext.Ins.Categories
       .Where(c => FootballStoreContext.Ins.Products
           .Any(p => p.CategoryId == c.CategoryId &&
                     FootballStoreContext.Ins.OrderDetails.Any(od => od.ProductId == p.ProductId &&
                         FootballStoreContext.Ins.Orders.Any(o => o.OrderId == od.OrderId
                             && (o.Status == "Shipped" || o.Status == "Processing"))))) // Chỉ lấy Order có status hợp lệ
       .Select(c => c.CategoryName)
       .OrderBy(c => c)
       .ToList();

            // Thêm tùy chọn "Tất cả"
            cate.Insert(0, "Tất cả");

            cbxCategoryFilter.ItemsSource = cate;
            cbxCategoryFilter.SelectedIndex = 0;
        }

        private void btnSearch_Click(object sender, RoutedEventArgs e)
        {
            string keyword = txtSearch.Text.ToLower();
            var filteredData = FootballStoreContext.Ins.Products
                .Include(p => p.Category)
                .Select(p => new
                {
                    p.ProductId,
                    p.ProductName,
                    CategoryName = p.Category.CategoryName,
                    p.Brand,
                    p.Price,
                    p.StockQuantity,
                    TotalSold = FootballStoreContext.Ins.OrderDetails
                        .Where(od => od.ProductId == p.ProductId &&
                            FootballStoreContext.Ins.Orders
                                .Where(o => o.OrderId == od.OrderId &&
                                    (o.Status == "Shipped" || o.Status == "Processing"))
                                .Any()) // Chỉ lấy Order hợp lệ
                        .Sum(od => (int?)od.Quantity) ?? 0
                })
                .Where(p => p.TotalSold > 0) // Chỉ lấy sản phẩm đã bán
                .Where(p =>
                    p.ProductName.ToLower().Contains(keyword) ||
                    p.Brand.ToLower().Contains(keyword) ||
                    p.CategoryName.ToLower().Contains(keyword)
                )
                .OrderByDescending(p => p.TotalSold)
                .ToList();

            dgvDisplay.ItemsSource = filteredData;
        }




        private void btnFilter_Click(object sender, RoutedEventArgs e)
        {
            string selectedCategory = cbxCategoryFilter.SelectedItem as string;

            var filteredData = FootballStoreContext.Ins.Products
                .Include(p => p.Category)
                .Select(p => new
                {
                    p.ProductId,
                    p.ProductName,
                    CategoryName = p.Category.CategoryName,
                    p.Brand,
                    p.Price,
                    p.StockQuantity,
                    TotalSold = FootballStoreContext.Ins.OrderDetails
                        .Where(od => od.ProductId == p.ProductId &&
                            FootballStoreContext.Ins.Orders
                                .Where(o => o.OrderId == od.OrderId &&
                                    (o.Status == "Shipped" || o.Status == "Processing"))
                                .Any()) // Chỉ lấy Order hợp lệ
                        .Sum(od => (int?)od.Quantity) ?? 0
                })
                .Where(p => p.TotalSold > 0) // Chỉ lấy sản phẩm đã bán
                .AsQueryable();

            if (!string.IsNullOrEmpty(selectedCategory) && selectedCategory != "Tất cả")
            {
                filteredData = filteredData.Where(p => p.CategoryName == selectedCategory);
            }

            dgvDisplay.ItemsSource = filteredData
                .OrderByDescending(p => p.TotalSold)
                .ToList();
        }


        private void btnBack_Click(object sender, RoutedEventArgs e)
        {
            AdminStatistic statistic = new AdminStatistic();
            statistic.Show();
            this.Close();
        }
    }
}
