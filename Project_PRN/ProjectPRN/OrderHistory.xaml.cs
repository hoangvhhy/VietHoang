using System;
using System.Collections.Generic;
using System.Diagnostics;
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
    /// Interaction logic for OrderHistory.xaml
    /// </summary>
    public partial class OrderHistory : Window
    {

        private Home home;
        public OrderHistory(Home home)
        {
            InitializeComponent();
            this.home = home;
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            LoadOrderHistory();
        }

        private void LoadOrderHistory()
        {
            var accountId = App.LoggedInUser.AccountId;
            var orders = FootballStoreContext.Ins.Orders
                .Where(a => a.AccountId == accountId)
                .Select(x => new
                {
                    x.OrderId,
                    x.OrderDate,
                    x.TotalPrice,
                    x.Status
                }).ToList();

            dgvOrderHistory.ItemsSource = orders;
        }

        private void btnClose_Click(object sender, RoutedEventArgs e)
        {
            this.Close();

        }

        private void btnCancel_Click(object sender, RoutedEventArgs e)
        {
            if (sender is FrameworkElement button && button.Tag is int orderId)
            {
                // Tìm đơn hàng cần hủy
                var order = FootballStoreContext.Ins.Orders.FirstOrDefault(o => o.OrderId == orderId);
                if (order != null)
                {
                    if (order.Status == "Pending")
                    {
                        // Hủy đơn hàng
                        order.Status = "Cancelled";

                        // Khôi phục tồn kho sản phẩm
                        var orderDetails = FootballStoreContext.Ins.OrderDetails
                            .Where(od => od.OrderId == orderId)
                            .ToList();

                        foreach (var detail in orderDetails)
                        {
                            var product = FootballStoreContext.Ins.Products.FirstOrDefault(p => p.ProductId == detail.ProductId);
                            if (product != null)
                            {
                                product.StockQuantity += detail.Quantity;
                            }
                        }

                        FootballStoreContext.Ins.SaveChanges();
                        MessageBox.Show("Đơn hàng đã được hủy.", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
                        LoadOrderHistory(); // Tải lại danh sách đơn hàng
                        home.LoadProducts();

                    }
                    else
                    {
                        MessageBox.Show("Chỉ có thể hủy đơn hàng đang ở trạng thái 'Pending'.", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
                    }
                }
                else
                {
                    MessageBox.Show("Không tìm thấy đơn hàng để hủy.", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
                }
            }
            else
            {
                MessageBox.Show("Không thể xác định đơn hàng cần hủy.", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
            }
        }

        private void btnViewDetail_Click(object sender, RoutedEventArgs e)
        {
            // Lấy OrderId từ Tag của nút
            if (sender is FrameworkElement button && button.Tag is int orderId)
            {
                // Tìm đơn hàng theo OrderId
                var order = FootballStoreContext.Ins.Orders
                    .Include(o => o.OrderDetails)
                    .ThenInclude(od => od.Product)
                    .FirstOrDefault(o => o.OrderId == orderId);

                if (order != null)
                {
                    // Hiển thị chi tiết đơn hàng
                    OrderDetailWindow orderDetail = new OrderDetailWindow(order);
                    orderDetail.ShowDialog();
                }
                else
                {
                    MessageBox.Show("Không tìm thấy đơn hàng.", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
                }
            }
            else
            {
                MessageBox.Show("Không thể xác định đơn hàng.", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
            }
        }
    }
}
