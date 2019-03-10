package org.kylin.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor
@Getter
public enum ExportPatternEnum {
    NORMAL(0, "常规导出"),
    BIG_SUM_KILL(2, "大和杀导出"),
    CONTAIN_FOUR_KILL(5, "含四杀导出"),
    CONTAIN_FIVE_KILL(6, "含五杀导出"),
    HALF_PAGE(100, "半页导出"),

    ;

    private int id;
    private String desc;

    public static Optional<ExportPatternEnum> getById(int id){
        for(ExportPatternEnum exportPatternEnum : ExportPatternEnum.values()){
            if(exportPatternEnum.getId() == id){
                return Optional.of(exportPatternEnum);
            }
        }

        return Optional.empty();
    }
}
