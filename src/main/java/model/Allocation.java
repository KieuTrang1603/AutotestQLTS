package model;

public class Allocation {
    public static final String STATUS_ALLOCATION_CTN = "71b6b0a1-d225-44db-95c5-b00a47c4789b";
    private String ngay;
    private String trangThai;
    private String phongGiao;
    private String nguoiGiao;
    private String phongNhan;
    private String nguoiNhan;

    public Allocation(String ngay, String trangThai, String phongGiao, String nguoiGiao, String phongNhan, String nguoiNhan) {
        this.ngay = ngay;
        this.trangThai = trangThai;
        this.phongGiao = phongGiao;
        this.nguoiGiao = nguoiGiao;
        this.phongNhan = phongNhan;
        this.nguoiNhan = nguoiNhan;
    }

    public Allocation(String rawText) {
        String[] lines = rawText.split("\n");
        this.ngay = lines[0].replace("Ngày chứng từ: ", "").trim();
        this.trangThai = lines[1].trim();
        this.phongGiao = lines[2].replace("Phòng bàn giao: ", "").trim();
        this.nguoiGiao = lines[3].replace("Người bàn giao: ", "").trim();
        this.phongNhan = lines[4].replace("Phòng tiếp nhận: ", "").trim();
        this.nguoiNhan = lines[5].replace("Người tiếp nhận: ", "").trim();
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getPhongGiao() {
        return phongGiao;
    }

    public void setPhongGiao(String phongGiao) {
        this.phongGiao = phongGiao;
    }

    public String getNguoiGiao() {
        return nguoiGiao;
    }

    public void setNguoiGiao(String nguoiGiao) {
        this.nguoiGiao = nguoiGiao;
    }

    public String getPhongNhan() {
        return phongNhan;
    }

    public void setPhongNhan(String phongNhan) {
        this.phongNhan = phongNhan;
    }

    public String getNguoiNhan() {
        return nguoiNhan;
    }

    public void setNguoiNhan(String nguoiNhan) {
        this.nguoiNhan = nguoiNhan;
    }

    @Override
    public String toString() {
        return ngay + " | " + trangThai + " | " + phongGiao + " | " + nguoiGiao + " | " + phongNhan + " | " + nguoiNhan;
    }
}
