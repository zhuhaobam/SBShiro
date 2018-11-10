package com.bamboobam.sbshiro.config.reposeconfig;

/**
 * 捕获异常，并捕捉封装返回resultbean
 */
public class CheckException extends RuntimeException {

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CheckException() {
        super();
        this.msg = "";
    }

    public CheckException(String msg) {
        super();
        this.msg = msg;
    }


}
