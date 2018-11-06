package com.bamboobam.sbshiro.config.resultconfig;

import java.io.Serializable;

/**
 * 封装返回的bean
 * 0成功
 * 1失败
 */
public class ResultBean<T> implements Serializable {

    private T data;
    private String msg = "success";
    private int code = 0;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ResultBean() {
        super();
    }

    public ResultBean(T data) {
        super();
        this.data = data;
    }

    public ResultBean(Throwable e) {
        super();
        this.msg = e.toString();
        this.code = 1;
    }
}
