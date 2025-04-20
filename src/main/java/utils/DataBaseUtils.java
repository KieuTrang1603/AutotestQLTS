package utils;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseUtils {
    private static final String URL = "jdbc:mysql://localhost:3306/assetvn_net";
    private static final String USER = "root";
    private static final String PASSWORD = "kFviK&1466FT@Oct";
    private static final String ORG_ID = "6af1ff18-f0bd-44ce-bf98-69492806016c";
    private static final String status_luu_kho = "44e7ed1b-435d-469b-b8e4-e5291aa39f79";
    private static final String status_nhap_kho = "74044507-9a34-4e5e-86c5-6f70e7510663";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static ResultSet executeQuery(String query) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

    public static int executeUpdate(String query) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        return stmt.executeUpdate(query);
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public static List<String> getNamePhongBan() throws SQLException {
        String query = "SELECT name FROM tbl_department WHERE org_id = ? AND is_active = 1";
        List<String> names = new ArrayList<>();

        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setString(1, ORG_ID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                names.add(rs.getString("name"));
            }
        }

        return names;
    }

    public static List<String> getCodePhongBan() throws SQLException {
        String query = "SELECT code FROM tbl_department WHERE org_id = ? AND is_active = 1";
        List<String> codes = new ArrayList<>();

        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setString(1, ORG_ID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                codes.add(rs.getString("code"));
            }
        }

        return codes;
    }

    public static String getDepartmentNameByCode(String code) throws SQLException {
        String query = "SELECT name FROM tbl_department WHERE code = ? AND org_id = ?";
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, code);
            statement.setString(2, ORG_ID);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("name");
            } else {
                return null;
            }
        }
    }

    public static String getDepartmentIdByCode(String maPTN) throws SQLException {
        String query = "SELECT id FROM tbl_department WHERE org_id = ? AND code = ?";
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, ORG_ID);
        statement.setString(2, maPTN);

        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return rs.getString("id");
        }
        return null;
    }

    public static List<String> getUserByDepartmentId(String departmentId) throws SQLException {
        String query = "SELECT display_name FROM tbl_person " +
                "JOIN tbl_user_deparment ON tbl_person.user_id = tbl_user_deparment.user_id " +
                "WHERE tbl_user_deparment.department_id = ?";
        List<String> result = new ArrayList<>();
        try (
                Connection connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)
        ) {
            stmt.setString(1, departmentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(rs.getString("display_name"));
            }
        }
        return result;
    }

    public static int countAssetsAvailable(String departmentId, String allocationStatusId) throws SQLException {
        String query = "SELECT COUNT(*) FROM tbl_asset " +
                "WHERE org_id = ? " +
                "AND asset_class = 1 " +
                "AND is_deleted = 0 " +
                "AND management_department_id = ? " +
                "AND (status = ? OR status = ?) " +
                "AND id NOT IN (" +
                "    SELECT asset_id FROM tbl_asset_voucher vdt " +
                "    JOIN tbl_voucher v ON vdt.voucher_id = v.id " +
                "    WHERE v.allocation_status_id = ?" +
                ")";

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, ORG_ID);
        statement.setString(2, departmentId);
        statement.setString(3, status_luu_kho);
        statement.setString(4, status_nhap_kho);
        statement.setString(5, allocationStatusId);

        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public static int countAllcationORG() throws SQLException {
        String query = "SELECT COUNT(*) FROM tbl_voucher " +
                "WHERE org_id = ? " +
                "AND asset_class = 1 " +
                "AND type = 2 ";

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, ORG_ID);

        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }
    public static int countAllcationAM(String departmentId) throws SQLException {
        String query = "SELECT COUNT(*) FROM tbl_voucher " +
                "WHERE org_id = ? " +
                "AND asset_class = 1 " +
                "AND type = 2 " +
                "AND handover_department_id = ? ";

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, ORG_ID);
        statement.setString(2, departmentId);

        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public static int countAllcationAU(String departmentId) throws SQLException {
        String query = "SELECT COUNT(*) FROM tbl_voucher " +
                "WHERE org_id = ? " +
                "AND asset_class = 1 " +
                "AND type = 2 " +
                "AND receiver_department_id = ? ";

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, ORG_ID);
        statement.setString(2, departmentId);

        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public static List<String> getAssetsAvailable(String departmentId, String allocationStatusId) throws SQLException, ParseException {
        String query = "SELECT a.code AS asset_code, a.date_of_reception, t.code AS store_code " +
                "FROM tbl_asset a " +
                "LEFT JOIN tbl_store t ON a.store_id = t.id " +
                "WHERE a.org_id = ? " +
                "AND a.asset_class = 1 " +
                "AND a.is_deleted = 0 " +
                "AND a.management_department_id = ? " +
                "AND (a.status = ? OR a.status = ?) " +
                "AND a.id NOT IN (" +
                "    SELECT asset_id FROM tbl_asset_voucher vdt " +
                "    JOIN tbl_voucher v ON vdt.voucher_id = v.id " +
                "    WHERE v.allocation_status_id = ?" +
                ") "+
                "ORDER BY a.date_of_reception DESC " +
                "LIMIT 1";

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, ORG_ID);
        statement.setString(2, departmentId);
        statement.setString(3, status_luu_kho);
        statement.setString(4, status_nhap_kho);
        statement.setString(5, allocationStatusId);

        List<String> result = new ArrayList<>();
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            String assetCode = rs.getString("asset_code");
            String issueDate = MyUtil.convertDate(rs.getString("date_of_reception"));
            String warehouseName = rs.getString("store_code");
            result.add(0,"1");
            result.add(1, issueDate);
            result.add(2, assetCode);
            result.add(3, warehouseName);
        } else {
            System.out.println("Không có tài sản phù hợp.");
        }
        return result;
    }

    public static String getUserNonDepartment(String departmentCode) throws SQLException, ParseException {
        String query = "SELECT username " +
                "FROM tbl_user " +
                "WHERE org_id = ? " +
                "AND id NOT IN (" +
                "    SELECT user_id FROM tbl_user u " +
                "    LEFT JOIN tbl_user_deparment ud ON u.id = ud.user_id " +
                "    LEFT JOIN tbl_department d ON d.id = ud.department_id " +
                "    WHERE d.code = ?" +
                ")"+
                "LIMIT 1";

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, ORG_ID);
        statement.setString(2, departmentCode);

        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return rs.getString("username");
        }
        return null;
    }
}
