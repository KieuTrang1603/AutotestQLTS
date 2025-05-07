package model.enums;

public enum AllocationStatus {
    NEW_CREATE("0b76f01f-a6c0-465d-a36c-9e944efbdd35","Mới tạo"),
    WAIT_RECEPT("71b6b0a1-d225-44db-95c5-b00a47c4789b","Chờ tiếp nhận"),
    ISSUED("352868d7-44ee-11ef-b5b3-000c29116a6f","Đã cấp phát"),
    RETURNED("e68efaa7-cbe9-4bc8-a4be-627bceb232f7","Trả về");

    private final String code;
    private final String description;

    AllocationStatus(String code, String description) {
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
