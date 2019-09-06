package retrofit_rx.Api;

/**
 * 回调信息统一封装类
 * Created by WZG on 2016/7/16.
 */
public class BaseResultEntity<T> {
    //  判断标示
    private int code;
    //    提示信息
    private String message;
    //  是否成功
    private String success;
    //显示数据（用户需要关心的数据）
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
