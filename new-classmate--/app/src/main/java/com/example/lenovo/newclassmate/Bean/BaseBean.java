package com.example.lenovo.newclassmate.Bean;

/**
 * Created by DELL on 2018/10/26.
 */

public class BaseBean {
    /**
     * result: 1:请求成功, 0:请求失败
     */
    public int result;//
    public String message;
    public int flag;
    /**
     * 下面是message常量值，用于在其它类做判断用
     */
    // 1:请求成功
    public final static int MESSAGE_SUCCESS = 1;
    // 0:请求失败
    public final static int MESSAGE_FAILED = 0;
    // -1:参数解密错误
    public final static int MESSAGE_DECODE_WRONG = -1;

    public void setResult(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
