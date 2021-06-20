package org.zhx.common.commonnetwork.api;

/**
 * Name: CommonLocalError
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-14 13:58
 */
public enum CommonLocalError {
    PARSE_ERROR(101, "解析数据失败"),
    BAD_NETWORK(102, "请打开网络连接"),
    CONNECT_ERROR(103, "连接错误"),
    CONNECT_TIMEOUT(104, "连接超时"),
    UNKNOWN_LOCAL_ERROR(105, "未知错误"),
    URL_NOT_FOUND(106, "错误的url"),
    ROMOTE_NOT_FOUND(107, "未找到服务"),
    ROMOTE_ERROR(108, "服务异常"),
    ROMOTE_DATA_ERROR(112, "返回数据结构错"),
    METHOD_NOT_ALLOW(109, "方法错误"),
    ILLEGAL_ARGUMENT(110, "请求格式错误"),
    NULL_RESPONE(111, "接口返回null");
    private int errorCode;
    private String errorMsg;

    CommonLocalError(int code, String msg) {
        this.errorCode = code;
        this.errorMsg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
