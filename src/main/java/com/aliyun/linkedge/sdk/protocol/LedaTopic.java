package com.aliyun.linkedge.sdk.protocol;

class LedaTopic {
    private String driverId;

    private String getConfigTopic = "edge/driver/%s/x/x/request/iot/driver/proxy/x/get_config";
    private String getConfigReplyTopic = "egde/iot/driver/proxy/x/response/driver/%s/x/x/get_config";

    private String getTslTopic = "edge/driver/%s/%s/x/request/iot/driver/proxy/x/get_tsl";
    private String getTslReplyTopic = "edge/iot/driver/proxy/x/response/driver/%s/%s/x/get_tsl";

    private String getTslConfigTopic = "edge/driver/%s/%s/x/request/iot/driver/proxy/x/get_tsl_ext";
    private String getTslConfigReplyTopic = "edge/iot/driver/proxy/x/response/driver/%s/%s/x/get_tsl_ext";

    private String loginTopic = "edge/device/%s/%s/%s/request/iot/driver/proxy/x/online";
    private String loginReplyTopic = "edge/iot/driver/proxy/x/response/device/%s/%s/%s/online";

    private String logoutTopic = "edge/device/%s/%s/%s/request/iot/driver/proxy/x/offline";
    private String logoutReplyTopic = "edge/iot/driver/proxy/x/response/device/%s/%s/%s/offline";

    private String reportPropertyTopic = "edge/device/%s/%s/%s/broadcast/thing/%s/%s/property";

    private String reportEventTopic = "edge/device/%s/%s/%s/broadcast/thing/%s/%s/event/%s";

    private String getPropertyTopic = "edge/iot/driver/proxy/x/request/device/%s/%s/%s/callservice/thing/service/property/get";
    private String getPropertyReplyTopic = "edge/device/%s/%s/%s/response/iot/driver/proxy/x/callservice/thing/service/property/get";

    private String setPropertyTopic = "edge/iot/driver/proxy/x/request/device/%s/%s/%s/callservice/thing/service/property/set";
    private String setPropertyReplyTopic = "edge/device/%s/%s/%s/response/iot/driver/proxy/x/callservice/thing/service/property/set";

    private String serviceTopic = "edge/iot/driver/proxy/x/request/device/%s/%s/%s/callservice/thing/service/%s";
    private String serviceReplyTopic = "edge/device/%s/%s/%s/response/iot/driver/proxy/x/callservice/thing/service/%s";

    private String upRawTopic = "edge/device/%s/%s/%s/broadcast/thing/%s/%s/up_raw";

    private String downRawTopic = "edge/iot/driver/proxy/x/request/device/%s/%s/%s/down_raw";
    private String downRawReplyTopic = "edge/device/%s/%s/%s/repsonse/iot/driver/proxy/x/down_raw";

    LedaTopic(String driverId) {
        this.driverId = driverId;
    }

    public String getConfigTopic() {
        return String.format(this.getConfigTopic, this.driverId);
    }

    public String getConfigReplyTopic() {
        return String.format(this.getConfigReplyTopic, this.driverId);
    }

    public String getTslTopic(String productKey) {
        return String.format(this.getTslTopic, this.driverId, productKey);
    }

    public String getTslReplyTopic(String productKey) {
        return String.format(this.getTslReplyTopic, this.driverId, productKey);
    }

    public String getTslConfigTopic(String productKey) {
        return String.format(this.getTslConfigTopic, this.driverId, productKey);
    }

    public String getTslConfigReplyTopic(String productKey) {
        return String.format(this.getTslConfigReplyTopic, this.driverId, productKey);
    }

    public String loginTopic(String productKey, String deviceName) {
        return String.format(this.loginTopic, this.driverId, productKey, deviceName);
    }

    public String loginReplyTopic(String productKey, String deviceName) {
        return String.format(this.loginReplyTopic, this.driverId, productKey, deviceName);
    }

    public String logoutTopic(String productKey, String deviceName) {
        return String.format(this.logoutTopic, this.driverId, productKey, deviceName);
    }

    public String logoutReplyTopic(String productKey, String deviceName) {
        return String.format(this.logoutReplyTopic, this.driverId, productKey, deviceName);
    }

    public String reportPropertyTopic(String productKey, String deviceName) {
        return String.format(this.reportPropertyTopic, this.driverId, productKey, deviceName, productKey, deviceName);
    }

    public String reportEventTopic(String productKey, String deviceName, String eventName) {
        return String.format(this.reportEventTopic, this.driverId, productKey, deviceName, productKey, deviceName, eventName);
    }

    public String getPropertyTopic(String productKey, String deviceName) {
        return String.format(this.getPropertyTopic, this.driverId, productKey, deviceName);
    }

    public String getPropertyReplyTopic(String productKey, String deviceName) {
        return String.format(this.getPropertyReplyTopic, this.driverId, productKey, deviceName);
    }

    public String setPropertyTopic(String productKey, String deviceName) {
        return String.format(this.setPropertyTopic, this.driverId, productKey, deviceName);
    }

    public String setPropertyReplyTopic(String productKey, String deviceName) {
        return String.format(this.setPropertyReplyTopic, this.driverId, productKey, deviceName);
    }

    public String serviceTopic(String productKey, String deviceName, String serviceName) {
        return String.format(this.serviceTopic, this.driverId, productKey, deviceName, serviceName);
    }

    public String serviceReplyTopic(String productKey, String deviceName, String serviceName) {
        return String.format(this.serviceReplyTopic, this.driverId, productKey, deviceName, serviceName);
    }

    public String upRawTopic(String productKey, String deviceName) {
        return String.format(this.upRawTopic, this.driverId, productKey, deviceName, productKey, deviceName);
    }

    public String downRawTopic(String productKey, String deviceName) {
        return String.format(this.downRawTopic, this.driverId, productKey, deviceName);
    }

    public String downReplyTopic(String productKey, String deviceName) {
        return String.format(this.downRawReplyTopic, this.driverId, productKey, deviceName);
    }
}