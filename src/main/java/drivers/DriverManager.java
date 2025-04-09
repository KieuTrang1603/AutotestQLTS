package drivers;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;

public class DriverManager {
    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    private static ThreadLocal<AndroidDriver> appiumDriver = new ThreadLocal<>();

    public static void setWebDriver(WebDriver driverInstance) {
        webDriver.set(driverInstance);
    }

    public static WebDriver getWebDriver() {
        return webDriver.get();
    }

    public static void quitWebDriver() {
        if (webDriver.get() != null) {
            webDriver.get().quit();
            webDriver.remove();
        }
    }

    public static void setAppiumDriver(AndroidDriver driverInstance) {
        appiumDriver.set(driverInstance);
    }

    public static AndroidDriver getAppiumDriver() {
        return appiumDriver.get();
    }

    public static void quitAppiumDriver() {
        if (appiumDriver.get() != null) {
            appiumDriver.get().quit();
            appiumDriver.remove();
        }
    }
}
