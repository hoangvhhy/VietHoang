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
    /// Interaction logic for AdminImport.xaml
    /// </summary>
    public partial class AdminImport : Window
    {
        private FootballStoreContext context = FootballStoreContext.Ins;
        public AdminImport()
        {
            InitializeComponent();
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            LoadImportList();
        }
        private void LoadImportList()
        {
            var imports = context.ProductImports
                .Select(i => new
                {
                    i.Product.ProductName,
                    i.Quantity,
                    i.ImportPrice,
                    i.ImportDate
                }).ToList();

            dgvImportList.ItemsSource = imports;
        }

        private void btnBack_Click(object sender, RoutedEventArgs e)
        {
            AdminStatistic statistic = new AdminStatistic();
            statistic.Show();
            this.Close();
        }
    }
}
