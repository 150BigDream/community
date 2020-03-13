package com.example.community.Exception;

/**
 * 自定义Exception
 */
public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;

    /**
     * 构造器初始化属性
     * @param errorCode
     */
    public CustomizeException(ICustomizeErrorCode errorCode){
        this.message=errorCode.getMessage();
        this.code=errorCode.getCode();
    }

    /**
     * get方法，得到自定义异常的属性
     * @return
     */
    public String getMessage() {
        return message;
    }
    public Integer getCode() {
        return code;
    }
}
