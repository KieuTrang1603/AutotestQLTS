package tests;

import base.BaseTestWeb;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.Allocations_VoucherPageWeb;
import pages.HomePageWeb;
import pages.LoginPageWeb;

public class Allocations_VoucherTestWeb extends BaseTestWeb {
    Allocations_VoucherPageWeb all_vou;
    @Test
    public void testSuccessfulLoginORG() {
        all_vou = new Allocations_VoucherPageWeb(driver);
        all_vou.navigateToAllocation_VoucherPage();
    }
}
