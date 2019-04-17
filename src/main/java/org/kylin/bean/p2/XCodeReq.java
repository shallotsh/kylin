package org.kylin.bean.p2;

import lombok.extern.slf4j.Slf4j;
import org.kylin.bean.p5.WCode;

import java.util.List;

@Slf4j
public class XCodeReq {

    private List<String> sequences;
    private String boldCodeSeq;
    private String inverseCodeSeq;
    private String gossipCodeSeq;
    private List<WCode> wCodes;
    private Boolean freqSeted;
//    private List<WCode>[] wCodesArray;
//    private List<Integer> arrayIndexes;
    private List<XCodePair> xCodePairs;


    public XCodeReq() {
    }

    public List<String> getSequences() {
        return sequences;
    }

    public XCodeReq setSequences(List<String> sequences) {
        this.sequences = sequences;
        return this;
    }

    public String getBoldCodeSeq() {
        return boldCodeSeq;
    }

    public XCodeReq setBoldCodeSeq(String boldCodeSeq) {
        this.boldCodeSeq = boldCodeSeq;
        return this;
    }

    public String getInverseCodeSeq() {
        return inverseCodeSeq;
    }

    public XCodeReq setInverseCodeSeq(String inverseCodeSeq) {
        this.inverseCodeSeq = inverseCodeSeq;
        return this;
    }

    public String getGossipCodeSeq() {
        return gossipCodeSeq;
    }

    public XCodeReq setGossipCodeSeq(String gossipCodeSeq) {
        this.gossipCodeSeq = gossipCodeSeq;
        return this;
    }

    public List<WCode> getwCodes() {
        return wCodes;
    }

    public XCodeReq setwCodes(List<WCode> wCodes) {
        this.wCodes = wCodes;
        return this;
    }

    public Boolean getFreqSeted() {
        return freqSeted;
    }

    public XCodeReq setFreqSeted(Boolean freqSeted) {
        this.freqSeted = freqSeted;
        return this;
    }

    public List<XCodePair> getxCodePairs() {
        return xCodePairs;
    }

    public XCodeReq setxCodePairs(List<XCodePair> xCodePairs) {
        this.xCodePairs = xCodePairs;
        return this;
    }

    @Override
    public String toString() {
        return "XCodeReq{" +
                "sequences=" + sequences +
                ", boldCodeSeq='" + boldCodeSeq + '\'' +
                ", inverseCodeSeq='" + inverseCodeSeq + '\'' +
                ", gossipCodeSeq='" + gossipCodeSeq + '\'' +
                ", wCodes=" + wCodes +
                ", freqSeted=" + freqSeted +
                ", xCodePairs=" + xCodePairs +
                '}';
    }
}
