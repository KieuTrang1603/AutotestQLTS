package tests.allo_vouchers.web;

import model.Asset;
import model.Department;
import model.User;
import model.UsersRole;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pagesweb.Assets_Page;
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
    Assets_Page as;
    User user;
    @BeforeMethod
    public void prepareVoucherCreatePage(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        ds = new DS_TSCD_Dialog(DriverManager.getWebDriver());
        user = UsersRole.getUserByRole("AM");
        all_vou.navigateToAllocation_VoucherPage(user.getUsername(), user.getPassword());
        all_vou.closeMenu();
        all_vou.All_Btn_click();
    }
    @Test(priority = 1)
    public void testCreateAllocation_emptyNgayChungTu(){
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
        all.setNgayChungTuInput(MyUtil.getNgaychungtu());
        all.chonNguoiBanGiaoInput();
        all.chonPhongBanTiepNhanMacDinh();
        all.chonNguoiTiepNhanInput();
        Asset taisan = new Asset();
        taisan.setCode(ds.chonVaLayTSTuDialog());
        ds.ChosenTS();
        all.chonTrangThaiPhieu(1);
        all.setLuu_btn();
        String toastText = all_vou.getToastMessageText();

        //Kiêm tra hiển thị thông báo
        Assert.assertEquals(toastText, "Thành công",
                "Toast không hiển thị hoặc sai nội dung.");

        // Kiểm tra xem về trang danh sách cấp phát
        Assert.assertFalse(all.isAllocatonDialogDisplayed(),
                "Form chưa bị ẩn sau khi click Lưu với dữ liệu chuẩn");

        // Kiểm tra tài sản ở màn danh sách
        as= new Assets_Page(DriverManager.getWebDriver());
        as.navigateToAssetsPage();
        as.closeMenu();
        // Kiểm tra xem dữ liệu tài sản sau cấp phát
        Assert.assertTrue(as.checkTSCapPhat(taisan.getCode(), 1, Department.DEPARTMENT_NAME_AU1),
                "Trạng thái Tài sản bị hiển thị sai");
    }

    @Test(priority = 10)
    public void testCreateAllocation_TrangThaiChoTiepNhan(){
        all.setNgayChungTuInput(MyUtil.getNgaychungtu());
        all.chonNguoiBanGiaoInput();
        all.chonPhongBanTiepNhanMacDinh();
        all.chonNguoiTiepNhanInput();
        Asset taisan = new Asset();
        taisan.setCode(ds.chonVaLayTSTuDialog());
        ds.ChosenTS();
        all.chonTrangThaiPhieu(3);
        all.setLuu_btn();
        String toastText = all_vou.getToastMessageText();

        //Kiêm tra hiển thị thông báo
        Assert.assertEquals(toastText, "Thành công",
                "Toast không hiển thị hoặc sai nội dung.");

        // Kiểm tra xem về trang danh sách cấp phát
        Assert.assertFalse(all.isAllocatonDialogDisplayed(),
                "Form chưa bị ẩn sau khi click Lưu với dữ liệu chuẩn");

        // Kiểm tra tài sản ở màn danh sách
        as= new Assets_Page(DriverManager.getWebDriver());
        as.navigateToAssetsPage();
        as.closeMenu();
        // Kiểm tra xem dữ liệu tài sản sau cấp phát
        Assert.assertTrue(as.checkTSCapPhat(taisan.getCode(), 2, Department.DEPARTMENT_NAME_AU1),
                "Trạng thái Tài sản bị hiển thị sai");
    }

    @Test(priority = 11)
    public void testCreateAllocation_TrangThaiDaCapPhat(){
        all.setNgayChungTuInput(MyUtil.getNgaychungtu());
        all.chonNguoiBanGiaoInput();
        all.chonPhongBanTiepNhanMacDinh();
        all.chonNguoiTiepNhanInput();
        Asset taisan = new Asset();
        taisan.setCode(ds.chonVaLayTSTuDialog());
        ds.ChosenTS();
        all.chonTrangThaiPhieu(2);
        all.setLuu_btn();
        String toastText = all_vou.getToastMessageText();

        //Kiêm tra hiển thị thông báo
        Assert.assertEquals(toastText, "Thành công",
                "Toast không hiển thị hoặc sai nội dung.");

        // Kiểm tra xem về trang danh sách cấp phát
        Assert.assertFalse(all.isAllocatonDialogDisplayed(),
                "Form chưa bị ẩn sau khi click Lưu với dữ liệu chuẩn");

        // Kiểm tra tài sản ở màn danh sách role AM
        as= new Assets_Page(DriverManager.getWebDriver());
        as.navigateToAssetsPage();
        as.closeMenu();
        // Kiểm tra xem dữ liệu tài sản sau cấp phát
        Assert.assertTrue(as.checkTSCapPhat(taisan.getCode(), 3, Department.DEPARTMENT_NAME_AU1),
                "Trạng thái Tài sản bị hiển thị sai");

        // Kiểm tra tài sản ở màn danh sách role AU
        as= new Assets_Page(DriverManager.getWebDriver());
        user = UsersRole.getUserByRole("AU");
        as.navigateToAssetsPageAU(user.getUsername(), user.getPassword());
        as.closeMenu();
        // Kiểm tra xem dữ liệu tài sản sau cấp phát
        Assert.assertTrue(as.checkTSCapPhat(taisan.getCode(), 3, Department.DEPARTMENT_NAME_AU1),
                "Trạng thái Tài sản bị hiển thị sai");
    }

    @AfterMethod
    public void reset(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
