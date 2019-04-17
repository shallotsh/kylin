package org.kylin.bean.p2;


import org.kylin.bean.p5.WCode;

import java.util.List;

public class XCodePair {
    private List<WCode> wCodes;
    private Integer index;

    public XCodePair() {
    }

    public List<WCode> getwCodes() {
        return wCodes;
    }

    public XCodePair setwCodes(List<WCode> wCodes) {
        this.wCodes = wCodes;
        return this;
    }

    public Integer getIndex() {
        return index;
    }

    public XCodePair setIndex(Integer index) {
        this.index = index;
        return this;
    }

    @Override
    public String toString() {
        return "XCodePair{" +
                "wCodes=" + wCodes +
                ", index=" + index +
                '}';
    }
}
