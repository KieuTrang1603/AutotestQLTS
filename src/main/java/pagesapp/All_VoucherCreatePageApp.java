package pagesapp;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class All_VoucherCreatePageApp {
    private final AndroidDriver driver;
    private final WebDriverWait wait ;

    @FindBy(xpath = "//android.view.View[contains(@content-desc, 'Ngày chứng từ')]/following-sibling::android.view.View[1]")
    private WebElement ngayTaoPhieuInput;

    @FindBy(xpath = "//android.view.View[contains(@content-desc, 'Ngày chứng từ')]/following-sibling::android.view.View[2]")
    private WebElement ngayChungTuInput;

    // Khởi tạo các phần tử giao diện bằng Page Factory
    public All_VoucherCreatePageApp(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public String getNgayTaoPhieuValue() {
        return ngayTaoPhieuInput.getText();
    }

    public boolean isNgayTaoPhieuReadonly(){
        String enabledAttribute = ngayTaoPhieuInput.getAttribute("enabled");
        if (enabledAttribute != null && enabledAttribute.equals("false")) {
            System.out.println("Thuộc tính 'enabled' là 'false', cho thấy element có thể ở trạng thái chỉ đọc.");
            return true;
        } else {
            System.out.println("Không tìm thấy thuộc tính 'enabled' hoặc nó có giá trị 'true'.");
            return false;
        }
    }

    public String getNgayChungTuValue() {
        return ngayChungTuInput.getText();
    }

    public boolean isNgayChungTuEdit(){
        String enabledAttribute = ngayChungTuInput.getAttribute("enabled");
        if (enabledAttribute != null && enabledAttribute.equals("true")) {
            System.out.println("Thuộc tính 'enabled' là 'true', cho thấy element có thể ở trạng thái chỉnh sửa.");
            return true;
        } else {
            System.out.println("Không tìm thấy thuộc tính 'enabled' hoặc nó có giá trị 'false'.");
            return false;
        }
    }
}
