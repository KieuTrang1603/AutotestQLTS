package tests.main_tains;

import base.BaseTestApp;
import drivers.DriverManager;
import model.Department;
import model.User;
import model.UsersRole;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pagesapp.*;
import utils.DataBaseUtils;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main_tainReportTestValidateApp extends BaseTestApp {
    Main_tainReportPageApp reportPageapp;
    LoginPageApp loginPageapp;
    HomePageApp homePageApp;
    @BeforeClass
    public void prepareVoucherCreatePage(){
        loginPageapp = new LoginPageApp(DriverManager.getAppiumDriver());
        homePageApp = new HomePageApp(DriverManager.getAppiumDriver());
        reportPageapp = new Main_tainReportPageApp(DriverManager.getAppiumDriver());
        UsersRole Users = null;
        User user = Users.getUserByRole("AU");
        loginPageapp.login(user.getUsername(), user.getPassword());
        homePageApp.navigationtoMaintain();
    }
    @Test(priority = 1)
    public void testNgayBaoCao_ShouldBeToday_AndEdit() {
        // 1. Kiểm tra giá trị là ngày hôm nay
        String expectedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String actualDate = reportPageapp.getNgayBaoCaoValue();
        Assert.assertEquals(actualDate, expectedDate, "Ngày báo cáo không phải là ngày hiện tại!");
        // 2. Kiểm tra không chỉnh sửa được
        boolean isEdit = reportPageapp.isNgayBaoCaoEdit();
        Assert.assertTrue(isEdit, "Trường 'Ngày báo cáo' không được chỉnh sửa!");
    }

    @Test(priority = 2)
    public void testTrangThaiReadonly() {
        boolean able = reportPageapp.isTrangThaiAble();
        Assert.assertTrue(!able, "Trường 'Trạng thái' không disable");
    }

    @Test(priority = 3)
    public void testPhongBaoSuCoData() {
        Assert.assertTrue(reportPageapp.checkPhongBanBaoSuCo(Department.DEPARTMENT_NAME_AU1),
                "Trường 'Phòng ban bàn giao' không hiển thị đúng!");
        boolean able = reportPageapp.isPhongBanBaoSuCoAble();
        Assert.assertTrue(able, "Trường 'Phòng báo sự cố' bị disable");
    }

    @AfterMethod
    public void resetToAddNewScreen() {
        DriverManager.getAppiumDriver().navigate().back();
        // Nếu form thêm mới bị ẩn (không còn), thì click lại "Thêm mới" để reset màn hình
        if (!reportPageapp.isMainTainDialogDisplayed()) {
            homePageApp.navigationtoMaintain();
        }
    }
}
