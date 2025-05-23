package tests.main_tains.app;

import drivers.DriverManager;
import model.User;
import model.UsersRole;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pagesweb.DS_TSCD_Maintain_Dialog;
import pagesweb.Main_TainPageWeb;
import pagesweb.Main_TainReportWeb;
import utils.MyUtil;

public class Main_tainReportTestCreateApp {
    Main_TainPageWeb main_tainPageWeb;
    Main_TainReportWeb reportWeb;
    DS_TSCD_Maintain_Dialog ds;
    UsersRole Users;
    User user;
    @BeforeMethod
    public void prepareMainTainCreatePage(){
        main_tainPageWeb = new Main_TainPageWeb(DriverManager.getWebDriver());
        user = Users.getUserByRole("AU");
        main_tainPageWeb.navigateToMain_TainPage(user.getUsername(),user.getPassword());
        main_tainPageWeb.closeMenu();
        main_tainPageWeb.BaoSuCo_Btn_click();
        reportWeb = new Main_TainReportWeb(DriverManager.getWebDriver());
        ds = new DS_TSCD_Maintain_Dialog(DriverManager.getWebDriver());
    }

    @Test(priority = 1)
    public void testCreateAllocation_emptyPhongTiepNhan(){
        reportWeb.setNgayBaoCaoInput(MyUtil.getNgaychungtu());
        reportWeb.chonPhongBaoSuCoInput();
        reportWeb.clearPhongTiepNhan();
        reportWeb.chonNguoiBaoSuCoInput();
        ds.chonTSCD();
        reportWeb.setLuu_btn();
        // Kiểm tra xem thông báo lỗi có hiển thị không
        Assert.assertTrue(reportWeb.isErrorMessageDisplayed(),
                "Thông báo lỗi không hiển thị!");

        // Kiểm tra hiển thị dưới thông báo
        Assert.assertTrue(reportWeb.empty(3),
                "Thông báo lỗi không nằm ngay bên dưới ô Phòng ban tiếp nhận!");

    }

    @Test(priority = 2)
    public void testCreateAllocation_emptyTaiSan(){
        reportWeb.setNgayBaoCaoInput(MyUtil.getNgaychungtu());
        reportWeb.chonPhongBaoSuCoInput();
        reportWeb.chonPhongTiepNhanInput();
        reportWeb.chonNguoiBaoSuCoInput();
        reportWeb.setLuu_btn();
        String toastText = main_tainPageWeb.getToastErrorMessageText();

        //Kiêm tra hiển thị thông báo
        Assert.assertEquals(toastText, "Tài sản báo sự cố không được để trống",
                "Toast không hiển thị hoặc sai nội dung.");

    }

//    @Test(priority = 6)
//    public void testCreateAllocation_NgayChungTuFutureDate(){
//        reportWeb.setNgayBaoCaoInput(MyUtil.getFutureDate(1));
//        reportWeb.chonPhongBaoSuCoInput();
//        reportWeb.chonPhongTiepNhanInput();
//        reportWeb.chonNguoiBaoSuCoInput();
//        ds.chonTSCD();
//        reportWeb.setLuu_btn();
//
//        // Kiểm tra xem thông báo lỗi có hiển thị không
//        Assert.assertTrue(reportWeb.isErrorMessageDisplayed1(),
//                "Thông báo lỗi không hiển thị!");
//
//        // Kiểm tra hiển thị dưới thông báo
//        Assert.assertTrue(reportWeb.empty(5),
//                "Thông báo lỗi không nằm ngay bên dưới ô Ngày báo cáo!");
//    }

//    @Test(priority = 7)
//    public void testCreateAllocation_NgayChungTuLessNgayTiepNhan(){
//        reportWeb.chonPhongBaoSuCoInput();
//        reportWeb.chonPhongTiepNhanInput();
//        reportWeb.chonNguoiBaoSuCoInput();
//        ds.chonTSCD();
//        all.setNgayChungTuInput(all.lessNgayTiepNhan());
//        reportWeb.setLuu_btn();
//        String toastText = main_tainPageWeb.getToastErrorMessageText();
//        //Kiêm tra hiển thị thông báo
//        Assert.assertEquals(toastText, "Ngày chứng từ không thể nhỏ hơn ngày tiếp nhận",
//                "Toast không hiển thị hoặc sai nội dung.");
//    }

    @Test(priority = 3)
    public void testCreateAllocation_ThanhCong(){
        reportWeb.setNgayBaoCaoInput(MyUtil.getNgaychungtu());
        reportWeb.chonPhongBaoSuCoInput();
        reportWeb.chonPhongTiepNhanInput();
        reportWeb.chonNguoiBaoSuCoInput();
        ds.chonTSCD();
        reportWeb.setLuu_btn();
        String toastText = main_tainPageWeb.getToastMessageText();

        //Kiêm tra hiển thị thông báo
        Assert.assertEquals(toastText, "Thêm mới thành công",
                "Toast không hiển thị hoặc sai nội dung.");
    }
}
