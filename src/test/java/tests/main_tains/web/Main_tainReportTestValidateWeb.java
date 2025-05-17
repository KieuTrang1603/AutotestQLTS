package tests.main_tains.web;

import base.BaseTestWeb;
import drivers.DriverManager;
import model.Asset;
import model.Department;
import model.User;
import model.UsersRole;
import model.enums.AllocationStatus;
import model.enums.MaintainStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pagesweb.*;
import utils.DataBaseUtils;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main_tainReportTestValidateWeb extends BaseTestWeb {
    Main_TainPageWeb main_tainPageWeb;
    Main_TainReportWeb reportWeb;
    UsersRole Users;
    User user;
    @BeforeClass
    public void prepareMainTainCreatePage(){
        main_tainPageWeb = new Main_TainPageWeb(DriverManager.getWebDriver());
        user = Users.getUserByRole("AU");
        main_tainPageWeb.navigateToMain_TainPage(user.getUsername(),user.getPassword());
        main_tainPageWeb.closeMenu();
        main_tainPageWeb.BaoSuCo_Btn_click();
        reportWeb = new Main_TainReportWeb(DriverManager.getWebDriver());
    }

    @Test(priority = 1)
    public void testNgayBaoCao_ShouldBeToday_AndEdit() {
        // 1. Kiểm tra giá trị là ngày hôm nay
        String expectedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String actualDate = reportWeb.getNgayBaoCao();
        System.out.println("Giá trị lấy ra là :" + actualDate);
        Assert.assertEquals(actualDate, expectedDate, "Ngày báo cáo không khớp với ngày hiện tại!");
        // 2. Kiểm tra chỉnh sửa được
        boolean isEdit = reportWeb.isNgayBaoCaoEditable();
        Assert.assertTrue(isEdit, "Trường 'Ngày báo cáo' không được chỉnh sửa!");
    }

    @Test(priority = 2)
    public void testTrangThaiReadonly() {
        boolean able = reportWeb.isTrangThaiReadonly();
        Assert.assertTrue(able, "Trường 'Trạng thái' không disable");
        //Kiem tra trạng thái có là chờ xử lý không
        Assert.assertEquals(reportWeb.getTrangThaiStatus(), MaintainStatus.PENDING.getDescription(), "Trường 'Trạng thái' không phải trạng thái chờ xử lý");
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
    public void testNguoiBaoSuCoFirstly() {
        boolean able = reportWeb.isNguoiBaoSuCoAble();
        Assert.assertTrue(!able, "Trường 'Người báo cáo' không disable khi chưa chọn phòng ban báo sự cố");
    }

    @Test(priority = 6)
    public void testNguoiBaoCao(){
        reportWeb.openDropdownPhongBaoSuCo();
        reportWeb.chonPhongBaoSuCoInput();
        String tenPBSC = reportWeb.getPhongBaoSuCoInput();
        try {
            String department_id = DataBaseUtils.getDepartmentIdByTen(tenPBSC);
            System.out.println("Data from DB: " + department_id);
            List<String> displayNames = DataBaseUtils.getUserByDepartmentId(department_id);
            System.out.println("Data from database: " + displayNames);
            Assert.assertTrue(reportWeb.checkNguoiBaoSuCo(displayNames),
                    "Danh sách 'Người tiếp nhận' chưa hiển thị đúng!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(priority = 7)
    public void testChonTaiSanFirstly() {
        boolean able = reportWeb.isChonTaiSanAble();
        Assert.assertTrue(able, "Trường 'Chọn tài sản' không disable khi chưa chọn phòng ban báo sự cố");
    }

    @Test(priority = 8)
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

    @Test(priority = 9)
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

    @Test(priority = 10)
    public void testUpLoadFile_Hople() {
        String fileName = "hongmay.jpg";
        reportWeb.setUploadFile(fileName);
        String toastText = main_tainPageWeb.getToastMessageText();
        Assert.assertTrue(reportWeb.isUpLoadFile(fileName), "File không được upload thành công");
        Assert.assertEquals(toastText, "Thành công",
                "Toast không hiển thị hoặc sai nội dung.");
    }

    @Test(priority = 11)
    public void testUpLoadFile_KhongHople() {
        String fileName = "KHTH-HCQT.docx";
        reportWeb.setUploadFile(fileName);
        String toastText = main_tainPageWeb.getToastErrorMessageText();
        Assert.assertFalse(reportWeb.isUpLoadFile(fileName), "File được upload thành công");
        //Kiêm tra hiển thị thông báo
        Assert.assertEquals(toastText, "Định dạng file không hợp lệ",
                "Toast không hiển thị hoặc sai nội dung.");
    }

    @Test(priority = 12)
    public void testUpLoad2File_Hople() {
        String fileName1 = "hongmay.jpg";
        String fileName2 = "QR-siteVTL.png";
        reportWeb.setUpload2File(fileName1, fileName2);
        String toastText = main_tainPageWeb.getToastMessageText();
        Assert.assertTrue(reportWeb.isUpLoad2File(fileName1,fileName2), "File không được upload thành công");
        Assert.assertEquals(toastText, "Thành công",
                "Toast không hiển thị hoặc sai nội dung.");
    }

    @Test(priority = 13)
    public void testUpLoad2File_HoplevaKhong() {
        String fileName1 = "hongmay.jpg";
        String fileName2 = "KHTH-HCQT.docx";
        reportWeb.setUpload2File(fileName1,fileName2);
        String toastText = main_tainPageWeb.getToastErrorMessageText();
        Assert.assertTrue(reportWeb.isUpLoad2File(fileName1,fileName2), "File không được upload thành công");
        Assert.assertEquals(toastText, "Thành công",
                "Toast không hiển thị hoặc sai nội dung.");
    }
}
