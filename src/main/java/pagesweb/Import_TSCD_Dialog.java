package pagesweb;

import org.openqa.selenium.NoSuchElementException;
import utils.FileHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.MyUtil;

import java.time.Duration;

public class Import_TSCD_Dialog {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(xpath = "//label//span[text()='Mẫu nhập Excel']")
    private WebElement taiMau_btn;

    @FindBy(css = "input[type='file']")
    private WebElement taiFile_btn;

    @FindBy(xpath = "//h2//span[contains(text(),'Nhập excel lỗi')]")
    private WebElement popupTitle;

    @FindBy(xpath = "//button//span[text()='File lỗi']")
    private WebElement fileError_btn;

    public Import_TSCD_Dialog(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void taiMau_click(){
        taiMau_btn.click();
        try {
            Thread.sleep(2000); // chờ 2 giây
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean downloadFile(){
        boolean downloaded = FileHelper.waitForDownload(MyUtil.DOWNLOAD_PATH, "Mẫu nhập tài sản đã cấp phát", 20);
        if (downloaded) {
            System.out.println("File đã tải thành công!");
            return true;
        } else {
            System.out.println("Không tìm thấy file sau timeout.");
            return false;
        }
    }

    public void uploadFile(){
        taiFile_btn.sendKeys(MyUtil.FILE_PATH);
        try {
            Thread.sleep(2000); // chờ 2 giây
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isPopupErrorDisplayed(){
        try {
            Thread.sleep(2000); // chờ 2 giây
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            return popupTitle.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean downloadFileError(){
        fileError_btn.click();
        boolean downloaded = FileHelper.waitForDownload(MyUtil.DOWNLOAD_PATH, "Mẫu nhập tài sản đã cấp phát (1)", 20);
        if (downloaded) {
            System.out.println("File đã tải thành công!");
            return true;
        } else {
            System.out.println("Không tìm thấy file sau timeout.");
            return false;
        }
    }
}
