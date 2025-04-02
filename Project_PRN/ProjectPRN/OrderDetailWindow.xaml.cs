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
using ProjectPRN.Models;

namespace ProjectPRN
{
    /// <summary>
    /// Interaction logic for OrderDetailWindow.xaml
    /// </summary>
    public partial class OrderDetailWindow : Window
    {

        private Order order;
        public OrderDetailWindow(Order order)
        {
            InitializeComponent();
            this.order = order;
        }

        private void LoadOrderDetail(Order order)
        {
            txtCustomerName.Text = order.Account?.FullName ?? "N/A";
            txtCustomerPhone.Text = order.PhoneNumber ?? "N/A";
            txtCustomerAddress.Text = order.Address ?? "N/A";

            lvOrderDetails.ItemsSource = order.OrderDetails.Select(detail => new
            {
                Product = detail.Product,
                Quantity = detail.Quantity,
                Price = detail.Price,
                TotalPrice = detail.Quantity * detail.Price
            }).ToList();

        }
        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            LoadOrderDetail(this.order);
        }
    }
}
