package leda.java.sdk.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.linkedge.sdk.LedaConfig;
import com.aliyun.linkedge.sdk.LedaData;
import com.aliyun.linkedge.sdk.LedaDevice;
import com.aliyun.linkedge.sdk.exception.LedaErrorCode;

/**
 * leda java sdk demo!
 *
 */
public class App extends LedaDevice {
    private String productKey;
    private String deviceName;
    private int temperature;

    public App(String productKey, String deviceName) {
        super(productKey, deviceName);
        this.productKey = productKey;
        this.deviceName = deviceName;
        this.temperature = 20;
    }

    /**
     * @param temperature the temperature to set
     */
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    /**
     * @return the temperature
     */
    public int getTemperature() {
        return this.temperature;
    }

    @Override
    public LedaData getProperties(List<String> propertyNameList) {
        System.out.println("productKey: " + this.productKey + "deviceName: " + this.deviceName);

        HashMap<String, Object> data = new HashMap<String,Object>();
        for (String propertyName : propertyNameList) {
            System.out.println("propertyName: " + propertyName);
            if ("temperature".equals(propertyName)) {
                data.put(propertyName, this.temperature);
            } else {
                System.out.println("unsupport the propertyName: " + propertyName);
            }
        }

        return new LedaData(LedaErrorCode.LE_SUCCESS, data);
    }

    @Override
    public int setProperties(HashMap<String, Object> properties) {
        System.out.println("productKey: " + this.productKey + "deviceName: " + this.deviceName);

        for (Map.Entry<String, Object> element : properties.entrySet()) {
            System.out.println("propertyName: " + element.getKey());
            System.out.println("propertyValue: " + element.getValue());
            if ("temperature".equals(element.getKey())) {
                this.temperature = Integer.parseInt(element.getValue().toString());
            } else {
                System.out.println("unsupport the propertyName: " + element.getKey());
            }
        }

        return LedaErrorCode.LE_SUCCESS;
    }

    @Override
    public LedaData callService(String methodName, HashMap<String, Object> params) {
        System.out.println("productKey: " + this.productKey + "deviceName: " + this.deviceName);

        HashMap<String, Object> data = new HashMap<String,Object>();
        System.out.println("methodName: " + methodName);
        if (params != null) {
            for (Map.Entry<String, Object> element : params.entrySet()) {
                System.out.println("paramName: " + element.getKey());
                System.out.println("paramValue: " + element.getValue());
            }
        }

        return new LedaData(LedaErrorCode.LE_SUCCESS, data);
    }

    public static void main(String[] args) {
        String driverConfig = LedaConfig.getDriverConfig();
        String deviceConfig = LedaConfig.getDeviceConfig();

        if (null != driverConfig) {
            System.out.println("driver config " +  driverConfig);
        }

        if ( null != deviceConfig) {
            System.out.println("device config " +  deviceConfig);
        }

        List<App> deviceList = new ArrayList<App>();
        JSONArray deviceConfigList = JSONArray.parseArray(LedaConfig.getDeviceConfig());
        if (null != deviceConfigList) {
            for (int i = 0; i < deviceConfigList.size(); i++) {
                JSONObject config = deviceConfigList.getJSONObject(i);

                String tslInfo = LedaConfig.getTsl(config.getString("productKey"));
                String tslConfigInfo = LedaConfig.getTsl(config.getString("productKey"));

                if (null != tslInfo) {
                    System.out.println("tsl info " +  tslInfo);
                }
        
                if ( null != tslConfigInfo) {
                    System.out.println("tsl config info " +  tslConfigInfo);
                }

                App device = new App(config.getString("productKey"), config.getString("deviceName"));
                if (null != device) {
                    deviceList.add(device);
                }
            }
        }

        while (true) {
            for (App device : deviceList) {
                HashMap<String, Object> data = new HashMap<String, Object>();
                data.put("temperature", device.getTemperature());

                int ret = LedaErrorCode.LE_SUCCESS;
                ret = device.reportProperties(data);
                ret |= device.reportEvents("high_temperature", data);
                if (ret == LedaErrorCode.LEDA_ERROR_DEVICE_OFFLINE) {
                    System.out.println("the connection with le is disconnected");
                    device.online();
                }
            }

            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                System.out.println("it's have exception {} happen when call thread sleep " + e);
            }
        }
    }
}
