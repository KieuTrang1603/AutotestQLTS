package pagesweb;

import model.Allocation;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Transfer_VoucherPageWeb {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Constructor
    public Transfer_VoucherPageWeb(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void navigateToTransfer_VoucherPage(String user, String password){
        LoginPageWeb loginPageWeb = new LoginPageWeb(driver);
        HomePageWeb homePageWeb = new HomePageWeb(driver);
        loginPageWeb.navigateToLoginPage();
        loginPageWeb.login(user, password);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        homePageWeb.Transfer_voucher_ui();
    }

    public void closeMenu(){
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ESCAPE).perform();
    }

    public List<Allocation> getAllocationRecord(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement firstTable = driver.findElement(
                By.xpath("(//table[contains(@class, 'MuiTable-root')])[1]")
        );
        List<WebElement> rows = firstTable.findElements(By.xpath(".//tbody/tr"));
        List<Allocation> list = new ArrayList<>();

        for (WebElement row : rows) {
            String ngay = row.findElement(By.xpath("./td[3]")).getText().trim();
            String trangThai = row.findElement(By.xpath("./td[4]")).getText().trim();
            String phongGiao = row.findElement(By.xpath("./td[5]")).getText().trim();
            String nguoiGiao = row.findElement(By.xpath("./td[6]")).getText().trim();
            String phongNhan = row.findElement(By.xpath("./td[7]")).getText().trim();
            String nguoiNhan = row.findElement(By.xpath("./td[8]")).getText().trim();

            Allocation p = new Allocation("", "", "", "", "", "");
            p.setNgay(ngay);
            p.setTrangThai(trangThai);
            p.setPhongGiao(phongGiao);
            p.setNguoiGiao(nguoiGiao);
            p.setPhongNhan(phongNhan);
            p.setNguoiNhan(nguoiNhan);
            list.add(p);
        }
        return list;
    }
}
