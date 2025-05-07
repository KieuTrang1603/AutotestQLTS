package model.enums;

public enum AssetStatus {
    NEW_IN_STORAGE("44e7ed1b-435d-469b-b8e4-e5291aa39f79","Lưu kho, mới"),
    IN_USE("7bdf25c0-ad29-45f6-84dc-de4e885778a3","Đang sử dụng"),
    RETURNED_TO_STORAGE("74044507-9a34-4e5e-86c5-6f70e7510663","Nhập kho"),
    UNDER_REPAIR("e7aacfdd-e5ad-4aaa-b700-c9180723d0a7","Đang sửa chữa-bảo dưỡng"),
    BROKEN_PROPOSED_RETURN("d499f4ca-0b65-4eb6-b96d-7e1eb5918001","Đã hỏng, đề xuất nhập kho"),
    DISPOSED("6540daa4-da6c-4a69-ab1f-1bee10d9a30c","Đã thanh lý, tiêu hủy"),
    TRANSFERRED("7d7e97bb-815f-4415-9ea5-f190e3e698fa","Chuyển đi"),
    PROPOSED_DISPOSAL("75357b9d-3900-4c6b-8690-e2e9aa9d632e","Đề xuất thanh lý"),
    DRAFT("b5b34fde-440b-11ef-b5b3-000c29116a6f","Bản nháp");

    private final String code;
    private final String description;

    AssetStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }
}
