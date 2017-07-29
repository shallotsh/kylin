package org.kylin.constant;

/**
 * @author huangyawu
 * @date 2017/7/29 下午2:21.
 */
public enum  ConstantsEnum {
    DEFAULT_FILTER_CODES("默认杀码序列", 1, "1234"),
    DEFAULT_RANGE_CODE("默认跨度", 2, "12789");

    private String name;
    private Integer id;
    private String data;

    private ConstantsEnum(String name, Integer id, String data) {
        this.name = name;
        this.id = id;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getData() {
        return data;
    }
}
