package org.kylin.service.xcode;

import org.kylin.bean.p2.XCodeReq;
import org.kylin.bean.p5.WCode;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface XCodeService {

    /**
     * 二码洗牌预测排列码
     *
     * @param riddles
     * @return
     */
    List<WCode> quibinaryEncode(List<Set<Integer>> riddles);

    /**
     * 杀码
     *
     * @param req
     * @return
     */
    List<WCode> killCodes(XCodeReq req);

    /**
     * 综合选码
     *
     * @param req
     * @return
     */
    List<WCode> compSelectCodes(XCodeReq req);


    /**
     * 生成导出文件并返回文件路径
     *
     * @param xCodeReq
     * @return
     */
    Optional<String> exportWCodeToFile(XCodeReq xCodeReq);


}
