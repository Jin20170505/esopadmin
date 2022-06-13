package com.jeeplus.modules.u8api.bean;

public class U8ApiResult {

    private String code; // 0/1
    private String msg;
    private boolean success; // false / true

    public String getCode() {
        return code;
    }

    public U8ApiResult setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public U8ApiResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public U8ApiResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }
}
