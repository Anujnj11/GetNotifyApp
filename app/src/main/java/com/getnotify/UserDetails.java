package com.getnotify;

import java.io.Serializable;
import java.util.Date;

public class UserDetails implements Serializable {
//    private String Username;
//    private String MacId;
    private String Message;
    private String CallLog;
    private Boolean IsCall;
    private Boolean IsSMS;
    private String Date;
    private String AESToken;
    private String MobileNumber;

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getAESToken() {
        return AESToken;
    }

    public void setAESToken(String AESToken) {
        this.AESToken = AESToken;
    }

//    public String getUsername() {
//        return Username;
//    }
//
//    public void setUsername(String username) {
//        Username = username;
//    }
//
//    public String getMacId() {
//        return MacId;
//    }
//
//    public void setMacId(String macId) {
//        MacId = macId;
//    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getCallLog() {
        return CallLog;
    }

    public void setCallLog(String callLog) {
        CallLog = callLog;
    }

    public Boolean getCall() {
        return IsCall;
    }

    public void setCall(Boolean call) {
        IsCall = call;
    }

    public Boolean getSMS() {
        return IsSMS;
    }

    public void setSMS(Boolean SMS) {
        IsSMS = SMS;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
