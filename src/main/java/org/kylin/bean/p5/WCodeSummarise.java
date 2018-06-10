package org.kylin.bean.p5;

import java.util.List;

public class WCodeSummarise {
    private List<WCode> randomKillCodes;
    private List<WCode> wCodes;
    private Integer pairCodes;
    private Integer nonPairCodes;
    private boolean isRandomKill;
    private Integer remainedCodesCount;

    public List<WCode> getRandomKillCodes() {
        return randomKillCodes;
    }

    public WCodeSummarise setRandomKillCodes(List<WCode> randomKillCodes) {
        this.randomKillCodes = randomKillCodes;
        return this;
    }

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

    public boolean isRandomKill() {
        return isRandomKill;
    }

    public WCodeSummarise setRandomKill(boolean randomKill) {
        isRandomKill = randomKill;
        return this;
    }

    public Integer getRemainedCodesCount() {
        return remainedCodesCount;
    }

    public WCodeSummarise setRemainedCodesCount(Integer remainedCodesCount) {
        this.remainedCodesCount = remainedCodesCount;
        return this;
    }
}
