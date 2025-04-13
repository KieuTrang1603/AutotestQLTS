package tests.allo_vouchers;

import base.BaseMultiTestWeb;
import base.BaseTestWeb;
import drivers.DriverManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pagesweb.All_VoucherPageWeb;
import utils.DataBaseUtils;
import utils.MyUtil;

import java.sql.SQLException;

public class Allocations_VoucherTestUIWeb extends BaseMultiTestWeb {
    All_VoucherPageWeb all_vou;
    @Test
    public void UI_AllocationAM(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all_vou.navigateToAllocation_VoucherPage("pvt1", "123456");
        Assert.assertTrue(all_vou.isMenuDisplayedCorrectly(MyUtil.getButtonORG()),
                "Không hiển thị đúng menu role ORG");
    }

    @Test
    public void UI_AllocationORG(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all_vou.navigateToAllocation_VoucherPage("bvdka", "123456");
        Assert.assertTrue(all_vou.isMenuDisplayedCorrectly(MyUtil.getButtonORG()),
                "Không hiển thị đúng menu role ORG");
    }

    @Test
    public void UI_AllocationAU(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all_vou.navigateToAllocation_VoucherPage("audemo", "123123");
        Assert.assertTrue(all_vou.isMenuDisplayedCorrectly(MyUtil.getButtonAU()),
                "Không hiển thị đúng menu role ORG");
    }

    @Test
    public void TotalRecord_AllocationORG(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all_vou.navigateToAllocation_VoucherPage("bvdka", "123456");
        try {
            Integer sobanghi = DataBaseUtils.countAllcationORG();
            System.out.printf("Data from database: %d%n", sobanghi);
            Assert.assertTrue(all_vou.checkSobanghi(sobanghi),
                    "Danh sách 'Người bàn giao' chưa hiển thị đúng!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void TotalRecord_AllocationAM(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all_vou.navigateToAllocation_VoucherPage("pvt1", "123456");
        String department_id = "9484b376-8470-4d06-b1a0-e59179f93ca6";
        try {
            Integer sobanghi = DataBaseUtils.countAllcationAM(department_id);
            System.out.printf("Data from database: %d%n", sobanghi);
            Assert.assertTrue(all_vou.checkSobanghi(sobanghi),
                    "Danh sách 'Người bàn giao' chưa hiển thị đúng!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void TotalRecord_AllocationAU(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all_vou.navigateToAllocation_VoucherPage("phs21", "123456");
        String department_id = "f8359400-b129-4486-abae-b694589fcc1e";
        try {
            Integer sobanghi = DataBaseUtils.countAllcationAU(department_id);
            System.out.printf("Data from database: %d%n", sobanghi);
            Assert.assertTrue(all_vou.checkSobanghi(sobanghi),
                    "Danh sách 'Người bàn giao' chưa hiển thị đúng!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
