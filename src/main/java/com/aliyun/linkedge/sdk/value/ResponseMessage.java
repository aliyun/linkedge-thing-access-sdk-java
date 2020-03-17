package com.aliyun.linkedge.sdk.value;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

public class ResponseMessage {
    @JSONField(name = "id")
    private String id;

    @JSONField(name = "code")
    private int code;

    @JSONField(name = "data")
    private JSONObject data;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public JSONObject getData() {
        return this.data;
    }

    public ResponseMessage() {
        super();
    }

    public ResponseMessage(String id, int code, JSONObject data) {
        super();
        this.id = id;
        this.code = code;
        this.data = data;
    }
}