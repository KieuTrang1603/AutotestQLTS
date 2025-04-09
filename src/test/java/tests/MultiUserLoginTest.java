package tests;

import base.BaseTestWeb;
import drivers.DriverManager;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pagesweb.HomePageWeb;
import pagesweb.LoginPageWeb;

import java.util.List;

public class MultiUserLoginTest extends BaseTestWeb {
    @DataProvider(name = "userData", parallel = true)
    public Object[][] getUsers() {
        return new Object[][] {
                {"bvdka", "123456", expectedMenusORG},
                {"pvt1", "123456", expectedMenusAM},
                {"audemo", "123123", expectedMenusAU},
                {"userkn", "123456", expectedMenusUser}
        };
    }

    @Test(dataProvider = "userData")
    public void testMultipleUsersLogin(String username, String password, List<String> expectedMenus) {
        LoginPageWeb loginPageWeb = new LoginPageWeb(DriverManager.getWebDriver());
        HomePageWeb homePageWeb = new HomePageWeb(DriverManager.getWebDriver());

        // Thực hiện đăng nhập
        loginPageWeb.login(username, password);

        /// Kiểm tra xem đã chuyển sang trang khác sau khi đăng nhập chưa
        Assert.assertFalse(loginPageWeb.isLoginPageDisplayed(), "Đăng nhập " + username +" không thành công, vẫn ở trang đăng nhập");

        // Kiểm tra menu có hiển thị đúng với vai trò user
        Assert.assertTrue(homePageWeb.isMenuDisplayedCorrectly(expectedMenus),
                "User " + username + " có menu hiển thị không đúng!");
    }
}
