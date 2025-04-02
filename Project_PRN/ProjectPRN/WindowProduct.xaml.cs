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
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.Win32;
using ProjectPRN.Models;

namespace ProjectPRN
{
    /// <summary>
    /// Interaction logic for WindowProduct.xaml
    /// </summary>
    public partial class WindowProduct : Window
    {
        public WindowProduct()
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
            var prd = FootballStoreContext.Ins.Products.Include(x => x.Category).ToList();
            dgvDisplay.ItemsSource = prd;
        }
        private void loadCategory()
        {
            var cate = FootballStoreContext.Ins.Categories
                        .Select(x => x.CategoryName)
                        .OrderBy(x => x)
                        .ToList();

            // Thêm tùy chọn "Tất cả"
            cate.Insert(0, "Tất cả");

            cbxcate.ItemsSource = cate;
            cbxcate.SelectedIndex = 0;

            cbxCategoryFilter.ItemsSource = cate; // Gán danh mục cho bộ lọc
            cbxCategoryFilter.SelectedIndex = 0;
        }


        private void btninsert_Click(object sender, RoutedEventArgs e)
        {
            var prd = GetProductnoid();
            if (prd != null)
            {
                var x = FootballStoreContext.Ins.Products.Find(prd.ProductId);
                if (x != null)
                {
                }
                else
                {

                    FootballStoreContext.Ins.Products.Add(prd);
                    FootballStoreContext.Ins.SaveChanges();
                    loadProduct();
                }
            }
        }

        private void btnupdate_Click(object sender, RoutedEventArgs e)
        {
            var prd = GetProduct();
            if (prd != null)
            {
                var x = FootballStoreContext.Ins.Products.Find(prd.ProductId);
                if (x != null)
                {
                    x.ProductId = prd.ProductId;
                    x.CategoryId = prd.CategoryId;
                    x.ProductName = prd.ProductName;
                    x.Brand = prd.Brand;
                    x.Description = prd.Description;
                    x.Price = prd.Price;
                    x.StockQuantity = prd.StockQuantity;
                    x.Image = prd.Image;
                    x.Status = prd.Status;
                    FootballStoreContext.Ins.Products.Update(x);
                    FootballStoreContext.Ins.SaveChanges();
                    loadProduct();
                }
            }
        }


        private void dgvDisplay_SelectedCellsChanged(object sender, SelectedCellsChangedEventArgs e)
        {
            var prd = dgvDisplay.SelectedItem as Product;
            if (prd != null)
            {
                txtid.Text = prd.ProductId.ToString();
                cbxcate.SelectedValue = prd.Category.CategoryName;
                txtname.Text = prd.ProductName;
                txtbrand.Text = prd.Brand;
                txtdescription.Text = prd.Description;
                txtprice.Text = prd.Price.ToString();
                txtstock.Text = prd.StockQuantity.ToString();
                txtimage.Text = prd.Image;
                cbxStatus.SelectedItem = cbxStatus.Items.Cast<ComboBoxItem>().FirstOrDefault(x => x.Content.ToString() == prd.Status);

            }
        }
        private Product GetProduct()
        {
            try
            {
                int id = int.Parse(txtid.Text);
                var selectedCategoryName = cbxcate.SelectedValue as string;

                if (string.IsNullOrWhiteSpace(selectedCategoryName))
                {
                    MessageBox.Show("Vui lòng chọn một danh mục hợp lệ!");
                    return null;
                }

                var category = FootballStoreContext.Ins.Categories
                    .FirstOrDefault(x => x.CategoryName == selectedCategoryName);

                if (category == null)
                {
                    MessageBox.Show("Danh mục không tồn tại trong hệ thống!");
                    return null;
                }

                string name = txtname.Text;
                string brand = txtbrand.Text;
                string description = txtdescription.Text;
                string status = ((ComboBoxItem)cbxStatus.SelectedItem)?.Content.ToString();

                if (string.IsNullOrWhiteSpace(name) || string.IsNullOrWhiteSpace(brand) ||
                    string.IsNullOrWhiteSpace(description) || string.IsNullOrWhiteSpace(status))
                {
                    MessageBox.Show("Tên sản phẩm, hãng, mô tả và trạng thái không được để trống!");
                    return null;
                }

                decimal price = decimal.Parse(txtprice.Text);
                int stock = int.Parse(txtstock.Text);
                if (price <= 0)
                {
                    MessageBox.Show("Giá tiền không thể âm");
                    return null;
                }
                if (stock < 0)
                {
                    MessageBox.Show("Số lượng không thể âm");
                    return null;
                }
                string image = txtimage.Text;

                return new Product
                {
                    ProductId = id,
                    CategoryId = category.CategoryId,
                    ProductName = name.Trim(),
                    Brand = brand.Trim(),
                    Description = description.Trim(),
                    Price = price,
                    StockQuantity = stock,
                    Image = image,
                    Status = status.Trim()
                };
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            return null;
        }


        private Product GetProductnoid()
        {
            try
            {
                var selectedCategoryName = cbxcate.SelectedValue as string;

                if (string.IsNullOrWhiteSpace(selectedCategoryName))
                {
                    MessageBox.Show("Vui lòng chọn một danh mục hợp lệ!");
                    return null;
                }

                var category = FootballStoreContext.Ins.Categories
                    .FirstOrDefault(x => x.CategoryName == selectedCategoryName);

                if (category == null)
                {
                    MessageBox.Show("Danh mục không tồn tại trong hệ thống!");
                    return null;
                }

                string name = txtname.Text;
                string brand = txtbrand.Text;
                string description = txtdescription.Text;
                string status = ((ComboBoxItem)cbxStatus.SelectedItem)?.Content.ToString();

                if (string.IsNullOrWhiteSpace(name) || string.IsNullOrWhiteSpace(brand) ||
                    string.IsNullOrWhiteSpace(description) || string.IsNullOrWhiteSpace(status))
                {
                    MessageBox.Show("Tên sản phẩm, hãng, mô tả và trạng thái không được để trống!");
                    return null;
                }

                decimal price = decimal.Parse(txtprice.Text);
                int stock = int.Parse(txtstock.Text);
                if (price <= 0)
                {
                    MessageBox.Show("Giá tiền không thể âm");
                    return null;
                }
                if (stock < 0)
                {
                    MessageBox.Show("Số lượng không thể âm");
                    return null;
                }
                string image = txtimage.Text;

                return new Product
                {
                    CategoryId = category.CategoryId,
                    ProductName = name.Trim(),
                    Brand = brand.Trim(),
                    Description = description.Trim(),
                    Price = price,
                    StockQuantity = stock,
                    Image = image,
                    Status = status.Trim()
                };
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            return null;
        }
        private void btnSearch_Click(object sender, RoutedEventArgs e)
        {
            string keyword = txtSearch.Text.ToLower();
            var filteredData = FootballStoreContext.Ins.Products
                .Where(p =>
                    p.ProductName.ToLower().Contains(keyword) ||
                    p.Brand.ToLower().Contains(keyword) ||
                    (p.Description != null && p.Description.ToLower().Contains(keyword))
                )
                .ToList();

            dgvDisplay.ItemsSource = filteredData;
        }


        private void btnFilter_Click(object sender, RoutedEventArgs e)
        {
            string selectedCategory = cbxCategoryFilter.SelectedItem as string;
            string selectedStatus = (cbxStatusFilter.SelectedItem as ComboBoxItem)?.Content.ToString();

            var filteredData = FootballStoreContext.Ins.Products.AsQueryable();

            if (!string.IsNullOrEmpty(selectedCategory) && selectedCategory != "Tất cả")
            {
                var category = FootballStoreContext.Ins.Categories.FirstOrDefault(x => x.CategoryName == selectedCategory);
                if (category != null)
                {
                    filteredData = filteredData.Where(p => p.CategoryId == category.CategoryId);
                }
            }

            if (!string.IsNullOrEmpty(selectedStatus) && selectedStatus != "Tất cả")
            {
                filteredData = filteredData.Where(p => p.Status.ToLower() == selectedStatus.ToLower());
            }

            dgvDisplay.ItemsSource = filteredData.ToList();
        }
        private void btnSelectImage_Click(object sender, RoutedEventArgs e)
        {
            OpenFileDialog openFileDialog = new OpenFileDialog
            {
                Filter = "Image Files|*.jpg;*.jpeg;*.png;*.bmp;*.gif",
                Title = "Chọn ảnh sản phẩm"
            };

            if (openFileDialog.ShowDialog() == true)
            {
                string imagePath = openFileDialog.FileName;

                // Lưu đường dẫn ảnh vào textbox
                txtimage.Text = imagePath;
            }
        }

        private void btnnhaphang_Click(object sender, RoutedEventArgs e)
        {
            Nhaphang nhaphang
                = new Nhaphang();
            nhaphang.Show();
            this.Close();
        }
    }
}
