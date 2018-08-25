package org.kylin.bean;


import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.Set;

import org.kylin.algorithm.RandomKill;
import org.kylin.constant.BitConstant;

/**
 * @author huangyawu
 * @date 2017/6/30 上午1:42.
 */
public class W3DCode implements RandomKill,Cloneable{
    private Integer[] codes = new Integer[3];
    private int freq;
    private int  sumTail;
    private int classify;
    private boolean deleted;

    public W3DCode() {
    }

    public W3DCode(int h, int d, int u) {
        this.codes[0] = u;
        this.codes[1] = d;
        this.codes[2] = h;
        int sum = h + d + u;
        this.sumTail = sum % 10;
        this.freq = 0;
    }

    public W3DCode(int d, int u) {
        this.codes[0] = u;
        this.codes[1] = d;

        this.freq = 0;

        int sum = d + u;

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

    public boolean isDeleted() {
        return deleted;
    }

    public W3DCode setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    @Override
    public void setBeDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean isBeDeleted() {
        return this.deleted;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(this.getFreq());
        sb.append("]");
        if(this.codes[BitConstant.HUNDRED] != null){
            sb.append(this.codes[BitConstant.HUNDRED]);
        }
        sb.append(this.codes[BitConstant.DECADE]);
        sb.append(this.codes[BitConstant.UNIT]);
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
        return w3DCode.codes[BitConstant.UNIT] == this.codes[BitConstant.UNIT]
                && w3DCode.codes[BitConstant.DECADE] == this.codes[BitConstant.DECADE]
                && w3DCode.codes[BitConstant.HUNDRED] == this.codes[BitConstant.HUNDRED] ;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        if(this.codes[BitConstant.HUNDRED] != null) {
            result = prime * result + this.codes[BitConstant.HUNDRED];
        }
        if(this.codes[BitConstant.DECADE] != null) {
            result = prime * result + this.codes[BitConstant.DECADE];
        }

        if(this.codes[BitConstant.UNIT] != null){
            result = prime * result + this.codes[BitConstant.UNIT];
        }

        return result;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        W3DCode newObj = (W3DCode) super.clone();
        newObj.setClassify(this.classify);
        return newObj;
    }

    public W3DCode asc(){
        if(this.codes[BitConstant.HUNDRED] == null) {
            Arrays.sort(this.codes, 0, 2);
            int tmp = this.codes[BitConstant.UNIT];
            this.codes[BitConstant.UNIT] = this.codes[BitConstant.DECADE];
            this.codes[BitConstant.DECADE] = tmp;
        }else{
            Arrays.sort(this.codes, 0, 3);
            int tmp = this.codes[BitConstant.UNIT];
            this.codes[BitConstant.UNIT] = this.codes[BitConstant.HUNDRED];
            this.codes[BitConstant.HUNDRED] = tmp;
        }

        return this;
    }

    public int sum(){
        int sum = 0;
        if(this.codes[BitConstant.HUNDRED] != null){
            sum += this.codes[BitConstant.HUNDRED];
        }

        if(this.codes[BitConstant.DECADE] != null){
            sum += this.codes[BitConstant.DECADE];
        }

        if(this.codes[BitConstant.UNIT] != null){
            sum += this.codes[BitConstant.UNIT];
        }

        return sum;
    }

    public int getMax(){
        int max = Math.max(this.codes[BitConstant.UNIT], this.codes[BitConstant.DECADE]);
        if(this.codes[BitConstant.HUNDRED] != null){
            max = Math.max(this.codes[BitConstant.HUNDRED], max);
        }
        return max;
    }

    public int getMin(){
        int min = Math.min(this.codes[BitConstant.UNIT], this.codes[BitConstant.DECADE]);
        if(this.codes[BitConstant.HUNDRED] != null){
            min = Math.min(this.codes[BitConstant.HUNDRED], min);
        }

        return min;
    }


    public int getClassify() {
        return classify;
    }

    public void setClassify(int classify) {
        this.classify = classify;
    }

    public boolean isInSeq(Set<Integer> seq){
        if(CollectionUtils.isEmpty(seq)){
            return false;
        }

        boolean flag = seq.contains(this.getCodes()[BitConstant.UNIT]) && seq.contains(this.getCodes()[BitConstant.DECADE]);

        if(this.getCodes()[BitConstant.HUNDRED] != null){
            return flag && seq.contains(this.getCodes()[BitConstant.HUNDRED]);
        }else{
            return flag;
        }
    }

    public W3DCode setBit(int index, Integer value){
        if((index != BitConstant.DECADE
                && index != BitConstant.HUNDRED
                && index != BitConstant.UNIT) || value < 0){
            throw new  RuntimeException("参数错误");
        }

        this.codes[index] = value;
        return this;
    }

}
