package org.kylin.bean;


import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

/**
 * @author huangyawu
 * @date 2017/6/30 上午1:42.
 */
public class W3DCode implements Cloneable{
    private Integer[] codes = new Integer[3];
    private int freq;
    private int  sumTail;

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

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public int getSumTail() {
        return sumTail;
    }

    public void setSumTail(int sumTail) {
        this.sumTail = sumTail;
    }

    public void addFreq(int count){
        this.freq += count;
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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
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

    public int sum(){
        int sum = 0;
        if(this.codes[2] != null){
            sum += this.codes[2];
        }

        if(this.codes[1] != null){
            sum += this.codes[1];
        }

        if(this.codes[0] != null){
            sum += this.codes[0];
        }

        return sum;
    }

    public int getMax(){
        int max = Math.max(this.codes[0], this.codes[1]);
        if(this.codes[2] != null){
            max = Math.max(this.codes[2], max);
        }
        return max;
    }

    public int getMin(){
        int min = Math.min(this.codes[0], this.codes[1]);
        if(this.codes[2] != null){
            min = Math.min(this.codes[2], min);
        }

        return min;
    }


    public boolean isInSeq(Set<Integer> seq){
        if(CollectionUtils.isEmpty(seq)){
            return false;
        }

        boolean flag = seq.contains(this.getCodes()[0]) && seq.contains(this.getCodes()[1]);

        if(this.getCodes()[2] != null){
            return flag && seq.contains(this.getCodes()[2]);
        }else{
            return flag;
        }
    }

}
