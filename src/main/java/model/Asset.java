package model;

public class Asset {
    private String code;
    private String name;
    private String management_code, model, serial;
    private String store;
    private String status;
    private String manager_department_id, use_department_id;

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

    public String getUse_department_id() {
        return use_department_id;
    }

    public void setUse_department_id(String use_department_id) {
        this.use_department_id = use_department_id;
    }

    public String getManagement_code() {
        return management_code;
    }

    public void setManagement_code(String management_code) {
        this.management_code = management_code;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getManager_department() {
        return manager_department_id;
    }

    public void setManager_department(String manager_department_id) {
        this.manager_department_id = manager_department_id;
    }

    @Override
    public String toString() {
        return code + " | " + name ;
    }

    public String hienThiTaisan(){return code + " | " + management_code + " | " +
            name + " | " + model +  " | " + serial + " | " + manager_department_id;}
}
