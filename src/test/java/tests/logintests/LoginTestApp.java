package tests.logintests;
import drivers.DriverManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pagesapp.HomePageApp;
import pagesapp.LoginPageApp;
import base.BaseTestApp;
import utils.MyUtil;

public class LoginTestApp extends BaseTestApp {
    LoginPageApp loginPageapp;
    HomePageApp homePageApp;

    @BeforeClass
    public void creatsetup(){
        loginPageapp = new LoginPageApp(DriverManager.getAppiumDriver());
        homePageApp = new HomePageApp(DriverManager.getAppiumDriver());
    }
    @Test(priority = 8)
    public void testSuccessfulLoginORG() {
        loginPageapp.login("bvdka", "123456");
        if (homePageApp.isMenuDisplayedCorrectly(MyUtil.getExpectedMenusORGApp()) && loginPageapp.isLoginSuccessful()) {
            System.out.println("Đăng nhập thành công!");
        } else {
            System.out.println("Đăng nhập thất bại!");
//            Assert.assertFalse(loginPageapp.getErrorMessage().isEmpty(), "Lỗi hiển thị không mong đợi!");
        }
        homePageApp.setLogOutORG();
    }

    @Test(priority = 7)
    public void testSuccessfulLoginAM() {
        loginPageapp.login("pvt1", "123456");
        if (homePageApp.isMenuDisplayedCorrectly(MyUtil.getExpectedMenusAMApp())) {
            System.out.println("Đăng nhập thành công!");
        } else {
            System.out.println("Đăng nhập thất bại!");
//            Assert.assertFalse(loginPageapp.getErrorMessage().isEmpty(), "Lỗi hiển thị không mong đợi!");
        }

        homePageApp.setLogOutAM();
    }

    @Test(priority = 6)
    public void testSuccessfulLoginAU() {
        loginPageapp.login("audemo", "123123");
        if (homePageApp.isMenuDisplayedCorrectly(MyUtil.getExpectedMenusAUApp())) {
            System.out.println("Đăng nhập thành công!");
        } else {
            System.out.println("Đăng nhập thất bại!");
//            Assert.assertFalse(loginPageapp.getErrorMessage().isEmpty(), "Lỗi hiển thị không mong đợi!");
        }
        homePageApp.setLogOutAU();
    }

    @Test(priority = 5)
    public void testSuccessfulLoginUser() {
        loginPageapp.login("userkn", "123456");
        if (homePageApp.isMenuDisplayedCorrectly(MyUtil.getExpectedMenusUserApp())) {
            System.out.println("Đăng nhập thành công!");
        } else {
            System.out.println("Đăng nhập thất bại!");
//            Assert.assertFalse(loginPageapp.getErrorMessage().isEmpty(), "Lỗi hiển thị không mong đợi!");
        }
        homePageApp.setLogOutAU();
    }

    @Test(priority = 3)
    public void testInvalidLogin_incorrectaccount() {
        loginPageapp.login("pvvt1", "123456");
        // Kiểm tra vẫn ở màn đăng nhap khong
        Assert.assertTrue(loginPageapp.isLoginPageDisplayed(), "Không còn ở màn đăng nhập");
    }

    @Test(priority = 4)
    public void testInvalidLogin_incorrectpassword() {
        loginPageapp.login("pvt1", "1234567");
        // Kiểm tra vẫn ở màn đăng nhap khong
        Assert.assertTrue(loginPageapp.isLoginPageDisplayed(), "Không còn ở màn đăng nhập");
    }

    @Test(priority = 1)
    public void testInvalidLogin_emptyaccount() {
        loginPageapp.login("", "123456");
        // Kiểm tra vẫn ở màn đăng nhap khong
        Assert.assertTrue(loginPageapp.isLoginPageDisplayed(), "Không còn ở màn đăng nhập");
    }

    @Test(priority = 2)
    public void testInvalidLogin_emptypassword() {
        loginPageapp.login("pvt1", "");
        // Kiểm tra vẫn ở màn đăng nhap khong
        Assert.assertTrue(loginPageapp.isLoginPageDisplayed(), "Không còn ở màn đăng nhập");
    }
}
