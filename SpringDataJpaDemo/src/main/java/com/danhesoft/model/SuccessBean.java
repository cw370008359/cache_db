package com.danhesoft.model;

/**
 * Created by caowei on 2018/5/9.
 */
public class SuccessBean extends ResultBean {

    public SuccessBean(){
        this.setSuccess(true);
        this.setMessage("success");
    }
}
