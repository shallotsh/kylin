package org.kylin.bean;

/**
 * @author huangyawu
 * @date 2017/6/25 下午3:39.
 */
public class FilterParam {

    private String sumValue;
    private String boldCode;
    private String gossip;
    private String range;
    private String ternaryLocation;
    private String fishMan;
    private String hdBits;
    private String duBits;
    private String hBits;
    private String dBits;
    private String uBits;

    private Boolean isKillDipolar;
    private Boolean isKillOneEnd;
    private Boolean isKillBigSum;
    private Boolean isKillAllOddEven;

    public String getSumValue() {
        return sumValue;
    }

    public void setSumValue(String sumValue) {
        this.sumValue = sumValue;
    }

    public String getBoldCode() {
        return boldCode;
    }

    public void setBoldCode(String boldCode) {
        this.boldCode = boldCode;
    }

    public String getGossip() {
        return gossip;
    }

    public void setGossip(String gossip) {
        this.gossip = gossip;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getTernaryLocation() {
        return ternaryLocation;
    }

    public void setTernaryLocation(String ternaryLocation) {
        this.ternaryLocation = ternaryLocation;
    }

    public String getFishMan() {
        return fishMan;
    }

    public void setFishMan(String fishMan) {
        this.fishMan = fishMan;
    }

    public String getHdBits() {
        return hdBits;
    }

    public void setHdBits(String hdBits) {
        this.hdBits = hdBits;
    }

    public String getDuBits() {
        return duBits;
    }

    public void setDuBits(String duBits) {
        this.duBits = duBits;
    }

    public String gethBits() {
        return hBits;
    }

    public void sethBits(String hBits) {
        this.hBits = hBits;
    }

    public String getdBits() {
        return dBits;
    }

    public void setdBits(String dBits) {
        this.dBits = dBits;
    }

    public String getuBits() {
        return uBits;
    }

    public void setuBits(String uBits) {
        this.uBits = uBits;
    }

    public Boolean getKillDipolar() {
        return isKillDipolar;
    }

    public void setKillDipolar(Boolean killDipolar) {
        isKillDipolar = killDipolar;
    }

    public Boolean getKillOneEnd() {
        return isKillOneEnd;
    }

    public void setKillOneEnd(Boolean killOneEnd) {
        isKillOneEnd = killOneEnd;
    }

    public Boolean getKillBigSum() {
        return isKillBigSum;
    }

    public void setKillBigSum(Boolean killBigSum) {
        isKillBigSum = killBigSum;
    }

    public Boolean getKillAllOddEven() {
        return isKillAllOddEven;
    }

    public void setKillAllOddEven(Boolean killAllOddEven) {
        isKillAllOddEven = killAllOddEven;
    }
}
