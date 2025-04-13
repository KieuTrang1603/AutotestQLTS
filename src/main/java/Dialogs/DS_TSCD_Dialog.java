package Dialogs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DS_TSCD_Dialog {
    private final WebDriver driver;
    private final WebDriverWait wait;

//    @FindBy(xpath = "//p[contains(@class, 'MuiTablePagination-caption') and contains(text(), 'trong')]")
//    private WebElement paginationText;

    public DS_TSCD_Dialog(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public String getPaginationText() {
        WebElement paginationElement = driver.findElement(By.xpath("//div[contains(@class, 'MuiDialogContent-root')]//p[contains(@class, 'MuiTablePagination-caption') and contains(text(), 'trong')]"));
        String paginationText = paginationElement.getText();
        System.out.println("Text lấy được: " + paginationText);
        return paginationText;
    }
}
