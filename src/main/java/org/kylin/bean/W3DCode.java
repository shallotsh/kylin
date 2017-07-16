package org.kylin.bean;


/**
 * @author huangyawu
 * @date 2017/6/30 上午1:42.
 */
public class W3DCode {
    private Integer h;
    private Integer d;
    private Integer u;
    private Integer freq;
    private Integer sumTail;

    public W3DCode() {
    }

    public W3DCode(Integer h, Integer d, Integer u) {
        this.h = h;
        this.d = d;
        this.u = u;
        this.freq = 1;
        int sum = 0;
        if(this.h != null){
            sum += this.h;
        }

        if(this.d != null){
            sum += this.d;
        }

        if(this.u != null){
            sum += this.u;
        }
        this.sumTail = sum % 10;
    }

    public W3DCode(Integer d, Integer u) {
        this.d = d;
        this.u = u;

        this.freq = 1;

        int sum = 0;

        if(this.d != null){
            sum += this.d;
        }

        if(this.u != null){
            sum += this.u;
        }
        this.sumTail = sum % 10;
    }

    public Integer getH() {
        return h;
    }

    public void setH(Integer h) {
        this.h = h;
    }

    public Integer getD() {
        return d;
    }

    public void setD(Integer d) {
        this.d = d;
    }

    public Integer getU() {
        return u;
    }

    public void setU(Integer u) {
        this.u = u;
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
        if(this.getH() != null){
            sb.append(this.getH());
        }
        sb.append(this.getD());
        sb.append(this.getU());
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
        return w3DCode.getH() == this.getH() && w3DCode.getD() == this.getD() && w3DCode.getU() == this.getU();
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        if(getH() != null) {
            result = prime * result + getH();
        }
        if(getD() != null) {
            result = prime * result + getD();
        }

        if(getU() != null){
            result = prime * result + getU();
        }

        return result;
    }
}
