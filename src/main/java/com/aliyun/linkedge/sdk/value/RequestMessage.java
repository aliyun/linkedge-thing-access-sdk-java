package com.aliyun.linkedge.sdk.value;

import com.alibaba.fastjson.annotation.JSONField;

public class RequestMessage {
    @JSONField(name = "id")
    private String id;

    @JSONField(name = "version")
    private String version;

    @JSONField(name = "params")
    private Object params;

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

    public void setParams(Object params) {
        this.params = params;
    }

    public Object getParams() {
        return this.params;
    }

    public RequestMessage() {
        super();
    }

    public RequestMessage(String id, String version, Object params) {
        super();
        this.id = id;
        this.version = version;
        this.params = params;
    }
}