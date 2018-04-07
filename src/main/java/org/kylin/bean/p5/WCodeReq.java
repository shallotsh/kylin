package org.kylin.bean.p5;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class WCodeReq {
    private Integer filterType;
    private String boldCodeFive;
    private List<WCode> wCodes;
    private List<String> bits;
    private Integer exportType;

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

    public Integer getExportType() {
        return exportType;
    }

    public void setExportType(Integer exportType) {
        this.exportType = exportType;
    }

    @Override
    public String toString() {
        return "WCodeReq{" +
                "filterType=" + filterType +
                ", boldCodeFive='" + boldCodeFive + '\'' +
                ", wCodes=" + wCodes +
                ", bits=" + bits +
                '}';
    }
}
