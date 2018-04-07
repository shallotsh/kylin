package org.kylin.algorithm.strategy;

import org.kylin.algorithm.Validator;
import org.kylin.bean.FilterParam;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;

import java.util.List;

public interface SequenceProcessor extends Validator {
    SequenceProcessor init(WCodeReq wCodeReq);
    List<WCode> process();
}
