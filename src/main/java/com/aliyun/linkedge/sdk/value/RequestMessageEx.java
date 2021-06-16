package com.aliyun.linkedge.sdk.value;

import com.alibaba.fastjson.annotation.JSONField;

public class RequestMessageEx {
    @JSONField(name = "id")
    private String id;

    @JSONField(name = "version")
    private String version;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }

    public RequestMessageEx() {
        super();
    }

    public RequestMessageEx(String id, String version) {
        super();
        this.id = id;
        this.version = version;
    }
}