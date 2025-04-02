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
    /// Interaction logic for Cart.xaml
    /// </summary>
    public partial class Cart : Window
    {

        private Home home;
        public Cart(Home home)
        {
            InitializeComponent();
            this.home = home;
            LoadCart();
        }

        private void LoadCart()
        {
            if (App.LoggedInUser == null)
            {
                MessageBox.Show("Vui lòng đăng nhập trước khi xem giỏ hàng.", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }

            var accountId = App.LoggedInUser.AccountId;
            var cartItems = FootballStoreContext.Ins.Carts.Where(c => c.AccountId == accountId).Include(c => c.Product).OrderByDescending(c => c.CartId).ToList();

            cartList.ItemsSource = cartItems;

            // Tính tổng tiền
            var totalPrice = cartItems.Sum(c => c.Quantity * c.Product.Price);
            txtTotalPrice.Text = $"Tổng tiền: {totalPrice:C0} VND";
        }

        private void btnDeleteCart_Click(object sender, RoutedEventArgs e)
        {
            var button = sender as Button;
            if (button?.DataContext is Models.Cart cartItem)
            {
                var result = MessageBox.Show("Xóa sản phẩm khỏi giỏ hàng ?", "Xác nhận", MessageBoxButton.YesNo, MessageBoxImage.Question);
                if (result == MessageBoxResult.Yes)
                {
                    FootballStoreContext.Ins.Carts.Remove(cartItem);
                    FootballStoreContext.Ins.SaveChanges();
                    LoadCart();
                }
            }
        }

        private void btnDecrease_Click(object sender, RoutedEventArgs e)
        {
            var button = sender as Button;
            if (button?.DataContext is Models.Cart cartItem)
            {
                if (cartItem.Quantity > 1)
                {
                    cartItem.Quantity--;
                    UpdateCart(cartItem);
                }
                else
                {
                    var result = MessageBox.Show("Xóa sản phẩm khỏi giỏ hàng ?", "Xác nhận", MessageBoxButton.YesNo, MessageBoxImage.Question);
                    if (result == MessageBoxResult.Yes)
                    {
                        FootballStoreContext.Ins.Carts.Remove(cartItem);
                        FootballStoreContext.Ins.SaveChanges();
                        LoadCart();
                    }
                }
            }
        }

        private void btnIncrease_Click(object sender, RoutedEventArgs e)
        {
            var button = sender as Button;
            if (button?.DataContext is Models.Cart cartItem)
            {
                var product = FootballStoreContext.Ins.Products.FirstOrDefault(p => p.ProductId == cartItem.ProductId);
                if (product != null)
                {
                    if (cartItem.Quantity < product.StockQuantity)
                    {
                        cartItem.Quantity++;
                        UpdateCart(cartItem);
                    }
                    else
                    {
                        MessageBox.Show("Không thể tăng số lượng vì đã đạt đến số lượng tồn kho tối đa.", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
                    }
                }
            }
        }


        private void UpdateCart(Models.Cart cartItem)
        {
            FootballStoreContext.Ins.Carts.Update(cartItem);
            FootballStoreContext.Ins.SaveChanges();
            LoadCart();
        }

        private void btnPay_Click(object sender, RoutedEventArgs e)
        {
            var accountId = App.LoggedInUser.AccountId;

            var cartItems = FootballStoreContext.Ins.Carts.Where(c => c.AccountId == accountId).Include(c => c.Product).ToList();

            if (!cartItems.Any())
            {
                MessageBox.Show("Giỏ hàng của bạn đang trống. Vui lòng thêm sản phẩm trước khi thanh toán.", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }

            foreach (var item in cartItems)
            {
                if (item.Quantity > item.Product.StockQuantity)
                {
                    MessageBox.Show($"Sản phẩm \"{item.Product.ProductName}\" không đủ tồn kho. Vui lòng cập nhật lại giỏ hàng.", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
                    return;
                }
            }

            Invoice invoice = new Invoice();
            invoice.ShowDialog();

            // Làm mới giao diện sau khi thanh toán
            home.LoadProducts();
            LoadCart();
        }
    }
}

