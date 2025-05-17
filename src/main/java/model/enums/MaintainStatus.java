package model.enums;

public enum MaintainStatus {
    PENDING(1,"Chờ xử lý"),
    IN_PROCESS(2,"Đang xử lý"),
    PROCESSED(3,"Đã xử lý");
    private final int code;
    private final String description;

    MaintainStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCode() {
        return code;
    }
}
