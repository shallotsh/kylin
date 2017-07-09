package org.kylin.bean;

/**
 * @author huangyawu
 * @date 2017/6/25 下午3:12.
 */
public class WyfDataResponse<T> extends WyfResponse {
    T data;

    public WyfDataResponse(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
