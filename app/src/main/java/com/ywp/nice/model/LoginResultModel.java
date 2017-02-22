package com.ywp.nice.model;

/**
 * Created by Administrator on 2016/12/21.
 */

public class LoginResultModel {

    private String code ;
    private String result ;


    public LoginResultModel(String code, String result){
        this.code = code;
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "LoginResultModel{" +
                "code='" + code + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
