package utils;

import org.openqa.selenium.WebElement;

public class PageUtil {
    public static String getValueText(WebElement el){
        return el.getText();
    }

    public static void clickElement(WebElement el) {
        el.click();
    }
}
