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
using Microsoft.Identity.Client;
using ProjectPRN.Models;

namespace ProjectPRN
{
    /// <summary>
    /// Interaction logic for Login.xaml
    /// </summary>
    public partial class Login : Window
    {
        public Login()
        {
            InitializeComponent();
        }

        private void btnlogin_Click(object sender, RoutedEventArgs e)
        {
            string inputEmail = txtemail.Text;
            string inputPassword = txtpassword.Password;

            string adminEmail = AppConfig.GetAdminEmail();
            string adminPassword = AppConfig.GetAdminPassword();
            if (inputEmail == adminEmail && adminPassword == adminPassword)
            {
                App.loged = inputEmail;
                AdminStatistic admin = new AdminStatistic();
                admin.Show();
                this.Close();
            }
            else if (checkManager())
            {
                App.loged = inputEmail;
                WindowProduct product = new WindowProduct();
                product.Show();
                this.Close();
            }
            else if (checkStaff())
            {
                App.loged = inputEmail;
                PendingOrder order = new PendingOrder();
                order.Show();
                this.Close();
            }
            else if (checkUser())
            {
                var account = FootballStoreContext.Ins.Accounts.FirstOrDefault(a => a.Email == inputEmail);
                if (account != null)
                {
                    App.LoggedInUser = account;

                    MessageBox.Show($"Đăng nhập thành công! Chào mừng {account.FullName}.", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);

                    Home home = new Home();
                    home.Show();
                    this.Close();
                }
            }
            else
            {
                MessageBox.Show("Sai email hoặc mật khẩu!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }


        private bool checkUser()
        {
            return FootballStoreContext.Ins.Accounts.Any(x => x.Email.Equals(txtemail.Text)
            && x.Password.Equals(txtpassword.Password) && x.RoleId == 4);
        }
        private bool checkStaff()
        {
            return FootballStoreContext.Ins.Accounts.Any(x => x.Email.Equals(txtemail.Text)
            && x.Password.Equals(txtpassword.Password) && x.RoleId == 3);
        }

        private bool checkManager()
        {
            return FootballStoreContext.Ins.Accounts.Any(x => x.Email.Equals(txtemail.Text)
            && x.Password.Equals(txtpassword.Password) && x.RoleId == 2);
        }

        private void btnRegister_Click(object sender, RoutedEventArgs e)
        {
            Register re = new Register();
            re.Show();
            this.Close();
        }

        private void btnCancel_Click(object sender, RoutedEventArgs e)
        {
            Home home = new Home();
            home.Show();
            this.Close();
        }
    }
}