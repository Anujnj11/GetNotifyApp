package com.getnotify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetEncrptokenRes {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("AESToken")
    @Expose
    private String aESToken;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAESToken() {
        return aESToken;
    }

    public void setAESToken(String aESToken) {
        this.aESToken = aESToken;
    }

}