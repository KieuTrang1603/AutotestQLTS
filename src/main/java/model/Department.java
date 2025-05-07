package model;

public class Department {
    public static final String DEPARTMENT_ID_AM = "9484b376-8470-4d06-b1a0-e59179f93ca6";
    public static final String DEPARTMENT_ID_AU = "f8359400-b129-4486-abae-b694589fcc1e";
    public static final String DEPARTMENT_ID_AU1 = "dac1297a-4448-4a93-96ee-2aa5f0ab936d";
    public static final String DEPARTMENT_NAME_AM = "Vật tư TBYT";
    public static final String DEPARTMENT_NAME_AU1 = "Khoa hàm mặt";
    private String id;
    private String code;
    private String ten;

    public Department() {
    }

    public Department(String id, String code, String ten) {
        this.id = id;
        this.code = code;
        this.ten = ten;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
