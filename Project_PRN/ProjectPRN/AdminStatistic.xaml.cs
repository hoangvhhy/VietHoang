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
using ProjectPRN.Models;

namespace ProjectPRN
{
    /// <summary>
    /// Interaction logic for AdminStatistic.xaml
    /// </summary>
    public partial class AdminStatistic : Window
    {
        public AdminStatistic()
        {
            InitializeComponent();
        }
        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            DateTime startDate = DateTime.Today;
            DateTime endDate = DateTime.Today.AddDays(1).AddSeconds(-1); // Lấy đến cuối ngày hiện tại
            LoadStatistics(startDate, endDate);

        }

        private void btnFilter_Click(object sender, RoutedEventArgs e)
        {
            if (dpStartDate.SelectedDate == null || dpEndDate.SelectedDate == null)
            {
                MessageBox.Show("Vui lòng chọn đầy đủ ngày bắt đầu và ngày kết thúc!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }
            if (dpEndDate.SelectedDate.Value > DateTime.Today)
            {
                MessageBox.Show("Ngày kết thúc không được vượt quá ngày hôm nay!", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }
            if (dpEndDate.SelectedDate.Value < dpStartDate.SelectedDate.Value)
            {
                MessageBox.Show("Ngày kết thúc không được trước ngày bắt đầu!", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }
            DateTime startDate = dpStartDate.SelectedDate.Value.Date;
            DateTime endDate = dpEndDate.SelectedDate.Value.Date.AddDays(1).AddSeconds(-1); // Lấy đến cuối ngày
           

            LoadStatistics(startDate, endDate);
        }



        private void LoadStatistics(DateTime startDate, DateTime endDate)
        {
            using (var context = new FootballStoreContext())
            {
                // Lọc đơn hàng theo ngày
                var filteredOrders = context.Orders
    .Where(o => o.OrderDate >= startDate
                && o.OrderDate <= endDate
                && (o.Status == "Shipped" || o.Status == "Processing"))
    .ToList();

                var orderIds = filteredOrders.Select(o => o.OrderId).ToList();

                // Tổng doanh thu
                var revenue = filteredOrders.Sum(o => (decimal?)o.TotalPrice) ?? 0;

                // Tính tổng tiền vốn 
                var orderDetails = context.OrderDetails
                    .Where(od => orderIds.Contains((int)od.OrderId))
                    .GroupBy(od => od.ProductId)
                    .ToDictionary(g => g.Key, g => g.Sum(od => od.Quantity));

                decimal totalCost = 0;
                foreach (var productId in orderDetails.Keys)
                {
                    int remainingQuantity = orderDetails[productId];
                    var productImports = context.ProductImports
                        .Where(pi => pi.ProductId == productId)
                        .OrderBy(pi => pi.ImportDate)
                        .ToList();

                    foreach (var import in productImports)
                    {
                        if (remainingQuantity <= 0) break;

                        int usedQuantity = Math.Min(remainingQuantity, import.Quantity);
                        totalCost += usedQuantity * import.ImportPrice;
                        remainingQuantity -= usedQuantity;
                    }
                }

                // Tìm sản phẩm bán chạy nhất và ít bán nhất
                var productSales = orderDetails
                    .Select(kv => new { ProductId = kv.Key, TotalSold = kv.Value })
                    .ToList();

                var bestSelling = productSales.OrderByDescending(g => g.TotalSold).FirstOrDefault();
       

                // Lấy danh sách ProductId đã bán
                var soldProductIds = productSales.Select(p => p.ProductId).ToList();


                // Lấy danh sách sản phẩm đã bán
                var soldProducts = context.Products
                    .Include(p => p.Category)
                    .Where(p => soldProductIds.Contains(p.ProductId))
                    .ToList();

                // Tính toán loại hàng bán chạy nhất 
                var categorySales = soldProducts
                    .GroupBy(p => p.Category.CategoryName)
                    .Select(g => new { Category = g.Key, TotalSold = g.Sum(p => productSales.FirstOrDefault(ps => ps.ProductId == p.ProductId)?.TotalSold ?? 0) })
                    .OrderByDescending(g => g.TotalSold)
                    .FirstOrDefault();

                // Tính toán hãng bán chạy nhất
                var brandSales = soldProducts
                    .GroupBy(p => p.Brand)
                    .Select(g => new { Brand = g.Key,
                        TotalSold = g.Sum(p => productSales.FirstOrDefault(ps => ps.ProductId == p.ProductId)?.TotalSold ?? 0)
                    })
                    .OrderByDescending(g => g.TotalSold)
                    .FirstOrDefault();

                // Top 5 sản phẩm bán chạy nhất và ít nhất
                var top5BestSelling = productSales.OrderByDescending(g => g.TotalSold).Take(5).ToList();
                

                Dispatcher.Invoke(() =>
                {
                    txtTotalRevenue.Text = $"{revenue:N2} VND";
                    txtTotalCost.Text = $"{totalCost:N2} VND";
                    txtTotalProfit.Text = $"{(revenue - totalCost):N2} VND";
                    if (categorySales != null)
                    {
                        txtCate.Text = categorySales.Category;
                        txtTotalCate.Text = categorySales.TotalSold.ToString();
                    }
                    else
                    {
                        txtCate.Text = "N/A";
                        txtTotalCate.Text = "0";
                    }

                    // Hiển thị hãng bán chạy nhất
                    if (brandSales != null)
                    {
                        txtBrand.Text = brandSales.Brand;
                        txtTotalBrand.Text = brandSales.TotalSold.ToString();
                    }
                    else
                    {
                        txtBrand.Text = "N/A";
                        txtTotalBrand.Text = "0";
                    }

                    if (bestSelling != null)
                    {
                        var bestProduct = context.Products.Find(bestSelling.ProductId);
                        txtBestSelling.Text = bestProduct?.ProductName ?? "N/A";
                        txtTotalBestSelling.Text = bestSelling.TotalSold.ToString();
                    }

                   

                    // Cập nhật danh sách top 5 bán chạy nhất
                    var bestSellingProductIds = top5BestSelling.Select(t => t.ProductId).ToList();
                    var bestSellingList = context.Products
                        .Where(p => bestSellingProductIds.Contains(p.ProductId))
                        .ToList()
                        .Select(p => new
                        {
                            ProductName = p.ProductName,
                            TotalSold = top5BestSelling.First(t => t.ProductId == p.ProductId).TotalSold
                        })
                        .OrderByDescending(p => p.TotalSold)
                        .ToList();

                    dgTop5BestSelling.ItemsSource = bestSellingList;

                
                });
            }
        }

        private void btnSell_Click(object sender, RoutedEventArgs e)
        {
            AdminSell sell = new AdminSell();
            sell.Show();
            this.Close();
        }

        private void btnProduct_Click(object sender, RoutedEventArgs e)
        {
            AdminProduct product = new AdminProduct();
            product.Show();
            this.Close();
        }

        private void btnImport_Click(object sender, RoutedEventArgs e)
        {
            AdminImport import = new AdminImport();
            import.Show();
            this.Close();
        }
    }
}

