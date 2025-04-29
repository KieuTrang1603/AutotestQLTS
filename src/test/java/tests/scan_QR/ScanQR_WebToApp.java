package tests.scan_QR;

import base.BaseTestApp;
import drivers.DriverManager;
import model.User;
import model.UsersRole;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pagesapp.All_VoucherPageApp;
import pagesapp.HomePageApp;
import pagesapp.LoginPageApp;
import pagesapp.ScanQR;
import pagesweb.All_VoucherPageWeb;
import pagesweb.HomePageWeb;
import pagesweb.LoginPageWeb;
import utils.SnackbarScreenshot;

public class ScanQR_WebToApp extends BaseTestApp {
    LoginPageApp loginPageapp;
    HomePageApp homePageApp;
    LoginPageWeb loginPageWeb;
    HomePageWeb homePageWeb;
    UsersRole usersRole;
    ScanQR scanQR;

    @BeforeMethod
    public void creatsetup(){
        WebDriver driver;
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://asvn.oceantech.com.vn/session/signin");
        DriverManager.setWebDriver(driver);
        loginPageapp = new LoginPageApp(DriverManager.getAppiumDriver());
        homePageApp = new HomePageApp(DriverManager.getAppiumDriver());
        loginPageWeb = new LoginPageWeb(DriverManager.getWebDriver());
        homePageWeb = new HomePageWeb(DriverManager.getWebDriver());
        scanQR = new ScanQR(DriverManager.getAppiumDriver());
    }
    @Test
    public void scanQR_Incorrect(){
        User user = usersRole.getUserByRole("AM");
        loginPageapp.login(user.getUsername(),user.getPassword());
        homePageApp.navigationtoScanQR();
        scanQR.Chupmanhinh();
        SnackbarScreenshot snackbar = new SnackbarScreenshot(DriverManager.getAppiumDriver());
        Assert.assertTrue(snackbar.verifySnackbarContainsText("Không thể xác định tài sản"),
                "Thông báo lỗi hiển thị sai!");
    }

    @Test
    public void scanQR_Correct(){
        User user = usersRole.getUserByRole("AM");
        loginPageWeb.login(user.getUsername(), user.getPassword());

        loginPageapp.login(user.getUsername(),user.getPassword());

    }
}
