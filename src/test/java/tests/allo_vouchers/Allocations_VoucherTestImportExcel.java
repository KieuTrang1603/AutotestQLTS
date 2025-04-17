package tests.allo_vouchers;

import Dialogs.DS_TSCD_Dialog;
import Dialogs.Import_TSCD_Dialog;
import base.BaseMultiTestWeb;
import base.BaseTestFile;
import drivers.DriverManager;
import helpers.AllocationHelper;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pagesweb.All_VoucherCreatePageWeb;
import pagesweb.All_VoucherPageWeb;
import utils.DataBaseUtils;
import utils.FileHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class Allocations_VoucherTestImportExcel extends BaseTestFile {
    All_VoucherPageWeb all_vou;
    All_VoucherCreatePageWeb all;
    Import_TSCD_Dialog imp;

    @BeforeClass
    public void prepareImportExcelDialog(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        imp = new Import_TSCD_Dialog(DriverManager.getWebDriver());
        all_vou.navigateToAllocation_VoucherPage("pvt1", "123456");
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
        List<String> taisan = AllocationHelper.prepareEmptyNgayCapPhatData();
        FileHelper.insert_empty(taisan);
        imp.uploadFile();
        Assert.assertTrue(imp.isPopupErrorDisplayed(), "Popup lỗi không hiển thị");
        Assert.assertTrue(imp.downloadFileError(), "Không tải được file");
        Assert.assertTrue(FileHelper.empty(1), "Popup lỗi không hiển thị");
    }

    @Test(priority = 3)
    public void testCreateAllocation_emptyMaTaiSan() throws IOException, SQLException, ParseException {
        List<String> taisan = AllocationHelper.prepareEmptyMaTaiSanData();
        FileHelper.insert_empty(taisan);
        imp.uploadFile();
        Assert.assertTrue(imp.isPopupErrorDisplayed(), "Popup lỗi không hiển thị");
        Assert.assertTrue(imp.downloadFileError(), "Không tải được file");
        Assert.assertTrue(FileHelper.empty(2), "Popup lỗi không hiển thị");
    }

    @Test(priority = 4)
    public void testCreateAllocation_emptyMaKho() throws IOException, SQLException, ParseException {
        List<String> taisan = AllocationHelper.prepareEmptyMaKhoData();
        FileHelper.insert_empty(taisan);
        imp.uploadFile();
        Assert.assertTrue(imp.isPopupErrorDisplayed(), "Popup lỗi không hiển thị");
        Assert.assertTrue(imp.downloadFileError(), "Không tải được file");
        Assert.assertTrue(FileHelper.empty(3), "Popup lỗi không hiển thị");
    }

    @Test(priority = 5)
    public void testCreateAllocation_emptyMaPBTN() throws IOException, SQLException, ParseException {
        List<String> taisan = AllocationHelper.prepareEmptyMaPBTNData();
        FileHelper.insert_empty(taisan);
        imp.uploadFile();
        Assert.assertTrue(imp.isPopupErrorDisplayed(), "Popup lỗi không hiển thị");
        Assert.assertTrue(imp.downloadFileError(), "Không tải được file");
        Assert.assertTrue(FileHelper.empty(4), "Popup lỗi không hiển thị");
    }
}
