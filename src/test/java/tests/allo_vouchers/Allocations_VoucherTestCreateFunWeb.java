package tests.allo_vouchers;

import base.BaseTestWeb;
import drivers.DriverManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pagesweb.All_VoucherCreatePageWeb;
import pagesweb.All_VoucherPageWeb;
import utils.DataBaseUtils;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Allocations_VoucherTestCreateFunWeb extends BaseTestWeb {
    All_VoucherPageWeb all_vou;
    @BeforeClass
    public void prepareVoucherCreatePage(){
        all_vou = new All_VoucherPageWeb(DriverManager.getWebDriver());
        all_vou.navigateToAllocation_VoucherPage();
        all_vou.All_Btn_click();
    }
    @Test
    public void testNgayTaoPhieu_ShouldBeToday_AndReadonly() {
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        // 1. Kiểm tra giá trị là ngày hôm nay
        String expectedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String actualDate = all.getNgayTaoPhieuValue();
        Assert.assertEquals(actualDate, expectedDate, "Ngày tạo phiếu không phải là ngày hiện tại!");
        // 2. Kiểm tra không chỉnh sửa được
        boolean isReadonly = all.isNgayTaoPhieuReadonly();
        Assert.assertTrue(isReadonly, "Trường 'Ngày tạo phiếu' không bị readonly!");
    }

    @Test
    public void testNgayChungTu_ShouldBeToday_AndEdit() {
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        // 1. Kiểm tra giá trị là ngày hôm nay
        String expectedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String actualDate = all.getNgayTaoPhieuValue();
        Assert.assertEquals(actualDate, expectedDate, "Ngày chứng từ không khớp với ngày hiện tại!");
        // 2. Kiểm tra chỉnh sửa được
        boolean isEdit = all.isNgayChungTuEditable();
        Assert.assertTrue(isEdit, "Trường 'Ngày chứng từ' không được chỉnh sửa!");
    }

    @Test
    public void testNhapNgayKhongHopLe(){
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        all.setNgayChungTuInput("29/02/202");
        // Kiểm tra trường có bị đánh dấu là không hợp lệ không
        Assert.assertTrue(all.isNgayChungTuKhongHopLe(), "Không báo lỗi khi nhập ngày không hợp lệ");
    }

    @Test
    public void testPhongBanGiao() {
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        String query = "SELECT * FROM tbl_department WHERE code = '1.8.PVT' and org_id = '6af1ff18-f0bd-44ce-bf98-69492806016c'";
        try {
            ResultSet resultSet = DataBaseUtils.executeQuery(query);
            if (resultSet.next()) {
                String data = resultSet.getString("name");
                System.out.println("Data from database: " + data);
                // Sử dụng dữ liệu này để kiểm tra hoặc thiết lập dữ liệu test
                Assert.assertTrue(all.checkPhongBanBanGiao(data), "Trường 'Phòng ban bàn giao' không hiển thị đúng!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testNguoiBanGiao(){
        All_VoucherCreatePageWeb all = new All_VoucherCreatePageWeb(DriverManager.getWebDriver());
        String query = "SELECT * FROM tbl_person JOIN tbl_user_deparment on tbl_person.user_id = tbl_user_deparment.user_id WHERE tbl_user_deparment.department_id ='9484b376-8470-4d06-b1a0-e59179f93ca6'";
        try {
            ResultSet resultSet = DataBaseUtils.executeQuery(query);
            List<String> displayNames = new ArrayList<>();
            while (resultSet.next()) {
                displayNames.add(resultSet.getString("display_name"));
                System.out.println("Data from database: " + displayNames);
            }
            Assert.assertTrue(all.checkNguoiBanGiao(displayNames), "Danh sách 'Người bàn giao' chưa hiển thị đúng!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
