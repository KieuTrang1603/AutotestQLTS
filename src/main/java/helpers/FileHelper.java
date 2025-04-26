package helpers;

import utils.ExcelUtils;
import utils.MyUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileHelper {

    static int fromRow = 5;
    public static boolean isFileDownloaded(String downloadPath, String fileNamePart) {
        File dir = new File(downloadPath);
        File[] dirContents = dir.listFiles();

        if (dirContents != null) {
            for (File file : dirContents) {
                if (file.getName().contains(fileNamePart) && file.getName().endsWith(".xlsx")) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean waitForDownload(String downloadPath, String fileNamePart, int timeoutSeconds) {
        int waited = 0;
        while (waited < timeoutSeconds) {
            File folder = new File(downloadPath);
            File[] matchingFiles = folder.listFiles((dir, name) -> name.contains(fileNamePart) && name.endsWith(".xlsx"));
            File[] downloadingFiles = folder.listFiles((dir, name) -> name.contains(fileNamePart) && name.endsWith(".crdownload"));

            if (matchingFiles != null && matchingFiles.length > 0 && (downloadingFiles == null || downloadingFiles.length == 0)) {
                // Đã có file .xlsx và không còn .crdownload
                return true;
            }

            try {
                Thread.sleep(1000); // chờ 1 giây
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
            waited++;
        }
        return false;
    }

    public static void insert_empty(List<String> newData) throws IOException {
        ExcelUtils.clearDataFromRow(MyUtil.FILE_PATH, fromRow); // Xóa dữ liệu cũ
        ExcelUtils.writeDataToExcel(MyUtil.FILE_PATH, fromRow, newData); // Ghi dữ liệu mới
    }

    public static boolean empty(int a) throws IOException {
        boolean result =false;
        String errorRow = ExcelUtils.getDataFromErrorRow(MyUtil.FILE_PATH1, fromRow).toLowerCase();
        switch (a){
            case 1:
                if (errorRow.contains("ngày cấp phát") && errorRow.contains("trống")) {
                    System.out.println("Hiển thị thông báo chính xác");
                    result= true;
                }
                break;
            case 2:
                if (errorRow.contains("mã tài sản") && errorRow.contains("trống")) {
                    System.out.println("Hiển thị thông báo chính xác");
                    result= true;
                }
                break;
            case 3:
                if (errorRow.contains("mã kho") && errorRow.contains("trống")) {
                    System.out.println("Hiển thị thông báo chính xác");
                    result= true;
                }
                break;
            case 4:
                if (errorRow.contains("mã phòng ban tiếp nhận") && errorRow.contains("trống")) {
                    System.out.println("Hiển thị thông báo chính xác");
                    result= true;
                }
                break;
            case 5:
                if (errorRow.contains("ngày cấp phát") && errorRow.contains("không hợp lệ")) {
                    System.out.println("Hiển thị thông báo chính xác");
                    result= true;
                }
                break;
            case 6:
                if (errorRow.contains("mã tài sản") && errorRow.contains("không tồn tại")) {
                    System.out.println("Hiển thị thông báo chính xác");
                    result= true;
                }
                break;
            case 7:
                if (errorRow.contains("mã kho") && errorRow.contains("không tồn tại")) {
                    System.out.println("Hiển thị thông báo chính xác");
                    result= true;
                }
                break;
            case 8:
                if (errorRow.contains("mã phòng ban tiếp nhận") && errorRow.contains("không tồn tại")) {
                    System.out.println("Hiển thị thông báo chính xác");
                    result= true;
                }
                break;
            case 9:
                if (errorRow.contains("người dùng") && errorRow.contains("không tồn tại")) {
                    System.out.println("Hiển thị thông báo chính xác");
                    result= true;
                }
                break;
            case 10:
                if (errorRow.contains("người dùng") && errorRow.contains("không thuộc phòng ban")) {
                    System.out.println("Hiển thị thông báo chính xác");
                    result= true;
                }
                break;
            case 11:
                if (errorRow.contains("kho") && errorRow.contains("không thuộc quyền quản lý")) {
                    System.out.println("Hiển thị thông báo chính xác");
                    result= true;
                }
                break;
            case 12:
                if (errorRow.contains(" ngày cấp phát") && errorRow.contains("trước ngày tiếp nhận")) {
                    System.out.println("Hiển thị thông báo chính xác");
                    result= true;
                }
                break;
        }
        return result;
    }

    public static void deletedFile(){
        File file = new File(MyUtil.FILE_PATH1);
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                System.out.println("File đã được xóa thành công.");
            } else {
                System.out.println("Không thể xóa file.");
            }
        } else {
            System.out.println("File không tồn tại.");
        }
    }

}
