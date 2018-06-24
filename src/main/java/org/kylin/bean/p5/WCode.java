package org.kylin.bean.p5;


import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.kylin.algorithm.RandomKill;

import java.util.*;

/**
 * @author huangyawu
 * @date 2017/6/30 上午1:42.
 */
public class WCode implements Cloneable,Comparable,RandomKill{
    private List<Integer> codes;
    private int dim;
    private int freq;
    private int  sumTail;
    private int classify;
    private boolean deleted;

    public WCode() {
    }

    public WCode(Integer dim) {
        this.dim = dim ;
        codes = new ArrayList<>();
    }

    public WCode(int dim, int m, int t, int h, int d, int u){
        codes = new ArrayList<>();
        codes.add(m);
        codes.add(t);
        codes.add(h);
        codes.add(d);
        codes.add(u);
        this.dim = 5;
        this.sumTail = sum() % 10;
        this.freq = 1;
    }


    public WCode(int dim, int h, int d, int u) {
        codes = new ArrayList<>();
        codes.add(h);
        codes.add(d);
        codes.add(u);
        this.dim = dim;
        this.freq = 1;
        int sum = h + d + u;
        this.sumTail = sum % 10;
        this.freq = 1;
    }

    public WCode(int dim, int d, int u) {
        codes = new ArrayList<>();
        codes.add(d);
        codes.add(u);
        this.dim = 2;

        this.freq = 1;

        int sum = d + u;

        this.freq = 1;
        this.sumTail = sum % 10;
    }

    public List<Integer> getCodes() {
        return codes;
    }

    public void setCodes(List<Integer> codes) {
        this.codes = codes;
    }

    public int getDim() {
        return dim;
    }

    public void setDim(int dim) {
        this.dim = dim;
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

    public WCode setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public String getString(){
        StringBuilder sb = new StringBuilder();
        int size = CollectionUtils.size(codes);
        for(int i=0; i<size; i++){
            sb.append(codes.get(i));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        if(CollectionUtils.isEmpty(codes)){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(this.getFreq());
        sb.append("]");
        sb.append(getString());
        sb.append("-");
        sb.append(this.sumTail);

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof WCode)){
            return false;
        }
        if(this == obj){
            return true;
        }

        WCode wCode = (WCode) obj;
        if(CollectionUtils.isEmpty(wCode.getCodes()) || wCode.getDim() != this.getDim()){
            return false;
        }

        for(int i=wCode.getDim()-1; i>=0; i--){
            if(wCode.getCodes().get(i) != this.getCodes().get(i)){
                return false;
            }
        }

        return true;

    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;

        for(Integer code: this.getCodes()){
            result = prime * result + code;
        }

        return result;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        WCode newObj = (WCode) super.clone();
        newObj.setClassify(this.classify);
        return newObj;
    }

    public WCode asc(){
        Collections.sort(this.getCodes());
        return this;
    }

    public int sum(){
        return this.getCodes().stream().reduce(0, Integer::sum);
    }

    public int getMax(){
        return Collections.max(this.getCodes());
    }

    public int getMin(){
        return Collections.min(this.getCodes());
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

        Set<Integer> codeSet = new HashSet<>(this.getCodes());

        Set<Integer> diff = Sets.difference(codeSet, seq );
        return CollectionUtils.isEmpty(diff);
    }


    public boolean validate(){
        return dim == CollectionUtils.size(this.getCodes());
    }


    public boolean containsRepeatNumber(){
        Set<Integer> set = new HashSet<>();
        for(Integer e: codes){
            if(set.contains(e)){
                return true;
            }else{
                set.add(e);
            }
        }

        return false;
    }

    public int increaseFreq(){
        this.freq++;
        return this.freq;
    }

    @Override
    public int compareTo(Object o) {
        if(o == null || !(o instanceof WCode)){
            return 1;
        }
        WCode obj = (WCode) o;

        if(this.getFreq() > obj.getFreq()){
            return -1;
        }else if(this.getFreq() < obj.getFreq()){
            return 1;
        }

        for(int i=0; i<this.dim; i++){
            if(this.getCodes().get(i) > obj.getCodes().get(i)){
                return 1;
            }else if(this.getCodes().get(i) < obj.getCodes().get(i)){
                return -1;
            }
        }
        return 0;
    }


    @Override
    public int getRandomKillCount() {
        return 0;
    }

    @Override
    public void setBeDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean isBeDeleted() {
        return deleted;
    }
}
