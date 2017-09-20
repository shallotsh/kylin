package org.kylin.constant;

/**
 * @author huangyawu
 * @date 2017/9/20 上午12:11.
 */
public enum ClassifyEnum {
    PAIR_UNDERLAP(1, "对子非重叠部分"),
    PAIR_OVERLAP(2, "对子重叠部分"),
    NON_PAIR_UNDERLAP(3, "非对子不重叠部分"),
    NON_PAIR_OVERLAP(4, "非对子重叠部分");

    private int index;
    private String desc;

    private ClassifyEnum(int index, String desc) {
        this.index = index;
        this.desc = desc;
    }

    public int getIndex() {
        return index;
    }

    public String getDesc() {
        return desc;
    }

    public static ClassifyEnum getByIndex(int index){
        for(ClassifyEnum e : ClassifyEnum.values()){
            if(index == e.getIndex()){
                return e;
            }
        }

        return null;
    }
}

