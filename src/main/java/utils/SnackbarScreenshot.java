package utils;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class SnackbarScreenshot {
    private final AppiumDriver driver;

    public SnackbarScreenshot(AppiumDriver driver) {
        this.driver = driver;
    }

    /**
     * Chụp màn hình và trích xuất text từ vùng Snackbar màu đỏ
     * @return Text được trích xuất từ vùng Snackbar
     */
    public String getTextFromRedSnackbar() throws IOException, TesseractException {
        try {
            // Chụp màn hình
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            BufferedImage fullImage = ImageIO.read(screenshot);

            // Tạo tên file với timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
            File targetFile = new File(MyUtil.DOWNLOAD_PATH + "\\screenshot_" + timestamp + ".png");

            // Lưu hình ảnh
            ImageIO.write(fullImage, "png", targetFile);
            System.out.println("Đã lưu ảnh chụp màn hình tại: " + targetFile.getAbsolutePath());

            // Lấy kích thước màn hình
            int screenWidth = fullImage.getWidth();
            int screenHeight = fullImage.getHeight();

            // Xác định vùng có thể chứa Snackbar (điều chỉnh các thông số này dựa trên ứng dụng của bạn)
            // Thông thường Snackbar nằm ở phần dưới màn hình
            int startY = screenHeight - 300; // Vị trí bắt đầu tìm từ dưới lên

            // Tìm vùng có màu đỏ trong hình
            Rectangle redArea = findRedArea(fullImage, startY, screenHeight);

            if (redArea == null) {
                System.out.println("Không tìm thấy vùng màu đỏ trong ảnh chụp màn hình");
                return null; // Không tìm thấy vùng màu đỏ
            }

            // Cắt hình ảnh theo vùng tìm được
            BufferedImage snackbarImage = fullImage.getSubimage(
                    redArea.x, redArea.y, redArea.width, redArea.height);

            // Lưu hình ảnh đã cắt (để debug)
            String croppedTimestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
            File snackbarFile = new File(MyUtil.DOWNLOAD_PATH + "\\snackbar_cropped_" + croppedTimestamp + ".png");
            ImageIO.write(snackbarImage, "png", snackbarFile);
            System.out.println("Đã lưu ảnh Snackbar đã cắt tại: " + snackbarFile.getAbsolutePath());

            // Sử dụng OCR để trích xuất text
            Tesseract tesseract = new Tesseract();
            // Đường dẫn đến thư mục tessdata - điều chỉnh theo cài đặt của bạn
            tesseract.setDatapath("D:\\Tester\\Auto\\Selenium\\Login\\OCR\\tessdata");
            // Sử dụng tiếng Anh nếu không cần tiếng Việt
            tesseract.setLanguage("vie");

            String extractedText = tesseract.doOCR(snackbarFile).trim();
            System.out.println("Text được trích xuất từ Snackbar: " + extractedText);

            return extractedText;

        } catch (IOException | TesseractException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Tìm vùng màu đỏ trong hình ảnh
     */
    private Rectangle findRedArea(BufferedImage image, int startY, int endY) {
        int width = image.getWidth();
        int redStartX = -1;
        int redStartY = -1;
        int redEndX = -1;
        int redEndY = -1;

        // Duyệt từ dưới lên để tìm vùng màu đỏ
        for (int y = startY; y < endY; y++) {
            boolean foundRedInRow = false;
            int rowStartX = -1;
            int rowEndX = -1;

            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));

                // Kiểm tra xem pixel có phải màu đỏ không
                // Điều chỉnh các ngưỡng này cho phù hợp với màu đỏ cụ thể trong ứng dụng của bạn
                if (isRedOrGreen(color)) {
                    if (rowStartX == -1) {
                        rowStartX = x;
                    }
                    rowEndX = x;
                    foundRedInRow = true;
                }
            }

            if (foundRedInRow) {
                if (redStartY == -1) {
                    redStartY = y;
                    redStartX = rowStartX;
                    redEndX = rowEndX;
                }
                redEndY = y;
                redStartX = Math.min(redStartX, rowStartX);
                redEndX = Math.max(redEndX, rowEndX);
            }
        }

        if (redStartX != -1 && redStartY != -1) {
            return new Rectangle(redStartX, redStartY, redEndX - redStartX + 1, redEndY - redStartY + 1);
        }

        return null;
    }

    /**
     * Kiểm tra xem một màu có phải là màu đỏ không
     */
    private boolean isRedOrGreen(Color color) {
        // Kiểm tra màu đỏ
        boolean isRed = color.getRed() > 200 && color.getGreen() < 100 && color.getBlue() < 100;

        // Kiểm tra màu xanh (ví dụ xanh teal hoặc xanh success như Material Design)
        boolean isGreen = color.getRed() < 180 && color.getGreen() > 100 && color.getBlue() < 100;

        return isRed || isGreen;
    }

    /**
     * Kiểm tra xem Snackbar có chứa text cụ thể không
     */
    public boolean verifySnackbarContainsText(String expectedText) {
        try {
            String actualText = getTextFromRedSnackbar();
            return actualText != null && actualText.contains(expectedText);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
