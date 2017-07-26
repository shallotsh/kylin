package org.kylin.bean;

import java.util.List;

/**
 * @author huangyawu
 * @date 2017/7/26 下午11:14.
 */
public class PolyParam {
    private WelfareCode minuend;
    private WelfareCode subtractor;

    private List<WelfareCode> welfareCodes;

    public WelfareCode getMinuend() {
        return minuend;
    }

    public void setMinuend(WelfareCode minuend) {
        this.minuend = minuend;
    }

    public WelfareCode getSubtractor() {
        return subtractor;
    }

    public void setSubtractor(WelfareCode subtractor) {
        this.subtractor = subtractor;
    }

    public List<WelfareCode> getWelfareCodes() {
        return welfareCodes;
    }

    public void setWelfareCodes(List<WelfareCode> welfareCodes) {
        this.welfareCodes = welfareCodes;
    }
}
