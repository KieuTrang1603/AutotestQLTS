package model;

public class Asset {
    private String code;
    private String name;
    private String store;
    private String status;
    private String use_department;

    public Asset() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUse_department() {
        return use_department;
    }

    public void setUse_department(String use_department) {
        this.use_department = use_department;
    }

    @Override
    public String toString() {
        return code + " | " + name ;
    }
}
