package org.kylin.service.pfive;

import org.kylin.bean.p5.WCodeReq;
import org.kylin.bean.p5.WCodeSummarise;

import java.util.Optional;

public interface WCodeProcessService {

    /**
     * 顺序杀
     *
     * @param wCodeReq
     * @return
     */
    Optional<WCodeSummarise> sequenceProcess(WCodeReq wCodeReq);

    /**
     * 位杀
     *
     * @param wCodeReq
     * @return
     */
    Optional<WCodeSummarise> bitsProcess(WCodeReq wCodeReq);

    /**
     * 生成导出文件并返回文件路径
     *
     * @param wCodeReq
     * @return
     */
    Optional<String> exportWCodeToFile(WCodeReq wCodeReq);

}
