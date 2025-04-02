using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
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
    /// Interaction logic for Invoice.xaml
    /// </summary>
    public partial class Invoice : Window
    {
        public Invoice()
        {
            InitializeComponent();
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            LoadInvoiceData();
            LoadCustomerInfo();
        }

        private void LoadInvoiceData()
        {
            var accountId = App.LoggedInUser.AccountId;
            var cartItems = FootballStoreContext.Ins.Carts
                .Where(c => c.AccountId == accountId)
                .Include(c => c.Product)
                .ToList();

            var displayItems = cartItems.Select(c => new
            {
                Product = c.Product,
                Quantity = c.Quantity,
                TotalPrice = c.Quantity * c.Product.Price
            }).ToList();

            dgvDisplay.ItemsSource = displayItems;

            var total = displayItems.Sum(c => c.TotalPrice);
            txtTotalPrice.Text = total.ToString("C0");
        }

        private void LoadCustomerInfo()
        {
            if (App.LoggedInUser != null)
            {
                txtCustomerName.Text = $"Tên: {App.LoggedInUser.FullName}";

                var addresses = !string.IsNullOrEmpty(App.LoggedInUser.Address) ? App.LoggedInUser.Address.Split('|').ToList() : new List<string>();
                var phoneNumbers = !string.IsNullOrEmpty(App.LoggedInUser.Phone) ? App.LoggedInUser.Phone.Split('|').ToList() : new List<string>();

                cbCustomerAddress.ItemsSource = addresses;
                cbCustomerPhone.ItemsSource = phoneNumbers;

                cbCustomerAddress.SelectedIndex = addresses.Any() ? 0 : -1;
                cbCustomerPhone.SelectedIndex = phoneNumbers.Any() ? 0 : -1;

                if (!addresses.Any())
                {
                    cbCustomerAddress.ItemsSource = new List<string> { "Chưa có địa chỉ nào" };
                    cbCustomerAddress.SelectedIndex = 0;
                }
                else if (!phoneNumbers.Any())
                {
                    cbCustomerPhone.ItemsSource = new List<string> { "Chưa có SĐT nào" };
                    cbCustomerPhone.SelectedIndex = 0;
                }
            }
            else
            {
                txtCustomerName.Text = "Tên: Không có thông tin";
                cbCustomerAddress.ItemsSource = new List<string> { "Chưa có địa chỉ nào" };
                cbCustomerAddress.SelectedIndex = 0;
            }
        }

        private void btnAcceptPay_Click(object sender, RoutedEventArgs e)
        {
            var accountId = App.LoggedInUser.AccountId;
            var selectedAddress = cbCustomerAddress.SelectedItem?.ToString();
            var selectedPhoneNumber = cbCustomerPhone.SelectedItem?.ToString();
            if (string.IsNullOrEmpty(selectedAddress))
            {
                MessageBox.Show("Vui lòng chọn địa chỉ giao hàng trước khi thanh toán.", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }
            else if (string.IsNullOrEmpty(selectedPhoneNumber))
            {
                MessageBox.Show("Vui lòng chọn số điện thoại trước khi thanh toán.", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }
            var cartItems = FootballStoreContext.Ins.Carts
                .Where(c => c.AccountId == accountId)
                .Include(c => c.Product)
                .ToList();

            if (!cartItems.Any())
            {
                MessageBox.Show("Giỏ hàng của bạn trống, không thể thanh toán.", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }

            // Tạo đơn hàng mới
            var order = new Order
            {
                AccountId = accountId,
                OrderDate = DateTime.Now,
                TotalPrice = cartItems.Sum(c => c.Quantity * c.Product.Price),
                Status = "Pending",
                Address = selectedAddress,
                PhoneNumber = selectedPhoneNumber
            };

            FootballStoreContext.Ins.Orders.Add(order);
            FootballStoreContext.Ins.SaveChanges();
            foreach (var item in cartItems)
            {
                if (item.Quantity > item.Product.StockQuantity)
                {
                    throw new InvalidOperationException($"Sản phẩm \"{item.Product.ProductName}\" không đủ tồn kho.");
                }

                FootballStoreContext.Ins.OrderDetails.Add(new OrderDetail
                {
                    OrderId = order.OrderId,
                    ProductId = item.ProductId,
                    Quantity = item.Quantity,
                    Price = item.Product.Price
                });

                item.Product.StockQuantity -= item.Quantity;

                FootballStoreContext.Ins.Carts.Remove(item);
            }

            FootballStoreContext.Ins.SaveChanges();

            MessageBox.Show("Thanh toán thành công! Đơn hàng đã được ghi nhận.", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
            this.Close();
        }

        private void btnAddAddress_Click(object sender, RoutedEventArgs e)
        {
            AddAdress addAddressWindow = new AddAdress();
            if (addAddressWindow.ShowDialog() == true)
            {
                string newAddress = addAddressWindow.NewAddress;
                AddNewAddress(newAddress);
            }
        }
        private void btnAddPhoneNumber_Click(object sender, RoutedEventArgs e)
        {
            AddPhoneNumber addPhoneNumber = new AddPhoneNumber();
            if (addPhoneNumber.ShowDialog() == true)
            {
                string newPhoneNumber = addPhoneNumber.NewPhoneNumber;
                AddNewPhoneNumber(newPhoneNumber);
            }
        }

        private void AddNewAddress(string newAddress)
        {
            if (App.LoggedInUser != null && !string.IsNullOrWhiteSpace(newAddress))
            {
                var updatedAddress = string.IsNullOrEmpty(App.LoggedInUser.Address)
                    ? newAddress
                    : $"{App.LoggedInUser.Address}|{newAddress}";

                App.LoggedInUser.Address = updatedAddress;


                var user = FootballStoreContext.Ins.Accounts.SingleOrDefault(a => a.AccountId == App.LoggedInUser.AccountId);
                if (user != null)
                {
                    user.Address = updatedAddress;
                    FootballStoreContext.Ins.SaveChanges();
                }

                var addresses = updatedAddress.Split('|').ToList();
                cbCustomerAddress.ItemsSource = addresses;
                cbCustomerAddress.SelectedIndex = addresses.Count - 1;
            }
        }

        private void AddNewPhoneNumber(string newPhoneNumber)
        {
            if (App.LoggedInUser != null && !string.IsNullOrEmpty(newPhoneNumber))
            {
                var updatePhone = string.IsNullOrEmpty(App.LoggedInUser.Phone) ? newPhoneNumber : $"{App.LoggedInUser.Phone}|{newPhoneNumber}";

                App.LoggedInUser.Phone = updatePhone;

                var user = FootballStoreContext.Ins.Accounts.SingleOrDefault(p => p.AccountId == App.LoggedInUser.AccountId);
                if (user != null)
                {
                    user.Phone = updatePhone;
                    FootballStoreContext.Ins.SaveChanges();
                }

                var phoneNumbers = updatePhone.Split("|").ToList();
                cbCustomerPhone.ItemsSource = phoneNumbers;
                cbCustomerPhone.SelectedIndex = phoneNumbers.Count - 1;
            }
        }
    }
}
