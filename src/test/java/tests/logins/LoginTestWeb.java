package tests.logins;


import base.BaseTestWeb;
import drivers.DriverManager;
import model.HomeMenu;
import model.User;
import model.UsersRole;
import model.enums.PlatformType;
import model.enums.UserRole;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import pagesweb.HomePageWeb;
import pagesweb.LoginPageWeb;

import java.net.URI;

public class LoginTestWeb extends BaseTestWeb {
    private LoginPageWeb loginPageWeb;
    private HomePageWeb homePageWeb;

    @Test
    public void testSuccessfulLoginORG() {
        loginPageWeb = new LoginPageWeb(DriverManager.getWebDriver());
        homePageWeb = new HomePageWeb(DriverManager.getWebDriver());
        // Điều hướng đến trang đăng nhập
        loginPageWeb.navigateToLoginPage();
        UsersRole Users = null;
        User user = Users.getUserByRole("ORG");
        // Thực hiện đăng nhập với thông tin hợp lệ
        loginPageWeb.login(user.getUsername(),user.getPassword());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(homePageWeb.isMenuDisplayedCorrectly(HomeMenu.getExpectedMenus(UserRole.ORG, PlatformType.WEB)), "Không hiển thị đúng menu role ORG");
        // Kiểm tra xem đã chuyển sang trang khác sau khi đăng nhập chưa
        Assert.assertFalse(loginPageWeb.isLoginPageDisplayed(), "Đăng nhập không thành công, vẫn ở trang đăng nhập");
    }

    @Test
    public void testSuccessfulLoginAM() {
        loginPageWeb = new LoginPageWeb(DriverManager.getWebDriver());
        homePageWeb = new HomePageWeb(DriverManager.getWebDriver());
        // Điều hướng đến trang đăng nhập
        loginPageWeb.navigateToLoginPage();
        UsersRole Users = null;
        User user = Users.getUserByRole("AM");
        // Thực hiện đăng nhập với thông tin hợp lệ
        loginPageWeb.login(user.getUsername(),user.getPassword());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(homePageWeb.isMenuDisplayedCorrectly(HomeMenu.getExpectedMenus(UserRole.AM, PlatformType.WEB)), "Không hiển thị đúng menu role ORG");
        // Kiểm tra xem đã chuyển sang trang khác sau khi đăng nhập chưa
        Assert.assertFalse(loginPageWeb.isLoginPageDisplayed(), "Đăng nhập không thành công, vẫn ở trang đăng nhập");
    }

    @Test
    public void testSuccessfulLoginAU() {
        loginPageWeb = new LoginPageWeb(DriverManager.getWebDriver());
        homePageWeb = new HomePageWeb(DriverManager.getWebDriver());
        // Điều hướng đến trang đăng nhập
        loginPageWeb.navigateToLoginPage();
        UsersRole Users = null;
        User user = Users.getUserByRole("AU");
        // Thực hiện đăng nhập với thông tin hợp lệ
        loginPageWeb.login(user.getUsername(),user.getPassword());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(homePageWeb.isMenuDisplayedCorrectly(HomeMenu.getExpectedMenus(UserRole.AU, PlatformType.WEB)),
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
        UsersRole Users = null;
        User user = Users.getUserByRole("USER");
        // Thực hiện đăng nhập với thông tin hợp lệ
        loginPageWeb.login(user.getUsername(),user.getPassword());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(homePageWeb.isMenuDisplayedCorrectly(HomeMenu.getExpectedMenus(UserRole.USER, PlatformType.WEB)),
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
                "Thông báo lỗi không hiển thị!");

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
                "Thông báo lỗi không hiển thị!");

        // Kiểm tra hiển thị dưới thông báo
        Assert.assertTrue(loginPageWeb.emptypassword(),
                "Thông báo lỗi không nằm ngay bên dưới ô mật khẩu!");

        // Kiểm tra xem vẫn còn ở trang đăng nhập
        Assert.assertTrue(loginPageWeb.isLoginPageDisplayed(),
                "Không ở trang đăng nhập sau khi đăng nhập không hợp lệ");
    }

    //TH đăng nhập với tài khoản chưa kích hoạt
    @Test
    public void testInvalidLogin_accountnotkichhoat() {
        loginPageWeb = new LoginPageWeb(DriverManager.getWebDriver());
        // Điều hướng đến trang đăng nhập
        loginPageWeb.navigateToLoginPage();

        // Thực hiện đăng nhập với thông tin không hợp lệ
        loginPageWeb.login("kieutrangtestCKH", "123456");

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
    public void test1(){
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("http://qltsdemo.xhis.vn/Home/Login"))
//                .POST(java.net.http.HttpRequest.BodyPublishers.ofString("UserName=admin&Password=123456"))
//                .header("Content-Type", "application/x-www-form-urlencoded")
//                .build();
//
//        HttpResponse<String> response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.statusCode());
//        System.out.println(response.body());
    }
}