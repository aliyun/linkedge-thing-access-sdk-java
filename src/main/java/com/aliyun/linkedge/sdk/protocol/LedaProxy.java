package com.aliyun.linkedge.sdk.protocol;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.linkedge.sdk.LedaData;
import com.aliyun.linkedge.sdk.LedaDevice;
import com.aliyun.linkedge.sdk.value.RequestMessage;
import com.aliyun.linkedge.sdk.value.ResponseMessage;
import com.aliyun.linkedge.sdk.exception.LedaErrorCode;
import com.aliyun.linkedge.sdk.mqtt.MqttClientWrapper;

public class LedaProxy {
    private static LedaProxy ledaProxy = new LedaProxy();

    public static LedaProxy getInstance() {
        return ledaProxy;
    }

    private final Logger logger = LoggerFactory.getLogger(LedaProxy.class);

    private final static String PROTOCOL_VERSION = "1.0";
    private final static int SYNC_SEND_TIMEOUT = 10 * 1000;
    private final static int THREAD_POOL_SIZE = 5;

    private final static String MQTT_BROKER_IP = "127.0.0.1";
    private final static short MQTT_BROKER_PORT = 9883;

    private String driverId = System.getenv("FUNCTION_ID");
    private String driverName = System.getenv("FUNCTION_NAME");

    private long msgId = 1;
    private LedaTopic ledaTopic;
    private MqttClientWrapper mqttClient;
    private Map<String, LedaDevice> deviceList;
    private Map<String, ResponseMessage> rspMsgQueque;
    private ExecutorService threadPool;

    private LedaProxy() {
        this.initMqttClientWrapper();

        this.deviceList = new Hashtable<String, LedaDevice>();
        this.ledaTopic = new LedaTopic(this.driverId);
        this.rspMsgQueque = new Hashtable<String, ResponseMessage>();
        this.threadPool = new ThreadPoolExecutor(LedaProxy.THREAD_POOL_SIZE,
                                                 LedaProxy.THREAD_POOL_SIZE,
                                                 0,
                                                 TimeUnit.MILLISECONDS,
                                                 new LinkedBlockingQueue<Runnable>());
    }

    private void initMqttClientWrapper() {
        while (true) {
            try {
                if (null == mqttClient) {
                    if (System.getenv("FCBASE_IPADDR") != null) {
                        // container driver
                        this.mqttClient = new MqttClientWrapper(this.driverName, System.getenv("FCBASE_IPADDR"), LedaProxy.MQTT_BROKER_PORT);
                    } else {
                        // process driver
                        this.mqttClient = new MqttClientWrapper(this.driverName, LedaProxy.MQTT_BROKER_IP, LedaProxy.MQTT_BROKER_PORT);
                    }

                    this.mqttClient.setUserName("driver&" + this.driverName + "&" + this.driverId);
                    this.mqttClient.setPassword(this.driverId);
                    this.mqttClient.setCleanSession(true);
                    this.mqttClient.setAutoReconnect(true);
                    this.mqttClient.setQos(0);
                    this.mqttClient.setRetained(false);
                    this.mqttClient.setProxyCallback(this);
                }

                if (null != mqttClient) {
                    this.mqttClient.connect();
                    String [] topicFilters = {
                        String.format("edge/iot/driver/proxy/x/response/driver/%s/x/x/get_config", this.driverId),
                        String.format("edge/iot/driver/proxy/x/response/driver/%s/+/x/get_tsl", this.driverId),
                        String.format("edge/iot/driver/proxy/x/response/driver/%s/+/x/get_tsl_ext", this.driverId),
                        String.format("edge/iot/driver/proxy/x/response/device/%s/+/+/online", this.driverId),
                        String.format("edge/iot/driver/proxy/x/response/device/%s/+/+/offline", this.driverId),
                        String.format("edge/iot/driver/proxy/x/request/device/%s/+/+/callservice/thing/service/property/get", this.driverId),
                        String.format("edge/iot/driver/proxy/x/request/device/%s/+/+/callservice/thing/service/property/set", this.driverId),
                        String.format("edge/iot/driver/proxy/x/request/device/%s/+/+/callservice/thing/service/+", this.driverId)
                    };
                    this.mqttClient.subscribe(topicFilters);
                    break;
                }
            } catch (Exception e) {
                this.logger.warn("it's have exception {} happen when init mqtt client", e);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                this.logger.warn("it's have exception {} happen when call thread sleep", e);
            }
        }
    }

    public LedaTopic getLedaTopic() {
        return this.ledaTopic;
    }

    public Map<String, LedaDevice> getDeviceList() {
        return this.deviceList;
    }

    private void setMsgId(long msgId) {
        this.msgId = msgId;
    }

    private long getMsgId() {
        return this.msgId++;
    }

    private int sendRequest(String topic) {
        int ret = LedaErrorCode.LE_SUCCESS;
        RequestMessage reqMsg = new RequestMessage(String.valueOf(this.getMsgId()),
                                                   LedaProxy.PROTOCOL_VERSION,
                                                   new JSONObject());
        try {
            this.mqttClient.publish(topic, JSON.toJSONString(reqMsg));
        } catch (Exception e) {
            this.logger.warn("it's have exception {} happen when publish topic {}", e, topic);
            ret = LedaErrorCode.LE_ERROR_UNKNOWN;
        }

        return ret;
    }

    private int sendRequest(String topic, JSONObject params) {
        int ret = LedaErrorCode.LE_SUCCESS;
        RequestMessage reqMsg = new RequestMessage(String.valueOf(this.getMsgId()),
                                                   LedaProxy.PROTOCOL_VERSION,
                                                   params);
        try {
            this.mqttClient.publish(topic, JSON.toJSONString(reqMsg));
        } catch (Exception e) {
            this.logger.warn("it's have exception {} happen when publish topic {}", e, topic);
            ret = LedaErrorCode.LE_ERROR_UNKNOWN;
        }

        return ret;
    }

    private ResponseMessage sendSyncRequest(String topic) {
        RequestMessage reqMsg = new RequestMessage(String.valueOf(this.getMsgId()),
                                                   LedaProxy.PROTOCOL_VERSION,
                                                   new JSONObject());
        ResponseMessage rspMsg = new ResponseMessage();
        try {
            this.rspMsgQueque.put(reqMsg.getId(), rspMsg);
            synchronized (rspMsg) {
                this.mqttClient.publish(topic, JSON.toJSONString(reqMsg));

                long startTimeStamp = System.currentTimeMillis();
                rspMsg.wait(LedaProxy.SYNC_SEND_TIMEOUT);
                long endTimeStamp = System.currentTimeMillis();
                if (endTimeStamp - startTimeStamp > LedaProxy.SYNC_SEND_TIMEOUT) {
                    rspMsg.setCode(LedaErrorCode.LE_ERROR_TIMEOUT);
                }
            }
        } catch (Exception e) {
            this.logger.warn("it's have exception {} happen when publish topic {}", e, topic);
            rspMsg.setCode(LedaErrorCode.LE_ERROR_UNKNOWN);
        } finally {
            try {
                this.rspMsgQueque.remove(reqMsg.getId());
            } catch (Exception e) {
                this.logger.warn("it's have exception {} happen when remove msgId {} to topic {}", e, reqMsg.getId(), topic);
            }
        }
        
        return rspMsg;
    }

    private ResponseMessage sendSyncRequest(String topic, JSONObject params) {
        RequestMessage reqMsg = new RequestMessage(String.valueOf(this.getMsgId()),
                                                   LedaProxy.PROTOCOL_VERSION,
                                                   params);
        ResponseMessage rspMsg = new ResponseMessage();
        try {
            this.rspMsgQueque.put(reqMsg.getId(), rspMsg);
            synchronized (rspMsg) {
                this.mqttClient.publish(topic, JSON.toJSONString(reqMsg));

                long startTimeStamp = System.currentTimeMillis();
                rspMsg.wait(LedaProxy.SYNC_SEND_TIMEOUT);
                long endTimeStamp = System.currentTimeMillis();
                if (endTimeStamp - startTimeStamp > LedaProxy.SYNC_SEND_TIMEOUT) {
                    rspMsg.setCode(LedaErrorCode.LE_ERROR_TIMEOUT);
                }
            }
        } catch (Exception e) {
            this.logger.warn("it's have exception {} happen when publish topic {}", e, topic);
            rspMsg.setCode(LedaErrorCode.LE_ERROR_UNKNOWN);
        } finally {
            try {
                this.rspMsgQueque.remove(reqMsg.getId());
            } catch (Exception e) {
                this.logger.warn("it's have exception {} happen when remove msgId {} to topic {}", e, reqMsg.getId(), topic);
            }
        }

        return rspMsg;
    }

    public void proxySetDevice(String productKey, String deviceName, LedaDevice device) {
        String deviceId = productKey + deviceName;
        this.deviceList.put(deviceId, device);
    }

    public String proxyGetConfig() {
        ResponseMessage response = this.sendSyncRequest(this.ledaTopic.getConfigTopic());
        if (null == response) {
            return null;
        }

        JSONObject result = null;
        result = response.getData();
        if (null == result) {
            return null;
        }

        return result.toJSONString();
    }

    public String proxyGetTsl(String productKey) {
        ResponseMessage response = this.sendSyncRequest(this.ledaTopic.getTslTopic(productKey));
        if (null == response) {
            return null;
        }

        JSONObject result = null;
        result = response.getData();
        if (null == result) {
            return null;
        }

        return result.toJSONString();
    }

    public String proxyGetTslConfig(String productKey) {
        ResponseMessage response = this.sendSyncRequest(this.ledaTopic.getTslConfigTopic(productKey));
        if (null == response) {
            return null;
        }

        JSONObject result = null;
        result = response.getData();
        if (null == result) {
            return null;
        }

        return result.toJSONString();
    }

    public int proxyOnline(String productKey, String deviceName) {
        ResponseMessage response = this.sendSyncRequest(this.ledaTopic.loginTopic(productKey, deviceName));
        if (null == response) {
            return LedaErrorCode.LE_ERROR_UNKNOWN;
        }

        return response.getCode();
    }

    public int proxyOffline(String productKey, String deviceName) {
        ResponseMessage response = this.sendSyncRequest(this.ledaTopic.logoutTopic(productKey, deviceName));
        if (null == response) {
            return LedaErrorCode.LE_ERROR_UNKNOWN;
        }

        return response.getCode();
    }

    public int proxyReportProperties(String productKey, String deviceName, HashMap<String, Object> properties) {
        JSONObject params = new JSONObject();
        for (Map.Entry<String, Object> property : properties.entrySet()) {
            JSONObject element = new JSONObject();
            element.put("value", property.getValue());
            element.put("time", System.currentTimeMillis());
            params.put(property.getKey(), element);
        }

        return this.sendRequest(this.ledaTopic.reportPropertyTopic(productKey, deviceName), params);
    }

    public int proxyReportEvents(String productKey, String deviceName, String eventName, HashMap<String, Object> outputData) {
        JSONObject params = new JSONObject();
        JSONObject value = new JSONObject();
        for (Map.Entry<String, Object> data : outputData.entrySet()) {
            value.put(data.getKey(), data.getValue());
        }
        params.put("value", value);
        params.put("time", System.currentTimeMillis());

        return this.sendRequest(this.ledaTopic.reportEventTopic(productKey, deviceName, eventName), params);
    }

    public void sendResponse(String methodName, String topic, String msgId, Object object) {
        JSONObject response = new JSONObject();
        response.put("id", msgId);
        response.put("version", LedaProxy.PROTOCOL_VERSION);

        try {
            if (null != object) {
                String getMethod = "get";
                String setMethod = "set";
                if (getMethod.equals(methodName)) {
                    LedaData data = (LedaData)object;
                    response.put("code", data.code);
                    response.put("data", data.data);
                } else if (setMethod.equals(methodName)) {
                    response.put("code", object);
                    response.put("data", new JSONObject());
                } else {
                    LedaData data = (LedaData)object;
                    response.put("code", data.code);
                    response.put("data", data.data);
                }
            } else {
                response.put("code", LedaErrorCode.LE_ERROR_UNKNOWN);
                response.put("data", new JSONObject());
            }
  
            this.mqttClient.publish(topic, response.toJSONString());
        } catch (Exception e) {
            this.logger.warn("it's have exception {} happen when send topic {} response {}", e, topic, response.toJSONString());
        }
    }

    private void parseRequestMessage(final String topic, final String message) {
        if (-1 != topic.indexOf("callservice")) {
            String [] topicElementArray = topic.split("/");
            String condition = "property";
            if (topicElementArray.length == 15 && condition.equals(topicElementArray[13])) {
                this.threadPool.execute(new LedaDeviceCallback(topicElementArray[8], topicElementArray[9], topicElementArray[topicElementArray.length - 1], message, this));
            } else {
                this.threadPool.execute(new LedaDeviceCallback(topicElementArray[8], topicElementArray[9], topicElementArray[topicElementArray.length - 1], message, this));
            }
        }
    }

    private void parseResponseMessage(final String topic, final String message) {
        try {
            ResponseMessage rspMsg = JSON.parseObject(message, ResponseMessage.class);
            if (null != rspMsg) {
                ResponseMessage localRspMsg = this.rspMsgQueque.get(rspMsg.getId());
                if (null != localRspMsg) {
                    localRspMsg.setCode(rspMsg.getCode());
                    localRspMsg.setData(rspMsg.getData());
                    synchronized (localRspMsg) {
                        localRspMsg.notifyAll();
                    }
                }
            }
        } catch (Exception e) {
            this.logger.warn("it's have exception {} happen when parse topic {} response {}", e, topic, message);
        }
    }

    public void receiveMessage(final String topic, final String message) {
        if (-1 != topic.indexOf("response")) {
            this.parseResponseMessage(topic, message);
        } else {
            this.parseRequestMessage(topic, message);
        }
    }

    class LedaDeviceCallback implements Runnable {
        private String productKey;
        private String deviceName;
        private String methodName;
        private String message;

        private LedaProxy ledaProxy;

        LedaDeviceCallback(String productKey, String deviceName, String methodName, String message, LedaProxy ledaProxy) {
            this.productKey = productKey;
            this.deviceName = deviceName;
            this.methodName = methodName;
            this.message = message;
            this.ledaProxy = ledaProxy;
        }

        private void getProperties() {
            String topic;
            Object object;
            
            RequestMessage reqMsg = JSON.parseObject(this.message, RequestMessage.class);
            List<String> propertyNameList = JSON.parseArray(((JSONArray)reqMsg.getParams()).toJSONString(), String.class);

            object = this.ledaProxy.getDeviceList().get(this.productKey + this.deviceName).getProperties(propertyNameList);
            topic = this.ledaProxy.getLedaTopic().getPropertyReplyTopic(this.productKey, this.deviceName);
            this.ledaProxy.sendResponse(methodName, topic, reqMsg.getId(), object);
        }

        @SuppressWarnings("unchecked")
        private void setProperties() {
            String topic;
            Object object;

            RequestMessage reqMsg = JSON.parseObject(this.message, RequestMessage.class);
            HashMap<String, Object> params = JSON.parseObject(((JSONObject)(reqMsg.getParams())).toJSONString(), HashMap.class);

            object = this.ledaProxy.getDeviceList().get(this.productKey + this.deviceName).setProperties(params);
            topic = this.ledaProxy.getLedaTopic().setPropertyReplyTopic(this.productKey, this.deviceName);
            this.ledaProxy.sendResponse(methodName, topic, reqMsg.getId(), object);
        }

        @SuppressWarnings("unchecked")
        private void callService() {
            String topic;
            Object object;

            RequestMessage reqMsg = JSON.parseObject(this.message, RequestMessage.class);
            HashMap<String, Object> params = JSON.parseObject(((JSONObject)(reqMsg.getParams())).toJSONString(), HashMap.class);

            object = this.ledaProxy.getDeviceList().get(this.productKey + this.deviceName).callService(this.methodName, params);
            topic = this.ledaProxy.getLedaTopic().serviceReplyTopic(this.productKey, this.deviceName, this.methodName);
            this.ledaProxy.sendResponse(methodName, topic, reqMsg.getId(), object);
        }

        @Override
        public void run() {
            String getMethod = "get";
            String setMethod = "set";

            if (getMethod.equals(this.methodName)) {
                this.getProperties();
            } else if (setMethod.equals(this.methodName)) {
                this.setProperties();
            } else {
                this.callService();
            }
        }
    }
 }