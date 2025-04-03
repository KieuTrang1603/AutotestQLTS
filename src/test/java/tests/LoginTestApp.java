package tests;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePageApp;
import pages.LoginPageApp;
import base.BaseTestApp;

public class LoginTestApp extends BaseTestApp {

    @Test
    public void testSuccessfulLoginORG() {
        LoginPageApp loginPageapp = new LoginPageApp(driver);
        HomePageApp homePageApp = new HomePageApp(driver);
        loginPageapp.login("bvdka", "123456");
        if (homePageApp.isMenuDisplayedCorrectly(expectedMenusORG) && loginPageapp.isLoginSuccessful()) {
            System.out.println("Đăng nhập thành công!");
        } else {
            System.out.println("Đăng nhập thất bại!");
//            Assert.assertFalse(loginPageapp.getErrorMessage().isEmpty(), "Lỗi hiển thị không mong đợi!");
        }
        homePageApp.setLogOut();
    }

    @Test
    public void testSuccessfulLoginAM() {
        LoginPageApp loginPageapp = new LoginPageApp(driver);
        HomePageApp homePageApp = new HomePageApp(driver);
        loginPageapp.login("pvt1", "123456");
        if (homePageApp.isMenuDisplayedCorrectly(expectedMenusAM)) {
            System.out.println("Đăng nhập thành công!");
        } else {
            System.out.println("Đăng nhập thất bại!");
//            Assert.assertFalse(loginPageapp.getErrorMessage().isEmpty(), "Lỗi hiển thị không mong đợi!");
        }
        homePageApp.setLogOut();
    }

    @Test
    public void testSuccessfulLoginAU() {
        LoginPageApp loginPageapp = new LoginPageApp(driver);
        HomePageApp homePageApp = new HomePageApp(driver);
        loginPageapp.login("audemo", "123123");
        if (homePageApp.isMenuDisplayedCorrectly(expectedMenusAU)) {
            System.out.println("Đăng nhập thành công!");
        } else {
            System.out.println("Đăng nhập thất bại!");
//            Assert.assertFalse(loginPageapp.getErrorMessage().isEmpty(), "Lỗi hiển thị không mong đợi!");
        }
        homePageApp.setLogOut();
    }

    @Test
    public void testSuccessfulLoginUser() {
        LoginPageApp loginPageapp = new LoginPageApp(driver);
        HomePageApp homePageApp = new HomePageApp(driver);
        loginPageapp.login("userkn", "123456");
        if (homePageApp.isMenuDisplayedCorrectly(expectedMenusUser)) {
            System.out.println("Đăng nhập thành công!");
        } else {
            System.out.println("Đăng nhập thất bại!");
//            Assert.assertFalse(loginPageapp.getErrorMessage().isEmpty(), "Lỗi hiển thị không mong đợi!");
        }
        homePageApp.setLogOut();
    }

//    @Test
//    public void testInvalidLogin_incorrectaccount() {
//        LoginPageApp loginPageapp = new LoginPageApp(driver);
//        loginPageapp.login("pvvt1", "123456");
//
//        // Kiểm tra có thông báo lỗi hay không
////        Assert.assertTrue(loginPageapp.getErrorMessage().contains("Tài khoản mật khẩu không chính xác"), "Không có thông báo lỗi!");
//        // Kiểm tra lỗi có xuất hiện không
//        if (loginPageapp.isErrorMessageDisplayed()) {
//            String errorText = "Tài khoản mật khẩu không chính xác";
//            System.out.println("Thông báo lỗi hiển thị: " + errorText);
//
//            // Kiểm tra xem thông báo lỗi có đúng không
//            Assert.assertEquals(errorText, "Hiển thị sai");
//        } else {
//            System.out.println("Không thấy thông báo lỗi!");
//            Assert.fail("Thông báo lỗi không xuất hiện");
//        }
//    }

//    @Test
//    public void testInvalidLogin_emptyaccount() {
//        LoginPageApp loginPageapp = new LoginPageApp(driver);
//        loginPageapp.login("", "123456");
//
//        // Kiểm tra có thông báo lỗi hay không
////        Assert.assertTrue(loginPageapp.getErrorMessage().contains("Tài khoản mật khẩu không chính xác"), "Không có thông báo lỗi!");
//        // Kiểm tra lỗi có xuất hiện không
//        if (loginPageapp.isErrorMessageDisplayed()) {
//            String errorText = "Bạn cần nhập đầy đủ tài khoản mật khẩu";
//            System.out.println("Thông báo lỗi hiển thị: " + errorText);
//
//            // Kiểm tra xem thông báo lỗi có đúng không
//            Assert.assertEquals(errorText, "Hiển thị sai");
//        } else {
//            System.out.println("Không thấy thông báo lỗi!");
//            Assert.fail("Thông báo lỗi không xuất hiện");
//        }
//    }
}
