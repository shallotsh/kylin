package org.kylin.util;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
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

        if(welfareCode.getRandomKilled() != null && welfareCode.getRandomKilled()){
            // 随机杀导出
            exportRandom(doc, welfareCode);
        }else if(welfareCode.getW3DCodes().get(0).getClassify() != 0){
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

    private static void exportRandom(XWPFDocument doc, WelfareCode welfareCode){
        List<W3DCode> pairCodes = TransferUtil.getPairCodes(welfareCode.getW3DCodes());
        String title = String.format("对子 %d 注", pairCodes.size());
//        exportW3DCodeRandomByType(pairCodes, doc, title);
        writeCodes(doc.createParagraph(), pairCodes, title);

        List<W3DCode> nonPairCodes = TransferUtil.getNonPairCodes(welfareCode.getW3DCodes());
        title = String.format("非对子 %d 注", nonPairCodes.size());
//        exportW3DCodeRandomByType(nonPairCodes, doc, title);
        writeCodes(doc.createParagraph(), nonPairCodes, title);
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

        List<W3DCode> repeatPairCodes = w3DCodes.stream().filter(w3DCode -> ClassifyEnum.PAIR_OVERLAP.getIndex() == w3DCode.getClassify()).collect(Collectors.toList());
        String title = String.format("对子重叠部分 %d 注", CollectionUtils.size(repeatPairCodes));
        writeCodes(doc.createParagraph(), repeatPairCodes, toUTF8(title));

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

        XWPFRun hr3 = header.createRun();
        hr3.setText(" ");
        hr3.addBreak();

        if(wCodeReq.isRandomKill()){
            randomExport(wCodeReq, doc);
        }else{
            normalExport(wCodeReq, doc);
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


    private static void randomExport(WCodeReq wCodeReq, XWPFDocument doc){
        if(wCodeReq == null || CollectionUtils.isEmpty(wCodeReq.getwCodes()) || doc == null){
            return;
        }

        List<WCode> pairCodes = WCodeUtils.filterPairCodes(wCodeReq.getwCodes());
        String title = "对子( " + CollectionUtils.size(pairCodes) +" 注)";
        exportRandomByType(pairCodes, doc, title);

        List<WCode> nonPairCodes = WCodeUtils.filterNonPairCodes(wCodeReq.getwCodes());
        title = "非对子( " + CollectionUtils.size(nonPairCodes) +" 注)";
        exportRandomByType(nonPairCodes, doc, title);


    }

    private static void exportRandomByType(List<WCode> wCodes, XWPFDocument doc, String title){
        if(CollectionUtils.isEmpty(wCodes) || doc == null){
            return;
        }
        int highestFreq = WCodeUtils.getHighestFreq(wCodes);

        XWPFParagraph header = doc.createParagraph();
        header.setVerticalAlignment(TextAlignment.TOP);
        header.setWordWrap(true);
        header.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun hr1 = header.createRun();
        hr1.setText(toUTF8(title));
        hr1.setBold(true);
        hr1.setFontSize(20);
        hr1.addBreak();

        for(int i=2; i <= highestFreq; i++){
            int freq = i;
            List<WCode> exportRandomCodes = wCodes.stream().filter(wCode -> wCode.getFreq() == freq).collect(Collectors.toList());
            String exportTitle = "频度" + freq + "(注数" + CollectionUtils.size(exportRandomCodes) + ")";
            exportWCodes(doc, exportRandomCodes, exportTitle, null);
        }

        XWPFParagraph headerEnd = doc.createParagraph();
        header.setVerticalAlignment(TextAlignment.TOP);
        header.setWordWrap(true);
        header.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun hrEnd = headerEnd.createRun();
        hrEnd.setBold(true);
        hrEnd.setFontSize(20);
        hrEnd.addBreak();

    }



    private static void normalExport(WCodeReq wCodeReq, XWPFDocument doc){

        List<WCode> nonPairCodes = WCodeUtils.filterNonPairCodes(wCodeReq.getwCodes());

        int customRandomCount = 200;
        if(StringUtils.isNumeric(wCodeReq.getRandomCount()) && NumberUtils.toInt(wCodeReq.getRandomCount()) > 0
                && NumberUtils.toInt(wCodeReq.getRandomCount()) < 1000 ){
            customRandomCount = NumberUtils.toInt(wCodeReq.getRandomCount());
        }

        List<WCode> nonPairRandomTenCodes = WyfCollectionUtils.getRandomList(nonPairCodes, 10);
        List<WCode> nonPairRandFiveCodes = WyfCollectionUtils.getRandomList(nonPairCodes, 5);
        List<WCode> nonPairRand200Codes = WyfCollectionUtils.getRandomList(nonPairCodes, customRandomCount);

        String separator = "※※※";

        if(!CollectionUtils.isEmpty(nonPairRandomTenCodes)){
            Collections.sort(nonPairRandomTenCodes);
            String titleString = String.format("排列5码随机·非对子( %d 注)", nonPairRandomTenCodes.size());
            exportWCodes(doc, nonPairRandomTenCodes, titleString, null);
        }

        if(!CollectionUtils.isEmpty(nonPairRandFiveCodes)){
            Collections.sort(nonPairRandFiveCodes);
            String titleString = String.format("排列5码随机·非对子( %d 注)", nonPairRandFiveCodes.size());
            exportWCodes(doc, nonPairRandFiveCodes, titleString, null);
        }


        if(!CollectionUtils.isEmpty(nonPairRand200Codes) && nonPairRand200Codes.size() < CollectionUtils.size(nonPairCodes)){
            Collections.sort(nonPairRand200Codes);
            String titleString = String.format("排列5码随机·非对子( %d 注)", nonPairRand200Codes.size());
            exportWCodes(doc, nonPairRand200Codes, titleString, null);
        }

        List<WCode> pairCodes = WCodeUtils.filterPairCodes(wCodeReq.getwCodes());
        if(!CollectionUtils.isEmpty(pairCodes)){
            Collections.sort(pairCodes);
            String titleString = String.format("排列5码·对子( %d 注)", pairCodes.size());
            exportWCodes(doc, pairCodes, titleString, separator);
        }

        if(!CollectionUtils.isEmpty(nonPairCodes)){
            Collections.sort(nonPairCodes);
            String titleString = String.format("排列5码·非对子( %d 注)", nonPairCodes.size());
            exportWCodes(doc, nonPairCodes, titleString, separator);
        }
    }


    private static void exportWCodes(XWPFDocument doc, List<WCode> wCodes, String titleString, String separator){

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

        boolean hasSeparotor = StringUtils.isNotBlank(separator);
        if(hasSeparotor){
            wCodes.sort(Comparator.comparing(WCode::sum));
        }

        int preSum = wCodes.get(0).sum();
        for(WCode w3DCode : wCodes) {
            int currentSum = w3DCode.sum();
            if(hasSeparotor && currentSum != preSum){
                content.setText("(" + preSum + ")" + separator + "(" + currentSum + ")     ");
                preSum = currentSum;
            }
            content.setText(w3DCode.getString() + "     ");
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
            exportWCodes(doc, halfPageCodes, titleString, null);
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

        if(StringUtils.isNotBlank(titleString)) {
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun title = paragraph.createRun();
            title.setFontSize(18);
            title.setBold(true);
            title.setText(titleString);
            title.addBreak();
        }

        XWPFRun hr = paragraph.createRun();
        hr.setFontSize(10);
        hr.setText("----------------------------------------");
        hr.addBreak();

        XWPFRun content = paragraph.createRun();
        content.setFontSize(14);
        paragraph.setAlignment(ParagraphAlignment.LEFT);

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
