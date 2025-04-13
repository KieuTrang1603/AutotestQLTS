package tests.logintests;


import base.BaseTestWeb;
import drivers.DriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pagesweb.HomePageWeb;
import pagesweb.LoginPageWeb;
import utils.MyUtil;

public class LoginTestWeb extends BaseTestWeb {
    private LoginPageWeb loginPageWeb;
    private HomePageWeb homePageWeb;

    @Test
    public void testSuccessfulLoginORG() {
        loginPageWeb = new LoginPageWeb(DriverManager.getWebDriver());
        homePageWeb = new HomePageWeb(DriverManager.getWebDriver());
        // Điều hướng đến trang đăng nhập
        loginPageWeb.navigateToLoginPage();

        // Thực hiện đăng nhập với thông tin hợp lệ
        loginPageWeb.login("bvdka", "123456");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(homePageWeb.isMenuDisplayedCorrectly(MyUtil.getExpectedMenusORG()), "Không hiển thị đúng menu role ORG");
        // Kiểm tra xem đã chuyển sang trang khác sau khi đăng nhập chưa
        Assert.assertFalse(loginPageWeb.isLoginPageDisplayed(), "Đăng nhập không thành công, vẫn ở trang đăng nhập");
    }

    @Test
    public void testSuccessfulLoginAM() {
        loginPageWeb = new LoginPageWeb(DriverManager.getWebDriver());
        homePageWeb = new HomePageWeb(DriverManager.getWebDriver());
        // Điều hướng đến trang đăng nhập
        loginPageWeb.navigateToLoginPage();

        // Thực hiện đăng nhập với thông tin hợp lệ
        loginPageWeb.login("pvt1", "123456");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(homePageWeb.isMenuDisplayedCorrectly(MyUtil.getExpectedMenusAM()), "Không hiển thị đúng menu role ORG");
        // Kiểm tra xem đã chuyển sang trang khác sau khi đăng nhập chưa
        Assert.assertFalse(loginPageWeb.isLoginPageDisplayed(), "Đăng nhập không thành công, vẫn ở trang đăng nhập");
    }

    @Test
    public void testSuccessfulLoginAU() {
        loginPageWeb = new LoginPageWeb(DriverManager.getWebDriver());
        homePageWeb = new HomePageWeb(DriverManager.getWebDriver());
        // Điều hướng đến trang đăng nhập
        loginPageWeb.navigateToLoginPage();

        // Thực hiện đăng nhập với thông tin hợp lệ
        loginPageWeb.login("audemo", "123123");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(homePageWeb.isMenuDisplayedCorrectly(MyUtil.getExpectedMenusAU()),
                "Không hiển thị đúng menu role ORG");
        // Kiểm tra xem đã chuyển sang trang khác sau khi đăng nhập chưa
        Assert.assertFalse(loginPageWeb.isLoginPageDisplayed(),
                "Đăng nhập không thành công, vẫn ở trang đăng nhập");
    }

    @Test
    public void testSuccessfulLoginUser() {
        loginPageWeb = new LoginPageWeb(DriverManager.getWebDriver());
        homePageWeb = new HomePageWeb(DriverManager.getWebDriver());
        // Điều hướng đến trang đăng nhập
        loginPageWeb.navigateToLoginPage();

        // Thực hiện đăng nhập với thông tin hợp lệ
        loginPageWeb.login("userkn", "123456");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(homePageWeb.isMenuDisplayedCorrectly(MyUtil.getExpectedMenusUser()),
                "Không hiển thị đúng menu role ORG");
        // Kiểm tra xem đã chuyển sang trang khác sau khi đăng nhập chưa
        Assert.assertFalse(loginPageWeb.isLoginPageDisplayed(),
                "Đăng nhập không thành công, vẫn ở trang đăng nhập");
    }

    @Test
    public void testInvalidLogin_incorrectaccount() {
        loginPageWeb = new LoginPageWeb(DriverManager.getWebDriver());
        // Điều hướng đến trang đăng nhập
        loginPageWeb.navigateToLoginPage();

        // Thực hiện đăng nhập với thông tin không hợp lệ
        loginPageWeb.login("pvtu1", "123456");

        // Đợi alert xuất hiện
        Assert.assertTrue(loginPageWeb.waitForAlert(5),
                "Alert không xuất hiện sau khi đăng nhập không hợp lệ");

        // Kiểm tra nội dung alert
        String alertText = loginPageWeb.getAlertText();
        Assert.assertTrue(alertText.contains("Tài khoản hoặc mật khẩu không đúng"),
                "Nội dung alert không chứa thông báo lỗi mong đợi");

        // Chấp nhận alert
        loginPageWeb.acceptAlert();

        // Kiểm tra xem vẫn còn ở trang đăng nhập
        Assert.assertTrue(loginPageWeb.isLoginPageDisplayed(),
                "Không ở trang đăng nhập sau khi đăng nhập không hợp lệ");
    }

    @Test
    public void testInvalidLogin_incorrectpassword() {
        loginPageWeb = new LoginPageWeb(DriverManager.getWebDriver());
        // Điều hướng đến trang đăng nhập
        loginPageWeb.navigateToLoginPage();

        // Thực hiện đăng nhập với thông tin không hợp lệ
        loginPageWeb.login("pvt1", "1234567");

        // Đợi alert xuất hiện
        Assert.assertTrue(loginPageWeb.waitForAlert(5),
                "Alert không xuất hiện sau khi đăng nhập không hợp lệ");

        // Kiểm tra nội dung alert
        String alertText = loginPageWeb.getAlertText();
        Assert.assertTrue(alertText.contains("Tài khoản hoặc mật khẩu không đúng"),
                "Nội dung alert không chứa thông báo lỗi mong đợi");

        // Chấp nhận alert
        loginPageWeb.acceptAlert();

        // Kiểm tra xem vẫn còn ở trang đăng nhập
        Assert.assertTrue(loginPageWeb.isLoginPageDisplayed(),
                "Không ở trang đăng nhập sau khi đăng nhập không hợp lệ");

    }

    @Test
    public void testInvalidLogin_emptyaccount() {
        loginPageWeb = new LoginPageWeb(DriverManager.getWebDriver());
        // Điều hướng đến trang đăng nhập
        loginPageWeb.navigateToLoginPage();

        // Thực hiện đăng nhập với thông tin không hợp lệ
        loginPageWeb.login("", "1234567");
        // Kiểm tra xem thông báo lỗi có hiển thị không
        Assert.assertTrue(loginPageWeb.isErrorMessageDisplayed(),
                "Thông báo lỗi không hiển thị dưới ô tài khoản!");

        // Kiểm tra hiển thị dưới thông báo
        Assert.assertTrue(loginPageWeb.emptyusername(),
                "Thông báo lỗi không nằm ngay bên dưới ô tài khoản!");

        // Kiểm tra xem vẫn còn ở trang đăng nhập
        Assert.assertTrue(loginPageWeb.isLoginPageDisplayed(),
                "Không ở trang đăng nhập sau khi đăng nhập không hợp lệ");

    }

    @Test
    public void testInvalidLogin_emptypassword() {
        loginPageWeb = new LoginPageWeb(DriverManager.getWebDriver());
        // Điều hướng đến trang đăng nhập
        loginPageWeb.navigateToLoginPage();

        // Thực hiện đăng nhập với thông tin không hợp lệ
        loginPageWeb.login("pvt1", "");
        // Kiểm tra xem thông báo lỗi có hiển thị không
        Assert.assertTrue(loginPageWeb.isErrorMessageDisplayed(),
                "Thông báo lỗi không hiển thị dưới ô mật khẩu!");

        // Kiểm tra hiển thị dưới thông báo
        Assert.assertTrue(loginPageWeb.emptypassword(),
                "Thông báo lỗi không nằm ngay bên dưới ô mật khẩu!");

        // Kiểm tra xem vẫn còn ở trang đăng nhập
        Assert.assertTrue(loginPageWeb.isLoginPageDisplayed(),
                "Không ở trang đăng nhập sau khi đăng nhập không hợp lệ");
    }
}