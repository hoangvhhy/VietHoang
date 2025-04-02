using System.Linq;
using System.Windows;
using System.Windows.Controls;
using Microsoft.EntityFrameworkCore;
using ProjectPRN.Models;

namespace ProjectPRN
{
    public partial class Home : Window
    {
        public Home()
        {
            InitializeComponent();
            
        }

        public void UpdateUserName()
        {
            if (App.LoggedInUser != null)
            {
                txtUserName.Text = $"Xin chào, {App.LoggedInUser.FullName}";
            }
            else
            {
                txtUserName.Text = "Xin chào";
            }
        }


        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            LoadProducts();
            LoadBrands();
            UpdateUserName();
        }

        public void LoadProducts()
        {
            var products = FootballStoreContext.Ins.Products.ToList();
            foreach (var product in products)
            {
                if (!string.IsNullOrEmpty(product.Image))
                {
                    string relativePath = System.IO.Path.Combine("image", product.Image);
                    string fullPath = System.IO.Path.Combine(AppDomain.CurrentDomain.BaseDirectory, relativePath);

                    if (System.IO.File.Exists(fullPath))
                    {
                        product.Image = fullPath;
                    }
                    else
                    {
                        product.Image = System.IO.Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "image", "default.jpg");
                    }
                }
            }

            productList.ItemsSource = products;
        }

        public void LoadBrands()
        {
            var brands = FootballStoreContext.Ins.Products.Select(x => x.Brand).Distinct().ToList();
            brands.Insert(0, "All");
            cmbBrandFilter.ItemsSource = brands;
            cmbBrandFilter.SelectedIndex = 0;
        }

        private void Cart_Click(object sender, RoutedEventArgs e)
        {
            Cart cart = new Cart(this);
            cart.ShowDialog();
        }

        private void btnEditProfile_Click(object sender, RoutedEventArgs e)
        {
            EditProfile edit = new EditProfile(this);
            edit.ShowDialog();
        }

        private void btnOrderHistory_Click(object sender, RoutedEventArgs e)
        {
            OrderHistory orderHistory = new OrderHistory(this);
            orderHistory.ShowDialog();
        }

        private void cmbBrandFilter_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            string brand = cmbBrandFilter.SelectedItem.ToString();
            if (brand.Equals("All"))
            {
                productList.ItemsSource = FootballStoreContext.Ins.Products.ToList();
            }
            else
            {
                var filteredProducts = FootballStoreContext.Ins.Products.Where(x => x.Brand == brand).ToList();
                productList.ItemsSource = filteredProducts;
            }
        }



        private void btnAddToCart_Click(object sender, RoutedEventArgs e)
        {
            var button = sender as Button;
            if (button?.DataContext is Product product)
            {
                if (App.LoggedInUser == null)
                {
                    MessageBox.Show("Vui lòng đăng nhập trước khi thêm sản phẩm vào giỏ hàng.", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
                    return;
                }

                var accountId = App.LoggedInUser.AccountId;
                var cartItem = FootballStoreContext.Ins.Carts.FirstOrDefault(c => c.AccountId == accountId && c.ProductId == product.ProductId);

                if (cartItem == null)
                {
                    if (product.StockQuantity >= 1)
                    {
                        FootballStoreContext.Ins.Carts.Add(new Models.Cart
                        {
                            AccountId = accountId,
                            ProductId = product.ProductId,
                            Quantity = 1
                        });
                    }
                    else
                    {
                        MessageBox.Show("Sản phẩm này đã hết hàng!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
                        return;
                    }
                }
                else
                {
                    if (cartItem.Quantity < product.StockQuantity)
                    {
                        cartItem.Quantity += 1;
                    }
                    else
                    {
                        MessageBox.Show("Không thể thêm sản phẩm vì đã đạt đến số lượng tồn kho tối đa.", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
                        return;
                    }
                }
                FootballStoreContext.Ins.SaveChanges();
                MessageBox.Show($"{product.ProductName} đã được thêm vào giỏ hàng!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
            }
        }
    }
}

