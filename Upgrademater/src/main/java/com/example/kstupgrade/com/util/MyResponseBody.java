package com.example.kstupgrade.com.util;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * <p>
 * 文件描述
 * </p>
 * 类名：
 * 说明:
 *
 * @author xbl
 * @version V1.0.0
 * 日期: 2023/9/25/14:54/星期一
 */
@Data
@Slf4j

public class MyResponseBody implements Serializable {
    private static final long serialVersionUID = -3948389268046368059L;

    private Integer code;

    private String msg;

    private Object data;
    private Long count ;

    public void setResultCode(ResponseEnumCode code) {
        this.code = code.code();
        this.msg = code.message();
    }

    //成功 不返回数据直接返回成功信息
    public static MyResponseBody success() {
        MyResponseBody result = new MyResponseBody();
        result.setResultCode(ResponseEnumCode.SUCCESS);
        result.setCount(0L);
        return result;
    }

    //成功 并且加上返回数据
    public static MyResponseBody success(Object data) {
        MyResponseBody result = new MyResponseBody();
        result.setResultCode(ResponseEnumCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static MyResponseBody success(Object data,Long count) {
        MyResponseBody result = new MyResponseBody();
        result.setResultCode(ResponseEnumCode.SUCCESS);
        result.setData(data);
        result.setCount(count);
        return result;
    }
    public static MyResponseBody success(String msg ,Object data,Long count) {
        MyResponseBody result = new MyResponseBody();
        result.setResultCode(ResponseEnumCode.SUCCESS);
        result.setMsg(msg);
        result.setData(data);
        result.setCount(count);
        return result;
    }
    //成功 自定义成功返回状态 加上数据
    public static MyResponseBody success(ResponseEnumCode ResponseEnumCode, Object data,Long count) {
        MyResponseBody result = new MyResponseBody();
        result.setResultCode(ResponseEnumCode);
        result.setData(data);
        result.setCount(count);
        return result;
    }

    // 单返回失败的状态码
    public static MyResponseBody failure(ResponseEnumCode ResponseEnumCode) {
        MyResponseBody result = new MyResponseBody();
        result.setResultCode(ResponseEnumCode);
        return result;
    }
    public static MyResponseBody failure(ResponseEnumCode ResponseEnumCode,Long count) {
        MyResponseBody result = new MyResponseBody();
        result.setResultCode(ResponseEnumCode);
        result.setCount(count);
        return result;
    }

    // 返回失败的状态码 及 数据
    public static MyResponseBody failure(ResponseEnumCode ResponseEnumCode, Object data ,Long count) {
        MyResponseBody result = new MyResponseBody();
        result.setResultCode(ResponseEnumCode);
        result.setData(data);
        result.setCount(count);
        return result;
    }

}
