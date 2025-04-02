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
    /// Interaction logic for EditProfile.xaml
    /// </summary>
    public partial class EditProfile : Window
    {
        private Home home;
        private List<string> phoneNumbers = new List<string>();
        private List<string> addresses = new List<string>();


        private string previousPhoneNumber;

        public EditProfile(Home home)
        {
            this.home = home;
            InitializeComponent();
            LoadDataUser();
        }

        private void LoadDataUser()
        {
            if (App.LoggedInUser != null)
            {
                txtFullName.Text = App.LoggedInUser.FullName;
                txtEmail.Text = App.LoggedInUser.Email;

                if (App.LoggedInUser != null)
                {
                    var user = FootballStoreContext.Ins.Accounts.FirstOrDefault(x => x.AccountId == App.LoggedInUser.AccountId);
                    if (user != null)
                    {
                        phoneNumbers = user.Phone?.Split('|').ToList() ?? new List<string>();
                        addresses = user.Address?.Split('|').ToList() ?? new List<string>();

                        lbPhoneNumbers.ItemsSource = phoneNumbers;
                        lbAddresses.ItemsSource = addresses;

                        txtFullName.Text = user.FullName;
                        txtEmail.Text = user.Email;
                    }
                }
            }
        }

        private void btnSaveChange_Click(object sender, RoutedEventArgs e)
        {
            if (App.LoggedInUser != null)
            {
                var user = FootballStoreContext.Ins.Accounts.FirstOrDefault(x => x.AccountId == App.LoggedInUser.AccountId);
                if (user != null)
                {
                    user.FullName = txtFullName.Text.Trim();

                    user.Phone = string.Join("|", phoneNumbers);
                    user.Address = string.Join("|",addresses);
                    FootballStoreContext.Ins.SaveChanges();

                    home.UpdateUserName();

                    MessageBox.Show("Thông tin đã được cập nhật!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);

                    this.Close();
                }
                else
                {
                    MessageBox.Show("Không tìm thấy người dùng!", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
                }
            }
        }

        private void btnAddPhoneNumber_Click(object sender, RoutedEventArgs e)
        {
            var input = Microsoft.VisualBasic.Interaction.InputBox("Nhập số điện thoại mới:", "Thêm số điện thoại", "");

            if (string.IsNullOrWhiteSpace(input))
            {
                return;
            }

            if (phoneNumbers.Contains(input))
            {
                MessageBox.Show("Số điện thoại đã tồn tại!", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }

            phoneNumbers.Add(input);
            lbPhoneNumbers.ItemsSource = null;
            lbPhoneNumbers.ItemsSource = phoneNumbers;
            MessageBox.Show("Đã thêm số điện thoại mới!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
        }


        private void btnEditPhoneNumber_Click(object sender, RoutedEventArgs e)
        {
            if (lbPhoneNumbers.SelectedItem is string selectedPhone)
            {
                var input = Microsoft.VisualBasic.Interaction.InputBox("Sửa số điện thoại:", "Chỉnh sửa số điện thoại", selectedPhone);

                if (string.IsNullOrEmpty(input))
                {
                    return;
                }

                if (!string.IsNullOrWhiteSpace(input) && (input == selectedPhone || !phoneNumbers.Contains(input)))
                {
                    int index = phoneNumbers.IndexOf(selectedPhone);
                    phoneNumbers[index] = input;

                    lbPhoneNumbers.ItemsSource = null;
                    lbPhoneNumbers.ItemsSource = phoneNumbers;

                    MessageBox.Show("Đã cập nhật số điện thoại!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
                }
                else
                {
                    MessageBox.Show("Số điện thoại không hợp lệ hoặc đã tồn tại!", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Warning);
                }
            }
            else
            {
                MessageBox.Show("Vui lòng chọn một số điện thoại để sửa!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
            }
        }
        private void btnDeletePhoneNumber_Click(object sender, RoutedEventArgs e)
        {
            if (lbPhoneNumbers.SelectedItem is string selectedPhone)
            {
                MessageBoxResult result = MessageBox.Show($"Bạn có chắc chắn muốn xóa số điện thoại '{selectedPhone}' không?", "Xác nhận",
                    MessageBoxButton.YesNo, MessageBoxImage.Question);

                if (result == MessageBoxResult.Yes)
                {
                    phoneNumbers.Remove(selectedPhone);
                    lbPhoneNumbers.ItemsSource = null;
                    lbPhoneNumbers.ItemsSource = phoneNumbers;
                    MessageBox.Show("Đã xóa số điện thoại!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
                }
            }
            else
            {
                MessageBox.Show("Vui lòng chọn một số điện thoại để xóa!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
            }
        }


        private void btnChangePassword_Click(object sender, RoutedEventArgs e)
        {
            ChangePassword change = new ChangePassword();
            change.ShowDialog();
        }

        private void btnAddAddress_Click(object sender, RoutedEventArgs e)
        {
            var input = Microsoft.VisualBasic.Interaction.InputBox("Nhập địa chỉ mới:", "Thêm địa chỉ", "");

            if (string.IsNullOrWhiteSpace(input))
            {
                return;
            }

            // Nếu địa chỉ đã tồn tại
            if (addresses.Contains(input))
            {
                MessageBox.Show("Địa chỉ đã tồn tại!", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }

            // Thêm địa chỉ mới
            addresses.Add(input);
            lbAddresses.ItemsSource = null;
            lbAddresses.ItemsSource = addresses;
            MessageBox.Show("Đã thêm địa chỉ mới!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
        }


        private void btnEditAddress_Click(object sender, RoutedEventArgs e)
        {
            if (lbAddresses.SelectedItem is string selectedAddress)
            {
                var input = Microsoft.VisualBasic.Interaction.InputBox("Sửa địa chỉ:", "Chỉnh sửa địa chỉ", selectedAddress);

                if (string.IsNullOrEmpty(input))
                {
                    return;
                }

                // Kiểm tra nếu số mới hợp lệ và không bị trùng lặp
                if (!string.IsNullOrWhiteSpace(input) && (input == selectedAddress || !addresses.Contains(input)))
                {
                    int index = addresses.IndexOf(selectedAddress);
                    addresses[index] = input;

                    lbAddresses.ItemsSource = null;
                    lbAddresses.ItemsSource = addresses;

                    MessageBox.Show("Đã cập nhật địa chỉ!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
                }
                else
                {
                    MessageBox.Show("Địa chỉ không hợp lệ hoặc đã tồn tại!", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Warning);
                }
            }
            else
            {
                MessageBox.Show("Vui lòng chọn một địa chỉ!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
            }
        }

        private void btnDeleteAddress_Click(object sender, RoutedEventArgs e)
        {
            if (lbAddresses.SelectedItem is string selectedAddress)
            {
                MessageBoxResult result = MessageBox.Show($"Bạn có chắc chắn muốn xóa địa chỉ '{selectedAddress}' không?", "Xác nhận",
                    MessageBoxButton.YesNo, MessageBoxImage.Question);

                if (result == MessageBoxResult.Yes)
                {
                    addresses.Remove(selectedAddress);
                    lbAddresses.ItemsSource = null;
                    lbAddresses.ItemsSource = addresses;
                    MessageBox.Show("Đã xóa địa chỉ!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
                }
            }
            else
            {
                MessageBox.Show("Vui lòng chọn một địa chỉ để xóa!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
            }
        }
    }
}
