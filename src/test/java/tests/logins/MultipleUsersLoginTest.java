package tests.logins;

import base.BaseMultiTestWeb;
import drivers.DriverManager;
import model.HomeMenu;
import model.User;
import model.UsersRole;
import model.enums.PlatformType;
import model.enums.UserRole;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pagesweb.HomePageWeb;
import pagesweb.LoginPageWeb;

import java.util.List;

public class MultipleUsersLoginTest extends BaseMultiTestWeb {
    private UsersRole Users;
    User user1 = Users.getUserByRole("ORG");
    User user2 = Users.getUserByRole("AM");
    User user3 = Users.getUserByRole("AU");
    User user4 = Users.getUserByRole("USER");
    @DataProvider(name = "userData", parallel = true)
    public Object[][] getUsers() {
        return new Object[][] {
                {user1.getUsername(), user1.getPassword(), HomeMenu.getExpectedMenus(UserRole.ORG, PlatformType.WEB)},
                {user2.getUsername(), user2.getPassword(), HomeMenu.getExpectedMenus(UserRole.AM, PlatformType.WEB)},
                {user3.getUsername(), user3.getPassword(), HomeMenu.getExpectedMenus(UserRole.AU, PlatformType.WEB)},
                {user4.getUsername(), user4.getPassword(), HomeMenu.getExpectedMenus(UserRole.USER, PlatformType.WEB)}
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
