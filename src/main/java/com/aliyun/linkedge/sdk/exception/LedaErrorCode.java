package com.aliyun.linkedge.sdk.exception;

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

    public String getMessage(int code) {
        String message = null;
        switch (code) {
            case LE_SUCCESS:
                message = LE_SUCCESS_MSG;
                break;
            case LE_ERROR_UNKNOWN:
                message = LE_ERROR_UNKNOWN_MSG;
                break;
            case LE_ERROR_INVALID_PARAM:
                message = LE_ERROR_INVALID_PARAM_MSG;
                break;
            case LE_ERROR_TIMEOUT:
                message = LE_ERROR_TIMEOUT_MSG;
                break;
            case LE_ERROR_PARAM_RANGE_OVERFLOW:
                message = LE_ERROR_PARAM_RANGE_OVERFLOW_MSG;
                break;
            case LE_ERROR_SERVICE_UNREACHABLE:
                message = LE_ERROR_SERVICE_UNREACHABLE_MSG;
                break;
            case LEDA_ERROR_DEVICE_UNREGISTER:
                message = LEDA_ERROR_DEVICE_UNREGISTER_MSG;
                break;
            case LEDA_ERROR_DEVICE_OFFLINE:
                message = LEDA_ERROR_DEVICE_OFFLINE_MSG;
                break;
            case LEDA_ERROR_PROPERTY_NOT_EXIST:
                message = LEDA_ERROR_PROPERTY_NOT_EXIST_MSG;
                break;
            case LEDA_ERROR_PROPERTY_READ_ONLY:
                message = LEDA_ERROR_PROPERTY_READ_ONLY_MSG;
                break;
            case LEDA_ERROR_PROPERTY_WRITE_ONLY:
                message = LEDA_ERROR_PROPERTY_WRITE_ONLY_MSG;
                break;
            case LEDA_ERROR_SERVICE_NOT_EXIST:
                message = LEDA_ERROR_SERVICE_NOT_EXIST_MSG;
                break;
            case LEDA_ERROR_SERVICE_INPUT_PARAM:
                message = LEDA_ERROR_SERVICE_INPUT_PARAM_MSG;
                break;
            case LEDA_ERROR_INVALID_JSON:
                message = LEDA_ERROR_INVALID_JSON_MSG;
                break;
            case LEDA_ERROR_INVALID_TYPE:
                message = LEDA_ERROR_INVALID_TYPE_MSG;
                break;
            default:
                break;
        }

        return message;
    }
}