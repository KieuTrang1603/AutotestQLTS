package tests.allo_vouchers.web;

import base.BaseMultiTestWeb;
import drivers.DriverManager;
import model.*;
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
    UsersRole Users;
    User user;
    @Test (priority = 2)
    public void UI_AllocationAM(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        user= Users.getUserByRole("AM");
        all_vou.navigateToAllocation_VoucherPage(user.getUsername(), user.getPassword());
        Assert.assertTrue(all_vou.isMenuDisplayedCorrectly(AllocationMenu.getExpectedMenus(UserRole.AM, PlatformType.WEB)),
                "Không hiển thị đúng menu role ORG");
    }

    @Test (priority = 1)
    public void UI_AllocationORG(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        user= Users.getUserByRole("ORG");
        all_vou.navigateToAllocation_VoucherPage(user.getUsername(), user.getPassword());
        Assert.assertTrue(all_vou.isMenuDisplayedCorrectly(AllocationMenu.getExpectedMenus(UserRole.ORG, PlatformType.WEB)),
                "Không hiển thị đúng menu role ORG");
    }

    @Test (priority = 3)
    public void UI_AllocationAU(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        user= Users.getUserByRole("AU");
        all_vou.navigateToAllocation_VoucherPage(user.getUsername(), user.getPassword());
        Assert.assertTrue(all_vou.isMenuDisplayedCorrectly(AllocationMenu.getExpectedMenus(UserRole.AU, PlatformType.WEB)),
                "Không hiển thị đúng menu role ORG");
    }

    @Test (priority = 4)
    public void TotalRecord_AllocationORG(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        user= Users.getUserByRole("ORG");
        all_vou.navigateToAllocation_VoucherPage(user.getUsername(), user.getPassword());
        try {
            Integer sobanghi = DataBaseUtils.countAllcationORG();
            System.out.printf("Data from database: %d%n", sobanghi);
            Assert.assertTrue(all_vou.checkSobanghi(sobanghi),
                    "Danh sách 'Người bàn giao' chưa hiển thị đúng!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test (priority = 5)
    public void TotalRecord_AllocationAM(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        user= Users.getUserByRole("AM");
        all_vou.navigateToAllocation_VoucherPage(user.getUsername(), user.getPassword());
        try {
            Integer sobanghi = DataBaseUtils.countAllcationAM(Department.DEPARTMENT_ID_AM);
            System.out.printf("Data from database: %d%n", sobanghi);
            Assert.assertTrue(all_vou.checkSobanghi(sobanghi),
                    "Số lượng bản ghi chưa hiển thị đúng!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test (priority = 6)
    public void TotalRecord_AllocationAU(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        user= Users.getUserByRole("AU");
        all_vou.navigateToAllocation_VoucherPage(user.getUsername(), user.getPassword());
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
