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
    /// Interaction logic for AdminProduct.xaml
    /// </summary>
    public partial class AdminProduct : Window
    {
        public AdminProduct()
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

          

            cbxCategoryFilter.ItemsSource = cate; // Gán danh mục cho bộ lọc
            cbxCategoryFilter.SelectedIndex = 0;
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

        private void btnBack_Click(object sender, RoutedEventArgs e)
        {
            AdminStatistic statistic = new AdminStatistic();
            statistic.Show();
            this.Close();
        }


    }
}
