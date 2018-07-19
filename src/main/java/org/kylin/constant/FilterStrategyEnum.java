package org.kylin.constant;

public enum FilterStrategyEnum {
    LITTLE_SUM_FILTER(1, "小和过滤器"),
    BIG_SUM_FILTER(2, "大和过滤器"),
    ODD_EVEN_FILTER(3, "奇偶过滤器"),
    CONTAIN_THREE_FILTER(4, "含三过滤器"),
    CONTAIN_FOUR_FILTER(5, "含四过滤器"),
    CONTAIN_FIVE_FILTER(6, "含五过滤器"),
    EXTREMA_FILTER(7, "极值过滤器,全大全小过滤"),
    UNORDERED_FISH_MAN_FILTER(8, "无序钓叟"),
    TAIL_THREE_FILTER(9, "尾三杀码"),
    ORDERED_FISH_MAN_FILTER(10, "有序钓叟"),
    NON_REPEAT_FILTER(11, "非重码杀"),
    SINK_FILTER(12, "下沉比对"),
    SUM_TAIL_FILTER(13, "和值尾"),
    RANDOM_FILTER(14, "随机杀"),
    SUM_FILTER(15, "和值杀"),
    ;
    private Integer id;
    private String desc;

    private FilterStrategyEnum(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public static FilterStrategyEnum getById(Integer id){
        if(id == null){
            return null;
        }

        for(FilterStrategyEnum e : FilterStrategyEnum.values()){
            if(e.getId() == id){
                return e;
            }
        }

        return null;
    }
}
