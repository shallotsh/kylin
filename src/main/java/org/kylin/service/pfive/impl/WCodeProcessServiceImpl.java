package org.kylin.service.pfive.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.kylin.algorithm.strategy.SequenceProcessor;
import org.kylin.algorithm.strategy.Strategy;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;
import org.kylin.bean.p5.WCodeSummarise;
import org.kylin.constant.ExportPatternEnum;
import org.kylin.constant.FilterStrategyEnum;
import org.kylin.factory.StrategyFactory;
import org.kylin.service.pfive.WCodeProcessService;
import org.kylin.util.DocUtils;
import org.kylin.util.WCodeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class WCodeProcessServiceImpl implements WCodeProcessService{


    @Resource
    private List<Strategy< List<WCode>, WCodeReq>> bitStrategies;

    @Override
    public Optional<WCodeSummarise> sequenceProcess(WCodeReq wCodeReq) {
        if(wCodeReq == null){
            return Optional.empty();
        }
        FilterStrategyEnum filterStrategyEnum = FilterStrategyEnum.getById(wCodeReq.getFilterType());
        if(filterStrategyEnum == null){
            return Optional.empty();
        }

        SequenceProcessor sequenceProcessor = StrategyFactory.createProcessor(filterStrategyEnum);
        if(sequenceProcessor == null){
            return Optional.empty();
        }

        List<WCode> deletedCodes = new ArrayList<>();

        sequenceProcessor.init(wCodeReq);
        List<WCode> wCodes = sequenceProcessor.process(deletedCodes);

        Collections.sort(wCodes);

        if(filterStrategyEnum == FilterStrategyEnum.RANDOM_FILTER){
            WCodeUtils.plusFreq(wCodes);
        }

        WCodeSummarise wCodeSummarise = WCodeUtils.construct(wCodes, filterStrategyEnum.getKey(), deletedCodes, wCodeReq);

        return Optional.of(wCodeSummarise);
    }


    @Override
    public Optional<WCodeSummarise> bitsProcess(WCodeReq wCodeReq) {
        if(wCodeReq == null){
            return Optional.empty();
        }

        List<WCode> wCodes = wCodeReq.getWCodes();
        for(Strategy< List<WCode>, WCodeReq> strategy: bitStrategies) {
            if(strategy.shouldExecute(wCodeReq)) {
                wCodes = strategy.execute(wCodeReq, wCodes);
            }
        }

        Collections.sort(wCodes);

        return Optional.of(WCodeUtils.construct(wCodes,null, null, wCodeReq));
    }


    @Override
    public Optional<String> exportWCodeToFile(WCodeReq wCodeReq) {

        Optional<ExportPatternEnum> ep = ExportPatternEnum.getById(wCodeReq.getExportFormat());
        if(!ep.isPresent()){
            return Optional.empty();
        }

        try {
            ExportPatternEnum patternEnum = ep.get();
            if(patternEnum == ExportPatternEnum.NORMAL){
                return Optional.of(DocUtils.saveWCodes(wCodeReq));
            }else if(patternEnum == ExportPatternEnum.HALF_PAGE){
                return Optional.of(DocUtils.saveWCodesHalf(wCodeReq));
            }
        } catch (IOException e) {
            log.info("导出文件错误", e);
            return Optional.empty();
        }

        // 策略导出
        WCodeKillerDocumentExporter exporter = new WCodeKillerDocumentExporter(new XWPFDocument(), wCodeReq);
        try {
            exporter.init();
            exporter.writeDefaultDocHeader();
            exporter.writeBody();
            String fileName = exporter.exportCodes();
            return Optional.of(fileName);
        } catch (IOException e) {
            log.warn("导出文件错误", e);
            return Optional.empty();
        } catch (RuntimeException re){
            log.warn("导出文件错误", re);
            return Optional.empty();
        }
    }
}
