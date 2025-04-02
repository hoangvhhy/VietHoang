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
using Microsoft.EntityFrameworkCore;

namespace ProjectPRN
{
    /// <summary>
    /// Interaction logic for Admin.xaml
    /// </summary>
    public partial class Admin : Window
    {
        public Admin()
        {
            InitializeComponent();
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            LoadCustomer();
            LoadStaff();
        }

        private void LoadCustomer()
        {
            var cus = FootballStoreContext.Ins.Accounts.Where(x => x.RoleId == 4 ).ToList();
            dgvDisplayCustomer.ItemsSource = cus;
        }
        private void LoadStaff()
        {
            var cus = FootballStoreContext.Ins.Accounts.Where(x => x.RoleId == 3).ToList();
            dgvDisplayStaff.ItemsSource = cus;
        }

        private Account getCustomer()
        {
            try
            {
                if (string.IsNullOrWhiteSpace(txtId.Text) ||
                    string.IsNullOrWhiteSpace(txtname.Text) ||
                    string.IsNullOrWhiteSpace(txtemail.Text) ||
                    string.IsNullOrWhiteSpace(txtpassword.Text) ||
                    string.IsNullOrWhiteSpace(txtphone.Text) ||
                    string.IsNullOrWhiteSpace(txtaddress.Text) ||
                    (rdoActive.IsChecked == false && rdoInactive.IsChecked == false)
                    )
                {
                    MessageBox.Show("Phải điền đầy đủ thông tin!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
                    return null;
                }
                int id;
                if (!int.TryParse(txtId.Text, out id))
                {
                    MessageBox.Show("ID phải là số thực!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
                    return null;
                }
                string status = rdoActive.IsChecked == true ? "active" : "inactive";
                return new Account
                {
                    AccountId = id,
                    FullName = txtname.Text.Trim(),
                    Email = txtemail.Text.Trim(),
                    Password = txtpassword.Text.Trim(),
                    Address = txtaddress.Text.Trim(),
                    Phone = txtphone.Text.Trim(),
                    Status = status,
                    RoleId = 4

                };
            }
            catch (Exception e) { 
            }
            return null;
}
        private Account getCustomerNoID()
        {
            try
            {
                if (string.IsNullOrWhiteSpace(txtname.Text) ||
                    string.IsNullOrWhiteSpace(txtemail.Text) ||
                    string.IsNullOrWhiteSpace(txtpassword.Text) ||
                    string.IsNullOrWhiteSpace(txtphone.Text) ||
                    string.IsNullOrWhiteSpace(txtaddress.Text) ||
                    (rdoActive.IsChecked == false && rdoInactive.IsChecked == false)
                    )
                {
                    MessageBox.Show("Phải điền đầy đủ thông tin!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
                    return null;
                }
              
                string status = rdoActive.IsChecked == true ? "active" : "inactive";
                return new Account
                {
                    FullName = txtname.Text.Trim(),
                    Email = txtemail.Text.Trim(),
                    Password = txtpassword.Text.Trim(),
                    Address = txtaddress.Text.Trim(),
                    Phone = txtphone.Text.Trim(),
                    Status = status,
                    RoleId = 4

                };
            }
            catch (Exception ex)
            {
                MessageBox.Show("Lỗi khi lấy dữ liệu tài khoản: " + ex.Message, "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
            }
            return null;
        }

        private void clearForm()
        {
            txtsearch.Clear();
            txtId.Text = "";
            txtname.Text = "";
            txtemail.Text = "";
            txtpassword.Text = "";
            txtaddress.Text = "";
            txtphone.Text = "";
            if(rdoActive.IsChecked != null && rdoInactive.IsChecked != null)
            {
                rdoActive.IsChecked = false;
                rdoInactive.IsChecked = false;
            }
        }

        private void dgvDisplayCustomer_SelectedCellsChanged(object sender, SelectedCellsChangedEventArgs e)
        {
            var cus = dgvDisplayCustomer.SelectedItem as Account;
            if (cus != null) { 
               txtId.Text = cus.AccountId.ToString();
               txtname.Text = cus.FullName.ToString();
               txtemail.Text = cus.Email;
               txtpassword.Text = cus.Password;
               txtphone.Text = cus.Phone;
                txtaddress.Text = cus.Address;
                if (rdoActive != null && rdoInactive != null)
                {
                    rdoActive.IsChecked = cus.Status == "active";
                    rdoInactive.IsChecked = cus.Status == "inactive";
                }
            }
        }

        private void btnSearch_Click(object sender, RoutedEventArgs e)
        {
            var searchText = txtsearch.Text.Trim();
            if (!string.IsNullOrEmpty(searchText))
            {
                var searchResult = FootballStoreContext.Ins.Accounts.
                     Where(x => x.FullName.Contains(searchText)).ToList();
                dgvDisplayCustomer.ItemsSource = searchResult;
            }
            else {
                LoadCustomer();
            }
        }

        private void btnInsert_Click(object sender, RoutedEventArgs e)
        {
            var cus = getCustomerNoID();
            if (cus != null) 
            {
                cus.Status = "active";
                var x = FootballStoreContext.Ins.Accounts.FirstOrDefault(x => x.Email == cus.Email);
                if (x != null)
                {
                    MessageBoxResult result = MessageBox.Show("Email không được trùng!",
                        "Thông báo", MessageBoxButton.OK);
                    clearForm();
                }
                else
                {
                    try
                    {
                        MessageBoxResult result = MessageBox.Show("Bạn chắc chắn muốn thêm tài khoản này?",
                            "Xác nhận", MessageBoxButton.YesNo, MessageBoxImage.Question);
                        if (result == MessageBoxResult.Yes)
                        {
                            FootballStoreContext.Ins.Accounts.Add(cus);
                            FootballStoreContext.Ins.SaveChanges();
                            MessageBox.Show("Thêm tài khoản thành công!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
                            LoadCustomer();
                        }
                    }
                    catch (Exception ex) 
                    {
                        MessageBox.Show("Lỗi khi thêm tài khoản: " + ex.Message, "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
                    }
                }
            }
        }

        private void btnUpdate_Click(object sender, RoutedEventArgs e)
        {
            var cus = getCustomer();
            if (cus != null)
            {
                var x = FootballStoreContext.Ins.Accounts.Find(cus.AccountId);
                if (x != null)
                {
                    x.AccountId = cus.AccountId;
                    x.FullName = cus.FullName;
                    x.Email = cus.Email;
                    x.Password = cus.Password;
                    x.Address = cus.Address;
                    x.Phone = cus.Phone;
                    x.Status = cus.Status;
                    x.RoleId = cus.RoleId;
                    MessageBoxResult result = MessageBox.Show("Bạn chắc chắn muốn sửa tài khoản này?",
                        "Thông báo", MessageBoxButton.YesNo, MessageBoxImage.Question);
                    if (result == MessageBoxResult.Yes)
                    {
                        FootballStoreContext.Ins.Accounts.Update(x);
                        FootballStoreContext.Ins.SaveChanges();
                        MessageBox.Show("Sửa tài khoản thành công!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
                        LoadCustomer();
                        clearForm();
                    }
                }
               }
        }

        private void btnDelete_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                int id = int.Parse(txtId.Text);
                var x = FootballStoreContext.Ins.Accounts.Find(id);
                if (x != null)
                {
                    MessageBoxResult result = MessageBox.Show("Bạn chắc chắn muốn xoá tài khoản này?",
                        "Confirmation", MessageBoxButton.YesNo, MessageBoxImage.Question);
                    if (result == MessageBoxResult.Yes)
                    {
                        x.Status = "inactive";
                        FootballStoreContext.Ins.Accounts.Update(x);
                        FootballStoreContext.Ins.SaveChanges();
                        MessageBox.Show("Xoá tài khoản thành công!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
                        LoadCustomer();
                        clearForm();
                    }
                }
            }
            catch
            {

            }
        }


        //Staff
        private void dgvDisplayStaff_SelectedCellsChanged(object sender, SelectedCellsChangedEventArgs e)
        {
            var cus = dgvDisplayStaff.SelectedItem as Account;
            if (cus != null)
            {
                txtId1.Text = cus.AccountId.ToString();
                txtname1.Text = cus.FullName.ToString();
                txtemail1.Text = cus.Email;
                txtpassword1.Text = cus.Password;
                txtphone1.Text = cus.Phone;
                txtaddress1.Text = cus.Address;
                if (rdoActive1 != null && rdoInactive1 != null)
                {
                    rdoActive1.IsChecked = cus.Status == "active";
                    rdoInactive1.IsChecked = cus.Status == "inactive";
                }
            }
        }
        private Account getStaff()
        {
            try
            {
                if (string.IsNullOrWhiteSpace(txtId1.Text) ||
                    string.IsNullOrWhiteSpace(txtname1.Text) ||
                    string.IsNullOrWhiteSpace(txtemail1.Text) ||
                    string.IsNullOrWhiteSpace(txtpassword1.Text) ||
                    string.IsNullOrWhiteSpace(txtphone1.Text) ||
                    string.IsNullOrWhiteSpace(txtaddress1.Text) ||
                    (rdoActive1.IsChecked == false && rdoInactive1.IsChecked == false)
                    )
                {
                    MessageBox.Show("Phải điền đầy đủ thông tin!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
                    return null;
                }
                int id;
                if (!int.TryParse(txtId1.Text, out id))
                {
                    MessageBox.Show("ID phải là số thực!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
                    return null;
                }
                string status = rdoActive1.IsChecked == true ? "active" : "inactive";
                return new Account
                {
                    AccountId = id,
                    FullName = txtname1.Text.Trim(),
                    Email = txtemail1.Text.Trim(),
                    Password = txtpassword1.Text.Trim(),
                    Address = txtaddress1.Text.Trim(),
                    Phone = txtphone1.Text.Trim(),
                    Status = status,
                    RoleId = 3

                };
            }
            catch (Exception e)
            {
            }
            return null;
        }
        private Account getStaffNoID()
        {
            try
            {
                if (string.IsNullOrWhiteSpace(txtname1.Text) ||
                    string.IsNullOrWhiteSpace(txtemail1.Text) ||
                    string.IsNullOrWhiteSpace(txtpassword1.Text) ||
                    string.IsNullOrWhiteSpace(txtphone1.Text) ||
                    string.IsNullOrWhiteSpace(txtaddress1.Text) ||
                    (rdoActive1.IsChecked == false && rdoInactive1.IsChecked == false)
                    )
                {
                    MessageBox.Show("Phải điền đầy đủ thông tin!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
                    return null;
                }

                string status = rdoActive.IsChecked == true ? "active" : "inactive";
                return new Account
                {
                    FullName = txtname1.Text.Trim(),
                    Email = txtemail1.Text.Trim(),
                    Password = txtpassword1.Text.Trim(),
                    Address = txtaddress1.Text.Trim(),
                    Phone = txtphone1.Text.Trim(),
                    Status = status,
                    RoleId = 3

                };
            }
            catch (Exception ex)
            {
                MessageBox.Show("Lỗi khi lấy dữ liệu tài khoản: " + ex.Message, "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
            }
            return null;
        }

        private void clearForm1()
        {
            txtsearch1.Clear();
            txtId1.Text = "";
            txtname1.Text = "";
            txtemail1.Text = "";
            txtpassword1.Text = "";
            txtaddress1.Text = "";
            txtphone1.Text = "";
            if (rdoActive1.IsChecked != null && rdoInactive1.IsChecked != null)
            {
                rdoActive1.IsChecked = false;
                rdoInactive1.IsChecked = false;
            }
        }


        private void dgvDisplayStaff_SelectedCellsChangedStaff(object sender, SelectedCellsChangedEventArgs e)
        {
            var cus = dgvDisplayStaff.SelectedItem as Account;
            if (cus != null)
            {
                txtId1.Text = cus.AccountId.ToString();
                txtname1.Text = cus.FullName.ToString();
                txtemail1.Text = cus.Email;
                txtpassword1.Text = cus.Password;
                txtphone1.Text = cus.Phone;
                txtaddress1.Text = cus.Address;
                if (rdoActive1 != null && rdoInactive1 != null)
                {
                    rdoActive1.IsChecked = cus.Status == "active";
                    rdoInactive1.IsChecked = cus.Status == "inactive";
                }
            }
        }

        private void btnSearch_Click1(object sender, RoutedEventArgs e)
        {
            var searchText = txtsearch1.Text.Trim();
            if (!string.IsNullOrEmpty(searchText))
            {
                var searchResult = FootballStoreContext.Ins.Accounts.
                     Where(x => x.FullName.Contains(searchText)).ToList();
                dgvDisplayStaff.ItemsSource = searchResult;
            }
            else
            {
                LoadStaff();
            }
        }

        private void btnInsert_Click1(object sender, RoutedEventArgs e)
        {
            var cus = getStaffNoID();
            if (cus != null)
            {
                cus.Status = "active";
                var x = FootballStoreContext.Ins.Accounts.FirstOrDefault(x => x.Email == cus.Email);
                if (x != null)
                {
                    MessageBoxResult result = MessageBox.Show("Email không được trùng!",
                        "Thông báo", MessageBoxButton.OK);
                    clearForm1();
                }
                else
                {
                    try
                    {
                        MessageBoxResult result = MessageBox.Show("Bạn chắc chắn muốn thêm tài khoản này?",
                            "Xác nhận", MessageBoxButton.YesNo, MessageBoxImage.Question);
                        if (result == MessageBoxResult.Yes)
                        {
                            FootballStoreContext.Ins.Accounts.Add(cus);
                            FootballStoreContext.Ins.SaveChanges();
                            MessageBox.Show("Thêm tài khoản thành công!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
                            LoadStaff();
                            clearForm1();
                        }
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show("Lỗi khi thêm tài khoản: " + ex.Message, "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
                    }
                }
            }
        }

        private void btnUpdate_Click1(object sender, RoutedEventArgs e)
        {
            var cus = getStaff();
            if (cus != null)
            {
                var x = FootballStoreContext.Ins.Accounts.Find(cus.AccountId);
                if (x != null)
                {
                    x.AccountId = cus.AccountId;
                    x.FullName = cus.FullName;
                    x.Email = cus.Email;
                    x.Password = cus.Password;
                    x.Address = cus.Address;
                    x.Phone = cus.Phone;
                    x.Status = cus.Status;
                    x.RoleId = cus.RoleId;
                    MessageBoxResult result = MessageBox.Show("Bạn chắc chắn muốn sửa tài khoản này?",
                        "Thông báo", MessageBoxButton.YesNo, MessageBoxImage.Question);
                    if (result == MessageBoxResult.Yes)
                    {
                        FootballStoreContext.Ins.Accounts.Update(x);
                        FootballStoreContext.Ins.SaveChanges();
                        MessageBox.Show("Sửa tài khoản thành công!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
                        LoadStaff();
                        clearForm1();
                    }
                }
            }
        }

        private void btnDelete_Click1(object sender, RoutedEventArgs e)
        {
            try
            {
                int id = int.Parse(txtId1.Text);
                var x = FootballStoreContext.Ins.Accounts.Find(id);
                if (x != null)
                {
                    MessageBoxResult result = MessageBox.Show("Bạn chắc chắn muốn xoá tài khoản này?",
                        "Confirmation", MessageBoxButton.YesNo, MessageBoxImage.Question);
                    if (result == MessageBoxResult.Yes)
                    {
                        x.Status = "inactive";
                        FootballStoreContext.Ins.Accounts.Update(x);
                        FootballStoreContext.Ins.SaveChanges();
                        MessageBox.Show("Xoá tài khoản thành công!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
                        LoadStaff();
                        clearForm1();
                    }
                }
            }
            catch
            {

            }
        }

        private void btnBack_Click(object sender, RoutedEventArgs e)
        {
            Login login = new Login();
            login.Show();
            this.Close();
        }
        private void btnBack_Click1(object sender, RoutedEventArgs e)
        {
            Login login = new Login();
            login.Show();
            this.Close();
        }
    }
}
