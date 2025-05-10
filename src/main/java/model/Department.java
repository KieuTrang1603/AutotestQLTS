package model;

public class Department {
    //asvn
//    public static final String DEPARTMENT_ID_AM = "9484b376-8470-4d06-b1a0-e59179f93ca6";
//    public static final String DEPARTMENT_ID_AU1 = "dac1297a-4448-4a93-96ee-2aa5f0ab936d";
//    public static final String DEPARTMENT_NAME_AM = "Vật Tư TBYT";
//    public static final String DEPARTMENT_NAME_AU1 = "Khoa hàm mặt";
//    public static final String DEPARTMENT_CODE_AM = "1.8.PVT";
    //qltsdemo
    public static final String DEPARTMENT_ID_AM = "f7a1d439-4805-4cd0-a4ae-1a0060010f52";
    public static final String DEPARTMENT_ID_AU = "741ec112-d04e-4958-bd5a-be564fe6bf26";
    public static final String DEPARTMENT_NAME_AM = "Vật tư";
    public static final String DEPARTMENT_NAME_AU1 = "Khoa Gây mê hồi sức";
    public static final String DEPARTMENT_CODE_AM = "3.4VTTBYT";
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
