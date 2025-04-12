package tests.allo_vouchers;

import base.BaseTestWeb;
import drivers.DriverManager;
import org.testng.annotations.Test;
import pagesweb.All_VoucherPageWeb;

public class Allocations_VoucherTestUIWeb extends BaseTestWeb {
    All_VoucherPageWeb all_vou;
    @Test
    public void testSuccessfulLoginORG() {
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all_vou.navigateToAllocation_VoucherPage();
    }
}
