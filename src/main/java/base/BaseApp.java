package base;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BaseApp {
    protected AndroidDriver driver;
    protected WebDriverWait wait;

    public BaseApp(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void scrollDown(WebElement scrollView) {
        try {
            // Sử dụng tọa độ của scrollView để thực hiện thao tác vuốt
            Dimension size = scrollView.getSize();
            Point location = scrollView.getLocation();

            int centerX = location.getX() + size.getWidth() / 2;
            int startY = location.getY() + (int)(size.getHeight() * 0.7);
            int endY = location.getY() + (int)(size.getHeight() * 0.3);

            // Sử dụng W3C Actions API
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 0);

            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), centerX, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Collections.singletonList(swipe));

            Thread.sleep(800);
        } catch (Exception e) {
            System.out.println("Scroll operation failed: " + e.getMessage());
        }
    }

    protected void scrollUp(WebElement scrollView) {
        try {
            // Lấy kích thước và vị trí của ScrollView
            Dimension size = scrollView.getSize();
            Point location = scrollView.getLocation();

            // Tính toán điểm bắt đầu và kết thúc cho thao tác cuộn
            int centerX = location.getX() + size.getWidth() / 2;
            int startY = location.getY() + (int)(size.getHeight() * 0.3); // Điểm thấp hơn
            int endY = location.getY() + (int)(size.getHeight() * 0.7);   // Điểm cao hơn

            // Sử dụng W3C Actions API để thực hiện thao tác vuốt
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 0);

            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), centerX, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Collections.singletonList(swipe));

            // Đợi UI ổn định
            Thread.sleep(800);
        } catch (Exception e) {
            System.out.println("Scroll up operation failed: " + e.getMessage());
        }
    }

    protected void scrollUsingUiScrollable() {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"));
            Thread.sleep(800);
        } catch (Exception e) {
            System.out.println("Scroll operation failed: " + e.getMessage());
        }
    }
}
