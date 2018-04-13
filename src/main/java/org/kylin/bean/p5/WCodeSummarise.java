package org.kylin.bean.p5;

import java.util.List;

public class WCodeSummarise {
    private List<WCode> wCodes;
    private Integer pairCodes;
    private Integer nonPairCodes;

    public List<WCode> getwCodes() {
        return wCodes;
    }

    public WCodeSummarise setwCodes(List<WCode> wCodes) {
        this.wCodes = wCodes;
        return this;
    }

    public Integer getPairCodes() {
        return pairCodes;
    }

    public WCodeSummarise setPairCodes(Integer pairCodes) {
        this.pairCodes = pairCodes;
        return this;
    }

    public Integer getNonPairCodes() {
        return nonPairCodes;
    }

    public WCodeSummarise setNonPairCodes(Integer nonPairCodes) {
        this.nonPairCodes = nonPairCodes;
        return this;
    }
}
