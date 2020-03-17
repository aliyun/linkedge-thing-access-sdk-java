# 设备接入SDK（Java版本）开发手册

<a name="S6TeJ"></a>
# 1. 简介
设备接入SDK（Java版本）支持开发者使用Java语言开发设备接入驱动（以下简称驱动）。客户网关环境在安装有Link IoT Edge软件的条件下只要满足Java运行环境即可运行Java驱动。

<a name="Epu3p"></a>
# 2. 开发实例
<a name="tORtA"></a>
## 2.1. 安装Java环境
|  | 描述 |
| --- | --- |
| 版本要求 | JDK 1.8或以上 |
| 下载链接 | [http://www.oracle.com/technetwork/java/javase/downloads/index.html](http://www.oracle.com/technetwork/java/javase/downloads/index.html) |


<a name="NU0AE"></a>
## 2.2. 安装Maven环境
|  | 描述 |
| --- | --- |
| 版本要求 | Maven 3.3或以上 |
| 下载链接 | [http://maven.apache.org/download.cgi](http://maven.apache.org/download.cgi) |


<a name="BVxhF"></a>
## 2.3. 获取SDK
第一步，使用git下载SDK代码
```bash
git clone https://github.com/aliyun/linkedge-thing-access-sdk-java.git
```

第二步，进入linkedge-thing-access-sdk-java目录，生成jar包
```bash
cd linkedge-thing-access-sdk-java
mvn clean package
```

<br />第三步，linkedge-thing-access-sdk-java的jar包在驱动工程中引用
```xml
<dependency>
    <groupId>com.aliyun.linkedge.sdk</groupId>
    <artifactId>linkedge-thing-access-sdk-java</artifactId>
    <version>1.0.0</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/lib/linkedge-thing-access-sdk-java-1.0.0.jar</systemPath>
</dependency>
```

<a name="WhYu6"></a>
## 2.4. 常用类及其方法
<a name="41y9y"></a>
### 2.4.1. LedaConfig
类全名com.aliyun.linkedge.sdk.LedaConfig
```java
public class LedaConfig
```

方法概要

| 限定符和类型 | 方法和说明 |
| :--- | :--- |
| static String | getDriverConfig()<br />获取驱动配置 |
| static String | getDeviceConfig()<br />获取设备配置 |
| static String | getTsl(String productKey)<br />获取productKey对应物模型 |
| static String | getTslConfig(String productKey)<br />获取productKey对应物模型扩展配置 |


<a name="X3PJl"></a>
### 2.4.2. LedaDevice
类全名com.aliyun.linkedge.sdk.LedaDevice
```java
public class LedaDevice
```

构造器概要
```java
LedaDevice(String productKey, String deviceName)
```

方法概要

| 限定符和类型 | 方法和说明 |
| :--- | :--- |
| LedaData | getProperties(List<String> propertyNameList)<br />根据指定属性名称获取属性值，该方法需要驱动开发者实现重载 |
| int | setProperties(HashMap<String, Object> properties)<br />设置指定属性名称和属性值，该方法需要驱动开发者实现重载 |
| LedaData | callService(String methodName, HashMap<String, Object> params)<br />执行自定义方法，该方法需要驱动开发者实现重载 |
| int | online()<br />上线设备 |
| int | offline()<br />下线设备 |
| int | reportProperties(HashMap<String, Object> properties)<br />上报设备属性 |
| int | reportEvents(String eventName, HashMap<String, Object> outputData)<br />上报设备事件 |


<a name="axhPj"></a>
### 2.4.3. 数据类
类全名com.aliyun.linkedge.sdk.LedaDevice
```java
public class LedaData
```

构造器概要
```java
LedaData(int code, HashMap<String, Object> data)
```

方法概要

| 限定符和类型 | 方法和说明 |
| :--- | :--- |
| void | setCode(int code)<br />设置执行状态码 |
| int | getCode()<br />获取执行状态码<br /> |
| void | setData(HashMap<String, Object> data)<br />设置数据内容 |
| HashMap<String, Object> | getData()<br />获取数据内容 |


<a name="oB2ti"></a>
### 2.4.4. 错误码
类全名com.aliyun.linkedge.sdk.exception.LedaErrorCode;
```java
public class LedaErrorCode {
    public static final int LE_SUCCESS                              = 0;
    public static final int LE_ERROR_UNKNOWN                        = 100000;
    public static final int LE_ERROR_INVALID_PARAM                  = 100001;
    public static final int LE_ERROR_TIMEOUT                        = 100006;
    public static final int LE_ERROR_PARAM_RANGE_OVERFLOW           = 100007;
    public static final int LE_ERROR_SERVICE_UNREACHABLE            = 100008;
    public static final int LEDA_ERROR_DEVICE_UNREGISTER            = 109000;
    public static final int LEDA_ERROR_DEVICE_OFFLINE               = 109001;
    public static final int LEDA_ERROR_PROPERTY_NOT_EXIST           = 109002;
    public static final int LEDA_ERROR_PROPERTY_READ_ONLY           = 109003;
    public static final int LEDA_ERROR_PROPERTY_WRITE_ONLY          = 109004;
    public static final int LEDA_ERROR_SERVICE_NOT_EXIST            = 109005;
    public static final int LEDA_ERROR_SERVICE_INPUT_PARAM          = 109006;
    public static final int LEDA_ERROR_INVALID_JSON                 = 109007;
    public static final int LEDA_ERROR_INVALID_TYPE                 = 109008;

    private static final String LE_SUCCESS_MSG                      = "Success";                          /* 请求成功*/
    private static final String LE_ERROR_UNKNOWN_MSG                = "Unknown error";                    /* 不能被识别的错误，用户不应该看到的错误*/
    private static final String LE_ERROR_INVALID_PARAM_MSG          = "Invalid params";                   /* 传入参数为NULL或无效*/
    private static final String LE_ERROR_TIMEOUT_MSG                = "Tiemout";                          /* 超时*/
    private static final String LE_ERROR_PARAM_RANGE_OVERFLOW_MSG   = "Param range overflow";             /* 参数范围越界*/
    private static final String LE_ERROR_SERVICE_UNREACHABLE_MSG    = "Service unreachable";              /* 服务不可达*/

    private static final String LEDA_ERROR_DEVICE_UNREGISTER_MSG    = "Device has't register";            /* 设备未注册*/ 
    private static final String LEDA_ERROR_DEVICE_OFFLINE_MSG       = "Device has offline";               /* 设备已下线*/
    private static final String LEDA_ERROR_PROPERTY_NOT_EXIST_MSG   = "Property no exist";                /* 属性不存在*/
    private static final String LEDA_ERROR_PROPERTY_READ_ONLY_MSG   = "Property only support read";       /* 属性只读*/
    private static final String LEDA_ERROR_PROPERTY_WRITE_ONLY_MSG  = "Property only support write";      /* 属性只写*/
    private static final String LEDA_ERROR_SERVICE_NOT_EXIST_MSG    = "Service no exist";                 /* 服务不存在*/
    private static final String LEDA_ERROR_SERVICE_INPUT_PARAM_MSG  = "Service param invalid";            /* 服务的输入参数不正确错误码*/
    private static final String LEDA_ERROR_INVALID_JSON_MSG         = "Json format invalid";              /* JSON格式错误*/
    private static final String LEDA_ERROR_INVALID_TYPE_MSG         = "Param type invalid";               /* 参数类型错误*/
}
```

方法概要

| 限定符和类型 | 方法和说明 |
| :--- | :--- |
| String | getMessage(int code)<br />获取错误码对应的消息 |


<a name="4lqw6"></a>
## 2.5. 使用示例
<a name="73Zsr"></a>
### 2.5.1. 获取配置
```java
package demo.test.config;

import com.aliyun.linkedge.sdk.LedaConfig;

/**
 * 获取配置示例
 *
 */
public class TestConfig {

    public static void main(String[] args) {
        String driverConfig = LedaConfig.getDriverConfig();
        String deviceConfig = LedaConfig.getDeviceConfig();

        if (null != driverConfig) {
            System.out.println("driver config " + driverConfig);
        }

        if ( null != deviceConfig) {
            System.out.println("device config " + deviceConfig);
        }
    }
}
```

<a name="6PByH"></a>
### 2.5.2. 获取物模型及物模型配置
```java
package demo.test.tsl;

import java.util.ArrayList;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.linkedge.sdk.LedaConfig;

/**
 * 获取物模型及物模型配置示例 
 *
 */
public class TestTsl {

    public static void main(String[] args) {
        JSONArray deviceConfigList = JSONArray.parseArray(LedaConfig.getDeviceConfig());
        if (null != deviceConfigList) {
            for (int i = 0; i < deviceConfigList.size(); i++) {
                JSONObject config = deviceConfigList.getJSONObject(i);
                String tslInfo = LedaConfig.getTsl(config.getString("productKey"));
                String tslConfigInfo = LedaConfig.getTsl(config.getString("productKey"));
                if (null != tslInfo) {
                    System.out.println("tsl info " + tslInfo);
                }
        
                if ( null != tslConfigInfo) {
                    System.out.println("tsl config info " + tslConfigInfo);
                }
            }
        } else {
            System.out.println("no device online success in current driver");
        }
    }
}
```

<a name="ywhZM"></a>
### 2.5.3. 上线设备
```java
package demo.test.device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.linkedge.sdk.LedaConfig;
import com.aliyun.linkedge.sdk.LedaDevice;
import com.aliyun.linkedge.sdk.exception.LedaErrorCode;

/**
 * 上线设备示例
 *
 */
public class TestOnlineDevice extends LedaDevice {
    private String productKey;
    private String deviceName;
    TestOnlineDevice(String productKey, String deviceName) {
        super(productKey, deviceName);
        this.productKey = productKey;
        this.deviceName = deviceName;
    }
    public static void main(String[] args) {
        String driverConfig = LedaConfig.getDriverConfig();
        String deviceConfig = LedaConfig.getDeviceConfig();
        if (null != driverConfig) {
            System.out.println("driver config " + driverConfig);
        }
        if ( null != deviceConfig) {
            System.out.println("device config " + deviceConfig);
        }
        List<TestOnlineDevice> deviceList = new ArrayList<TestOnlineDevice>();
        JSONArray deviceConfigList = JSONArray.parseArray(LedaConfig.getDeviceConfig());
        if (null != deviceConfigList) {
            for (int i = 0; i < deviceConfigList.size(); i++) {
                JSONObject config = deviceConfigList.getJSONObject(i);
                TestOnlineDevice device = new TestOnlineDevice(config.getString("productKey"), config.getString("deviceName"));
                if (null != device) {
                    if (LedaErrorCode.LE_SUCCESS == device.online()) {
                        deviceList.add(device);
                    }
                }
            }
        } else {
            System.out.println("no device online success in current driver");
        }
    }
}
```

<a name="ks4el"></a>
### 2.5.4. 上报数据
```java
package demo.test.reportdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.linkedge.sdk.LedaConfig;
import com.aliyun.linkedge.sdk.LedaDevice;
import com.aliyun.linkedge.sdk.exception.LedaErrorCode;

/**
 * 上报数据示例
 *
 */
public class TestReportData extends LedaDevice {
    private String productKey;
    private String deviceName;
    public HashMap<String, Object> properties;
    TestReportData(String productKey, String deviceName) {
        super(productKey, deviceName);
        this.productKey = productKey;
        this.deviceName = deviceName;
        this.properties = new HashMap<String, Object>();
        this.properties.put("key_bool", true);
        this.properties.put("key_int", 1);
        this.properties.put("key_float", 1.0f);
        this.properties.put("key_double", 1.0d);
        this.properties.put("key_text", "string");
        this.properties.put("key_date", "1579244967102");
        this.properties.put("key_enum", 0);
        this.properties.put("key_array", new int[]{1, 2, 3, 4, 5});
        this.properties.put("key_struct", new HashMap<String, String>() {
            private static final long serialVersionUID = 1L;
            {
                put("key1", "value1");
            }
        });
    }
    public static void main(String[] args) {
        String driverConfig = LedaConfig.getDriverConfig();
        String deviceConfig = LedaConfig.getDeviceConfig();
        if (null != driverConfig) {
            System.out.println("driver config " + driverConfig);
        }
        if ( null != deviceConfig) {
            System.out.println("device config " + deviceConfig);
        }
        List<TestReportData> deviceList = new ArrayList<TestReportData>();
        JSONArray deviceConfigList = JSONArray.parseArray(LedaConfig.getDeviceConfig());
        if (null != deviceConfigList) {
            for (int i = 0; i < deviceConfigList.size(); i++) {
                JSONObject config = deviceConfigList.getJSONObject(i);
                TestReportData device = new TestReportData(config.getString("productKey"), config.getString("deviceName"));
                if (null != device) {
                    if (LedaErrorCode.LE_SUCCESS == device.online()) {
                        deviceList.add(device);
                    }
                }
            }
            
           while (true) {
                for (TestReportData device : deviceList) {
                    device.reportProperties(device.properties);
                    device.reportEvents("alertEvent", device.properties);
                }
    
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("no device online success in current driver");
        }
    }
}
```

<a name="MVtv4"></a>
### 2.5.5. 执行回调
```java
package demo.test.callbackmethod;

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
 * 执行方法示例
 *
 */
public class TestExcuteMethod extends LedaDevice {
    private String productKey;
    private String deviceName;
    public HashMap<String, Object> properties;
    TestExcuteMethod(String productKey, String deviceName) {
        super(productKey, deviceName);
        this.productKey = productKey;
        this.deviceName = deviceName;
        this.properties = new HashMap<String, Object>();
        this.properties.put("key_bool", true);
        this.properties.put("key_int", 1);
        this.properties.put("key_float", 1.0f);
        this.properties.put("key_double", 1.0d);
        this.properties.put("key_text", "string");
        this.properties.put("key_date", "1579244967102");
        this.properties.put("key_enum", 0);
        this.properties.put("key_array", new int[]{1, 2, 3, 4, 5});
        this.properties.put("key_struct", new HashMap<String, String>() {
            private static final long serialVersionUID = 1L;
            {
                put("key1", "value1");
            }
        });
    }
    @Override
    public LedaData getProperties(List<String> propertyNameList) {
        System.out.println("productKey: " + this.productKey + "deviceName: " + this.deviceName + "request get property");
        HashMap<String, Object> data = new HashMap<String,Object>();
        for (String propertyName : propertyNameList) {
            System.out.println("propertyName: " + propertyName);
            if (this.properties.containsKey(propertyName)) {
                data.put(propertyName, this.properties.get(propertyName));
            }
        }
        return new LedaData(LedaErrorCode.LE_SUCCESS, data);
    }
    @Override
    public int setProperties(HashMap<String, Object> properties) {
        System.out.println("productKey: " + this.productKey + "deviceName: " + this.deviceName + "request set property");
        for (Map.Entry<String, Object> element : properties.entrySet()) {
            System.out.println("propertyName: " + element.getKey());
            System.out.println("propertyValue: " + element.getValue());
            if (this.properties.containsKey(element.getKey())) {
                this.properties.put(element.getKey(), element.getValue());
            }
        }
        return LedaErrorCode.LE_SUCCESS;
    }
    @Override
    public LedaData callService(String methodName, HashMap<String, Object> params) {
        System.out.println("productKey: " + this.productKey + "deviceName: " + this.deviceName + "request call " + methodName);
        HashMap<String, Object> data = new HashMap<String,Object>();
        for (Map.Entry<String, Object> element : params.entrySet()) {
            System.out.println("paramName: " + element.getKey());
            System.out.println("paramValue: " + element.getValue());
            data.put(element.getKey(), element.getValue());
        }
        return new LedaData(LedaErrorCode.LE_SUCCESS, data);
    }
    public static void main(String[] args) {
        String driverConfig = LedaConfig.getDriverConfig();
        String deviceConfig = LedaConfig.getDeviceConfig();
        if (null != driverConfig) {
            System.out.println("driver config " + driverConfig);
        }
        if ( null != deviceConfig) {
            System.out.println("device config " + deviceConfig);
        }
        List<TestExcuteMethod> deviceList = new ArrayList<TestExcuteMethod>();
        JSONArray deviceConfigList = JSONArray.parseArray(LedaConfig.getDeviceConfig());
        if (null != deviceConfigList) {
            for (int i = 0; i < deviceConfigList.size(); i++) {
                JSONObject config = deviceConfigList.getJSONObject(i);
                TestExcuteMethod device = new TestExcuteMethod(config.getString("productKey"), config.getString("deviceName"));
                if (null != device) {
                    if (LedaErrorCode.LE_SUCCESS == device.online()) {
                        deviceList.add(device);
                    }
                }
            }
            while (true) {
                for (TestExcuteMethod device : deviceList) {
                    device.reportProperties(device.properties);
                    device.reportEvents("alertEvent", device.properties);
                }
    
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("no device online success in current driver");
        }
    }
}
```

<a name="KvqX3"></a>
## 2.6. 打包示例<br />
如果驱动依赖第三方jar包，建议驱动以jar包引用形式使用这些第三方jar包，即通过在编译驱动时指定第三方依赖jar路径。下面打包实例演示了第三方jar包在demo工程的lib文件夹下的引用示例。
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" 
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>leda.java.sdk.demo</groupId>
    <artifactId>main</artifactId>
    <version>1.0.0</version>
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>

    <packaging>jar</packaging>
    <name>demo</name>
    <description>The demo of linkedge sdk for java</description>
    <licenses>
        <license>
            <name>Apache License, Version 2.0</name>
        </license>
    </licenses>
  
    <dependencies>
        <dependency>
            <groupId>com.aliyun.linkedge.sdk</groupId>
            <artifactId>linkedge-thing-access-sdk-java</artifactId>
            <version>1.0</version>
            <scope>system</scope>
            <systemPath>${project.basedir}/lib/linkedge-thing-access-sdk-java-1.0.0.jar</systemPath>
        </dependency>
    </dependencies>

    <build>
        <finalName>${project.artifactId}</finalName>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.6.1</version>
                <configuration>
                    <compilerArgument>-Xlint:unchecked</compilerArgument>
                    <source>${project.java.version}</source>
                    <target>${project.java.version}</target>
                    <encoding>${project.build.sourceEncoding}</encoding>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>2.3.2</version>
                <configuration>
                    <archive>
                        <manifestEntries>
                            <Class-Path>lib/linkedge-thing-access-sdk-java-1.0.0.jar</Class-Path>
                        </manifestEntries>
                        <manifest>
                            <addClasspath>true</addClasspath>
                            <classpathPrefix>lib/</classpathPrefix>
                            <mainClass>leda.java.sdk.demo.App</mainClass>
                        </manifest>
                    </archive>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

<br />执行mvn clean package，生成驱动jar包，驱动包必须命名为**main.ja**r，否则无法启动：
```bash
mvn clean pacakge 
```

<br />接下来按照驱动打包格式，将驱动jar包和第三方依赖jar包打成**ZIP格式驱动包**：
```bash
zip -r your_driver_name.zip main.jar lib
```

<a name="pNtsX"></a>
## 2.7. 驱动调试
参考：<br />[https://help.aliyun.com/document_detail/120908.html?spm=a2c4g.11186623.6.578.119c71b8YzDLAV](https://help.aliyun.com/document_detail/120908.html?spm=a2c4g.11186623.6.578.119c71b8YzDLAV)<br />

<a name="Wxhmw"></a>
# 3. 应用示例
参考：<br />[https://help.aliyun.com/document_detail/104069.html?spm=a2c4g.11186623.6.569.45cf56c2Z8cXGp](https://help.aliyun.com/document_detail/104069.html?spm=a2c4g.11186623.6.569.45cf56c2Z8cXGp)

<a name="u3TSI"></a>
# 4. FAQ
| 序号 | **问题描述** | **问题解答** |
| --- | --- | --- |
| 1 | 驱动部署后出现驱动启动失败日志提示？ | 1、确认网关环境root用户下是否安装java8运行环境；<br />2、确认驱动是否按照驱动打包规范打包； |
| 2 | 调用属性和事件上报接口时失败？ | 确认设备是否已经上线 |
| 3 | 调用获取属性、设置属性和自定义方法失败？ | 确认是否重载实现获取属性、设置属性和自定义方法接口 |


