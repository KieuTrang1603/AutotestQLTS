package tests.main_tains.app;

import base.BaseTestApp;
import drivers.DriverManager;
import model.Asset;
import model.Department;
import model.User;
import model.UsersRole;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pagesapp.*;
import pagesweb.DS_TSCD_Maintain_Dialog;
import pagesweb.Main_TainReportWeb;
import utils.DataBaseUtils;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main_tainReportTestValidateApp extends BaseTestApp {
    Main_TainReportPageApp reportPageapp;
    LoginPageApp loginPageapp;
    HomePageApp homePageApp;
    Main_TainReportWeb reportWeb;
    UsersRole Users;
    User user;
    @BeforeClass
    public void prepareMainTainCreatePage(){
        loginPageapp = new LoginPageApp(DriverManager.getAppiumDriver());
        homePageApp = new HomePageApp(DriverManager.getAppiumDriver());
        reportPageapp = new Main_TainReportPageApp(DriverManager.getAppiumDriver());
        user = Users.getUserByRole("AU");
        loginPageapp.login(user.getUsername(), user.getPassword());
        homePageApp.navigationtoMaintain();
    }
    @Test(priority = 1)
    public void testNgayBaoCao_ShouldBeToday_AndEdit() {
        // 1. Kiểm tra giá trị là ngày hôm nay
        String expectedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String actualDate = reportPageapp.getNgayBaoCaoValue();
        Assert.assertEquals(actualDate, expectedDate, "Ngày báo cáo không phải là ngày hiện tại!");
        // 2. Kiểm tra không chỉnh sửa được
        boolean isEdit = reportPageapp.isNgayBaoCaoEdit();
        Assert.assertTrue(isEdit, "Trường 'Ngày báo cáo' không được chỉnh sửa!");
    }

    @Test(priority = 2)
    public void testTrangThaiReadonly() {
        boolean able = reportPageapp.isTrangThaiAble();
        Assert.assertTrue(!able, "Trường 'Trạng thái' không disable");
    }

    @Test(priority = 3)
    public void testPhongBaoSuCo() {
        try {
            List<String> expectedDepartments = DataBaseUtils.getNamePhongBanBSCAU();
            System.out.println("Danh sách theo Database: " + expectedDepartments);
            Assert.assertTrue(reportWeb.checkPhongBaoSuCo(expectedDepartments),
                    "Danh sách 'Phòng ban báo sự cố' chưa hiển thị đúng!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(priority = 4)
    public void testPhongTiepNhan() {
        try {
            List<String> expectedDepartments = DataBaseUtils.getNamePhongBanAM();
            System.out.println("Danh sách theo Database: " + expectedDepartments);
            Assert.assertTrue(reportWeb.checkPhongTiepNhan(expectedDepartments),
                    "Danh sách 'Phòng ban báo sự cố' chưa hiển thị đúng!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(priority = 5)
    public void testDanhSachTaiSan() {
        reportWeb.openDropdownPhongBaoSuCo();
        reportWeb.chonPhongBaoSuCoInput();
        DS_TSCD_Maintain_Dialog ds = new DS_TSCD_Maintain_Dialog(DriverManager.getWebDriver());
        ds.getPaginationText();
        String tenPBSC = reportWeb.getPhongBaoSuCoInput();
        try {
            String department_id = DataBaseUtils.getDepartmentIdByTen(tenPBSC);
            Integer sobanghi = DataBaseUtils.countAssetsAvailable_Maintain(department_id);
            System.out.printf("Data from database: %d%n", sobanghi);
            Assert.assertTrue(ds.checkSobanghi(sobanghi),
                    "Danh sách 'TSCĐ' chưa hiển thị đúng!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(priority = 6)
    public void testDataTaiSan() throws SQLException {
        DS_TSCD_Maintain_Dialog ds = new DS_TSCD_Maintain_Dialog(DriverManager.getWebDriver());
        String maTS = ds.chonVaLayTSTuDialog();
        ds.ChosenTS();
        Asset assetDB= new Asset();
        assetDB = DataBaseUtils.getTSByCode(maTS);
        assetDB.setManager_department(DataBaseUtils.getDepartmentNameById(assetDB.getManager_department()));
        Assert.assertTrue(reportWeb.checkDataTaiSan(assetDB),
                "Dữ liệu 'TSCĐ' chưa hiển thị đúng!");
    }

    @Test(priority = 7)
    public void testAnhSuCo(){
        try {
            List<String> expectedDepartments = DataBaseUtils.getNamePhongBanAM();
            Assert.assertTrue(reportPageapp.checkPhongBanTiepNhan(expectedDepartments),
                    "Danh sách 'Phòng ban tiếp nhận' chưa hiển thị đúng!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterMethod
    public void resetToAddNewScreen() {
        DriverManager.getAppiumDriver().navigate().back();
        // Nếu form thêm mới bị ẩn (không còn), thì click lại "Thêm mới" để reset màn hình
        if (!reportPageapp.isMainTainDialogDisplayed()) {
            homePageApp.navigationtoMaintain();
        }
    }
}
