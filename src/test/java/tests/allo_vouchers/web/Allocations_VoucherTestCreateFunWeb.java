package tests.allo_vouchers.web;

import model.User;
import model.UsersRole;
import pagesweb.DS_TSCD_Dialog;
import base.BaseMultiTestWeb;
import drivers.DriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pagesweb.All_VoucherCreatePageWeb;
import pagesweb.All_VoucherPageWeb;
import utils.MyUtil;

public class Allocations_VoucherTestCreateFunWeb extends BaseMultiTestWeb {
    All_VoucherPageWeb all_vou;
    All_VoucherCreatePageWeb all;
    DS_TSCD_Dialog ds;
//    @BeforeMethod
//    public void prepareVoucherCreatePage(){
//        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
//        all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
//        all_vou.navigateToAllocation_VoucherPage("pvt1", "123456");
//        all_vou.closeMenu();
//        all_vou.All_Btn_click();
//    }
    @Test(priority = 1)
    public void testCreateAllocation_emptyNgayChungTu(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        ds = new DS_TSCD_Dialog(DriverManager.getWebDriver());
        UsersRole Users = null;
        User user = Users.getUserByRole("AM");
        all_vou.navigateToAllocation_VoucherPage(user.getUsername(), user.getPassword());
        all_vou.closeMenu();
        all_vou.All_Btn_click();
        all.clearNgayChungTu();
        all.chonNguoiBanGiaoInput();
        all.chonPhongBanTiepNhanInput();
        all.chonNguoiTiepNhanInput();
        ds.chonTSCD();
        all.setLuu_btn();
        // Kiểm tra xem thông báo lỗi có hiển thị không
        Assert.assertTrue(all.isErrorMessageDisplayed(),
                "Thông báo lỗi không hiển thị!");

        // Kiểm tra hiển thị dưới thông báo
        Assert.assertTrue(all.empty(1),
                "Thông báo lỗi không nằm ngay bên dưới ô Ngày chứng từ!");

        // Kiểm tra xem vẫn còn ở trang thêm mới
        Assert.assertTrue(all.isAllocatonDialogDisplayed(),
                "Form bị ẩn sau khi click Lưu với dữ liệu thiếu");
    }

    @Test(priority = 2)
    public void testCreateAllocation_emptyNguoiBanGiao(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        ds = new DS_TSCD_Dialog(DriverManager.getWebDriver());
        UsersRole Users = null;
        User user = Users.getUserByRole("AM");
        all_vou.navigateToAllocation_VoucherPage(user.getUsername(), user.getPassword());
        all_vou.closeMenu();
        all_vou.All_Btn_click();
        all.setNgayChungTuInput(MyUtil.getNgaychungtu());
        all.clearNguoiBanGiao();
        all.chonPhongBanTiepNhanInput();
        all.chonNguoiTiepNhanInput();
        ds.chonTSCD();
        all.setLuu_btn();
        // Kiểm tra xem thông báo lỗi có hiển thị không
        Assert.assertTrue(all.isErrorMessageDisplayed(),
                "Thông báo lỗi không hiển thị!");

        // Kiểm tra hiển thị dưới thông báo
        Assert.assertTrue(all.empty(3),
                "Thông báo lỗi không nằm ngay bên dưới ô Người bàn giao!");

        // Kiểm tra xem vẫn còn ở trang thêm mới
        Assert.assertTrue(all.isAllocatonDialogDisplayed(),
                "Form bị ẩn sau khi click Lưu với dữ liệu thiếu");
    }

    @Test(priority = 3)
    public void testCreateAllocation_emptyPhongBanTiepNhan(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        ds = new DS_TSCD_Dialog(DriverManager.getWebDriver());
        UsersRole Users = null;
        User user = Users.getUserByRole("AM");
        all_vou.navigateToAllocation_VoucherPage(user.getUsername(), user.getPassword());
        all_vou.closeMenu();
        all_vou.All_Btn_click();
        all.setNgayChungTuInput(MyUtil.getNgaychungtu());
        all.chonNguoiBanGiaoInput();
        all.clearPhongBanTiepNhan();
        ds.chonTSCD();
        all.setLuu_btn();
        // Kiểm tra xem thông báo lỗi có hiển thị không
        Assert.assertTrue(all.isErrorMessageDisplayed(),
                "Thông báo lỗi không hiển thị!");

        // Kiểm tra hiển thị dưới thông báo
        Assert.assertTrue(all.empty(5),
                "Thông báo lỗi không nằm ngay bên dưới ô Phòng ban tiếp nhận!");

        // Kiểm tra xem vẫn còn ở trang thêm mới
        Assert.assertTrue(all.isAllocatonDialogDisplayed(),
                "Form bị ẩn sau khi click Lưu với dữ liệu thiếu");
    }

    @Test(priority = 4)
    public void testCreateAllocation_emptyNguoiTiepNhan(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        ds = new DS_TSCD_Dialog(DriverManager.getWebDriver());
        UsersRole Users = null;
        User user = Users.getUserByRole("AM");
        all_vou.navigateToAllocation_VoucherPage(user.getUsername(), user.getPassword());
        all_vou.closeMenu();
        all_vou.All_Btn_click();
        all.setNgayChungTuInput(MyUtil.getNgaychungtu());
        all.chonNguoiBanGiaoInput();
        all.chonPhongBanTiepNhanInput();
        all.clearNguoiTiepNhan();
        ds.chonTSCD();
        all.setLuu_btn();
        // Kiểm tra xem thông báo lỗi có hiển thị không
        Assert.assertTrue(all.isErrorMessageDisplayed(),
                "Thông báo lỗi không hiển thị!");

        // Kiểm tra hiển thị dưới thông báo
        Assert.assertTrue(all.empty(6),
                "Thông báo lỗi không nằm ngay bên dưới ô Người tiếp nhận!");

        // Kiểm tra xem vẫn còn ở trang thêm mới
        Assert.assertTrue(all.isAllocatonDialogDisplayed(),
                "Form bị ẩn sau khi click Lưu với dữ liệu thiếu");
    }

    @Test(priority = 5)
    public void testCreateAllocation_emptyChonTS(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        UsersRole Users = null;
        User user = Users.getUserByRole("AM");
        all_vou.navigateToAllocation_VoucherPage(user.getUsername(), user.getPassword());
        all_vou.closeMenu();
        all_vou.All_Btn_click();
        all.setNgayChungTuInput(MyUtil.getNgaychungtu());
        all.chonNguoiBanGiaoInput();
        all.chonPhongBanTiepNhanInput();
        all.chonNguoiTiepNhanInput();
        all.setLuu_btn();
        String toastText = all.getToastMessageText();

        //Kiêm tra hiển thị thông báo
        Assert.assertEquals(toastText, "Bạn chưa chọn tài sản nào",
                "Toast không hiển thị hoặc sai nội dung.");

        // Kiểm tra xem vẫn còn ở trang thêm mới
        Assert.assertTrue(all.isAllocatonDialogDisplayed(),
                "Form bị ẩn sau khi click Lưu với dữ liệu thiếu");
    }

    @Test(priority = 6)
    public void testCreateAllocation_emptyTrangThaiPhieu(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        ds = new DS_TSCD_Dialog(DriverManager.getWebDriver());
        UsersRole Users = null;
        User user = Users.getUserByRole("AM");
        all_vou.navigateToAllocation_VoucherPage(user.getUsername(), user.getPassword());
        all_vou.closeMenu();
        all_vou.All_Btn_click();
        all.setNgayChungTuInput(MyUtil.getNgaychungtu());
        all.chonNguoiBanGiaoInput();
        all.chonPhongBanTiepNhanInput();
        all.chonNguoiTiepNhanInput();
        ds.chonTSCD();
        all.clearTrangThaiPhieu();
        all.setLuu_btn();

        // Kiểm tra xem thông báo lỗi có hiển thị không
        Assert.assertTrue(all.isErrorMessageDisplayed(),
                "Thông báo lỗi không hiển thị!");

        // Kiểm tra hiển thị dưới thông báo
        Assert.assertTrue(all.empty(4),
                "Thông báo lỗi không nằm ngay bên dưới ô Trạng Thái Phiếu!");

        // Kiểm tra xem vẫn còn ở trang thêm mới
        Assert.assertTrue(all.isAllocatonDialogDisplayed(),
                "Form bị ẩn sau khi click Lưu với dữ liệu thiếu");
    }

    @Test(priority = 7)
    public void testCreateAllocation_NgayChungTuFutureDate(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        ds = new DS_TSCD_Dialog(DriverManager.getWebDriver());
        UsersRole Users = null;
        User user = Users.getUserByRole("AM");
        all_vou.navigateToAllocation_VoucherPage(user.getUsername(), user.getPassword());
        all_vou.closeMenu();
        all_vou.All_Btn_click();
        all.setNgayChungTuInput(MyUtil.getFutureDate(1));
        all.chonNguoiBanGiaoInput();
        all.chonPhongBanTiepNhanInput();
        all.chonNguoiTiepNhanInput();
        ds.chonTSCD();
        all.chonTrangThaiPhieu(1);
        all.setLuu_btn();

        // Kiểm tra xem thông báo lỗi có hiển thị không
        Assert.assertTrue(all.isErrorMessageDisplayed1(),
                "Thông báo lỗi không hiển thị!");

        // Kiểm tra hiển thị dưới thông báo
        Assert.assertTrue(all.empty(7),
                "Thông báo lỗi không nằm ngay bên dưới ô Ngày chứng từ!");

        // Kiểm tra xem vẫn còn ở trang thêm mới
        Assert.assertTrue(all.isAllocatonDialogDisplayed(),
                "Form bị ẩn sau khi click Lưu với dữ liệu thiếu");
    }

    @Test(priority = 8)
    public void testCreateAllocation_NgayChungTuLessNgayTiepNhan(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        ds = new DS_TSCD_Dialog(DriverManager.getWebDriver());
        UsersRole Users = null;
        User user = Users.getUserByRole("AM");
        all_vou.navigateToAllocation_VoucherPage(user.getUsername(), user.getPassword());
        all_vou.closeMenu();
        all_vou.All_Btn_click();
        all.chonNguoiBanGiaoInput();
        all.chonPhongBanTiepNhanInput();
        all.chonNguoiTiepNhanInput();
        ds.chonTSCD();
        all.chonTrangThaiPhieu(1);
        all.setNgayChungTuInput(all.lessNgayTiepNhan());
        all.setLuu_btn();
        String toastText = all.getToastMessageText();
        //Kiêm tra hiển thị thông báo
        Assert.assertEquals(toastText, "Ngày chứng từ không thể nhỏ hơn ngày tiếp nhận",
                "Toast không hiển thị hoặc sai nội dung.");

        // Kiểm tra xem vẫn còn ở trang thêm mới
        Assert.assertTrue(all.isAllocatonDialogDisplayed(),
                "Form bị ẩn sau khi click Lưu với dữ liệu thiếu");
    }

    @Test(priority = 9)
    public void testCreateAllocation_TrangThaiMoiTao(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        ds = new DS_TSCD_Dialog(DriverManager.getWebDriver());
        UsersRole Users = null;
        User user = Users.getUserByRole("AM");
        all_vou.navigateToAllocation_VoucherPage(user.getUsername(), user.getPassword());
        all_vou.closeMenu();
        all_vou.All_Btn_click();
        all.setNgayChungTuInput(MyUtil.getNgaychungtu());
        all.chonNguoiBanGiaoInput();
        all.chonPhongBanTiepNhanInput();
        all.chonNguoiTiepNhanInput();
        ds.chonTSCD();
        all.chonTrangThaiPhieu(1);
        all.setLuu_btn();
        String toastText = all_vou.getToastMessageText();

        //Kiêm tra hiển thị thông báo
        Assert.assertEquals(toastText, "Thành công",
                "Toast không hiển thị hoặc sai nội dung.");

        // Kiểm tra xem về trang danh sách cấp phát
        Assert.assertFalse(all.isAllocatonDialogDisplayed(),
                "Form chưa bị ẩn sau khi click Lưu với dữ liệu chuẩn");
    }

    @Test(priority = 10)
    public void testCreateAllocation_TrangThaiChoTiepNhan(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        ds = new DS_TSCD_Dialog(DriverManager.getWebDriver());
        UsersRole Users = null;
        User user = Users.getUserByRole("AM");
        all_vou.navigateToAllocation_VoucherPage(user.getUsername(), user.getPassword());
        all_vou.closeMenu();
        all_vou.All_Btn_click();
        all.setNgayChungTuInput(MyUtil.getNgaychungtu());
        all.chonNguoiBanGiaoInput();
        all.chonPhongBanTiepNhanInput();
        all.chonNguoiTiepNhanInput();
        ds.chonTSCD();
        all.chonTrangThaiPhieu(3);
        all.setLuu_btn();
        String toastText = all_vou.getToastMessageText();

        //Kiêm tra hiển thị thông báo
        Assert.assertEquals(toastText, "Thành công",
                "Toast không hiển thị hoặc sai nội dung.");

        // Kiểm tra xem về trang danh sách cấp phát
        Assert.assertFalse(all.isAllocatonDialogDisplayed(),
                "Form chưa bị ẩn sau khi click Lưu với dữ liệu chuẩn");
    }

    @Test(priority = 11)
    public void testCreateAllocation_TrangThaiDaCapPhat(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        ds = new DS_TSCD_Dialog(DriverManager.getWebDriver());
        UsersRole Users = null;
        User user = Users.getUserByRole("AM");
        all_vou.navigateToAllocation_VoucherPage(user.getUsername(), user.getPassword());
        all_vou.closeMenu();
        all_vou.All_Btn_click();
        all.setNgayChungTuInput(MyUtil.getNgaychungtu());
        all.chonNguoiBanGiaoInput();
        all.chonPhongBanTiepNhanInput();
        all.chonNguoiTiepNhanInput();
        ds.chonTSCD();
        all.chonTrangThaiPhieu(2);
        all.setLuu_btn();
        String toastText = all_vou.getToastMessageText();

        //Kiêm tra hiển thị thông báo
        Assert.assertEquals(toastText, "Thành công",
                "Toast không hiển thị hoặc sai nội dung.");

        // Kiểm tra xem về trang danh sách cấp phát
        Assert.assertFalse(all.isAllocatonDialogDisplayed(),
                "Form chưa bị ẩn sau khi click Lưu với dữ liệu chuẩn");
    }
}
