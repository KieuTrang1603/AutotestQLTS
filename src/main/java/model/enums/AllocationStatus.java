package model.enums;

public enum AllocationStatus {
    //asvn
//    NEW_CREATE("0b76f01f-a6c0-465d-a36c-9e944efbdd35","Mới tạo"),
//    WAIT_RECEPT("71b6b0a1-d225-44db-95c5-b00a47c4789b","Chờ tiếp nhận"),
//    ISSUED("352868d7-44ee-11ef-b5b3-000c29116a6f","Đã cấp phát"),
//    RETURNED("e68efaa7-cbe9-4bc8-a4be-627bceb232f7","Trả về");
    //qltsdemo
    NEW_CREATE("8262b766-cb9b-4d82-a947-34225706030a","Mới tạo"),
    WAIT_RECEPT("1757de24-993a-461c-b8f2-5ab4ecec6687","Chờ tiếp nhận"),
    ISSUED("87da67e2-cf2f-458a-ac28-c5b37e2efffa","Đã cấp phát"),
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
