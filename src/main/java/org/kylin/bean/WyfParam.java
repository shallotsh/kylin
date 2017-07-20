package org.kylin.bean;

import java.util.List;

/**
 * @author huangyawu
 * @date 2017/6/25 下午3:37.
 */
public class WyfParam {
    private List<String> riddles;
    private Integer srcCodeType;
    private Integer targetCodeType;
    private List<W3DCode> w3DCodes;
    private FilterParam filterParam;

    public List<String> getRiddles() {
        return riddles;
    }

    public void setRiddles(List<String> riddles) {
        this.riddles = riddles;
    }

    public Integer getSrcCodeType() {
        return srcCodeType;
    }

    public void setSrcCodeType(Integer srcCodeType) {
        this.srcCodeType = srcCodeType;
    }

    public Integer getTargetCodeType() {
        return targetCodeType;
    }

    public void setTargetCodeType(Integer targetCodeType) {
        this.targetCodeType = targetCodeType;
    }

    public List<W3DCode> getW3DCodes() {
        return w3DCodes;
    }

    public void setW3DCodes(List<W3DCode> w3DCodes) {
        this.w3DCodes = w3DCodes;
    }

    public FilterParam getFilterParam() {
        return filterParam;
    }

    public void setFilterParam(FilterParam filterParam) {
        this.filterParam = filterParam;
    }
}
