package tests;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPageApp;
import base.BaseTestApp;

public class LoginTestApp extends BaseTestApp {
    @Test
    public void testSuccessfulLogin() {
        LoginPageApp loginPageapp = new LoginPageApp(driver);
        loginPageapp.login("pvt1", "123456");
        if (loginPageapp.isLoginSuccessful()) {
            System.out.println("Đăng nhập thành công!");
        } else {
            System.out.println("Đăng nhập thất bại!");
        }

        // Kiểm tra xem có lỗi không (giả sử nếu login thành công, errorMessage sẽ không xuất hiện)
//        try {
//            Assert.assertFalse(loginPageapp.getErrorMessage().isEmpty(), "Lỗi hiển thị không mong đợi!");
//        } catch (Exception e) {
//            // Không có lỗi -> Đăng nhập thành công
//        }
    }

    @Test
    public void testInvalidLogin() {
        LoginPageApp loginPageapp = new LoginPageApp(driver);
        loginPageapp.login("wronguser", "wrongpassword");

        // Kiểm tra có thông báo lỗi hay không
//        Assert.assertTrue(loginPageapp.getErrorMessage().contains("Tài khoản mật khẩu không chính xác"), "Không có thông báo lỗi!");
        // Kiểm tra lỗi có xuất hiện không
        if (loginPageapp.isErrorMessageDisplayed()) {
            String errorText = "Tài khoản mật khẩu không chính xác";
            System.out.println("Thông báo lỗi hiển thị: " + errorText);

            // Kiểm tra xem thông báo lỗi có đúng không
            Assert.assertEquals(errorText, "Hiển thị sai");
        } else {
            System.out.println("Không thấy thông báo lỗi!");
            Assert.fail("Thông báo lỗi không xuất hiện");
        }
    }
}
