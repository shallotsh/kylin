package org.kylin.service.exporter.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.kylin.bean.p2.XCodeReq;
import org.kylin.bean.p5.WCode;
import org.kylin.service.exporter.AbstractDocumentExporter;
import org.kylin.util.CommonUtils;

import java.util.List;
import java.util.Objects;

public class XCode2DKillerDocExporter extends AbstractDocumentExporter<XCodeReq>{

    public XCode2DKillerDocExporter(XWPFDocument doc, XCodeReq data) {
        super(doc, data);
    }

    @Override
    public void writeStats() {

        XWPFParagraph header = doc.createParagraph();
        XWPFRun hr2 = header.createRun();

        hr2.setText(" 时间：" + CommonUtils.getCurrentDateString());
        hr2.setTextPosition(10);
        hr2.setFontSize(18);

        XWPFRun hr3 = header.createRun();
        hr3.setText(" ");
        hr3.addBreak();

    }

    @Override
    public void writeBody() {

        // ab*
        exportWCodes(doc, data.getwCodes(), "ab*", null, false, "ab*");

        // ba*
        exportWCodes(doc, data.getwCodes(), "ba*", null, false, "ba*");

        // *ab
        exportWCodes(doc, data.getwCodes(), "*ab", null, false, "*ab");

        // *ba
        exportWCodes(doc, data.getwCodes(), "*ba", null, false, "*ba");

        // a*b
        exportWCodes(doc, data.getwCodes(), "a*b", null, false, "a*b");

        // b*a
        exportWCodes(doc, data.getwCodes(), "b*a", null, false, "b*a");



    }


    public  void exportWCodes(XWPFDocument doc, List<WCode> wCodes, String titleString, String separator, Boolean freqSeted, String pattern){

        if(CollectionUtils.isEmpty(wCodes)){
            return;
        }

        XWPFParagraph paragraph = doc.createParagraph();
        if(!StringUtils.isBlank(titleString)){
            XWPFRun title = paragraph.createRun();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            title.setFontSize(18);
            title.setBold(true);
            title.setText(toUTF8(titleString));
            title.addBreak();
        }

        XWPFRun hr = paragraph.createRun();
        hr.setFontSize(10);
        hr.setText("----------------------------------------");
        hr.addBreak();

        XWPFRun content = paragraph.createRun();
        content.setFontSize(14);

        for(WCode code : wCodes) {

            if("ab*".equals(pattern)) {
                content.setText( ""+code.getCodes().get(0)+code.getCodes().get(1)+"*"+ "     ");
            }else if("ba*".equals(pattern)){
                content.setText( ""+code.getCodes().get(1)+code.getCodes().get(0)+"*"+ "     ");
            }else if("*ab".equals(pattern)){
                content.setText( ""+"*"+code.getCodes().get(0)+code.getCodes().get(1)+ "     ");
            }else if("*ba".equals(pattern)){
                content.setText( ""+code.getCodes().get(1)+code.getCodes().get(0)+"*"+ "     ");
            }else if("a*b".equals(pattern)){
                content.setText( ""+code.getCodes().get(0)+"*"+code.getCodes().get(1)+ "     ");
            }else if("b*a".equals(pattern)){
                content.setText( ""+code.getCodes().get(1)+"*"+code.getCodes().get(0)+ "     ");
            }

        }

        content.addBreak();
        content.setTextPosition(20);

        XWPFRun sep = paragraph.createRun();
        sep.setTextPosition(50);
    }
}