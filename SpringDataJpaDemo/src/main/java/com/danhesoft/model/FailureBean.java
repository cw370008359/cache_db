package com.danhesoft.model;

/**
 * Created by caowei on 2018/5/9.
 */
public class FailureBean extends ResultBean {

    public FailureBean(String message){
        this.setSuccess(false);
        this.setMessage(message);
    }
}
