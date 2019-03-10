package org.kylin.service.pfive.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;
import org.kylin.constant.ExportPatternEnum;
import org.kylin.constant.FilterStrategyEnum;
import org.kylin.service.pfive.AbstractDocumentExporter;
import org.kylin.util.CommonUtils;
import org.kylin.util.DocUtils;
import org.kylin.util.WCodeUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class WCodeKillerDocumentExporter extends AbstractDocumentExporter<WCodeReq>{

    private ExportPatternEnum exportPatternEnum;
    private FilterStrategyEnum filterStrategyEnum;
    private List<WCode> wCodes;

    public WCodeKillerDocumentExporter(XWPFDocument doc, WCodeReq data) {
        super(doc, data);
    }

    @Override
    public void init(){
        super.init();
        Optional<ExportPatternEnum> ep = ExportPatternEnum.getById(data.getExportFormat());
        if(!ep.isPresent() || Objects.isNull(data.getDeletedCodesPair())){
            throw new RuntimeException("导出类型错误或者数据为空");
        }
        exportPatternEnum = ep.get();
        filterStrategyEnum = FilterStrategyEnum.getById(exportPatternEnum.getId());
        wCodes = data.getDeletedCodesPair().get(filterStrategyEnum.getKey());
    }

    @Override
    public void writeStats() {

        assert(exportPatternEnum != null);
        assert(filterStrategyEnum != null);

        XWPFParagraph header = doc.createParagraph();
        XWPFRun hr2 = header.createRun();

        hr2.setText(toUTF8(filterStrategyEnum.getDesc() + " 共计" + CollectionUtils.size(wCodes)+ "注排列5码!!!     时间："
                + CommonUtils.getCurrentDateString() ));
        hr2.setTextPosition(10);
        hr2.setFontSize(18);

        XWPFRun hr3 = header.createRun();
        hr3.setText(" ");
        hr3.addBreak();
    }

    @Override
    public void writeBody() {

        if(CollectionUtils.isEmpty(wCodes)){
            return;
        }

        Collections.sort(wCodes);
        List<WCode> pairCodes = WCodeUtils.filterPairCodes(wCodes);
        List<WCode> nonPairCodes = WCodeUtils.filterNonPairCodes(wCodes);


        if(!CollectionUtils.isEmpty(pairCodes)) {
            String titleString = String.format(filterStrategyEnum.getDesc() + " 对子( %d 注)", pairCodes.size());
            DocUtils.exportWCodes(doc, pairCodes, titleString, null, data.getFreqSeted());
        }

        if(!CollectionUtils.isEmpty(nonPairCodes)) {
            String titleString = String.format(filterStrategyEnum.getDesc() + " 非对子( %d 注)", nonPairCodes.size());
            DocUtils.exportWCodes(doc, nonPairCodes, titleString, null, data.getFreqSeted());
        }
    }
}
