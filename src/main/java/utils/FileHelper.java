package utils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
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

//    public static File getLatestErrorFile(String downloadDir, String filePrefix) {
//        File dir = new File(downloadDir);
//        File[] matchingFiles = dir.listFiles((dir1, name) ->
//                name.startsWith(filePrefix) && name.endsWith(".xlsx") && !name.contains(".crdownload")
//        );
//
//        if (matchingFiles == null || matchingFiles.length == 0) {
//            return null;
//        }
//
//        // Sắp xếp theo thời gian sửa đổi gần nhất
//        Arrays.sort(matchingFiles, Comparator.comparingLong(File::lastModified).reversed());
//
//        return matchingFiles[0];
//    }

    public static boolean empty(int a) throws IOException {
        boolean result =false;
        switch (a){
            case 1:
                if(ExcelUtils.getDataFromErrorRow(MyUtil.FILE_PATH1, fromRow).contains("Ngày cấp phát bị trống")){
                    System.out.println("Hiển thị thông báo chính xác");
                    result= true;
                }
                break;
            case 2:
                if(ExcelUtils.getDataFromErrorRow(MyUtil.FILE_PATH1, fromRow).contains("Mã tài sản bị trống")){
                    System.out.println("Hiển thị thông báo chính xác");
                    result= true;
                }
                break;
            case 3:
                if(ExcelUtils.getDataFromErrorRow(MyUtil.FILE_PATH1, fromRow).contains("Mã kho trống")){
                    System.out.println("Hiển thị thông báo chính xác");
                    result= true;
                }
                break;
            case 4:
                if(ExcelUtils.getDataFromErrorRow(MyUtil.FILE_PATH1, fromRow).contains(" Mã phòng ban tiếp nhận trống hoặc không khớp với dữ liệu.")){
                    result= true;
                }
                break;
            case 5:
                if(ExcelUtils.getDataFromErrorRow(MyUtil.FILE_PATH1, fromRow).contains("Mã kho không đúng")){
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
