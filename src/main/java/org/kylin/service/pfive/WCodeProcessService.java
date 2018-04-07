package org.kylin.service.pfive;

import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;

import java.util.List;

public interface WCodeProcessService {

    List<WCode> sequenceProcess(WCodeReq wCodeReq);

    List<WCode> bitsProcess(WCodeReq wCodeReq);

}
