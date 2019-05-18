package org.kylin.bean.p5;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;

public class WCodeReq {
    private Integer filterType;
    private String boldCodeFive;
    private List<WCode> wCodes;
    private Map<String, List<WCode>> deletedCodesPair;
    private List<String> bits;
    private Integer exportType;
    private String p3Code;
    private String randomCount;
    private Boolean freqSeted;
    private boolean randomKill;
    private List<Integer> bitsSeq;
    private Integer exportFormat;
    private String bitAB;
    private String bitBC;
    private String bitCD;
    private String bitDE;

    public Integer getFilterType() {
        return filterType;
    }

    public void setFilterType(Integer filterType) {
        this.filterType = filterType;
    }

    public String getBoldCodeFive() {
        return boldCodeFive;
    }

    public void setBoldCodeFive(String boldCodeFive) {
        this.boldCodeFive = boldCodeFive;
    }

    public List<WCode> getWCodes() {
        return wCodes;
    }

    public void setWCodes(List<WCode> wCodes) {
        this.wCodes = wCodes;
    }

    public List<String> getBits() {
        return bits;
    }

    public void setBits(List<String> bits) {
        this.bits = bits;
    }

    public List<WCode> getwCodes() {
        return wCodes;
    }

    public void setwCodes(List<WCode> wCodes) {
        this.wCodes = wCodes;
    }

    public Map<String, List<WCode>> getDeletedCodesPair() {
        return deletedCodesPair;
    }

    public WCodeReq setDeletedCodesPair(Map<String, List<WCode>> deletedCodesPair) {
        this.deletedCodesPair = deletedCodesPair;
        return this;
    }

    public Integer getExportType() {
        return exportType;
    }

    public void setExportType(Integer exportType) {
        this.exportType = exportType;
    }

    public String getP3Code() {
        return p3Code;
    }

    public String getRandomCount() {
        return randomCount;
    }

    public boolean isRandomKill() {
        return randomKill;
    }

    public WCodeReq setRandomKill(boolean randomKill) {
        this.randomKill = randomKill;
        return this;
    }

    public WCodeReq setRandomCount(String randomCount) {
        this.randomCount = randomCount;
        return this;
    }

    public WCodeReq setP3Code(String p3Code) {
        this.p3Code = p3Code;
        return this;
    }

    public Boolean getFreqSeted() {
        return freqSeted;
    }

    public WCodeReq setFreqSeted(Boolean freqSeted) {
        this.freqSeted = freqSeted;
        return this;
    }

    public List<Integer> getBitsSeq() {
        return bitsSeq;
    }

    public WCodeReq setBitsSeq(List<Integer> bitsSeq) {
        this.bitsSeq = bitsSeq;
        return this;
    }

    public Integer getExportFormat() {
        return exportFormat;
    }

    public WCodeReq setExportFormat(Integer exportFormat) {
        this.exportFormat = exportFormat;
        return this;
    }


    public String getBitAB() {
        return bitAB;
    }

    public WCodeReq setBitAB(String bitAB) {
        this.bitAB = bitAB;
        return this;
    }

    public String getBitBC() {
        return bitBC;
    }

    public WCodeReq setBitBC(String bitBC) {
        this.bitBC = bitBC;
        return this;
    }

    public String getBitCD() {
        return bitCD;
    }

    public WCodeReq setBitCD(String bitCD) {
        this.bitCD = bitCD;
        return this;
    }

    public String getBitDE() {
        return bitDE;
    }

    public WCodeReq setBitDE(String bitDE) {
        this.bitDE = bitDE;
        return this;
    }

    public String getConditions(){
        return "WCodeReq{" +
                "filterType=" + filterType +
                ", boldCodeFive='" + boldCodeFive + '\'' +
                ", wCodes_size=" + CollectionUtils.size(wCodes) +
                ", bits=" + bits +
                '}';
    }

    @Override
    public String toString() {
        return "WCodeReq{" +
                "filterType=" + filterType +
                ", boldCodeFive='" + boldCodeFive + '\'' +
                ", bits=" + bits +
                ", exportType=" + exportType +
                ", p3Code='" + p3Code + '\'' +
                ", randomCount='" + randomCount + '\'' +
                ", freqSeted=" + freqSeted +
                ", randomKill=" + randomKill +
                ", bitsSeq=" + bitsSeq +
                ", exportFormat=" + exportFormat +
                ", bitAB='" + bitAB + '\'' +
                ", bitBC='" + bitBC + '\'' +
                ", bitCD='" + bitCD + '\'' +
                ", bitDE='" + bitDE + '\'' +
                '}';
    }
}
