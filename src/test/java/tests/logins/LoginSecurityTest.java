package tests.logins;

import base.BaseTestWeb;
import drivers.DriverManager;
import model.User;
import model.UsersRole;
import org.apache.commons.lang3.StringEscapeUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pagesweb.HomePageWeb;
import pagesweb.LoginPageWeb;
import utils.DataBaseUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LoginSecurityTest extends BaseTestWeb {
    private LoginPageWeb loginPageWeb;
    private HomePageWeb homePageWeb;
    UsersRole Users = null;
    User user = Users.getUserByRole("AM");
    @BeforeMethod
    public void setupTest() {
        loginPageWeb = new LoginPageWeb(DriverManager.getWebDriver());
        homePageWeb = new HomePageWeb(DriverManager.getWebDriver());
        loginPageWeb.navigateToLoginPage();
    }

    @Test(description = "Test SQL Injection vulnerability")
    public void testTraditionalSqlInjectionLogic() {
        // Common SQL injection payloads
        String[] sqlInjections = {
                "' OR '1'='1",
                "' OR ''='"
        };

        for (String injection : sqlInjections) {
            loginPageWeb.navigateToLoginPage();
            loginPageWeb.attemptSqlInjection(injection);

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
                    "SQL Injection might be successful with: " + injection);
        }
    }

    @Test(description = "Test SQL Injection vulnerability")
    public void testTraditionalSqlInjectionComment() {
        // Common SQL injection payloads
        String[] sqlInjections = {
                "admin' --",
                "admin' OR 1=1--",
                "' UNION SELECT 1,username,password FROM tbl_user where org_id = " + DataBaseUtils.ORG_ID + "--",
                "' OR 1=1 LIMIT 1; --"
        };

        for (String injection : sqlInjections) {
            loginPageWeb.navigateToLoginPage();
            loginPageWeb.attemptSqlInjection(injection);

            // Verify that SQL injection doesn't result in successful login
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
                    "SQL Injection might be successful with: " + injection);
        }
    }

    @Test(description = "Test SQL Injection vulnerability")
    public void testTraditionalSqlInjectionCommentDelete() throws Exception {
        // Common SQL injection payloads
        String[] sqlInjections = {
                "admin'; DELETE FROM tbl_user; --"
        };

        for (String injection : sqlInjections) {
            loginPageWeb.navigateToLoginPage();
            loginPageWeb.attemptSqlInjection(injection);

            // Verify that SQL injection doesn't result in successful login
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
                    "SQL Injection might be successful with: " + injection);

            // Kiểm lại bằng JDBC xem tài khoản còn không
            boolean isDeleted = DataBaseUtils.isUserExist();

            Assert.assertTrue(isDeleted, "Bảng user không được xóa bởi injection.");
        }
    }

    @Test(description = "Test JSON-based SQL Injection vulnerability")
    public void testJsonBasedSqlInjection() {
        // JSON-based SQL injection payloads
        String[] jsonSqlInjections = {
                "admin'->>'$.name' OR 1=1--",
                "admin'->>'$' OR 1=1--", //trích xuất giá trị trong PostgreSQL
                "' OR JSON_EXTRACT('{\"x\":1}', '$.x') = 1 --",
                "' OR JSON_KEYS('{\"1\":\"1\"}') IS NOT NULL --", //mysql
                "' OR JSON_VALUE('{\"x\":\"1\"}', '$.x') = '1' --" //sql server
        };
        for (String injection : jsonSqlInjections) {
            loginPageWeb.navigateToLoginPage();
            loginPageWeb.attemptSqlInjection(injection);

            // Verify that SQL injection doesn't result in successful login
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
                    "SQL Injection might be successful with: " + injection);
        }
    }

    @Test(description = "Test XSS vulnerability on login form")
    public void testXssVulnerability() {
        // Common XSS payloads
        String[] xssPayloads = {
                "<script>alert('XSS')</script>",
                "<img src=\"javascript:alert('XSS')\">",
                "<body onload=alert('XSS')>",
                "\"><script>alert(document.cookie)</script>",
                "<svg/onload=alert('XSS')>"
        };
        List<String> xssTriggered = new ArrayList<>();      // Gây alert thực sự
        List<String> reflectedOnly = new ArrayList<>();     // Chỉ phản chiếu, không thực thi
        for (String payload : xssPayloads) {
            // Điều hướng và nhập payload
            loginPageWeb.navigateToLoginPage();
            loginPageWeb.attemptXssAttack(payload);

            boolean isExecuted = false;

            // Bắt alert nếu có (XSS thực sự)
            try {
                WebDriverWait wait = new WebDriverWait(DriverManager.getWebDriver(), Duration.ofSeconds(2));
                Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                String alertText = alert.getText();

                if (alertText.contains("XSS") || alertText.contains("document.cookie")) {
                    xssTriggered.add(payload); // Alert thực thi => XSS thật
                    isExecuted = true;
                }
                alert.accept();
            } catch (TimeoutException ignored) {
                // Không có alert, kiểm tra phản chiếu
            }
            // Nếu không bị thực thi nhưng vẫn bị phản chiếu => có thể cần encode
            if (!isExecuted) {
                String pageSource = DriverManager.getWebDriver().getPageSource();
                String escapedPayload = StringEscapeUtils.escapeHtml4(payload);

                if (pageSource.contains(payload) || pageSource.contains(escapedPayload)) {
                    reflectedOnly.add(payload);
                }
            }
        }
        // Báo cáo lỗi nếu có XSS thực sự
        Assert.assertTrue(xssTriggered.isEmpty(),
                "XSS vulnerability detected with payloads (JavaScript executed): " + xssTriggered);

        // Ghi log cảnh báo nếu chỉ bị phản chiếu (tùy chọn, không fail test)
        if (!reflectedOnly.isEmpty()) {
            System.out.println("Payloads reflected in HTML (not executed): " + reflectedOnly);
        }
    }

    @Test(description = "Test brute force protection")
    public void testBruteForceProtection() {
        String username = "wronguser";
        String password = "wrongpassword";
        int maxAttempts = 5;
        List<Long> responseTimes = loginPageWeb.attemptMultipleLogins(username,password,maxAttempts);
        // Lấy HTML sau lần login cuối cùng
        String pageSource = DriverManager.getWebDriver().getPageSource();

        // Kiểm tra các dấu hiệu bảo vệ brute force
        boolean isAccountLocked = pageSource.toLowerCase().contains("account locked");
        boolean isCaptchaShown = pageSource.toLowerCase().contains("captcha");
        boolean isRateLimited = loginPageWeb.isIncreasingResponseTime(responseTimes);
        boolean isBlockedIp = pageSource.toLowerCase().contains("access denied") ||
                pageSource.toLowerCase().contains("your ip has been blocked");

        boolean hasProtection = isAccountLocked || isCaptchaShown || isBlockedIp || isRateLimited;

        // Báo cáo cụ thể theo từng loại bảo vệ
        if (isAccountLocked) {
            System.out.println("Tài khoản đã bị khóa sau nhiều lần đăng nhập sai.");
        }
        if (isCaptchaShown) {
            System.out.println("CAPTCHA xuất hiện sau nhiều lần đăng nhập sai.");
        }
        if (isRateLimited) {
            System.out.println("Hệ thống có dấu hiệu giới hạn tốc độ (tăng thời gian phản hồi)");
        }
        if (isBlockedIp) {
            System.out.println("Có thông báo IP bị chặn hoặc từ chối truy cập.");
        }

        // Xác minh có ít nhất 1 cơ chế bảo vệ
        Assert.assertTrue(hasProtection, "Hệ thống có thể thiếu cơ chế bảo vệ brute force.");
    }

    @Test(description = "Check that password field is secure in the UI (type and visibility)")
    public void testSecurePasswordHandling() {
        loginPageWeb.navigateToLoginPage();

        // Nhập tên và mật khẩu
        loginPageWeb.enterUsername("testuser");
        loginPageWeb.enterPassword("TestPassword123");

        // 1. Kiểm tra type="password"
        String passwordType = loginPageWeb.getTypePassword();
        Assert.assertEquals(passwordType, "password", "Password field should use type='password'");

        // 2. Kiểm tra không lộ mật khẩu trong HTML source
        String pageSource = DriverManager.getWebDriver().getPageSource();
        Assert.assertFalse(pageSource.contains("TestPassword123"),
                "Password should not be visible in page source (HTML)");
    }

    @Test(description = "Ensure password is not stored in cookies")
    public void testPasswordNotInCookies() {
        loginPageWeb.login(user.getUsername(), user.getPassword());
        Set<Cookie> cookies = DriverManager.getWebDriver().manage().getCookies();
        for (Cookie cookie : cookies) {
            String cookieValue = cookie.getValue();
            Assert.assertFalse(cookieValue.contains(user.getPassword()),
                    "Mật khẩu bị lưu trong cookie: " + cookie.getName());
        }
    }
}
