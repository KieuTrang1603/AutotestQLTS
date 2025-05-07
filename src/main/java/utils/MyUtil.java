package utils;

import model.Asset;
import model.enums.AllocationStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MyUtil {
    public static final String DOWNLOAD_PATH = "D:\\Tester\\Auto\\Selenium\\Login\\File";
    public static final String FILE_PATH = "D:\\Tester\\Auto\\Selenium\\Login\\File\\Mẫu nhập tài sản đã cấp phát.xlsx";
    public static final String FILE_PATH1 = "D:\\Tester\\Auto\\Selenium\\Login\\File\\Mẫu nhập tài sản đã cấp phát (1).xlsx";
    public static final String FILE_NAME = "D:\\Tester\\Auto\\Selenium\\Login\\File\\Mẫu nhập tài sản đã cấp phát";

    public static List<String> normalizeList(List<String> list) {
        return list.stream()
                .map(s -> s.trim())        // Bỏ khoảng trắng đầu/cuối
                .collect(Collectors.toList());
    }

    public static List<String> getTrangThaiPhieuAllocations() {
        return Arrays.asList(AllocationStatus.NEW_CREATE.getDescription(),
                AllocationStatus.ISSUED.getDescription(),
                AllocationStatus.WAIT_RECEPT.getDescription());
    }

    public static List<String> getTrangThaiPhieuTransfers() {
        return Arrays.asList("Chờ xác nhận", "Đã xác nhận", "Đã điều chuyển");
    }

    public static String getMaPhong(String department){
        String[] parts = department.split(" - ");
        String result = parts[1].trim();
        return result;
    }

    public static String getNgaychungtu(){
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return today.format(formatter);
    }

    public static String getSobangi(String text){
        String[] parts = text.split("trong");
        String totalRecords = parts[1].trim();
        return totalRecords;
    }

    public static int convertToInt(String numberString) {
        try {
            return Integer.parseInt(numberString.trim());
        } catch (NumberFormatException e) {
            System.err.println("Không thể chuyển đổi chuỗi thành số: " + numberString);
            return -1; // hoặc throw, hoặc return null nếu bạn sửa sang Integer
        }
    }

    public static String getFutureDate(int daysAfterToday) {
        LocalDate futureDate = LocalDate.now().plusDays(daysAfterToday);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return futureDate.format(formatter);
    }

    public static String subtractOneDayFromDate(String inputDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate date = LocalDate.parse(inputDate, formatter);
            LocalDate newDate = date.minusDays(1);
            return newDate.format(formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Ngày không hợp lệ: " + inputDate);
            return null;
        }
    }

    public static String convertDate(String dateStr) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date date = inputFormat.parse(dateStr);
        return outputFormat.format(date);
    }

    public static List<Asset> parseUiAssets(List<String> uiStrings) {
        List<Asset> assets = new ArrayList<>();

        for (String item : uiStrings) {
            String[] parts = item.split("\n", 2);
            String code = parts[0].trim();
            String name = parts.length > 1 ? parts[1].trim() : "";
            Asset taisan = new Asset();
            taisan.setCode(code);
            taisan.setName(name);
            assets.add(taisan);
        }
        return assets;
    }

    public static boolean sosanh2chuoi(List<String> data, List<String> data1){
        List<String> data2 = MyUtil.normalizeList(data);
        System.out.println(data2);
        List<String> data3 = MyUtil.normalizeList(data1);
        System.out.println(data3);
        if(data2.equals(data3)){
            return true;
        }
        else
            return false;
    }
}
