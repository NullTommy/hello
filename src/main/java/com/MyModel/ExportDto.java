package com.MyModel;

/**
 * Created by panghaichen on 2018-07-12 10:09
 **/
public class ExportDto {

    private int id;
    private String string = "--All--";
    private Integer status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
