package com.aliyun.linkedge.sdk;

import java.util.HashMap;
import java.util.List;
import com.aliyun.linkedge.sdk.protocol.LedaProxy;
import com.aliyun.linkedge.sdk.exception.LedaErrorCode;

/**
 * the base class of device
 */
public class LedaDevice {
    private String productKey;
    private String deviceName;
    private boolean isOnline;

    /**
     * @param productKey the product key of device
     * @param deviceName the device name of device
     */
    public LedaDevice(String productKey, String deviceName) {
        this.productKey = productKey;
        this.deviceName = deviceName;
        this.isOnline = false;

        LedaProxy.getInstance().proxySetDevice(this.productKey, this.deviceName, this);
    }

    /**
     * request get properties from device, it should override when request get property from device 
     * @param propertyNameList the property name list
     * @return the execution state and result
     */
    public LedaData getProperties(List<String> propertyNameList) {
        return null;
    }

    /**
     * request set properties to device, it should override when request set property to device 
     * @param properties the key-value pair of properties
     * @return the execution state, 0 is success, other is failed
     */
    public int setProperties(HashMap<String, Object> properties) {
        return LedaErrorCode.LE_SUCCESS;
    }

    /**
     * request call the customized method to device, it should override when request call the customized method to device
     * @param methodName the customized method name
     * @param params the input params of method
     * @return the execution state and result
     */
    public LedaData callService(String methodName, HashMap<String, Object> params) {
        return null;
    }

    /**
     * online device to cloud
     * @return the execution state, 0 is success, other is failed
     */
    public int online() {
        if (this.isOnline) {
            return LedaErrorCode.LE_SUCCESS;
        }

        int code = LedaProxy.getInstance().proxyOnline(this.productKey, this.deviceName);
        if (LedaErrorCode.LE_SUCCESS == code) {
            this.isOnline = true;
        }

        return code;
    }

    /**
     * offline device to cloud
     * @return 0 is success, other is failed
     */
    public int offline() {
        if (!this.isOnline) {
            return LedaErrorCode.LE_SUCCESS;
        }

        int code = LedaProxy.getInstance().proxyOffline(this.productKey, this.deviceName);
        if (LedaErrorCode.LE_SUCCESS == code ){
            this.isOnline = false;
        }

        return code;
    }

    /**
     * report device proeprtes to cloud
     * @param properties the property key-value pair
     * @return 0 is success, other is failed
     */
    public int reportProperties(HashMap<String, Object> properties) {
        if (null == properties || 0 == properties.size()) {
            return LedaErrorCode.LE_ERROR_INVALID_PARAM;
        }

        if (!this.isOnline) {
            return LedaErrorCode.LEDA_ERROR_DEVICE_UNREGISTER;
        }

        return LedaProxy.getInstance().proxyReportProperties(this.productKey, this.deviceName, properties);
    }

    /**
     * report device event to cloud
     * @param eventName the event name
     * @param outputData the event key-value pair
     * @return 0 is success, other is failed
     */
    public int reportEvents(String eventName, HashMap<String, Object> outputData) {
        if (null == eventName || 0 == eventName.length()) {
            return LedaErrorCode.LE_ERROR_INVALID_PARAM;
        }

        if (null == outputData || 0 == outputData.size()) {
            return LedaErrorCode.LE_ERROR_INVALID_PARAM;
        }

        if (!this.isOnline) {
            return LedaErrorCode.LEDA_ERROR_DEVICE_UNREGISTER;
        }

        return LedaProxy.getInstance().proxyReportEvents(this.productKey, this.deviceName, eventName, outputData);
    }
}