package com.aliyun.linkedge.sdk;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.linkedge.sdk.protocol.LedaProxy;;

/**
 * the managerment class of driver and device config
 */
public class LedaConfig {
    private static String driverConfig;
    private static String deviceConfig;

    static {
        String config;
        config = LedaProxy.getInstance().proxyGetConfig();
        if (null != config) {
            JSONObject object = JSONObject.parseObject(config);
            if (null != object) {    
                JSONObject driver = object.getJSONObject("config");
                if (null != driver) {
                    LedaConfig.driverConfig = driver.toJSONString();
                }
    
                JSONArray device = object.getJSONArray("deviceList");
                if (null != device) {
                    LedaConfig.deviceConfig = device.toJSONString();
                }
            }
        }
    }

    /**
     * get driver config
     * driver config format(details: https://help.aliyun.com/document_detail/120906.html?spm=a2c4g.11186623.6.577.5acc56c2xg7CPO):
        {
            "json":{
                "ip":"127.0.0.1",
                "port":54321
            },
            "kv":[
                {
                    "key":"ip",
                    "value":"127.0.0.1",
                    "note":"ip地址"
                },
                {
                    "key":"port",
                    "value":"54321",
                    "note":"port端口"
                }
            ],
            "fileList":[
                {
                    "path":"device_config.json"
                }
            ]
        }
     * @return null is failed, other is success
     */
    public static String getDriverConfig() {
        return LedaConfig.driverConfig;
    }

    /**
     * get device config
     * device config format(detail: https://help.aliyun.com/document_detail/120906.html?spm=a2c4g.11186623.6.577.5acc56c2xg7CPO)
        [
            {
                "custom":{
                    "port":12345,
                    "ip":"127.0.0.1"
                },
                "deviceName":"device1",
                "productKey":"xxxxxxxxxxx"
            }
        ]
     * @return null is failed, other is success
     */
    public static String getDeviceConfig() {
        return LedaConfig.deviceConfig;
    }

    /**
     * get device tsl
     * tsl format(detail: https://help.aliyun.com/document_detail/73727.html?spm=a2c4g.11186623.2.11.2325112bcVHWXd#concept-okp-zlv-tdb)
     * @param productKey the product key of device
     * @return null is failed, other is success
     */
    public static String getTsl(String productKey) {
        if (null == productKey || 0 == productKey.length()) {
            return null;
        }

        return LedaProxy.getInstance().proxyGetTsl(productKey);
    }

    /**
     * get device tsl config
     * tsl config format(detail: https://help.aliyun.com/document_detail/73727.html?spm=a2c4g.11186623.2.11.2325112bcVHWXd#concept-okp-zlv-tdb)
     * @param productKey the product key of device
     * @return null is failed, other is success
     */
    public static String getTslConfig(String productKey) {
        if (null == productKey || 0 == productKey.length()) {
            return null;
        }

        return LedaProxy.getInstance().proxyGetTslConfig(productKey);
    }
}