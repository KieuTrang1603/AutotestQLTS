package pagesweb;

import model.Asset;
import model.enums.AssetStatus;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Assets_Page {
    private final WebDriver driver;
    private final WebDriverWait wait;
    Asset asset;

    public Assets_Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    public void navigateToAssetsPage(){
        HomePageWeb homePageWeb = new HomePageWeb(driver);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        homePageWeb.Assets_ui();
    }

    public void navigateToAssetsPagetoLogin(String user, String password){
        LoginPageWeb loginPageWeb = new LoginPageWeb(driver);
        HomePageWeb homePageWeb = new HomePageWeb(driver);
        loginPageWeb.navigateToLoginPage();
        loginPageWeb.login(user, password);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        homePageWeb.Assets_ui();
    }
    public void closeMenu(){
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ESCAPE).perform();
    }

    public void searchAsset(String ma){
        WebElement inputSearch = driver.findElement(By.id("search_box")); // hoặc sửa thành id thực tế nếu khác
        inputSearch.clear();
        inputSearch.sendKeys(ma);
        inputSearch.sendKeys(Keys.ENTER);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Asset getDuLieuTS(){
        WebElement table = driver.findElement(By.cssSelector("table.MuiTable-root"));

        // Lấy dòng tiêu đề để tìm vị trí cột
        WebElement headerRow = table.findElement(By.cssSelector("thead tr"));
        List<WebElement> headers = headerRow.findElements(By.tagName("th"));
        // Tìm index của cột "Trạng thái tài sản"
        int trangThaiIndex = -1;
        for (int i = 0; i < headers.size(); i++) {
            String headerText = headers.get(i).getText().trim();
            if (headerText.equalsIgnoreCase("Trạng thái")) {
                trangThaiIndex = i;
                break;
            }
        }
        // Nếu không tìm thấy thì báo lỗi
        if (trangThaiIndex == -1) {
            throw new NoSuchElementException("Không tìm thấy cột 'Trạng thái'");
        }

        // Tìm index của cột "Kho tài sản"
        int khoIndex = -1;
        for (int i = 0; i < headers.size(); i++) {
            String headerText = headers.get(i).getText().trim();
            if (headerText.equalsIgnoreCase("Tên kho")) {
                khoIndex = i;
                break;
            }
        }
        // Nếu không tìm thấy thì báo lỗi
        if (khoIndex == -1) {
            throw new NoSuchElementException("Không tìm thấy cột 'Tên kho'");
        }

        // Tìm index của cột "Phòng sử dụng tài sản"
        int phongSuDungIndex = -1;
        for (int i = 0; i < headers.size(); i++) {
            String headerText = headers.get(i).getText().trim();
            if (headerText.equalsIgnoreCase("Phòng ban sử dụng")) {
                phongSuDungIndex = i;
                break;
            }
        }
        // Nếu không tìm thấy thì báo lỗi
        if (phongSuDungIndex == -1) {
            throw new NoSuchElementException("Không tìm thấy cột 'Phòng ban sử dụng'");
        }

        // Lấy tất cả các dòng trong tbody
        List<WebElement> rows = table.findElements(By.cssSelector("tbody tr"));
        if (rows.isEmpty()) {
            throw new NoSuchElementException("Không có dòng nào trong bảng.");
        }
        // Lấy dòng đầu tiên trong tbody
        WebElement firstRow = rows.get(0);
        List<WebElement> cells = firstRow.findElements(By.tagName("td"));
        asset = new Asset();
        asset.setStore(cells.get(khoIndex).getText().trim());
        asset.setStatus(cells.get(trangThaiIndex).getText().trim());
        asset.setUse_department_id(cells.get(phongSuDungIndex).getText().trim());
        return asset;
    }

    public boolean checkTSCapPhat(String maTS, int a, String tenPBSD){
        AssetStatus status1= AssetStatus.NEW_IN_STORAGE;
        AssetStatus status2= AssetStatus.RETURNED_TO_STORAGE;
        AssetStatus status3= AssetStatus.IN_USE;
        searchAsset(maTS);
        getDuLieuTS();
        boolean check = false;
        switch (a){
            case 1,2:
                if(asset.getStatus().equals(status1.getDescription()) || asset.getStatus().equals(status2.getDescription())){
                    if(!asset.getStore().isEmpty()){
                        if(asset.getUse_department_id().isEmpty()){
                            check =true;
                            return check;
                        }
                    }
                } else
                    return check;
                break;
            case 3:
                if(asset.getStatus().equals(status3.getDescription())){
                    if(asset.getStore().isEmpty()){
                        if(asset.getUse_department_id().contains(tenPBSD)){
                            check =true;
                            return check;
                        }
                    }
                } else
                    return check;
                break;
        }
        return check;
    }

}
