package org.kylin.bean.p2;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.kylin.bean.p5.WCode;

import java.util.List;

@Slf4j
@Data
public class XCodeReq {

    private List<String> sequences;
    private String boldCodeSeq;
    private String inverseCodeSeq;
    private String gossipCodeSeq;
    private List<WCode> wCodes;
    private Boolean freqSeted;

}
