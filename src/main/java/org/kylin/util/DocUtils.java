package org.kylin.util;


import java.io.*;
import java.text.Format;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import freemarker.template.utility.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.BreakClear;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.LineSpacingRule;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.kylin.bean.W3DCode;
import org.kylin.bean.WelfareCode;
import org.kylin.bean.p5.WCode;
import org.kylin.bean.p5.WCodeReq;
import org.kylin.constant.ClassifyEnum;
import org.kylin.constant.CodeTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author huangyawu
 * @date 2017/7/16 下午3:57.
 * ref: http://svn.apache.org/repos/asf/poi/trunk/src/examples/src/org/apache/poi/xwpf/usermodel/examples/SimpleDocument.java
 *      https://poi.apache.org/document/quick-guide-xwpf.html
 */
public class DocUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocUtils.class);

    private static final String BASE_PATH = "/var/attachment/";


    public static void saveW3DCodes(WelfareCode welfareCode, OutputStream outputStream) throws IOException{
        if(welfareCode == null || CollectionUtils.isEmpty(welfareCode.getW3DCodes())){
            throw new IllegalArgumentException("参数错误");
        }

        XWPFDocument doc = new XWPFDocument();

        XWPFParagraph header = doc.createParagraph();
        header.setVerticalAlignment(TextAlignment.TOP);
        header.setWordWrap(true);
        header.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun hr1 = header.createRun();
        hr1.setText(toUTF8("《我要发·518》福彩3D预测报表"));
        hr1.setBold(true);
        hr1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
        hr1.setTextPosition(20);
        hr1.setFontSize(28);
        hr1.addBreak();

        XWPFRun hr2 = header.createRun();

        hr2.setText(toUTF8("共计" + welfareCode.getW3DCodes().size() + "注3D码!!!     时间："
                + CommonUtils.getCurrentDateString() + " 编码方式:" + welfareCode.getCodeTypeEnum().getDesc()));
        hr2.setTextPosition(10);
        hr2.setFontSize(18);


        List<W3DCode> pairCodes = TransferUtil.getPairCodes(welfareCode.getW3DCodes());
        String title = String.format("对子共计 %d 注", pairCodes.size());
        writeCodes(doc.createParagraph(), pairCodes, toUTF8(title));

        List<W3DCode> nonPairCodes = TransferUtil.getNonPairCodes(welfareCode.getW3DCodes());
        title = String.format("对子共计 %d 注", nonPairCodes.size());
        writeCodes(doc.createParagraph(), nonPairCodes, toUTF8(title));

        doc.write(outputStream);

    }

    public static String saveW3DCodes(WelfareCode welfareCode) throws IOException{
        if(welfareCode == null || CollectionUtils.isEmpty(welfareCode.getW3DCodes())){
            return "";
        }

        String fileName = CommonUtils.getCurrentTimeString();
        String subDirectory = fileName.substring(0,6);
        String targetDirName = BASE_PATH + subDirectory;

        if(!createDirIfNotExist(targetDirName)){
            LOGGER.info("save-w3dCodes-create-directory-error targetDirName={}", targetDirName);
            throw new IOException("directory create error");
        }

        XWPFDocument doc = new XWPFDocument();

        XWPFParagraph header = doc.createParagraph();
        header.setVerticalAlignment(TextAlignment.TOP);
        header.setWordWrap(true);
        header.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun hr1 = header.createRun();
        hr1.setText(toUTF8("《我要发·518》福彩3D预测报表"));
        hr1.setBold(true);
        hr1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
        hr1.setTextPosition(20);
        hr1.setFontSize(28);
        hr1.addBreak();

        XWPFRun hr2 = header.createRun();

        hr2.setText(toUTF8("共计" + welfareCode.getW3DCodes().size() + "注3D码!!!     时间："
                + CommonUtils.getCurrentDateString() + " 编码方式:" + welfareCode.getCodeTypeEnum().getDesc()));
        hr2.setTextPosition(10);
        hr2.setFontSize(18);

        if(welfareCode.getW3DCodes().get(0).getClassify() != 0){
            exportOneKeyStrategy(doc, welfareCode);
        }else{
            exportNormal(doc, welfareCode);
        }




        // 保存
        StringBuilder sb = new StringBuilder();
        sb.append(targetDirName);
        sb.append(File.separator);
        sb.append(fileName);
        sb.append(".docx");
        FileOutputStream out = new FileOutputStream(sb.toString());
        doc.write(out);
        out.close();

        return fileName + ".docx";
    }

    private static void exportNormal(XWPFDocument doc, WelfareCode welfareCode){
        List<W3DCode> w3DCodes = welfareCode.sort(WelfareCode::tailSort).generate().getW3DCodes();
        List<W3DCode> repeatCodes = TransferUtil.findAllRepeatW3DCodes(w3DCodes);

        List<W3DCode> nonRepeatCodes = Encoders.minus(w3DCodes, repeatCodes, CodeTypeEnum.DIRECT);

        List<W3DCode> pairCodes = TransferUtil.getPairCodes(nonRepeatCodes);
        String title = String.format("对子不重叠部分 %d 注", pairCodes.size());
        writeCodes(doc.createParagraph(), pairCodes, toUTF8(title));

        List<W3DCode> repeatPairCodes = TransferUtil.getPairCodes(repeatCodes);
        title = String.format("对子重叠部分 %d 注", CollectionUtils.size(repeatPairCodes));
        writeCodes(doc.createParagraph(), repeatPairCodes, toUTF8(title));

        List<W3DCode> nonPairCodes = TransferUtil.getNonPairCodes(nonRepeatCodes);
        title = String.format("非对子不重叠共计 %d 注", nonPairCodes.size());
        writeCodes(doc.createParagraph(), nonPairCodes, toUTF8(title));

        List<W3DCode> repeatNonPairCodes = TransferUtil.getNonPairCodes(repeatCodes);
        title = String.format("非对子重叠部分 %d 注", CollectionUtils.size(repeatNonPairCodes));
        writeCodes(doc.createParagraph(), repeatNonPairCodes, toUTF8(title));

        if(welfareCode.getCodeTypeEnum() == CodeTypeEnum.DIRECT) {

            List<W3DCode> randomTenCodes = WyfCollectionUtils.getRandomList(nonPairCodes, 10);
            title = String.format("直选非对子随机 10 注", nonPairCodes.size());
            writeCodes(doc.createParagraph(), randomTenCodes, toUTF8(title));

            List<W3DCode> randomFiveCodes = WyfCollectionUtils.getRandomList(nonPairCodes, 5);
            title = String.format("直选非对子随机 5 注", nonPairCodes.size());
            writeCodes(doc.createParagraph(), randomFiveCodes, toUTF8(title));
        }


        // 输出组选
        if(CodeTypeEnum.DIRECT.equals(welfareCode.getCodeTypeEnum())){

            XWPFRun hr = doc.createParagraph().createRun();
            hr.setFontSize(10);
            hr.setText("----------------------------------------------------------------------");
            hr.addBreak();

            List<W3DCode> groupRepeatPairCodes = TransferUtil.grouplize(repeatPairCodes);
            title = String.format("对子重叠部分（组选） %d 注", CollectionUtils.size(groupRepeatPairCodes));
            writeCodes(doc.createParagraph(), groupRepeatPairCodes, toUTF8(title));

            List<W3DCode> groupRepeatNonPairCodes = TransferUtil.grouplize(repeatNonPairCodes);
            title = String.format("非对子重叠部分 (组选) %d 注", CollectionUtils.size(groupRepeatNonPairCodes));
            writeCodes(doc.createParagraph(), groupRepeatNonPairCodes, toUTF8(title));
        }

    }

    private static void exportOneKeyStrategy(XWPFDocument doc, WelfareCode welfareCode){
        List<W3DCode> w3DCodes = welfareCode.sort(WelfareCode::tailSort).generate().getW3DCodes();

//        List<W3DCode> pairCodes = w3DCodes.stream().filter(w3DCode -> ClassifyEnum.PAIR_UNDERLAP.getIndex() == w3DCode.getClassify()).collect(Collectors.toList());
//        String title = String.format("对子不重叠部分 %d 注", pairCodes.size());
//        writeCodes(doc.createParagraph(), pairCodes, toUTF8(title));

        List<W3DCode> repeatPairCodes = w3DCodes.stream().filter(w3DCode -> ClassifyEnum.PAIR_OVERLAP.getIndex() == w3DCode.getClassify()).collect(Collectors.toList());
        String title = String.format("对子重叠部分 %d 注", CollectionUtils.size(repeatPairCodes));
        writeCodes(doc.createParagraph(), repeatPairCodes, toUTF8(title));

//        List<W3DCode> nonPairCodes = w3DCodes.stream().filter(w3DCode -> ClassifyEnum.NON_PAIR_UNDERLAP.getIndex() == w3DCode.getClassify()).collect(Collectors.toList());
//        title = String.format("非对子不重叠共计 %d 注", nonPairCodes.size());
//        writeCodes(doc.createParagraph(), nonPairCodes, toUTF8(title));

        List<W3DCode> repeatNonPairCodes = w3DCodes.stream().filter(w3DCode -> ClassifyEnum.NON_PAIR_OVERLAP.getIndex() == w3DCode.getClassify()).collect(Collectors.toList());
        title = String.format("非对子重叠部分 %d 注", CollectionUtils.size(repeatNonPairCodes));
        writeCodes(doc.createParagraph(), repeatNonPairCodes, toUTF8(title));


        // 输出组选
        if(CodeTypeEnum.DIRECT.equals(welfareCode.getCodeTypeEnum())){

            XWPFRun hr = doc.createParagraph().createRun();
            hr.setFontSize(12);
            hr.setText("-------------------------------组选部分-----------------------------------");
            hr.addBreak();

            List<W3DCode> groupRepeatPairCodes = TransferUtil.grouplize(repeatPairCodes);
            title = String.format("对子重叠部分（组选） %d 注", CollectionUtils.size(groupRepeatPairCodes));
            writeCodes(doc.createParagraph(), groupRepeatPairCodes, toUTF8(title));

            List<W3DCode> groupRepeatNonPairCodes = TransferUtil.grouplize(repeatNonPairCodes);
            title = String.format("非对子重叠部分 (组选) %d 注", CollectionUtils.size(groupRepeatNonPairCodes));
            writeCodes(doc.createParagraph(), groupRepeatNonPairCodes, toUTF8(title));
        }


    }


    public static String saveWCodes(WCodeReq wCodeReq) throws IOException {
        if(wCodeReq == null || CollectionUtils.isEmpty(wCodeReq.getwCodes())){
            return "";
        }

        String fileName = CommonUtils.getCurrentTimeString();
        String subDirectory = fileName.substring(0,6);
        String targetDirName = BASE_PATH + subDirectory;

        if(!createDirIfNotExist(targetDirName)){
            LOGGER.info("save-wCodes-create-directory-error targetDirName={}", targetDirName);
            throw new IOException("directory create error");
        }

        XWPFDocument doc = new XWPFDocument();

        XWPFParagraph header = doc.createParagraph();
        header.setVerticalAlignment(TextAlignment.TOP);
        header.setWordWrap(true);
        header.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun hr1 = header.createRun();
        hr1.setText(toUTF8("《我要发·排列5》福彩3D预测报表"));
        hr1.setBold(true);
        hr1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
        hr1.setTextPosition(20);
        hr1.setFontSize(28);
        hr1.addBreak();

        XWPFRun hr2 = header.createRun();

        hr2.setText(toUTF8("共计" + wCodeReq.getwCodes().size() + "注排列5码!!!     时间："
                + CommonUtils.getCurrentDateString() ));
        hr2.setTextPosition(10);
        hr2.setFontSize(18);

        int randPairCount = 0;
        int randomTenCodes = 0;

        List<WCode> nonPairCodes = WCodeUtils.filterNonPairCodes(wCodeReq.getwCodes());
        if(!CollectionUtils.isEmpty(nonPairCodes)){
            Collections.sort(nonPairCodes);
            String titleString = String.format("排列5码·非对子( %d 注)", nonPairCodes.size());
            exportWCodes(doc, nonPairCodes, titleString);
            randomTenCodes = nonPairCodes.size() > 10? 10 : nonPairCodes.size();
        }

        XWPFRun hr3 = header.createRun();
        hr3.setText(" ");
        hr3.addBreak();

        List<WCode> nonPairRandomTenCodes = WyfCollectionUtils.getRandomList(nonPairCodes, randomTenCodes);
        List<WCode> nonPairRandFiveCodes = WyfCollectionUtils.getRandomList(nonPairCodes, 5);
        List<WCode> nonPairRand200Codes = WyfCollectionUtils.getRandomList(nonPairCodes, 200);
        List<WCode> nonPairRand400Codes = WyfCollectionUtils.getRandomList(nonPairCodes, 400);

        if(!CollectionUtils.isEmpty(nonPairRandomTenCodes)){
            Collections.sort(nonPairRandomTenCodes);
            String titleString = String.format("排列10码随机·非对子( %d 注)", nonPairRandomTenCodes.size());
            exportWCodes(doc, nonPairRandomTenCodes, titleString);
        }

        if(!CollectionUtils.isEmpty(nonPairRandFiveCodes)){
            Collections.sort(nonPairRandFiveCodes);
            String titleString = String.format("排列5码随机·非对子( %d 注)", nonPairRandFiveCodes.size());
            exportWCodes(doc, nonPairRandFiveCodes, titleString);
        }


        if(!CollectionUtils.isEmpty(nonPairRand200Codes)){
            Collections.sort(nonPairRand200Codes);
            String titleString = String.format("排列5码随机·非对子( %d 注)", nonPairRand200Codes.size());
            exportWCodes(doc, nonPairRand200Codes, titleString);
        }

        if(!CollectionUtils.isEmpty(nonPairRand400Codes)){
            Collections.sort(nonPairRand400Codes);
            String titleString = String.format("排列5码随机·非对子( %d 注)", nonPairRand400Codes.size());
            exportWCodes(doc, nonPairRand400Codes, titleString);
        }


        List<WCode> pairCodes = WCodeUtils.filterPairCodes(wCodeReq.getwCodes());
        if(!CollectionUtils.isEmpty(pairCodes)){
            Collections.sort(pairCodes);
            String titleString = String.format("排列5码·对子( %d 注)", pairCodes.size());
            exportWCodes(doc, pairCodes, titleString);
            randPairCount = pairCodes.size() > 10? 10: pairCodes.size();
        }

        // 保存
        StringBuilder sb = new StringBuilder();
        sb.append(targetDirName);
        sb.append(File.separator);
        sb.append(fileName);
        sb.append(".docx");
        FileOutputStream out = new FileOutputStream(sb.toString());
        doc.write(out);
        out.close();

        LOGGER.info("导出文件名: {}", sb.toString());

        return fileName + ".docx";
    }

    private static void exportWCodes(XWPFDocument doc, List<WCode> wCodes, String titleString){

        if(CollectionUtils.isEmpty(wCodes)){
            return;
        }

        XWPFParagraph paragraph = doc.createParagraph();
        if(!StringUtils.isBlank(titleString)){
            XWPFRun title = paragraph.createRun();
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


        for(WCode w3DCode : wCodes) {
            content.setText(w3DCode.getString() + "     ");
        }

        content.addBreak();
        content.setTextPosition(20);

        XWPFRun sep = paragraph.createRun();
        sep.setTextPosition(50);
    }


    private static void exportWCodesArray(XWPFDocument doc, List<List<WCode>> wCodes, String titleString){

        if(CollectionUtils.isEmpty(wCodes)){
            return;
        }

        XWPFParagraph paragraph = doc.createParagraph();
        if(!StringUtils.isBlank(titleString)){
            XWPFRun title = paragraph.createRun();
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


        for(int i=0; i < wCodes.size(); i++) {
            List<WCode> wCodeList = wCodes.get(i);
            Collections.sort(wCodeList);
            content.setText(toUTF8("第 " + (i+1) + " 组: "));
            for (WCode w3DCode : wCodeList) {
                content.setText(w3DCode.getString() + "     ");
            }
            content.addBreak();
        }

        content.addBreak();
        content.setTextPosition(20);

        XWPFRun sep = paragraph.createRun();
        sep.setTextPosition(50);
    }

    public static String saveWCodesHalf(WCodeReq wCodeReq) throws IOException {
        if(wCodeReq == null || CollectionUtils.isEmpty(wCodeReq.getwCodes())){
            return "";
        }

        String fileName = CommonUtils.getCurrentTimeString();
        String subDirectory = fileName.substring(0,6);
        String targetDirName = BASE_PATH + subDirectory;

        if(!createDirIfNotExist(targetDirName)){
            LOGGER.info("save-wCodes-create-directory-error targetDirName={}", targetDirName);
            throw new IOException("directory create error");
        }

        XWPFDocument doc = new XWPFDocument();

        XWPFParagraph header = doc.createParagraph();
        header.setVerticalAlignment(TextAlignment.TOP);
        header.setWordWrap(true);
        header.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun hr1 = header.createRun();
        hr1.setText(toUTF8("《我要发·排列5》福彩3D预测报表(半页)"));
        hr1.setBold(true);
        hr1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
        hr1.setTextPosition(20);
        hr1.setFontSize(26);
        hr1.addBreak();

        XWPFRun hr2 = header.createRun();

        hr2.setText(toUTF8("共计" + wCodeReq.getwCodes().size() + "注排列5码!!!     时间："
                + CommonUtils.getCurrentDateString() ));
        hr2.setTextPosition(10);
        hr2.setFontSize(16);

        List<WCode> nonPairCodes = WCodeUtils.filterNonPairCodes(wCodeReq.getwCodes());

        List<WCode> halfPageCodes = WyfCollectionUtils.getSubList(nonPairCodes, 8, 4);
        if(!CollectionUtils.isEmpty(halfPageCodes)){
            Collections.sort(halfPageCodes);
            String titleString = String.format("排列5码·半页码(非对子 %d 注)", halfPageCodes.size());
            exportWCodes(doc, halfPageCodes, titleString);
        }

        // 保存
        String prefix = "Half-";
        StringBuilder sb = new StringBuilder();
        sb.append(targetDirName);
        sb.append(File.separator);
        sb.append(prefix);
        sb.append(fileName);
        sb.append(".docx");
        FileOutputStream out = new FileOutputStream(sb.toString());
        doc.write(out);
        out.close();

        LOGGER.info("导出文件名: {}", sb.toString());

        return  prefix + fileName + ".docx";
    }



    public static void writeCodes(XWPFParagraph paragraph, List<W3DCode> w3DCodes, String titleString){
        if(paragraph == null || CollectionUtils.isEmpty(w3DCodes)){
            return;
        }

        XWPFRun title = paragraph.createRun();
        title.setFontSize(18);
        title.setBold(true);
        title.setText(titleString);
        title.addBreak();

        XWPFRun hr = paragraph.createRun();
        hr.setFontSize(10);
        hr.setText("----------------------------------------");
        hr.addBreak();

        XWPFRun content = paragraph.createRun();
        content.setFontSize(14);


        for(W3DCode w3DCode : w3DCodes) {
            content.setText(w3DCode.toString() + "     ");
        }

        content.addBreak();
        content.setTextPosition(20);

        XWPFRun sep = paragraph.createRun();
        sep.setTextPosition(50);
    }

    public static void writeTitle(XWPFParagraph paragraph, String titleString){
        paragraph.setPageBreak(true);
        XWPFRun title = paragraph.createRun();
        title.setBold(true);
        title.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
        title.setFontSize(24);
        title.setBold(true);
        title.setText(titleString);
        title.addBreak();
    }


    public static boolean createDirIfNotExist(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            return true;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }

        //创建目录
        if (dir.mkdirs()) {
            LOGGER.info("创建目录" + destDirName + "成功！");
            return true;
        } else {
            LOGGER.info("创建目录" + destDirName + "失败！");
            return false;
        }
    }
    public static String toUTF8(String str){
        if(StringUtils.isBlank(str)){
            return str;
        }
        LOGGER.info("target:{}", str);
        return str;
//        try {
//            return new String(str.getBytes(), "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            return str;
//        }
    }

    public static void main(String[] args) throws Exception {
        WelfareCode welfareCode = new WelfareCode();
        List<W3DCode> w3DCodes = new ArrayList<>();
        W3DCode w3DCode = new W3DCode(2,3,5);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(2,3,5);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(2,2,5);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(2,1,5);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(1,3,5);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(1,2,5);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(1,1,5);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(2,3,4);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(2,2,4);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(2,1,4);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(2,3,6);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(2,2,6);
        w3DCodes.add(w3DCode);
        w3DCode = new W3DCode(2,1,6);
        w3DCodes.add(w3DCode);
        welfareCode.setW3DCodes(w3DCodes);


        String filePath = saveW3DCodes(welfareCode);

        LOGGER.info("file saved!!! path:{}", filePath);
    }

}
