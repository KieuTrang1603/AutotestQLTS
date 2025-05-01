package helpers;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PermissionHandler {

    private AppiumDriver driver;

    public PermissionHandler(AppiumDriver driver) {
        this.driver = driver;
    }

    /**
     * Xử lý popup xin quyền hệ thống Android (Allow/Deny)
     */
    public void handleSystemPermissionPopup() {
//        try {
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//            wait.until(ExpectedConditions.alertIsPresent());
//            driver.switchTo().alert().accept();
//            System.out.println("Đã ấn 'Allow' hệ thống.");
//        } catch (NoAlertPresentException e) {
//            System.out.println("Không có alert hệ thống.");
//        } catch (WebDriverException e) {
//            System.out.println("WebDriverException: " + e.getMessage());
//        }
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            // Đợi 1 trong các button hiển thị
            WebElement allowButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[@text='While using the app']")));

            allowButton.click();
            System.out.println("Đã chọn 'While using the app' để cấp quyền.");

        } catch (TimeoutException e) {
            System.out.println("Không thấy popup xin quyền.");
        } catch (WebDriverException e) {
            System.out.println("WebDriverException: " + e.getMessage());
        }
    }

    /**
     * Hàm tap vào nút "Chụp màn hình"
     */
    public void tapScreenCaptureButton() {
        try {
            System.out.println("Đang thử tap nút 'Chụp màn hình'...");

//            // Tap vào nút Chụp màn hình - cần chỉnh lại tọa độ chính xác theo màn hình bạn
//            driver.executeScript("mobile: shell", new HashMap<String, Object>() {{
//                put("command", "input");
//                put("args", Arrays.asList("tap", "350", "1500"));
//            }});
//
//            Thread.sleep(2000); // Chờ app phản hồi
//            System.out.println("Đã tap xong!");

            WebElement captureButton = driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Chụp màn hình\")"
            ));
            captureButton.click();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hàm tap vào nút "Bật Camera"
     */
    public void tapEnableCameraButton() {
        try {
            Thread.sleep(1500); // Chờ popup hiện

            Map<String, Object> args = new HashMap<>();
            // ️TOẠ ĐỘ CỦA NÚT "Bật Camera" (bạn chỉnh tọa độ chuẩn với máy ảo của bạn nhé)
            args.put("command", "input tap 600 1300"); // ← Toạ độ bên phải
            driver.executeScript("mobile: shell", args);

            System.out.println("Đã tap vào nút 'Bật Camera'.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hàm tổng: xử lý hết popup
     */
    public void handleAllPermissions(String choice) {
        handleSystemPermissionPopup();

        if (choice.equalsIgnoreCase("capture")) {
            tapScreenCaptureButton();
        } else if (choice.equalsIgnoreCase("camera")) {
            tapEnableCameraButton();
        }
    }

    public void pushQRCodeImage(String adbPath, String deviceId, String imagePath) {
        try {
            String deviceImagePath = "/sdcard/Pictures/your_image.png"; // Đường dẫn nơi bạn muốn lưu ảnh trên thiết bị

            // Lệnh push ảnh lên thiết bị
            String pushCommand = adbPath + " -s " + deviceId + " push " + imagePath + " " + deviceImagePath;
            Process pushProcess = Runtime.getRuntime().exec(pushCommand);
            pushProcess.waitFor();
            System.out.println("Image pushed to device!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void injectQRCodeImage(String adbPath, String emulatorName, String imagePath) {
//        try {
//            String command = adbPath + " -s " + emulatorName + " emu camera inject-image " + imagePath;
//            Process process = Runtime.getRuntime().exec(command);
//            process.waitFor();
//            System.out.println("Inject QR image done!");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            String command = adbPath + " -s " + emulatorName + " emu camera inject-image " + imagePath;
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader stdOut = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdErr = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            while ((line = stdOut.readLine()) != null) {
                System.out.println("[stdout] " + line);
            }
            while ((line = stdErr.readLine()) != null) {
                System.err.println("[stderr] " + line);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Inject QR image done!");
            } else {
                System.err.println("Inject failed with exit code: " + exitCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

