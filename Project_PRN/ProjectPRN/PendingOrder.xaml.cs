using System;
using System.Linq;
using System.Windows;
using System.Windows.Controls;
using Microsoft.EntityFrameworkCore;
using ProjectPRN.Models;

namespace ProjectPRN
{
    public partial class PendingOrder : Window
    {
        private FootballStoreContext _context = FootballStoreContext.Ins;

        public PendingOrder()
        {
            InitializeComponent();
            
        }
        

        private void LoadOrders()
        {
            dgvOrders.ItemsSource = _context.Orders
                .Where(o => o.Status == "Pending")
                .Select(o => new
                {
                    o.OrderId,
                    o.AccountId,
                    o.OrderDate,
                    o.TotalPrice,
                    o.PhoneNumber,
                    o.Address,
                    o.Status
                })
                .ToList();
        }

        private void dgvOrders_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            var selectedOrder = dgvOrders.SelectedItem as dynamic;
            if (selectedOrder == null) return;

            int orderId = selectedOrder.OrderId;
            dgvOrderDetails.ItemsSource = _context.OrderDetails
                .Where(od => od.OrderId == orderId)
                .Include(od => od.Product)  // ✅ Đảm bảo lấy thông tin sản phẩm
                .Select(od => new
                {
                    od.ProductId,
                    ProductName = od.Product != null ? od.Product.ProductName : "Không có dữ liệu", //  Tránh null
                    od.Quantity,
                    od.Price,
                    Total = od.Quantity * od.Price
                })
                .ToList();
        }

        private void btnAccept_Click(object sender, RoutedEventArgs e)
        {
            var selectedOrder = dgvOrders.SelectedItem as dynamic;
            if (selectedOrder == null)
            {
                MessageBox.Show("Vui lòng chọn đơn hàng!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }

            int orderId = selectedOrder.OrderId;
            var order = _context.Orders.FirstOrDefault(o => o.OrderId == orderId);
            if (order != null)
            {
                order.Status = "Processing";
                _context.SaveChanges();
                LoadOrders();
                dgvOrderDetails.ItemsSource = null;
                MessageBox.Show("Đã duyệt đơn hàng!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
            }
        }

        private void btnReject_Click(object sender, RoutedEventArgs e)
        {
            var selectedOrder = dgvOrders.SelectedItem as dynamic;
            if (selectedOrder == null)
            {
                MessageBox.Show("Vui lòng chọn đơn hàng!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }

            int orderId = selectedOrder.OrderId;
            var order = _context.Orders.FirstOrDefault(o => o.OrderId == orderId);
            if (order != null)
            {
                order.Status = "Cancelled";
                _context.SaveChanges();
                LoadOrders();
                dgvOrderDetails.ItemsSource = null;
                MessageBox.Show("Đã từ chối đơn hàng!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
            }
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            LoadOrders();
        }
    }
}
