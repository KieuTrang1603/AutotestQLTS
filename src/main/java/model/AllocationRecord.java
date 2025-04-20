package model;

public class AllocationRecord {
    public String ngay;
    public String trangThai;
    public String phongGiao;
    public String nguoiGiao;
    public String phongNhan;
    public String nguoiNhan;

    public AllocationRecord(String ngay, String trangThai, String phongGiao, String nguoiGiao, String phongNhan, String nguoiNhan) {
        this.ngay = ngay;
        this.trangThai = trangThai;
        this.phongGiao = phongGiao;
        this.nguoiGiao = nguoiGiao;
        this.phongNhan = phongNhan;
        this.nguoiNhan = nguoiNhan;
    }

    public AllocationRecord(String rawText) {
        String[] lines = rawText.split("\n");
        this.ngay = lines[0].replace("Ngày chứng từ: ", "").trim();
        this.trangThai = lines[1].trim();
        this.phongGiao = lines[2].replace("Phòng bàn giao: ", "").trim();
        this.nguoiGiao = lines[3].replace("Người bàn giao: ", "").trim();
        this.phongNhan = lines[4].replace("Phòng tiếp nhận: ", "").trim();
        this.nguoiNhan = lines[5].replace("Người tiếp nhận: ", "").trim();
    }

    @Override
    public String toString() {
        return ngay + " | " + trangThai + " | " + phongGiao + " | " +nguoiGiao + " | " + phongNhan + " | " + nguoiNhan;
    }
}
