package tests;

import base.BaseTestWeb;
import drivers.DriverManager;
import org.testng.annotations.Test;
import pagesweb.Allocations_VoucherPageWeb;

public class Allocations_VoucherTestWeb extends BaseTestWeb {
    Allocations_VoucherPageWeb all_vou;
    @Test
    public void testSuccessfulLoginORG() {
        all_vou = new Allocations_VoucherPageWeb(DriverManager.getWebDriver());
        all_vou.navigateToAllocation_VoucherPage();
    }
}
