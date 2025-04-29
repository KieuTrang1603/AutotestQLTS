package utils;

import drivers.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PageAppUtil {
    public static boolean isNgayTaoPhieuReadonly(WebElement el){
        String enabledAttribute = el.getAttribute("clickable");
        if (enabledAttribute != null && enabledAttribute.equals("false")) {
            System.out.println("Thuộc tính 'enabled' là 'false', cho thấy element có thể ở trạng thái chỉ đọc.");
            return true;
        } else {
            System.out.println("Không tìm thấy thuộc tính 'enabled' hoặc nó có giá trị 'true'.");
            return false;
        }
    }

    public static boolean isEdit(WebElement el){
        String enabledAttribute = el.getAttribute("clickable");
        if (enabledAttribute != null && enabledAttribute.equals("true")) {
            System.out.println("Thuộc tính 'enabled' là 'true', cho thấy element có thể ở trạng thái chỉnh sửa.");
            return true;
        } else {
            System.out.println("Không tìm thấy thuộc tính 'enabled' hoặc nó có giá trị 'false'.");
            return false;
        }
    }

    public static String getValueContent(WebElement el){
        String phongBGApp = el.getAttribute("content-desc");
        System.out.println("Phòng bàn giao trên APP:" + phongBGApp);
        return phongBGApp;
    }

    public static List<String> getAllDropdownOptionsElements(List<WebElement> elements) {
        List<String> options = new ArrayList<>();
        for (WebElement option : elements) {
            options.add(option.getAttribute("content-desc"));
        }
        return options;
    }

    public static List<String> getAllDanhsachTSCD(WebElement els) {
        PageUtil.clickElement(els);
        WebDriverWait wait = new WebDriverWait(DriverManager.getAppiumDriver(), Duration.ofSeconds(5));
        WebElement scrollView = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.ScrollView")
        ));
        List<String> contentDescs = new ArrayList<>();
        List<WebElement> visibleElements = scrollView.findElements(By.xpath(".//android.widget.Button[@content-desc]"));
        for (WebElement el : visibleElements) {
            contentDescs.add(el.getAttribute("content-desc"));
        }

        return new ArrayList<>(contentDescs);
    }
}
