package org.zhx.common.commonnetwork.commonokhttp.customObservable.api;

/**
 * Name: CommonLocalError
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-14 13:58
 */
public enum CommonLocalError {
    SUC(200, "操作成功"),
    FAIL(300, "操作失败"),
    PARAM_ERROR(10401, "请求参数错误"),
    PARAM_EMPTY(10402, "参数缺失"),
    TOKEN_ERROR(10403, "缺少token参数"),
    METHOD_GET(10405, "需要GET请求"),
    METHOD_POST(10406, "需要POST请求"),
    REQUEST_BUSY(10407, "请求过于太频繁，请稍候再试"),
    USER_ERROR(10409, "用户受限"),
    UNKNOWN_ERROR(10500, "未知错误"),
    CONNECT_SQLLITE_FAIL(10501, "连接数据库失败"),
    OPEN_SQLIITE_ERROR(10502, "打开数据库失败"),
    SQL_EXCUTE_ERROR(1050, "执行SQL语句失败"),
    PROMISS_FAIL(10504, "事务执行失败"),
    SYSTEM_BUSY(10505, "系统繁忙，请稍候再试"),
    SYSTEM_ERROR(10506, "系统错误"),
    PARSE_ERROR(101, "解析数据失败"),
    BAD_NETWORK(102, "请打开网络连接"),
    CONNECT_ERROR(103, "连接错误"),
    CONNECT_TIMEOUT(104, "连接超时"),
    UNKNOWN_LOCAL_ERROR(105, "未知错误"),
    URL_NOT_FOUND(106, "错误的url"),
    ROMOTE_NOT_FOUND(107, "未找到服务"),
    ROMOTE_ERROR(108, "服务异常"),
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
