package org.kylin.bean;


import java.util.Arrays;
import java.util.Collections;

/**
 * @author huangyawu
 * @date 2017/6/30 上午1:42.
 */
public class W3DCode {
    private Integer[] codes = new Integer[3];
    private Integer freq;
    private Integer sumTail;

    public W3DCode() {
    }

    public W3DCode(int h, int d, int u) {
        this.codes[0] = u;
        this.codes[1] = d;
        this.codes[2] = h;
        this.freq = 1;
        int sum = h + d + u;
        this.sumTail = sum % 10;
        this.freq = 1;
    }

    public W3DCode(int d, int u) {
        this.codes[0] = u;
        this.codes[1] = d;

        this.freq = 1;

        int sum = d + u;

        this.freq = 1;
        this.sumTail = sum % 10;
    }

    public Integer[] getCodes() {
        return codes;
    }

    public void setCodes(Integer[] codes) {
        this.codes = codes;
    }

    public Integer getFreq() {
        return freq;
    }

    public void setFreq(Integer freq) {
        this.freq = freq;
    }

    public Integer getSumTail() {
        return sumTail;
    }

    public void setSumTail(Integer sumTail) {
        this.sumTail = sumTail;
    }

    public void addFreq(int count){
        if(this.freq == null){
            this.freq = count;
        }else{
            this.freq += count;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(this.getFreq());
        sb.append("]");
        if(this.codes[2] != null){
            sb.append(this.codes[2]);
        }
        sb.append(this.codes[1]);
        sb.append(this.codes[0]);
        sb.append("-");
        sb.append(this.sumTail);

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof W3DCode)){
            return false;
        }
        if(this == obj){
            return true;
        }

        W3DCode w3DCode = (W3DCode) obj;
        return w3DCode.codes[0] == this.codes[0]
                && w3DCode.codes[1] == this.codes[1]
                && w3DCode.codes[2] == this.codes[2] ;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        if(this.codes[2] != null) {
            result = prime * result + this.codes[2];
        }
        if(this.codes[1] != null) {
            result = prime * result + this.codes[1];
        }

        if(this.codes[0] != null){
            result = prime * result + this.codes[0];
        }

        return result;
    }


    public W3DCode asc(){
        if(this.codes[2] == null) {
            Arrays.sort(this.codes, 0, 2);
            int tmp = this.codes[0];
            this.codes[0] = this.codes[1];
            this.codes[1] = tmp;
        }else{
            Arrays.sort(this.codes, 0, 3);
            int tmp = this.codes[0];
            this.codes[0] = this.codes[2];
            this.codes[2] = tmp;
        }

        return this;
    }


}
