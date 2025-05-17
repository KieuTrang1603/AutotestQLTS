package tests.allo_vouchers.web;

import model.Asset;
import model.Department;
import model.User;
import model.UsersRole;
import org.testng.annotations.AfterMethod;
import pagesweb.Assets_Page;
import pagesweb.Import_TSCD_Dialog;
import base.BaseTestFile;
import drivers.DriverManager;
import helpers.AllocationHelper;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pagesweb.All_VoucherCreatePageWeb;
import pagesweb.All_VoucherPageWeb;
import helpers.FileHelper;
import utils.DataBaseUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class Allocations_VoucherTestImportExcel extends BaseTestFile {
    All_VoucherPageWeb all_vou;
    All_VoucherCreatePageWeb all;
    Import_TSCD_Dialog imp;
    Assets_Page as;

    @BeforeClass
    public void prepareImportExcelDialog(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        imp = new Import_TSCD_Dialog(DriverManager.getWebDriver());
        UsersRole Users = null;
        User user = Users.getUserByRole("AM");
        all_vou.navigateToAllocation_VoucherPage(user.getUsername(), user.getPassword());
        all_vou.closeMenu();
        all_vou.Nhap_Btn_click();
        imp.taiMau_click();
    }
    @BeforeMethod
    public void deleteFileError(){
        FileHelper.deletedFile();
    }

    @Test(priority = 1)
    public void Taimau(){
        Assert.assertTrue(imp.downloadFile(), "Không tải được file");
    }

    @Test(priority = 2)
    public void testCreateAllocation_emptyNgayChungTu() throws IOException, SQLException, ParseException {
        List<String> taisan = AllocationHelper.prepareEmptyData(1);
        FileHelper.insert_empty(taisan);
        imp.uploadFile();
        Assert.assertTrue(imp.isPopupErrorDisplayed(), "Popup lỗi không hiển thị");
        Assert.assertTrue(imp.downloadFileError(), "Không tải được file");
        Assert.assertTrue(FileHelper.empty(1), "Thông báo lỗi không hiển thị");
    }

    @Test(priority = 3)
    public void testCreateAllocation_emptyMaTaiSan() throws IOException, SQLException, ParseException {
        List<String> taisan = AllocationHelper.prepareEmptyData(2);
        FileHelper.insert_empty(taisan);
        imp.uploadFile();
        Assert.assertTrue(imp.isPopupErrorDisplayed(), "Popup lỗi không hiển thị");
        Assert.assertTrue(imp.downloadFileError(), "Không tải được file");
        Assert.assertTrue(FileHelper.empty(2), "Thông báo lỗi không hiển thị");
    }

    @Test(priority = 4)
    public void testCreateAllocation_emptyMaKho() throws IOException, SQLException, ParseException {
        List<String> taisan = AllocationHelper.prepareEmptyData(3);
        FileHelper.insert_empty(taisan);
        imp.uploadFile();
        Assert.assertTrue(imp.isPopupErrorDisplayed(), "Popup lỗi không hiển thị");
        Assert.assertTrue(imp.downloadFileError(), "Không tải được file");
        Assert.assertTrue(FileHelper.empty(3), "Thông báo lỗi không hiển thị");
    }

    @Test(priority = 5)
    public void testCreateAllocation_emptyMaPBTN() throws IOException, SQLException, ParseException {
        List<String> taisan = AllocationHelper.prepareEmptyMaPBTNData();
        FileHelper.insert_empty(taisan);
        imp.uploadFile();
        Assert.assertTrue(imp.isPopupErrorDisplayed(), "Popup lỗi không hiển thị");
        Assert.assertTrue(imp.downloadFileError(), "Không tải được file");
        Assert.assertTrue(FileHelper.empty(4), "Thông báo lỗi không hiển thị");
    }

    @Test(priority = 6)
    public void testCreateAllocation_inCorrectNgayCapPhat() throws IOException, SQLException, ParseException {
        List<String> taisan = AllocationHelper.isCorrectData(1);
        FileHelper.insert_empty(taisan);
        imp.uploadFile();
        Assert.assertTrue(imp.isPopupErrorDisplayed(), "Popup lỗi không hiển thị");
        Assert.assertTrue(imp.downloadFileError(), "Không tải được file");
        Assert.assertTrue(FileHelper.empty(5), "Thông báo lỗi không hiển thị");
    }

    @Test(priority = 7)
    public void testCreateAllocation_inCorrectMaTaiSan() throws IOException, SQLException, ParseException {
        List<String> taisan = AllocationHelper.isCorrectData(2);
        FileHelper.insert_empty(taisan);
        imp.uploadFile();
        Assert.assertTrue(imp.isPopupErrorDisplayed(), "Popup lỗi không hiển thị");
        Assert.assertTrue(imp.downloadFileError(), "Không tải được file");
        Assert.assertTrue(FileHelper.empty(6), "Thông báo lỗi không hiển thị");
    }

    @Test(priority = 8)
    public void testCreateAllocation_inCorrectMaKho() throws IOException, SQLException, ParseException {
        List<String> taisan = AllocationHelper.isCorrectData(3);
        FileHelper.insert_empty(taisan);
        imp.uploadFile();
        Assert.assertTrue(imp.isPopupErrorDisplayed(), "Popup lỗi không hiển thị");
        Assert.assertTrue(imp.downloadFileError(), "Không tải được file");
        Assert.assertTrue(FileHelper.empty(7), "Thông báo lỗi không hiển thị");
    }

    @Test(priority = 9)
    public void testCreateAllocation_inCorrectMaPBTN() throws IOException, SQLException, ParseException {
        List<String> taisan = AllocationHelper.isCorrectMaPBTNData();
        FileHelper.insert_empty(taisan);
        imp.uploadFile();
        Assert.assertTrue(imp.isPopupErrorDisplayed(), "Popup lỗi không hiển thị");
        Assert.assertTrue(imp.downloadFileError(), "Không tải được file");
        Assert.assertTrue(FileHelper.empty(8), "Thông báo lỗi không hiển thị");
    }

    @Test(priority = 10)
    public void testCreateAllocation_inCorrectNguoiDung() throws IOException, SQLException, ParseException {
        List<String> taisan = AllocationHelper.isCorrectData(5);
        FileHelper.insert_empty(taisan);
        imp.uploadFile();
        Assert.assertTrue(imp.isPopupErrorDisplayed(), "Popup lỗi không hiển thị");
        Assert.assertTrue(imp.downloadFileError(), "Không tải được file");
        Assert.assertTrue(FileHelper.empty(9), "Thông báo lỗi không hiển thị");
    }

    @Test(priority = 11)
    public void testCreateAllocation_nondepartmentaluser() throws IOException, SQLException, ParseException {
        List<String> taisan = AllocationHelper.isCorrectNguoiDungData();
        FileHelper.insert_empty(taisan);
        imp.uploadFile();
        Assert.assertTrue(imp.isPopupErrorDisplayed(), "Popup lỗi không hiển thị");
        Assert.assertTrue(imp.downloadFileError(), "Không tải được file");
        Assert.assertTrue(FileHelper.empty(10), "Thông báo lỗi không hiển thị");
    }

//    @Test(priority = 12)
//    public void testCreateAllocation_ChonTSDaCapPhat() throws IOException, SQLException, ParseException {
//        List<String> taisan = AllocationHelper.isCorrectData(5);
//        FileHelper.insert_empty(taisan);
//        imp.uploadFile();
//        Assert.assertTrue(imp.isPopupErrorDisplayed(), "Popup lỗi không hiển thị");
//        Assert.assertTrue(imp.downloadFileError(), "Không tải được file");
//        Assert.assertTrue(FileHelper.empty(11), "Thông báo lỗi không hiển thị");
//    }

    @Test(priority = 13)
    public void testCreateAllocation_NCPnhoNTN() throws IOException, SQLException, ParseException {
        List<String> taisan = AllocationHelper.isCorrectNgayCapPhatData();
        FileHelper.insert_empty(taisan);
        imp.uploadFile();
        Assert.assertTrue(imp.isPopupErrorDisplayed(), "Popup lỗi không hiển thị");
        Assert.assertTrue(imp.downloadFileError(), "Không tải được file");
        Assert.assertTrue(FileHelper.empty(12), "Thông báo lỗi không hiển thị");
    }
    @Test(priority = 14)
    public void testCreateAllocation_NCPlonNHT() throws IOException, SQLException, ParseException {
        List<String> taisan = AllocationHelper.ngayCapPhatlonNgayHienTaiData();
        FileHelper.insert_empty(taisan);
        imp.uploadFile();
        Assert.assertTrue(imp.isPopupErrorDisplayed(), "Popup lỗi không hiển thị");
        Assert.assertTrue(imp.downloadFileError(), "Không tải được file");
        Assert.assertTrue(FileHelper.empty(13), "Thông báo lỗi không hiển thị");
    }

    @Test(priority = 15)
    public void testCreateAllocation_TrangThaiDaCapPhat() throws IOException, SQLException, ParseException {
        List<String> taisan = AllocationHelper.CorrectData();
        Asset asset = new Asset();
        asset.setCode(taisan.get(2));
        String tenPBSD = DataBaseUtils.getDepartmentNameByCode(taisan.get(4));
        asset.setUse_department_id(tenPBSD);
        FileHelper.insert_empty(taisan);
        imp.uploadFile();
        String toastText = all_vou.getToastMessageText();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //Kiêm tra hiển thị thông báo
        Assert.assertTrue(toastText.contains("thành công"),
                "Toast không hiển thị hoặc sai nội dung.");
        // Kiểm tra xem về trang danh sách cấp phát
        Assert.assertFalse(all.isAllocatonDialogDisplayed(),
                "Form chưa bị ẩn sau khi click Lưu với dữ liệu chuẩn");
        Assert.assertTrue(all_vou.checkBanghiCapphat(asset.getCode(),3, Department.DEPARTMENT_NAME_AM, tenPBSD),
                "Chưa hiển thị bản ghi Cấp phát");
        // Kiểm tra tài sản ở màn danh sách
        as= new Assets_Page(DriverManager.getWebDriver());
        as.navigateToAssetsPage();
        as.closeMenu();
        // Kiểm tra xem dữ liệu tài sản sau cấp phát
        Assert.assertTrue(as.checkTSCapPhat(asset.getCode(), 3, asset.getUse_department_id()),
                "Trạng thái Tài sản bị hiển thị sai");
    }

    @AfterMethod
    public void reset(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
