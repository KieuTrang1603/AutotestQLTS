package tests.logins;

import base.BaseMultiTestWeb;
import drivers.DriverManager;
import model.User;
import model.UsersRole;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pagesweb.LoginPageWeb;

public class LoginPerformanceTest extends BaseMultiTestWeb {
    UsersRole Users;
    User user1 = Users.getUserByRole("ORG");
    User user2 = Users.getUserByRole("AM");
    User user3 = Users.getUserByRole("AU");
    User user4 = Users.getUserByRole("USER");

    @DataProvider(name = "userData", parallel = true)
    public Object[][] getUsers() {
        return new Object[][] {
                {user1.getUsername(), user1.getPassword()},
                {user2.getUsername(), user2.getPassword()},
                {user3.getUsername(), user3.getPassword()},
                {user4.getUsername(), user4.getPassword()}
        };
    }

    /**
     * Đo thời gian đăng nhập cho từng user, kiểm tra có dưới 5 giây không.
     */
    @Test(dataProvider = "userData", description = "Hiệu năng - Thời gian đăng nhập cho từng vai trò")
    public void testLoginPerformance(String username, String password) {
        LoginPageWeb loginPageWeb = new LoginPageWeb(DriverManager.getWebDriver());

        loginPageWeb.navigateToLoginPage();

        long startTime = System.currentTimeMillis();
        loginPageWeb.login(username, password);
        long duration = System.currentTimeMillis() - startTime;

        System.out.println("Thời gian đăng nhập cho user " + username + ": " + duration + "ms");

        Assert.assertTrue(duration < 5000,
                "Thời gian đăng nhập vượt quá giới hạn cho phép: " + duration + "ms");
    }

    /**
     * Đăng nhập 5 lần để đo thời gian trung bình.
     */
    @Test(description = "Hiệu năng - Đăng nhập nhiều lần để đo thời gian trung bình")
    public void testAverageLoginTimeForORG() {
        LoginPageWeb loginPageWeb = new LoginPageWeb(DriverManager.getWebDriver());
        User user = Users.getUserByRole("ORG");

        int iterations = 5;
        long totalDuration = 0;

        for (int i = 0; i < iterations; i++) {
            loginPageWeb.navigateToLoginPage();

            long start = System.currentTimeMillis();
            loginPageWeb.login(user.getUsername(), user.getPassword());
            long end = System.currentTimeMillis();

            long duration = end - start;
            totalDuration += duration;

            System.out.println("Lần đăng nhập " + (i + 1) + ": " + duration + "ms");

            DriverManager.getWebDriver().navigate().back(); // Quay lại trang login
        }

        long average = totalDuration / iterations;
        System.out.println("Thời gian trung bình đăng nhập (ORG): " + average + "ms");

        Assert.assertTrue(average < 5000, "Thời gian trung bình vượt giới hạn cho phép!");
    }
}
