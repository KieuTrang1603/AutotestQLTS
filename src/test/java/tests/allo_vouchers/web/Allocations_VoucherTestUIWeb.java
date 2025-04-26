package tests.allo_vouchers.web;

import base.BaseMultiTestWeb;
import drivers.DriverManager;
import model.AllocationMenu;
import model.Department;
import model.HomeMenu;
import model.enums.PlatformType;
import model.enums.UserRole;
import org.testng.Assert;
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
        Assert.assertTrue(all_vou.isMenuDisplayedCorrectly(AllocationMenu.getExpectedMenus(UserRole.AM, PlatformType.WEB)),
                "Không hiển thị đúng menu role ORG");
    }

    @Test
    public void UI_AllocationORG(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all_vou.navigateToAllocation_VoucherPage("bvdka", "123456");
        Assert.assertTrue(all_vou.isMenuDisplayedCorrectly(AllocationMenu.getExpectedMenus(UserRole.ORG, PlatformType.WEB)),
                "Không hiển thị đúng menu role ORG");
    }

    @Test
    public void UI_AllocationAU(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all_vou.navigateToAllocation_VoucherPage("audemo", "123123");
        Assert.assertTrue(all_vou.isMenuDisplayedCorrectly(AllocationMenu.getExpectedMenus(UserRole.AU, PlatformType.WEB)),
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
        try {
            Integer sobanghi = DataBaseUtils.countAllcationAM(Department.DEPARTMENT_ID_AM);
            System.out.printf("Data from database: %d%n", sobanghi);
            Assert.assertTrue(all_vou.checkSobanghi(sobanghi),
                    "Số lượng bản ghi chưa hiển thị đúng!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void TotalRecord_AllocationAU(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all_vou.navigateToAllocation_VoucherPage("phs21", "123456");
        try {
            Integer sobanghi = DataBaseUtils.countAllcationAU(Department.DEPARTMENT_ID_AU);
            System.out.printf("Data from database: %d%n", sobanghi);
            Assert.assertTrue(all_vou.checkSobanghi(sobanghi),
                    "Số lượng bản ghi chưa chưa hiển thị đúng!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
