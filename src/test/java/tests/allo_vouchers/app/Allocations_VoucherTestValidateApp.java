package tests.allo_vouchers.app;

import base.BaseTestApp;
import drivers.DriverManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pagesapp.All_VoucherCreatePageApp;
import pagesapp.All_VoucherPageApp;
import pagesapp.HomePageApp;
import pagesapp.LoginPageApp;
import pagesweb.All_VoucherCreatePageWeb;
import pagesweb.All_VoucherPageWeb;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Allocations_VoucherTestValidateApp extends BaseTestApp {
    All_VoucherPageApp all_vou;
    LoginPageApp loginPageapp;
    HomePageApp homePageApp;
    @BeforeClass
    public void prepareVoucherCreatePage(){
        loginPageapp = new LoginPageApp(DriverManager.getAppiumDriver());
        homePageApp = new HomePageApp(DriverManager.getAppiumDriver());
        all_vou = new All_VoucherPageApp(DriverManager.getAppiumDriver());
        loginPageapp.login("pvt1", "123456");
        homePageApp.navigationtoAllocation();
        all_vou.clickThemmoi();
    }
    @Test(priority = 1)
    public void testNgayTaoPhieu_ShouldBeToday_AndReadonly() {
        All_VoucherCreatePageApp all = new All_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        // 1. Kiểm tra giá trị là ngày hôm nay
        String expectedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String actualDate = all.getNgayTaoPhieuValue();
        Assert.assertEquals(actualDate, expectedDate, "Ngày tạo phiếu không phải là ngày hiện tại!");
        // 2. Kiểm tra không chỉnh sửa được
        boolean isReadonly = all.isNgayTaoPhieuReadonly();
        Assert.assertTrue(isReadonly, "Trường 'Ngày tạo phiếu' không bị readonly!");
    }

    @Test(priority = 2)
    public void testNgayChungTu_ShouldBeToday_AndEdit() {
        All_VoucherCreatePageApp all = new All_VoucherCreatePageApp(DriverManager.getAppiumDriver());
        // 1. Kiểm tra giá trị là ngày hôm nay
        String expectedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String actualDate = all.getNgayTaoPhieuValue();
        Assert.assertEquals(actualDate, expectedDate, "Ngày chứng từ không khớp với ngày hiện tại!");
        // 2. Kiểm tra chỉnh sửa được
        boolean isEdit = all.isNgayChungTuEdit();
        Assert.assertTrue(isEdit, "Trường 'Ngày chứng từ' không được chỉnh sửa!");
    }
}
