package org.kylin.bean;

/**
 * @author huangyawu
 * @date 2017/6/25 下午3:24.
 */
public class WyfErrorResponse {
    private Error error;

    public WyfErrorResponse(Integer code, String message) {
        this.error = new Error(code, message);
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    class Error {
        Integer code;
        String message;

        public Error(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
