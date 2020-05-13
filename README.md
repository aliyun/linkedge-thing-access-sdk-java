# Link IoT Edge设备接入SDK Java语言版

本项目提供设备接入SDK（Java语言版），方便开发者在[Link IoT Edge](https://help.aliyun.com/product/69083.html?spm=a2c4g.11186623.6.540.7c1b705eoBIMFA)上编写设备接入驱动。

## 编译环境

### 依赖工具

本工程需要的编译工具版本需保证与表格中列举版本一致或更高版本，否则编译可能会失败

Tool           | Version |
---------------|---------|
maven          | 3.3.0+  |
jdk            | 1.8.0+  |

### 依赖组件

本工程依赖库的版本要保证与version字段中版本一致或更高版本，否则编译可能会失败

``` xml
  <dependencies>
    <dependency>
      <groupId>org.eclipse.paho</groupId>
      <artifactId>org.eclipse.paho.client.mqttv3</artifactId>
      <version>1.2.0</version>
    </dependency>
    <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>fastjson</artifactId>
      <version>1.2.62</version>
    </dependency>
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
      <version>1.7.25</version>
    </dependency>
    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
      <version>1.1.3</version>
    </dependency>
  </dependencies>
```

## 快速开始 - demo
### Link IoT Edge标准版JDK安装注意事项
1. Link IoT Edge以root身份运行，所以在安装JDK时需要以root身份安装，否则运行java驱动时会提示找不到java命令。
2. JDK安装时必须保证java程序位于如下目录/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin，否则运行java驱动时会提示找不到java命令。

`demo_led` 示例演示将设备接入Link IoT Edge的过程。

### demo编译

``` sh
    $git clone https://github.com/aliyun/linkedge-thing-access-sdk-java.git

    $cd linkedge-thing-access-sdk-java && mvn clean package

    $mkdir demo/lib && cp -f target/linkedge-thing-access-sdk-java-1.0.0.jar demo/lib/

    $cd demo && mvn clean package

    $zip -r led_driver.zip main.jar lib/
```

### demo演示

1. 进入工程demo目录。
2. 找到led_driver.zip包。
3. 进入Link IoT Edge控制台，**边缘计算**，**驱动管理**，**自研驱动**，**新建驱动**。
4. 通信协议类型选择*自定义*，语言类型选择*Java 8*，驱动内置选择*否*。
5. 驱动名称设置为`led_driver`，并上传前面准备好的zip包。
6. 创建一个产品，名称为`demo_led`。该产品包含一个`temperature`属性（int32类型）和一个`high_temperature`事件（int32类型和一个int32类型名为`temperature`的输入参数）。
7. 创建一个名为`demo_led`的上述产品的设备。
8. 根据Link IoT Edge[环境搭建](https://help.aliyun.com/product/69083.html?spm%3Da2c4g.11186623.6.540.7c1b705eoBIMFA)流程官方文档完成环境创建。
9. 进入设备驱动配置页，添加`led_driver`驱动。
10. 将`demo_led`设备分配到`led_driver`驱动。
11. 进入消息路由页，使用如下配置添加*消息路由*：
  * 消息来源：`demo_led`设备
  * TopicFilter：属性
  * 消息目标：IoT Hub
12. 部署分组。`demo_led`设备将每隔5秒上报属性到云端，可在Link IoT Edge控制台设备运行状态页面查看。
