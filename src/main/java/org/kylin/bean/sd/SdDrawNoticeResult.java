package org.kylin.bean.sd;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class SdDrawNoticeResult {

    private Integer state;
    private String message;
    private Integer pageCount;
    private List<SdDrawOverview> result;

    public boolean isSuccess(){

        if(Objects.isNull(this.getState())
                || this.getState() != 0){
            return false;
        }

        return true;
    }

}
