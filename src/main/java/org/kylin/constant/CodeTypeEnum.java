package org.kylin.constant;

/**
 * @author huangyawu
 * @date 2017/6/25 下午3:15.
 */
public enum CodeTypeEnum {
    GROUP(1, "组选"),
    DIRECT(2, "直选"),
    BINARY_CODE(3, "二码");
    private Integer id;
    private String desc;

    private CodeTypeEnum(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public static CodeTypeEnum getById(Integer id){

        for(CodeTypeEnum codeType : CodeTypeEnum.values()){
            if(codeType.getId().equals(id)){
                return codeType;
            }
        }

        return null;
    }
}
