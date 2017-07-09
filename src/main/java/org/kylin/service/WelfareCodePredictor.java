package org.kylin.service;

import org.kylin.bean.WelfareCode;
import org.kylin.constant.CodeTypeEnum;

import java.util.List;

/**
 * @author huangyawu
 * @date 2017/6/25 下午3:35.
 */
public interface WelfareCodePredictor {
    WelfareCode encode(List<String> riddles, CodeTypeEnum codeTypeEnum);
}
