package com.aliyun.linkedge.sdk;

import java.util.HashMap;

/**
 * the code and result of method execution
 */
 public class LedaData {
    public int code;
    public HashMap<String, Object> data;

    /**
     * @param code the code of method execution
     * @param data the result of method execution
     */
    public LedaData(int code, HashMap<String, Object> data) {
        this.code = code;
        this.data = data;
    }

    /**
     * set the code of method execution
     * @param code the code of method execution
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * get the code of method execution
     * @return the code of method execution
     */
    public int getCode() {
        return this.code;
    }

    /**
     * set the result of method execution
     * @param data the result of method execution
     */
    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    /**
     * get the result of method execution
     * @return the result of method execution
     */
    public HashMap<String, Object> getData() {
        return this.data;
    }
 }