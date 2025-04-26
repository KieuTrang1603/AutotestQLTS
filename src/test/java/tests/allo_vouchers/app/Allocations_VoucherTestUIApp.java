package tests.allo_vouchers.app;

import base.BaseTestApp;
import drivers.DriverManager;
import helpers.AllocationHelper;
import model.Allocation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pagesapp.All_VoucherPageApp;
import pagesapp.HomePageApp;
import pagesapp.LoginPageApp;
import pagesweb.All_VoucherPageWeb;

import java.util.List;

public class Allocations_VoucherTestUIApp extends BaseTestApp {
    LoginPageApp loginPageapp;
    HomePageApp homePageApp;
    All_VoucherPageApp all_app;
    All_VoucherPageWeb all_web;

    @BeforeMethod
    public void creatsetup(){
        WebDriver driver;
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://asvn.oceantech.com.vn/session/signin");
        DriverManager.setWebDriver(driver);
        loginPageapp = new LoginPageApp(DriverManager.getAppiumDriver());
        homePageApp = new HomePageApp(DriverManager.getAppiumDriver());
    }

    @Test
    public void soSanhCapPhatGiuaAppVaWebORG() {
        loginPageapp.login("bvdka", "123456");
        homePageApp.navigationtoAllocation();
        all_app = new All_VoucherPageApp(DriverManager.getAppiumDriver());
        all_web = new All_VoucherPageWeb(DriverManager.getWebDriver());

        // Lấy danh sách trên mobile
        List<String> rawMobile;
        rawMobile = all_app.layDanhSachContentDesc();

        // Lấy danh sách trên web
        all_web.navigateToAllocation_VoucherPage("bvdka","123456");
        all_web.closeMenu();
        List<Allocation> webRecords = all_web.getAllocationRecord();
        Assert.assertTrue(AllocationHelper.soSanhCapPhatGiuaAppVaWeb(rawMobile,webRecords), "Không khớp bản ghi trên App ");
        all_app.returnHomeApp();
        homePageApp.setLogOutORG();
    }

    @Test
    public void soSanhCapPhatGiuaAppVaWebAM() {
        loginPageapp.login("pvt1", "123456");
        homePageApp.navigationtoAllocation();
        all_app = new All_VoucherPageApp(DriverManager.getAppiumDriver());
        all_web = new All_VoucherPageWeb(DriverManager.getWebDriver());

        // Lấy danh sách trên mobile
        List<String> rawMobile;
        rawMobile = all_app.layDanhSachContentDesc();

        // Lấy danh sách trên web
        all_web.navigateToAllocation_VoucherPage("pvt1","123456");
        all_web.closeMenu();
        List<Allocation> webRecords = all_web.getAllocationRecord();
        Assert.assertTrue(AllocationHelper.soSanhCapPhatGiuaAppVaWeb(rawMobile,webRecords), "Không khớp bản ghi trên App ");
        all_app.returnHomeApp();
        homePageApp.setLogOutAM();
    }

    @Test
    public void soSanhCapPhatGiuaAppVaWebAU() {
        loginPageapp.login("audemo", "123123");
        homePageApp.navigationtoAllocation();
        all_app = new All_VoucherPageApp(DriverManager.getAppiumDriver());
        all_web = new All_VoucherPageWeb(DriverManager.getWebDriver());

        // Lấy danh sách trên mobile
        List<String> rawMobile;
        rawMobile = all_app.layDanhSachContentDesc();

        // Lấy danh sách trên web
        all_web.navigateToAllocation_VoucherPage("audemo","123123");
        all_web.closeMenu();
        List<Allocation> webRecords = all_web.getAllocationRecord();
        Assert.assertTrue(AllocationHelper.soSanhCapPhatGiuaAppVaWeb(rawMobile,webRecords), "Không khớp bản ghi trên App ");
        all_app.returnHomeApp();
        homePageApp.setLogOutAU();
    }

    @AfterMethod
    public void tearDown() {
        try {
            WebDriver driver = DriverManager.getWebDriver();
            if (DriverManager.getWebDriver() != null) {
                DriverManager.quitWebDriver();
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi đóng WebDriver: " + e.getMessage());
        }
    }
}
